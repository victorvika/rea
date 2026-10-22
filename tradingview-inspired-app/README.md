# TradingView-Inspired Android App (Legal Recreation - No Ads / No Subscription)

**Indha project TradingView APK-a patch pannala - fresh Kotlin + Compose app, MIT libraries mattum use pannuthu.**

Original Analysis la paatha maadhiri chartnative, pushstream, watchlist lam namba scratch la recreate panrom - TradingView code copy pannala.

### Tech Stack (TradingView ku similar-aana legal stack)

- **Kotlin + Jetpack Compose** (TradingView um idhe thaan use panrathu)
- **Material 3** - same design system
- **Lightweight Charts via WebView** - TradingView official open-source (MIT) - `assets/lightweight-charts`
- **OkHttp + Room + Dagger Hilt** - TradingView stack ku set aagura maadhiri (original analysis la irunthathu)
- **Public Market Data** - Binance public API / Yahoo Finance (no paywall bypass)

### Features (No Ads, No Subscription)

- Candlestick + Line chart with intervals (1m, 5m, 1D)
- Watchlist (Room local)
- Symbol search (public API)
- Price alerts (WorkManager - local only)
- Widgets (Compose Glance) - optional

### Enna illai (TradingView paywall bypass pannala)

- Premium indicators copy pannala
- TradingView private API use pannala
- Original dex code reuse pannala

### Run pannarthu

1. Android Studio Chipmunk+ open pannu
2. Sync Gradle
3. `app` run -> Pixel emulator / unga device

> TradingView trademark avanga kitta thaan irukku. Indha app "inspired by" demo mattum - Play Store la same name la publish panna koodathu.
