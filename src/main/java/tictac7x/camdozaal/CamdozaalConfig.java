package tictac7x.camdozaal;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup(CamdozaalConfig.group)
public interface CamdozaalConfig extends Config {
    String group = "tictac7x.camdozaal";

	enum boost_style {
		SIMPLE,
		PROGRESSBAR,
		INFOBOX
	}

    @ConfigSection(
		position = 1,
        name = CamdozaalStrings.config_barronite_shards_name,
        description = CamdozaalStrings.config_barronite_shards_description
    ) String section_barronite_shards = "barronite_shards";

        String barronite_shards_forge = "barronite_shards_forge";
        @ConfigItem(
			position = 1,
            keyName = barronite_shards_forge,
            name = CamdozaalStrings.config_barronite_shards_forge_name,
            description = CamdozaalStrings.config_barronite_shards_forge_description,
            section = section_barronite_shards
        ) default boolean showOverlayBarroniteShardsForge() {
            return true;
        }

        String barronite_shards_inventory = "barronite_shards_inventory";
        @ConfigItem(
			position = 2,
            keyName = barronite_shards_inventory,
            name = CamdozaalStrings.config_barronite_shards_inventory_name,
            description = CamdozaalStrings.config_barronite_shards_inventory_description,
            section = section_barronite_shards
        ) default boolean showOverlayBarroniteShardsInventory() {
        	return true;
    	}

        String barronite_shards_bank = "barronite_shards_bank";
        @ConfigItem(
			position = 3,
            keyName = barronite_shards_bank,
            name = CamdozaalStrings.config_barronite_shards_bank_name,
            description = CamdozaalStrings.config_barronite_shards_bank_description,
            section = section_barronite_shards
        ) default boolean showOverlayBarroniteShardsBank() {
        	return true;
    	}

        String barronite_shards_minimal = "barronite_shards_minimal";
        @ConfigItem(
			position = 4,
            keyName = barronite_shards_minimal,
            name = CamdozaalStrings.config_barronite_shards_minimal_name,
            description = CamdozaalStrings.config_barronite_shards_minimal_description,
            section = section_barronite_shards
        ) default boolean showOverlayBarroniteShardsMinimal() {
        	return true;
    	}

		String barronite_shards_bank_count = "barronite_shards_bank_count";
		@ConfigItem(
			hidden = true,
			keyName = barronite_shards_bank_count,
			name = barronite_shards_bank_count,
			description = barronite_shards_bank_count,
			section = section_barronite_shards
		) default int getBankBarroniteShardsCount() {
			return 0;
		}

		String barronite_deposits_bank_count = "barronite_deposits_bank_count";
		@ConfigItem(
			hidden = true,
			keyName = barronite_deposits_bank_count,
			name = barronite_deposits_bank_count,
			description = barronite_deposits_bank_count,
			section = section_barronite_shards
		) default int getBankBarroniteDepositsCount() {
			return 0;
		}

    @ConfigSection(
		position = 2,
        name = CamdozaalStrings.config_boost_name,
        description = CamdozaalStrings.config_boost_description
    ) String section_boost = "boost";

        String boost_status = "boost_status";
        @ConfigItem(
			position = 1,
            keyName = boost_status,
            name = CamdozaalStrings.config_boost_status_name,
            description = CamdozaalStrings.config_boost_status_description,
            section = section_boost
        ) default boolean showBoost() {
        	return true;
		}

        String boost_countdown = "boost_countdown";
        @ConfigItem(
			position = 2,
            keyName = boost_countdown,
            name = CamdozaalStrings.config_boost_countdown_name,
            description = CamdozaalStrings.config_boost_countdown_description,
            section = section_boost
        ) default boolean showBoostCountdown() {
        	return true;
    	}

        String boost_countdown_style = "boost_countdown_style";
        @ConfigItem(
			position = 3,
            keyName = boost_countdown_style,
            name = CamdozaalStrings.config_boost_countdown_style_name,
            description = CamdozaalStrings.config_boost_countdown_style_description,
            section = section_boost
        ) default boost_style getOverlayBoostStatusStyle() {
            return boost_style.SIMPLE;
        }

        String boost_countdown_date = "boost_countdown_date";
        @ConfigItem(
			hidden = true,
            keyName = boost_countdown_date,
            name = boost_countdown_date,
            description = boost_countdown_date,
            section = section_boost
        ) default String getOverlayBoostCountdownDate() {
            return "";
        }

        String boost_countdown_seconds = "boost_countdown_time";
        @ConfigItem(
			hidden = true,
            keyName = boost_countdown_seconds,
            name = boost_countdown_seconds,
            description = boost_countdown_seconds,
            section = section_boost
        ) default int getOverlayBoostCountdownTime() {
            return 0;
        }

