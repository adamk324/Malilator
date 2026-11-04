# Third-party notices

This project is derived from Winlator Mali Bionic. Preserve the upstream `LICENSE` and all bundled notices. The upstream README identifies the following upstream components and licenses; this is an initial inventory, not a completed file-by-file audit:

| Component | Upstream-stated license | Audit status |
|---|---|---|
| Winlator Mali / app baseline | MIT | Source license present; preserve notices |
| Wine / Proton | LGPL-2.1-or-later (upstream summary) | Review exact bundled versions and source offers |
| Box64 | MIT | Review bundled archive provenance |
| FEX-Emu | MIT | Review bundled archive provenance |
| Mesa | MIT/LGPL (upstream summary) | Review exact bundled payloads |
| DXVK | zlib/libpng | Review exact bundled versions and notices |
| VKD3D | LGPL-2.1 | Review exact bundled versions and obligations |
| libadrenotools | GPL-3.0 | Submodule; review distribution implications |
| ZeroFG | Apache-2.0 / BSD-3-Clause (upstream summary) | Preserve component notices |
| Inter font | SIL OFL-1.1 | Preserve font license file |
| AOSP-derived components | Apache-2.0 (upstream summary) | Identify exact files and notices |

Additional bundled archives are under `app/src/main/assets`. Inspect each archive's contents and provenance before any public distribution. Do not bundle proprietary GPU drivers, game data, signing keys, or credentials without explicit redistribution rights.
