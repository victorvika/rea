# TradingView Android App - Reverse Engineering Report

**File:** `TradingView.apk` | Google Drive ID: `1OWjhjG4YW1KBFd7DMMii79QPw1Aj2FyF`
**Size:** 106.84 MB (106,849,651 bytes) | **SHA256:** `2a6501fbd012229277ab8c34bbbf1664f9dcad40550d38f4db31c9d755d0ba2f`
**Package:** `com.tradingview.tradingviewapp` | **Version:** `1.20.85.0.1002535` (versionCode 8 in base split - split APK, real code higher)
**Format:** APK (ZIP) | 4366 entries | REA artifact graph: 4145 nodes
**Signature:** v2 ✅ + v3 ✅ | v1 ❌ | Strongest: v3 | Self-consistent: true

---

## 1. Summary (Tamil la)

Idhu **TradingView official Android app** dhaan. Idhu oru **pure Kotlin + Jetpack Compose** app, native `.so` library edhuvum illa. Charting, alerts, brokers, news, widgets ellam Java/Kotlin la ezhuthirukanga. Flutter illa, React Native illa, Hermes illa.

Signing Google Play App Signing certificate (CN=Android, O=Google Inc, 2018 expiry 2048) - Play Store release dhaan.

**Safety:** v2/v3 signature correct-a irukku, digest matches APK bytes. Aana idhu **signature verification illa** - certificate-a yaarum create pannalam, Google chain verify pannala. So original Play Store file-nu 100% confirm panna koodadhu, aana Google cert use panrathu nala original maadhiri theriyuthu.

---

## 2. Technical Stack

| Layer          | Evidence                                                                                                                                                                   |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Language**   | Kotlin 100% - 42 kotlin_module META-INF, kotlinx-coroutines                                                                                                                |
| **UI**         | Jetpack Compose (material3, animation, foundation, ui-tooling, material-icons) + AndroidX AppCompat, ConstraintLayout                                                      |
| **DI**         | Dagger (`META-INF/com.google.dagger_dagger.version`)                                                                                                                       |
| **DB**         | Room (`androidx.room_room-ktx`), MultiInstanceInvalidationService                                                                                                          |
| **Network**    | OkHttp (native-image props), presumably Retrofit (not named in META-INF but common)                                                                                        |
| **Image**      | Picasso (`com.squareup.picasso`), Coil/Glide not detected                                                                                                                  |
| **Background** | WorkManager (SystemAlarmService, SystemJobService, DiagnosticsReceiver)                                                                                                    |
| **Analytics**  | Firebase (Messaging, Crashlytics, Perf, RemoteConfig, Installations), Google Analytics (AnalyticsService, AppMeasurement), Snowplow (`snowplowanalyticsservice` - 74 hits) |
| **Billing**    | Google Play Billing (ProxyBillingActivity, BILLING permission)                                                                                                             |
| **Ads**        | Google Mobile Ads (AdActivity, AdService, MobileAdsInitProvider)                                                                                                           |
| **Auth**       | Google Sign-In (SignInHubActivity), Credentials Play Services, Firebase Auth                                                                                               |
| **Web**        | WebView with `recaptcha.tradingview.com` dark/light HTML                                                                                                                   |

**No native libs:** `lib/` folder 0 files, `.so` count 0. `chartnative` package irundhalum adhu Java implementation dhaan (MPChart-like), C++ illa.

**DEX breakdown (8 files, 74 MB):**

```
classes.dex  10.26 MB (string_ids 79,468, method_ids huge)
classes2.dex 11.02 MB
classes3.dex 11.13 MB
classes4.dex 10.38 MB
classes5.dex  6.98 MB
classes6.dex 10.64 MB
classes7.dex 11.42 MB
classes8.dex  2.14 MB
```

---

## 3. Permissions (20)

```
INTERNET, ACCESS_NETWORK_STATE, VIBRATE, WAKE_LOCK, FOREGROUND_SERVICE, FOREGROUND_SERVICE_DATA_SYNC
READ/WRITE_EXTERNAL_STORAGE, READ/WRITE_CALENDAR
POST_NOTIFICATIONS, RECEIVE_BOOT_COMPLETED, DETECT_SCREEN_CAPTURE
BILLING, C2DM RECEIVE, AD_ID, AD_SERVICES (AD_ID, ATTRIBUTION, TOPICS)
DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION, BIND_GET_INSTALL_REFERRER_SERVICE
```

Queries: 5+ OEM safe center packages (MIUI, Huawei, Oppo, Vivo, Letv, IQOO) - Chinese OEM battery optimization bypass.

---

