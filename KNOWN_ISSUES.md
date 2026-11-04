# Known issues and open work

- The current local checkout is sparse and omits large runtime assets and the submodule working tree. GitHub Actions builds from the complete fork checkout.
- The baseline Android project contains no unit-test or instrumentation-test source sets. The successful Gradle unit-test task therefore did not exercise application behavior.
- There are no Xiaomi 15T Pro or Immortalis-G925 device test results yet.
- The initial GPU diagnostics screen reports system Vulkan name/vendor/API/extensions plus EGL/OpenGL ES strings and maximum 2D texture size. Vulkan feature bits, detailed limits/memory heaps, driverProperties, extension-scoped format support, and a verified driver version are still not exposed.
- GPU-family recommendations use the live Vulkan device name, but remain advisory and do not configure containers automatically.
- The project still bundles many upstream runtime and graphics archives; their individual redistribution terms need a full audit before public publication.
- Local environment has JDK 21 and no configured Android SDK environment variables; the documented project toolchain is JDK 17 plus Android SDK/NDK.
- The Android package namespace and Java classes intentionally remain `com.winlator.cmod`; only application IDs and user-visible primary identity were changed in this milestone.
