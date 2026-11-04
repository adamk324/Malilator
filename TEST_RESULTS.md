# Test results

The baseline source was validated on GitHub Actions run [41910695504](https://github.com/adamk324/Malilator/actions/runs/41910695504), commit `dbb3896`, using the configured Ubuntu toolchain. `testStandardDebugUnitTest`, `lintStandardDebug`, `assembleStandardDebug`, and `assembleStandardRelease` all completed successfully. The native CMake/JNI sources compiled as part of the APK tasks. That baseline had no test source files, so the unit-test task completed without exercising application behavior. The current change adds pure-Java GPU profile unit tests; its CI result is pending.

The build uploaded `Malilator-standard-1` with the standard debug APK, unsigned standard release APK, and Gradle log. Build success is not physical GPU or game validation.

| Feature | Build tested | Device tested | Result |
|---|---|---|---|
| App startup | Pending | Pending | Not tested |
| Container creation | Pending | Pending | Not tested |
| Windows executable launch | Pending | Pending | Not tested |
| GPU detection | Pending for current change | Xiaomi 15T Pro pending | Advisory family classification from the Vulkan device name; not device tested |
| Vulkan detection | Pending for current change | Xiaomi 15T Pro pending | New diagnostics queries Vulkan name, vendor ID, API version, and extensions |
| EGL renderer | Pending for current change | Xiaomi 15T Pro pending | New temporary EGL pbuffer probe; not device tested |
| BCn compatibility | Pending | Xiaomi 15T Pro pending | Not tested |
| Touch controls | Pending | Pending | Not tested |
| Gamepad | Pending | Pending | Not tested |
| Audio | Pending | Pending | Not tested |
| Frame generation | Pending | Xiaomi 15T Pro pending | Not tested |
| Performance overlay | Pending | Xiaomi 15T Pro pending | Not tested |

## CI build record

| Run | Commit | Result | Device tested |
|---|---|---|---|
| [41910695504](https://github.com/adamk324/Malilator/actions/runs/41910695504) | `dbb3896` | Unit-test task, lint, debug APK, unsigned release APK: passed | None |
