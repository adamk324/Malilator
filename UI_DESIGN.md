# UI design direction

## Product identity

Malilator keeps the underlying Winlator-derived container and launch model. The current milestone updates the app name, package IDs, About link, and main menu label. It does not claim a completed interface redesign.

## Liquid Glass-inspired Android design principles

- Use translucent, layered surfaces only where they improve hierarchy; retain opaque low-cost fallbacks.
- Use blur sparingly and never in continuously redrawn game overlays.
- Preserve strong text contrast in dark and light themes, accessibility font scaling, and controller navigation.
- Keep container setup progressive: common controls first, advanced options in expandable sections.
- Use user-owned artwork only where available; provide a neutral fallback and do not bundle copyrighted game art.
- Keep all existing workflows, save import/export, file access, and container actions reachable during redesign.

## Current state and next slices

The baseline uses Java/XML layouts, AndroidX, Material Components, and a custom theme manager. A first capability diagnostics panel is now available from Tools & Drivers. Existing themes and input/editor screens are heavily integrated; redesign incrementally and compare interactions before replacing layouts. Next slices: a cleaner container/game library surface, settings navigation, then touch-control editing polish. Measure redraw cost on real hardware before adding effects.