## 4. Components

**Application:** `com.tradingview.tradingviewapp.TradingViewApp` (label/icon from resources)

**Activities (6):**

- RootActivity (LAUNCHER, exported=true, clearTaskOnLaunch)
- Watchlist/ Symbol Widget Configuration Activities
- NewsPagerWidgetConfigurationActivity
- HiddenActivity (credentials), GoogleApiActivity, AdActivity etc.

**Services (10+):**

- MessagingService (Firebase, exported)
- WatchlistWidgetService (BIND_REMOTEVIEWS)
- WorkManager SystemForegroundService (foregroundServiceType dataSync)

**Receivers (10+):**

- WidgetProviders (OneSymbol, Watchlist, NewsPager) - all exported
- NotificationSettingsReceiver
- MyPackageReplacedReceiver, AnalyticsReceiver, MeasurementReceiver

**Providers (4):**

- ExportedFileProvider (`com.tradingview.tradingviewapp.fileProvider`, grantUriPermissions true) ⚠️
- PhotoProvider (FileProvider)
- MobileAdsInitProvider, FirebaseInitProvider

**Security Note:** `ExportedFileProvider` exported=true + grantUriPermissions true = potential file leak if not path-restricted. Need path XML check (requires apktool decode).

---

## 5. Network & Assets

**Hardcoded URLs (dex scan):**

```
https://s3.tradingview.com/
https://s3-symbol-logo.tradingview.com/
http://tradingview.com / https://tradingview.com
https://www.tradingview.com/gopro/?offer_type=sale
https://android.appassets.recaptcha.tradingview.com/assets/dark/index.html
wss://%s/socket.io/websocket?client=mobile  <- pushstream real-time
```

**Assets (47 files):**

- `benefits.json` (41 KB) - premium benefits?
- `units.json` (10 KB)
- `pushstream_js_client.js` (40 KB) - WebSocket push client (socket.io)
- `feature_chart_kotlin_js_client.js` (364 KB) - chart JS bridge
- `inline_scripts.js` (2.4 KB)
- `dark/light/index.html` (reCAPTCHA)
- `js/notes_subs.js`, `user_actions_subs.js` - notes streaming
- Stickers (6 WEBP) + dexopt profiles

**No API keys in plain dex scan** (RECAPTCHA_WEB_API_KEY placeholder only).

---

## 6. Features (dex module frequency)

Top tradingview feature modules:

```
profile/userstateinteractor, symbol/search (119 hits), chart/di + chartfragment,
market/feed (news), brokers/list (53), indicators/template, analytics/snowplow,
minds/feed, watchlist, alerts, paywalls/video, economic/calendar, ideas, news
```

Approx **33550** tradingview-related class strings found.

---

## 7. APK Signature Details (REA static)

```json
v1: absent
v2: present, signer SHA256 44f047fc6b34b0ba..., RSA, sha512 digest matches_apk true
v3: present, same cert, minSdk 24, maxSdk 2147483647, rotation false
v3.1: absent
source_stamp: present, cert 3257d599..., 2020-2050
posture: self_consistent true, schemes_agree_on_signer true
```

Limitations: **No crypto verification** - digests only show bytes unchanged, anyone can re-sign.

---

## 8. What REA Could / Could Not Do

✅ **Done static (no install needed):**

- inspect-apk-signature (evidence exact)
- inspect-artifact (4145 nodes inventory)
- identify-flutter-build (false - not Flutter)
- Manual dex string + manifest binary parse

❌ **Needs extra tools (you supply, REA never downloads):**

- `inspect-android-package` / `inspect-android-class` / `inspect-android-method` -> needs `REA_JADX_MCP_JAR` (jadx-headless-mcp 0.7.1)
- `decode-android-resources` -> needs Apktool
- Native decompilation -> needs Hopper/Ghidra/IDA

Base APK is split (`requiredSplitTypes: base__abi`) - full app needs additional split APKs for ABI.

---

## 9. Risk / Next Steps

1. **Exported provider** - check `res/xml/file_paths.xml` after apktool
2. **WebSocket** `wss://%s/socket.io` - hostname from runtime config (not hardcoded)
3. **Chart logic** is in `chartnative` Java (384 classes) - can be traced without native RE
4. For deep code review: `apktool d TradingView.apk` then JADX GUI

Want me to decode resources with apktool if you install it, or trace a specific feature (e.g., alerts, alerts/paywalls, chart intervals)?

**Evidence IDs:** `ev_a8a1a9e6c8d...` (signature), `ev_0a78ee253ac7...` (artifact), `ev_d5dc...` (JS demo earlier)
