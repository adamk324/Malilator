# Upstream baseline

- Repository: https://github.com/GunaCharanTeja/WinlatorMali
- Branch: `bionic-mali-1.2`
- Baseline commit: `0411a4b602ed00d71bef52e6a7ccb82a8e709b1a` (2026-11-01)
- Release tag present: `bionic-mali-1.2.1`
- Prior release tag: `bionic-mali-1.2`
- License file: MIT; bundled runtime and binary payloads have additional licenses and redistribution conditions.
- Submodule: `app/src/main/cpp/adrenotools`, pinned at `8483dfdaa2abf97ee89ad0e5f337e7b508550c6b`.

The branch history after the 1.2.1 version bump contains release-note and README changes; the 1.2.1 application changes are included in the branch and release tag. The worktree used for this audit is a sparse checkout that intentionally omits `app/src/main/assets`, `app/libs`, and `input_controls`; use a complete checkout with submodules for a reproducible build.

The Malilator checkout retains the upstream branch and full fetched commit history. A new GitHub remote has not been created or pushed from this environment.
