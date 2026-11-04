# Project overview

## Objective

Develop a maintainable Winlator Mali Bionic derivative for Android, with particular attention to MediaTek, Mali, and Immortalis devices. Preserve upstream container and runtime behavior while adding capability-based graphics diagnostics and validated compatibility improvements.

## Milestones

1. **Repository takeover and build reproduction** — source baseline and 1.2.1 release identified, upstream history retained through a real GitHub fork, `main` established as default, package identity separated, and first CI APK build passed.
2. **Hardware detection and diagnostics** — first on-demand Vulkan/EGL report and GPU-family classification implemented; full feature/limit probes and device validation remain pending.
3. **MediaTek compatibility** — research baseline recorded; validated workarounds not yet implemented.
4. **UI redesign** — not started.
5. **Performance and reliability** — not started.
6. **Release preparation** — not started.

## Primary target

Xiaomi 15T Pro, Dimensity 9400+, Immortalis-G925 MC12, 12 GB RAM, Android/HyperOS. No physical device is attached to this development environment; all device-specific validation is pending.

## Guardrails

- Detect exposed Android graphics capabilities; do not infer support from a device model or GPU name alone.
- Keep all automatic settings overridable and retain generic fallbacks.
- Preserve container data and user saves during upgrades.
- Do not redistribute proprietary GPU binaries.
- Do not mark a feature as tested based on compilation alone.
- Keep the repository private until bundled licenses and redistribution conditions are fully reviewed.
