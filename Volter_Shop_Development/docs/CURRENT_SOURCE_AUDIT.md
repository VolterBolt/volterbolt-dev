# Current Volter Shop Source Audit

## Current Architecture

- `VolterShop` is the plugin entry point.
- `ShopGUI` currently handles configuration loading, GUI rendering,
  click handling, and transactions.
- `EconomyProvider` abstracts the economy.
- Existing providers: Vault, PlayerPoints, and command-based economy.
- Categories and item prices are currently loaded from `config.yml`.

## Main Redesign Goals

1. Separate shop data from GUI code.
2. Separate GUI screens from transaction logic.
3. Centralize GUI navigation.
4. Make future quantity and confirmation menus easier to add.
5. Keep the existing economy abstraction compatible.
6. Keep development code separate from the stable plugin until verified.

## Reference Principle

The existing Volter Shop source is the compatibility reference.

External projects are used for architectural inspiration only.
Their code is not copied into this development tree.
