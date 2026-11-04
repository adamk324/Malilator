# Codex handoff

## Current state

- Source checkout is in `E:\apollo500\Malilator\source3` and has its own `.git`, tracking upstream `bionic-mali-1.2`.
- Baseline commit: `0411a4b602ed00d71bef52e6a7ccb82a8e709b1a`; 1.2.1 tag is present.
- This is a sparse checkout omitting `app/src/main/assets`, `app/libs`, and `input_controls`. Submodule is pinned but not initialized in the working tree.
- Local changes configure Malilator identity, release signing behavior, CI, and Milestone 1 documentation.
- The enclosing `E:\apollo500` repository already had unrelated deleted tracked files before this work. Do not restore, stage, or commit anything from that parent repository.

## Pending

1. Obtain a complete upstream clone including large assets and initialize `adrenotools`.
2. Audit all bundled runtime archives and licenses.
3. Verify the Gradle task graph and run the first baseline build in GitHub Actions.
4. Create a separate Malilator GitHub repository only after authenticated account access and private visibility are verified. Preserve this checkout's upstream remote and add a distinct Malilator remote; do not push to the upstream repository.
5. Continue with GPU detection and capability reporting before applying any Immortalis-specific setting.

No APK, remote repository, physical-device validation, or successful CI run is claimed yet.
