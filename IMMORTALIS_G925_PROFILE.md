# Immortalis-G925 profile

## Detection and selection

The profile must be selected from the actual Vulkan physical-device name (for example, a name containing `Immortalis-G925`) and corroborated with the Vulkan vendor ID and Android graphics data. Do not select it solely from `Build.MODEL`, a chipset name, or the Xiaomi product name. The existing `GPUInformation.getRenderer()` already returns the Vulkan `deviceName`; the current code does not yet gather enough EGL and Vulkan properties to produce a complete profile record.

## Safe initial recommendations

Until Malilator device tests exist:

- Keep the Android system Vulkan driver selected by default.
- Keep the upstream generic container and wrapper defaults. Let users select the existing Mali wrapper and BCn ASTC/ETC2 modes manually.
- Do not enable PanVK, DXVK, VKD3D, or frame generation automatically based only on G925 detection.
- Show experimental driver paths only as explicit choices with a clear source/build label and user override.
- Report unavailable telemetry as unavailable; do not show guessed GPU load, frequency, or memory.

These are conservative project defaults, not a tested performance preset.

## Evidence and limits

An independent Mesa PanVK kbase project documents G925 as architecture v13/CSF and reports testing on Galaxy Tab S11 family devices. Its project describes experimental support and per-device/per-build results; this does not prove compatibility with the Xiaomi 15T Pro vendor driver, kernel, memory allocator, or HyperOS build. Another third-party PanVK matrix reports successful Vulkan probes on selected G925 devices while noting that API probes alone do not prove clean image rendering.

The first diagnostics implementation classifies the Vulkan device name and recommends the system driver for G925, but does not apply settings. It reports live EGL/OpenGL ES data and leaves Vulkan feature bits, driver properties, and several limits unavailable. The upstream Malilator source does not include a Xiaomi 15T Pro result. No local G925 device is connected for this task. The profile remains **research-only** until startup, container creation, rendering, BCn, audio/input, telemetry, and sustained gameplay are tested on the target device.

## Validation checklist

Record the exact Android build fingerprint, kernel version, Vulkan device name/vendor ID/API, Vulkan driver name/version once exposed, EGL vendor/renderer/version, wrapper/DXVK/VKD3D versions, BCn mode, game, resolution, logs, observed artifacts, thermals, and repeatability. Do not generalize a result to every G925 device.

## Sources

- [PanVK kbase Android supported-device matrix](https://github.com/zenithblue-oss/panvk-kbase-android)
- [PanVK G925 v13 project notes](https://github.com/adesyaefull/panvk-kbase-android-g925)
- [Upstream Winlator Mali 1.2.1 release notes](https://github.com/GunaCharanTeja/WinlatorMali/blob/bionic-mali-1.2/RELEASE_NOTES_1.2.1.md)
