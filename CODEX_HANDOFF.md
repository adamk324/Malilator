# Codex handoff

## Current state

- Source checkout is in `E:\apollo500\Malilator\source3` and has its own `.git`; `origin` tracks upstream `bionic-mali-1.2`, and `malilator` points to the fork.
- Baseline commit: `0411a4b602ed00d71bef52e6a7ccb82a8e709b1a`; 1.2.1 tag is present.
- Public fork: `https://github.com/adamk324/Malilator`, parent `GunaCharanTeja/WinlatorMali`, default branch `main`.
- A first bootstrap repository created during setup was renamed to `adamk324/Malilator-bootstrap`; the same development commit was pushed to the actual fork.
- CI run [41910695504](https://github.com/adamk324/Malilator/actions/runs/41910695504) passed unit-test task, lint, standard debug build, and unsigned release build. The unit-test task had no test sources to execute.
- A GPU diagnostics screen and conservative profile classifier are now in the working tree; pure-Java classification tests were added. Push the next commit to run CI on this slice.
- This is a sparse checkout omitting `app/src/main/assets`, `app/libs`, and `input_controls`. Submodule is pinned but not initialized in the working tree.
- Local changes configure Malilator identity, release signing behavior, CI, and Milestone 1 documentation.
- The enclosing `E:\apollo500` repository already had unrelated deleted tracked files before this work. Do not restore, stage, or commit anything from that parent repository.

## Pending

1. Replace the sparse local checkout with a complete checkout when practical; the fork on GitHub contains the complete upstream tree and submodule reference.
2. Audit all bundled runtime archives and licenses.
3. Add actual unit/instrumentation test sources; current CI tasks do not exercise app behavior.
4. Continue with GPU detection and capability reporting before applying any Immortalis-specific setting.

CI artifact is available from the run linked above. No physical-device validation is claimed.
