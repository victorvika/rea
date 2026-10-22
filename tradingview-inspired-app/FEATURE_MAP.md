# TradingView → TradingLite Feature Mapping (Legal)

| TradingView (analysis la paathathu)            | TradingLite recreation (scratch)                                              | Library                       |
| ---------------------------------------------- | ----------------------------------------------------------------------------- | ----------------------------- |
| chartnative (384 classes, 8 dex)               | Lightweight Charts WebView                                                    | `lightweight-charts` MIT      |
| pushstream_js_client.js 40KB + wss://socket.io | Binance WebSocket public: `wss://stream.binance.com:9443/ws/btcusdt@kline_1m` | OkHttp                        |
| watchlist/widget (5 providers)                 | Glance widget + Room                                                          | `androidx.glance`, `room-ktx` |
| symbol/search (119 hits)                       | Search via `api.binance.com/api/v3/exchangeInfo`                              | OkHttp                        |
| alerts/light                                   | Local WorkManager alerts                                                      | `androidx.work`               |
| paywalls/video, gopro sale                     | **REMOVE** - no subscription logic                                            | -                             |
| ads (AdActivity, AdService)                    | **REMOVE** - zero ads                                                         | -                             |
| firebase analytics/snowplow                    | **REMOVE** or local only                                                      | -                             |

**Compliance note:** No dex patching, no `classes.dex` reuse, no TradingView logo/brand copy. API keys `RECAPTCHA_WEB_API_KEY` maadhiri keys reuse pannala.
