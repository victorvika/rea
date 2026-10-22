# APK Build Instructions (Tamil + English)

## Live Chart APK - Final Out

**Source ready:** `tradingview-inspired-app/` (pushed to branch `arena/5cd68b3c-rea`)

### Option 1: GitHub Actions (Recommended - 1 click, no setup)

1. Go to: https://github.com/victorvika/rea/actions/workflows/build-apk.yml
2. Click latest run `Build TradingLite APK` (triggered by push)
3. Download artifact `TradingLite-LiveChart-APK` -> inside `app-debug.apk`
4. Install on device: enable Unknown Sources -> tap APK

Direct link after build: https://github.com/victorvika/rea/actions (filter branch arena/5cd68b3c-rea)

### Option 2: Android Studio (Local)

1. Open folder `tradingview-inspired-app` in Android Studio
2. File -> Sync Project with Gradle Files
3. Build -> Build Bundle(s) / APK(s) -> Build APK(s)
4. APK at: `app/build/outputs/apk/debug/app-debug.apk`
5. Drag to emulator or `adb install app-debug.apk`

### Option 3: Command Line (if you have SDK)

```bash
cd tradingview-inspired-app
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

### What you get

- Package: com.example.tradinglite
- Label: TradingLite
- Theme: TradingView dark #131722
- Chart: Lightweight Charts (MIT) + Binance live via https://api.binance.com/api/v3/klines + wss://stream.binance.com
- No ads, no subscription check, no TradingView private API
- Permissions: INTERNET only

### Test in browser now (preview)

Open: https://8000-iedxir1wsxjo11d6l1qy1.e2b.app (live preview running)
