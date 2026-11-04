# MediaTek compatibility research

## Current findings

- The upstream application targets non-Snapdragon devices and contains Mali-specific rendering, texture, and telemetry code. Its 1.2.1 notes describe a reboot fix on some MediaTek Mali phones, EGL window-buffer flushing, and other renderer fixes; retain these baseline changes.
- Native Mali telemetry uses kbase CSF hardware-counter ioctls. The code checks for CSF/uAPI and a supported counter buffer format before enabling the reader. It intentionally avoids the path on job-manager devices; its comment cites an unverified-in-this-project Helio G95 reboot report.
- Android vendors can restrict sysfs metrics. Telemetry already has fallback paths; the UI must not label a metric available unless a live reading succeeds.
- Community reports show driver/backend compatibility is version-specific: DXVK version, Mali family, wrapper, kernel interface, and BCn path can each change the result.
- Current Android PanVK kbase projects demonstrate active work on BCn emulation, descriptor/features needed by VKD3D, memory pressure, CSF queues, and DRI3 imports. These projects are independent experimental stacks and are not safe drop-in dependencies without compatibility, provenance, and license review.

## DXML

The upstream Winlator Mali README describes DXML as an independent, Mali-specialized Direct3D translation project built from scratch. The inspected README does not provide a public source repository or integration API. Until a public codebase, license, ABI, and maintenance model are identified, Malilator should track it as external research and must not copy code, claim integration, or redistribute its binaries.

## Work still required

- Collect device/driver-specific Vulkan extensions, feature bits, limits, and driver identity from the live Android stack.
- Audit EGL context creation and renderer strings on target Android releases.
- Reproduce the upstream BCn modes with a repeatable texture test on G925 and other Mali families.
- Capture genuine MediaTek crash/artifact reports before adding workarounds.
- Keep each workaround capability-gated and user-overridable.

## Sources

- [Winlator Mali 1.2.1 changes](https://github.com/GunaCharanTeja/WinlatorMali/blob/bionic-mali-1.2/RELEASE_NOTES_1.2.1.md)
- [Winlator Mali source tree](https://github.com/GunaCharanTeja/WinlatorMali/tree/bionic-mali-1.2)
- [PanVK kbase Android](https://github.com/zenithblue-oss/panvk-kbase-android)
- [PanVK Android release notes for CSF and BCn work](https://github.com/MineGraph-bilibili/panvk-Android/releases)
- [Winlator Mali README DXML description](https://github.com/GunaCharanTeja/WinlatorMali#support-dxml)
