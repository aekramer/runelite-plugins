package tictac7x.camdozaal;

public class CamdozaalStrings {
    // Plugin.
    final static String
        plugin_name = "Ruins of Camdozaal",
        plugin_description = "Show helpful information inside the Ruins of Camdozaal",
        plugin_tags_ruins = "ruins",
        plugin_tags_camdozaal = "camdozaal",
        plugin_tags_barronite = "barronite",
        plugin_tags_shards = "shards",
		plugin_tags_vault = "vault"
    ;

    // Config.
    final static String
        // Config - Barronite Shards.
		config_barronite_shards_name = "Barronite Shards",
        config_barronite_shards_description = "Keep track of barronite shards in forge, inventory and bank.",

            config_barronite_shards_forge_name = "Forge",
            config_barronite_shards_forge_description = "Show amount of barronite shards in the forge.",

            config_barronite_shards_inventory_name = "Inventory",
            config_barronite_shards_inventory_description = "Show amount of barronite shards in the inventory.",

            config_barronite_shards_bank_name = "Bank",
            config_barronite_shards_bank_description = "Show amount of barronite shards in the bank.",

            config_barronite_shards_minimal_name = "Minimalistic information",
            config_barronite_shards_minimal_description = "Show minimalistic information, hide rows with zero and total counts.",

        // Config - Boost.
        config_boost_name = "Boost",
        config_boost_description = "Overlay about the status of the boost.",

            config_boost_status_name = "Status",
            config_boost_status_description = "Show status of boost.",

            config_boost_countdown_name = "Remaining Time",
            config_boost_countdown_description = "Show remaining time of the boost.",

            config_boost_countdown_style_name = "Style",
            config_boost_countdown_style_description = "Choose how to display the status of the boost.",

        // Config - Barronite Rocks.
        config_barronite_rocks_name = "Barronite Rocks",
        config_barronite_rocks_description = "Highlight barronite rocks.",

            config_barronite_rocks_highlight_name = "Highlight Rocks",
            config_barronite_rocks_highlight_description = "Draw border around barronite rocks that can be mined.",

            config_barronite_rocks_highlight_color_name = "Rocks Color",
            config_barronite_rocks_highlight_color_description = "Change color of barronite rocks outline.",

		config_vault_name = "Vault",
		config_vault_description = "Highlight vault lockboxes and doors",

			config_vault_lockboxes_simple_name = "Highlight Simple Lockboxes",
			config_vault_lockboxes_simple_description = "Highlight simple lockboxes.",

			config_vault_lockboxes_simple_color_name = "Simple Lockboxes Color",
			config_vault_lockboxes_simple_color_description = "Change color of simple lockboxes highlight.",

			config_vault_lockboxes_elaborate_name = "Highlight Elaborate Lockboxes",
			config_vault_lockboxes_elaborate_description = "Highlight elaborate lockboxes.",

			config_vault_lockboxes_elaborate_color_name = "Elaborate Lockboxes Color",
			config_vault_lockboxes_elaborate_color_description = "Change color of elaborate lockboxes highlight.",

			config_vault_lockboxes_ornate_name = "Highlight Ornate Lockboxes",
			config_vault_lockboxes_ornate_description = "Highlight ornate lockboxes.",

			config_vault_lockboxes_ornate_color_name = "Ornate Lockboxes Color",
			config_vault_lockboxes_ornate_color_description = "Change color of ornate lockboxes highlight.",

			config_vault_lockboxes_empty_name = "Highlight Empty Pedestals",
			config_vault_lockboxes_empty_description = "Highlight empty pedestals without lockboxes.",

			config_vault_lockboxes_empty_color_name = "Empty Pedestals Color",
			config_vault_lockboxes_empty_color_description = "Change color of empty pedestals highlight."
    ;

    // Overlay - Barronite Shards.
    final public static String
        barronite_shards = "Shards:",
        barronite_shards_forge = "Forge:",
        barronite_shards_inventory = "Inventory:",
        barronite_shards_bank = "Bank:",
		barronite_shards_deposits = "%d (+%d)"
    ;

    // Overlay - Boost.
    final public static String
        boost = "Boost",
        boost_active = "Active",
        boost_inactive = "Inactive",
        boost_minutes = "%d:%02d",
		boost_tooltip = "Boosted buffs to all of the activities within Camdozaal."
    ;

}
