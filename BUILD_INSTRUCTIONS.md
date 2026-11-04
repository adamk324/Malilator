# Build instructions

## Supported toolchain

- JDK 17
- Android SDK platform 34
- Android Build Tools 35.0.0
- Android NDK 29.0.14206865
- CMake 3.22.1 or newer (CI uses 3.22.1)
- Gradle wrapper 8.10.2

Initialize submodules and build from a complete checkout; runtime archives and assets are required. Gradle's `preBuild` downloads `app/src/main/assets/imagefs.tzst` from the upstream Winlator-Extras release when missing.

```bash
git clone --recurse-submodules --branch bionic-mali-1.2 <Malilator-repository-url>
cd Malilator
./gradlew :app:assembleStandardDebug
```

On Windows, run `gradlew.bat :app:assembleStandardDebug` from PowerShell. The debug APK is at `app/build/outputs/apk/standard/debug/app-standard-debug.apk`.

## Release APK

Without a configured private signing key, `:app:assembleStandardRelease` creates an unsigned release APK. Do not publish an unsigned APK as an installable production release. Configure `keystore.file`, `keystore.password`, `key.alias`, and `key.password` in an untracked local `local.properties` file to sign locally. Keep the keystore and passwords out of Git and logs. The CI unsigned artifact is named `Malilator-standard-release-unsigned.apk`.

For physical-device testing, install the signed debug APK with Android's package installer. A debug APK cannot update an installation signed with a different key; uninstalling may remove app-private data, so back up containers and saves first.
