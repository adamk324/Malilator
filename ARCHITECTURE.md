# Architecture audit

## Baseline

The inspected source is a native Android application, not a web UI. It uses Java and XML resources, AndroidX, Material Components, and custom Android views. The Gradle Android namespace and Java package tree remain `com.winlator.cmod`; the application ID is independently selected by product flavor.

## Major components

- `app/src/main/java/com/winlator/cmod`: app screens and application services, including container management, Wine/runtime downloads, settings, shortcuts, saves, logs, controllers, and X server integration.
- `app/src/main/cpp/winlator`: JNI and native renderer code, including EGL and Mali telemetry.
- `app/src/main/cpp/zerofg`: Vulkan frame generation implementation and shader sources.
- `app/src/main/cpp/adrenotools`: Git submodule for Adreno support.
- `app/src/main/assets`: runtime archives, graphics wrappers, fonts, configuration data, and other packaged payloads. These assets are large and carry distinct licensing obligations.
- `android_sysvshm`, `audio_plugin`: native helper components built with CMake/toolchain scripts.
- `app/src/main/res`: XML layouts, drawables, and theme resources.
- `GPUCompatibilityFragment`: on-demand diagnostics UI. It queries Vulkan through existing JNI and creates a temporary EGL pbuffer to read EGL/OpenGL ES strings; it does not change container settings.

## Build configuration

The app uses AGP 8.8.0, Gradle 8.10.2, compile SDK 34, Build Tools 35.0.0, NDK 29.0.14206865, CMake 3.22.1, Java 17, and ARM64-only ABI filters. `preBuild` runs `downloadAssets`, which downloads `imagefs.tzst` from the upstream Winlator-Extras release if it is absent.

## Graphics components found

- Native EGL renderer and GLX integration.
- Vulkan JNI/support code and Vulkan wrapper payloads.
- BCn-to-ASTC/ETC2 related assets and configuration.
- ZeroFG Vulkan implementation with Mali-oriented shader variants.
- Mali telemetry path using `/dev/mali0` counters, alongside fallback discovery paths in HUD code.
- Bundled DXVK/VKD3D, Wine/Proton, and multiple graphics driver/wrapper archives.

This is a source inventory, not a validation that each component works on every GPU or driver.

## Submodules

`.gitmodules` declares `app/src/main/cpp/adrenotools` from `https://github.com/Pipetto-crypto/libadrenotools.git`. The baseline records submodule commit `8483dfdaa2abf97ee89ad0e5f337e7b508550c6b`; initialize recursively before a full build.
