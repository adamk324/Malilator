# Changelog

## Malilator development baseline

- Preserved the Winlator Mali `bionic-mali-1.2` history and 1.2.1 release baseline.
- Set unique Malilator application IDs for standard, Ludashi, and AnTuTu build flavors.
- Updated About and main-menu branding to Malilator.
- Changed release builds to remain unsigned unless a keystore and all signing credentials are explicitly configured; removed default debug-key signing from release variants.
- Replaced manual-only APK workflow with push, pull-request, tag, and dispatch validation/build workflow; added test/lint tasks and named debug/unsigned release artifacts.
- Added initial architecture, build, upstream, test, licensing, and project status documentation.
