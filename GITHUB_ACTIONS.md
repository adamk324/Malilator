# GitHub Actions

`.github/workflows/build-apk.yml` runs on pushes to `main`, pull requests, version tags, and manual dispatch. It checks out submodules, provisions JDK 17 and the pinned Android toolchain, runs standard debug unit tests and lint, compiles the native Android code through the APK builds, and builds standard debug and release APKs.

Artifacts include:

- `Malilator-standard-debug.apk` — debug-signed, for device testing.
- `Malilator-standard-release-unsigned.apk` — unsigned unless an authorized maintainer configures a private key.
- `gradle-build.log` — Gradle output retained even if the build fails.

Gradle dependency caching is handled by `setup-java`; the Android platform, build tools, CMake, and NDK are cached by their exact toolchain key. Actions are pinned to immutable commit SHAs in the workflow. Workflow token permission is read-only.

Release signing is intentionally not configured with repository secrets or default passwords. To produce a signed release, use a locally secured keystore or add an approved protected signing workflow after repository ownership and secret handling are established. Never commit a keystore or signing material.
