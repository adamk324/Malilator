# Known issues and open work

- No baseline APK has yet been built in this environment; the current checkout is sparse and omits large runtime assets and the submodule working tree.
- GitHub Actions has not run because no Malilator repository has been created/pushed from this session.
- There are no Xiaomi 15T Pro or Immortalis-G925 device test results yet.
- GPU identity/capability detection and the compatibility database have not been implemented.
- The project still bundles many upstream runtime and graphics archives; their individual redistribution terms need a full audit before public publication.
- Local environment has JDK 21 and no configured Android SDK environment variables; the documented project toolchain is JDK 17 plus Android SDK/NDK.
- The Android package namespace and Java classes intentionally remain `com.winlator.cmod`; only application IDs and user-visible primary identity were changed in this milestone.
