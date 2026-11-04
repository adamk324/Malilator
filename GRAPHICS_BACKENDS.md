# Graphics backends

## Baseline inventory

- **Android system Vulkan driver:** current default available through `/system/lib64/libvulkan.so`.
- **Native EGL renderer:** included in the app's native code and integrated with its X server.
- **Mali wrapper and BCn layer:** upstream bundled assets include wrapper variants and BCn emulation modes. Keep archive version and source attribution with each option.
- **DXVK / VKD3D:** bundled translation layers with different Vulkan requirements; selection must account for live feature/extension support and game API.
- **Adrenotools / Turnip:** existing Adreno-oriented driver route. Preserve its detection and override behavior.
- **ZeroFG:** bundled Vulkan compute frame-generation implementation with Mali-oriented shader variants. Keep it opt-in until the device and renderer have been validated.
- **PanVK kbase:** independent community Mesa builds exist for selected Mali CSF GPUs, including G925 research builds. They are experimental third-party driver packages; they are not currently imported into Malilator.
- **DXML:** described upstream as a separate Mali Direct3D translator, but no public source/integration interface was found in the inspected upstream README.

## Backend-selection policy

1. Detect the live Vulkan physical device and available extensions/features.
2. Inspect actual EGL/GL strings through a current EGL context.
3. Recommend only a backend with evidence for that GPU and exact driver path.
4. Keep user override and safe system-driver fallback.
5. Label experimental wrappers and show their source/version; never fabricate capabilities.

## Baseline diagnostics limitation

Current native APIs expose Vulkan model, vendor ID, API version, and extensions. They do not yet expose Vulkan `driverProperties`, the feature chain, device limits/memory heaps, or system EGL vendor/renderer in one report. Complete these probes before automatic wrapper selection.