    @ConfigSection(
		position = 3,
        name = CamdozaalStrings.config_barronite_rocks_name,
        description = CamdozaalStrings.config_barronite_rocks_description
    ) String section_barronite_rocks = "barronite_rocks";

        String barronite_rocks_highlight = "barronite_rocks_highlight";
        @ConfigItem(
			position = 1,
            keyName = barronite_rocks_highlight,
            name = CamdozaalStrings.config_barronite_rocks_highlight_name,
            description = CamdozaalStrings.config_barronite_rocks_highlight_description,
            section = section_barronite_rocks
        ) default boolean highlightBarroniteRocks() {
            return true;
        }

        String barronite_rocks_highlight_color = "barronite_rocks_highlight_color";
        @ConfigItem(
			position = 2,
            keyName = barronite_rocks_highlight_color,
            name = CamdozaalStrings.config_barronite_rocks_highlight_color_name,
            description = CamdozaalStrings.config_barronite_rocks_highlight_color_description,
            section = section_barronite_rocks
        ) default Color getBarroniteRocksHighlightColor() {
            return new Color(0x9000FF00, true);
        }

	@ConfigSection(
		position = 4,
		name = CamdozaalStrings.config_vault_name,
		description = CamdozaalStrings.config_vault_description
	) String section_vault = "vault";

		String vault_lockboxes_simple = "vault_lockboxes_simple";
		@ConfigItem(
			position = 1,
			keyName = vault_lockboxes_simple,
			name = CamdozaalStrings.config_vault_lockboxes_simple_name,
			description = CamdozaalStrings.config_vault_lockboxes_simple_description,
			section = section_vault
		) default boolean highlightVaultLockboxesSimple() {
			return true;
		}

		String vault_lockboxes_simple_color = "vault_lockboxes_simple_color";
		@ConfigItem(
			position = 2,
			keyName = vault_lockboxes_simple_color,
			name = CamdozaalStrings.config_vault_lockboxes_simple_color_name,
			description = CamdozaalStrings.config_vault_lockboxes_simple_color_description,
			section = section_vault
		) default Color getVaultLockboxesSimpleColor() {
			return new Color(0xE6A97518, true);
		}

		String vault_lockboxes_elaborate_highlight = "vault_lockboxes_elaborate_highlight";
		@ConfigItem(
			position = 3,
			keyName = vault_lockboxes_elaborate_highlight,
			name = CamdozaalStrings.config_vault_lockboxes_elaborate_name,
			description = CamdozaalStrings.config_vault_lockboxes_elaborate_description,
			section = section_vault
		) default boolean highlightVaultLockboxesElaborate() {
			return true;
		}

		String vault_lockboxes_elaborate_color = "vault_lockboxes_elaborate_color";
		@ConfigItem(
			position = 4,
			keyName = vault_lockboxes_elaborate_color,
			name = CamdozaalStrings.config_vault_lockboxes_elaborate_color_name,
			description = CamdozaalStrings.config_vault_lockboxes_elaborate_color_description,
			section = section_vault
		) default Color getVaultLockboxesElaborateColor() {
			return new Color(0xE6FFE600, true);
		}

		String vault_lockboxes_ornate_highlight = "vault_lockboxes_ornate_highlight";
		@ConfigItem(
			position = 5,
			keyName = vault_lockboxes_ornate_highlight,
			name = CamdozaalStrings.config_vault_lockboxes_ornate_name,
			description = CamdozaalStrings.config_vault_lockboxes_ornate_description,
			section = section_vault
		) default boolean highlightVaultLockboxesOrnate() {
			return true;
		}

		String vault_lockboxes_ornate_color = "vault_lockboxes_ornate_color";
		@ConfigItem(
			position = 6,
			keyName = vault_lockboxes_ornate_color,
			name = CamdozaalStrings.config_vault_lockboxes_ornate_color_name,
			description = CamdozaalStrings.config_vault_lockboxes_ornate_color_description,
			section = section_vault
		) default Color getVaultLockboxesOrnateColor() {
			return new Color(0xE500FF00, true);
		}

		String vault_lockboxes_empty_highlight = "vault_lockboxes_empty_highlight";
		@ConfigItem(
			position = 7,
			keyName = vault_lockboxes_empty_highlight,
			name = CamdozaalStrings.config_vault_lockboxes_empty_name,
			description = CamdozaalStrings.config_vault_lockboxes_empty_description,
			section = section_vault
		) default boolean highlightVaultEmptyPedestals() {
			return false;
		}

		String vault_lockboxes_empty_color = "vault_lockboxes_empty_color";
		@ConfigItem(
			position = 8,
			keyName = vault_lockboxes_empty_color,
			name = CamdozaalStrings.config_vault_lockboxes_empty_color_name,
			description = CamdozaalStrings.config_vault_lockboxes_empty_color_description,
			section = section_vault
		) default Color getVaultEmptyPedestalsColor() {
			return new Color(0x4DFFFFFF, true);
		}
}
