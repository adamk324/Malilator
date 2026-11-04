# GPU compatibility database

This table separates upstream/community reports from Malilator validation. A named GPU or a successful API initialization is not evidence that a game or wrapper renders correctly.

| GPU family/model | Driver and stack evidence | Rendering/backend evidence | Known limitations or workarounds | Malilator status |
|---|---|---|---|---|
| Immortalis-G925 MC12 | Android vendor driver is the expected system baseline. Current Malilator native code can query a Vulkan device name, vendor ID, API version, and extensions, but not driver properties/features/limits. | Upstream Winlator Mali provides system Vulkan, native EGL, wrappers, and BCn translation. A separate open Mesa PanVK kbase project reports G925 v13/CSF testing on Galaxy Tab S11 family hardware. | PanVK is an experimental third-party build and test results do not transfer automatically between OEM kernels, Android builds, or games. G925 rendering has not been tested on Xiaomi 15T Pro. | Not physically tested; see [IMMORTALIS_G925_PROFILE.md](IMMORTALIS_G925_PROFILE.md). |
| Mali CSF (G610/G615/G710/G715/G720) | Community Android PanVK packages built on Mesa support selected CSF GPUs. Some packages implement BCn emulation and D3D12 feature fixes. | Reports include Winlator/DXVK and VKD3D scenarios on selected devices. | BCn emulation may keep both compressed and decoded image data, increasing memory use. Feature exposure can differ by architecture and driver build; never infer support from another GPU in the same family. | Upstream baseline only; no Malilator device results. |
| Mali Bifrost / job-manager (G52/G57/G76/G77/G78) | Upstream telemetry source detects CSF via kbase ioctl and declines its Mali counter path when CSF probing fails. Its source comment cites a Helio G95 reboot report for old MediaTek Bifrost counter/time-info ioctls. | Use the system graphics stack and established wrappers; community PanVK reports vary by GPU and build. | Do not probe the CSF hardware-counter reader on job-manager GPUs. Treat the reboot report as a precaution from the source, not an independently reproduced Malilator result. | No device validation. |
| Qualcomm Adreno | Existing Adrenotools/Turnip path remains in the source; UI currently exposes the Adrenotools driver section for detected Adreno devices, with an override. | Existing Winlator Adreno functionality must be retained. | Do not apply Mali-specific wrappers or texture assumptions to Adreno. | Regression testing pending. |
| Other / unknown Android GPU | Existing generic system Vulkan/EGL paths. | No hardware-specific recommendation. | Display unavailable values explicitly and let the user choose any supported installed wrapper. | Generic fallback required. |

## Detection evidence in the baseline

`GPUInformation.getRenderer()` returns Vulkan `VkPhysicalDeviceProperties.deviceName`; it is a Vulkan device name, not the EGL renderer string. Other native methods report vendor ID, Vulkan API version, and device extension names. The baseline does not expose Vulkan feature structs, limits, memory heaps, EGL vendor/renderer, or driver name/version as a diagnostics record. The legacy PC PCI-ID list in `app/src/main/assets/gpu_cards.json` is not a reliable Android GPU database.

## Sources

- [Winlator Mali source and release documentation](https://github.com/GunaCharanTeja/WinlatorMali/tree/bionic-mali-1.2)
- [Winlator Mali 1.2.1 notes](https://github.com/GunaCharanTeja/WinlatorMali/blob/bionic-mali-1.2/RELEASE_NOTES_1.2.1.md)
- [Mesa PanVK kbase Android project and device matrix](https://github.com/zenithblue-oss/panvk-kbase-android)
- [Experimental PanVK Android build notes for selected CSF GPUs](https://github.com/MineGraph-bilibili/panvk-Android/releases)
- [Community PanVK wrapper issues and per-device reports](https://github.com/FristOneRR/FristOneRR-Panvk-Driver)
- [Upstream Winlator Mali DXML description](https://github.com/GunaCharanTeja/WinlatorMali#support-dxml)

This database must be updated with a source link and a separate Malilator build/device result for every new compatibility claim.
