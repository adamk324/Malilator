# Malilator

Malilator is a Winlator Mali Bionic 1.2.1-derived Android compatibility environment for Windows applications and games. It targets Mali and MediaTek devices while retaining the upstream Adreno and other supported paths.

The current source baseline is upstream branch `bionic-mali-1.2` at commit [`0411a4b`](https://github.com/GunaCharanTeja/WinlatorMali/commit/0411a4b602ed00d71bef52e6a7ccb82a8e709b1a). The upstream `bionic-mali-1.2.1` release tag is included. Malilator development is beginning with build reproduction and project identity; new compatibility claims will be marked only after source review and device testing.

## Current status

- Android application: Java and XML UI, Android package namespace remains `com.winlator.cmod` for source compatibility.
- Standard application ID: `com.malilator.app`, so it can be installed alongside the upstream standard package.
- Native stack: C/C++, CMake, native EGL renderer, Vulkan support, ZeroFG, Mali telemetry, Wine/Box64/FEX runtime payloads.
- CI: [first GitHub Actions build passed](https://github.com/adamk324/Malilator/actions/runs/41910695504); debug and unsigned release APKs plus a Gradle log were uploaded.
- GPU diagnostics: the new on-demand screen reports the system Vulkan device, extensions, EGL/OpenGL ES strings, and an advisory family profile. Full Vulkan features/limits and physical-device validation remain pending.
- Physical device validation: not yet performed. A successful CI build is not evidence of Immortalis-G925 behavior.
- Licensing review: in progress. Keep any new repository private until bundled component redistribution terms are checked.

## Build and install

The recommended debug APK is `Malilator-standard-debug.apk` from the GitHub Actions artifact. It is signed with the standard Android debug key and is intended for testing. The release artifact is unsigned unless a maintainer configures a private release key. See [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md) and [GITHUB_ACTIONS.md](GITHUB_ACTIONS.md).

The project currently uses JDK 17, Android SDK platform 34, Build Tools 35.0.0, NDK 29.0.14206865, and CMake 3.22.1. The Android Gradle Plugin is 8.8.0 and the Gradle wrapper is 8.10.2.

## Upstream credits and source

Malilator preserves upstream history and required notices. See [UPSTREAM_BASE.md](UPSTREAM_BASE.md), [CREDITS.md](CREDITS.md), and [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md). The original project is [WinlatorMali](https://github.com/GunaCharanTeja/WinlatorMali) by GunaCharanTeja.

## Roadmap documents

- [Project overview](PROJECT_OVERVIEW.md)
- [Architecture audit](ARCHITECTURE.md)
- [Build instructions](BUILD_INSTRUCTIONS.md)
- [GitHub Actions](GITHUB_ACTIONS.md)
- [Known issues](KNOWN_ISSUES.md)
- [Test results](TEST_RESULTS.md)
- [GPU compatibility database](GPU_COMPATIBILITY_DATABASE.md)
- [Immortalis-G925 profile](IMMORTALIS_G925_PROFILE.md)
- [MediaTek compatibility](MEDIATEK_COMPATIBILITY.md)
- [Graphics backends](GRAPHICS_BACKENDS.md)
- [UI design](UI_DESIGN.md)
- [Changelog](CHANGELOG.md)
- [Codex handoff](CODEX_HANDOFF.md)

GPU compatibility, Immortalis-G925, MediaTek research, and graphics backend documents will be populated as the corresponding source and device investigation is completed. No unsupported driver capability or performance claim is implied by the project name or target hardware.
