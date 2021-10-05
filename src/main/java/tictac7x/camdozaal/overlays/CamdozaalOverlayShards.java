package tictac7x.camdozaal.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemID;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.camdozaal.CamdozaalConfig;
import tictac7x.camdozaal.CamdozaalPlugin;
import tictac7x.camdozaal.CamdozaalStrings;


public class CamdozaalOverlayShards extends CamdozaalOverlay {
	private final ConfigManager configs;
    private final PanelComponent panel = new PanelComponent();

	@Nullable
	private GameObject barronite_crusher;

    private final int VARP_FORGE_SHARDS = 2953;
	private final int BARRONITE_CRUSHER = 41551;
	private final int BARRONITE_DEPOSIT_SHARDS_AVERAGE = 7;

    @Inject
    public CamdozaalOverlayShards(final Client client, final CamdozaalPlugin plugin, final CamdozaalConfig config, final ConfigManager configs, final OverlayManager overlays, final ModelOutlineRenderer outlines) {
		super(client, plugin, config, overlays, outlines);
		this.configs = configs;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    public void bankUpdated(ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.BANK.getId()) {
            configs.setConfiguration(
                config.group,
                config.barronite_shards_bank_count,
                event.getItemContainer().count(ItemID.BARRONITE_SHARDS)
            );

			configs.setConfiguration(
				config.group,
				config.barronite_deposits_bank_count,
				event.getItemContainer().count(ItemID.BARRONITE_DEPOSIT)
			);
        }
    }

	public void barroniteCrusherSpawned(final GameObject object) {
		if (object.getId() == BARRONITE_CRUSHER) barronite_crusher = object;
	}

	public void barroniteCrusherDespawned(final GameObject object) {
		if (object.getId() == BARRONITE_CRUSHER) barronite_crusher = null;
	}

    @Override
    public Dimension render(Graphics2D graphics) {
		if (!plugin.inCamdozaal() || (
			!config.showOverlayBarroniteShardsForge() &&
			!config.showOverlayBarroniteShardsInventory() &&
			!config.showOverlayBarroniteShardsBank()
		)) return null;

		// Calculate shards.
        final int shards_forge = getForgeShards();
        final int shards_inventory = getInventoryShards();
		final int deposits_inventory = getInventoryShardsDeposits();
        final int shards_bank = getBankShards();
		final int deposits_bank = getBankDeposits();
        final int shards_total = shards_forge + shards_inventory + deposits_inventory + shards_bank;

		// Barronite crusher.
		if (deposits_inventory > 0 && barronite_crusher != null) {
			renderOutline(barronite_crusher, new Color(180, 10, 230, 160));
		}

        panel.getChildren().clear();

        // Total shards.
        panel.getChildren().add(LineComponent.builder()
            .left(CamdozaalStrings.barronite_shards).leftColor(Color.white)
            .right(String.valueOf(shards_total)).rightColor(Color.white)
            .build()
        );

        // Forge shards.
        if (config.showOverlayBarroniteShardsForge() && (
            !config.showOverlayBarroniteShardsMinimal() ||
            config.showOverlayBarroniteShardsMinimal() && shards_forge > 0 && shards_forge != shards_total
        )) {
            panel.getChildren().add(LineComponent.builder()
				.left(CamdozaalStrings.barronite_shards_forge).leftColor(Color.orange)
				.right(String.valueOf(shards_forge)).rightColor(Color.white)
				.build()
            );
        }

        // Inventory shards.
        if (config.showOverlayBarroniteShardsInventory() && (
            !config.showOverlayBarroniteShardsMinimal() ||
            config.showOverlayBarroniteShardsMinimal() && (shards_inventory > 0 && shards_inventory != shards_total || deposits_inventory > 0 && deposits_inventory != shards_total)
        )) {
            panel.getChildren().add(LineComponent.builder()
                .left(CamdozaalStrings.barronite_shards_inventory).leftColor(Color.ORANGE)
                .right(deposits_inventory > 0 ? String.format(CamdozaalStrings.barronite_shards_deposits, shards_inventory, deposits_inventory) : String.valueOf(shards_inventory)).rightColor(Color.white)
                .build()
            );
        }

        // Bank shards.
        if (config.showOverlayBarroniteShardsBank() && (
            !config.showOverlayBarroniteShardsMinimal() ||
            config.showOverlayBarroniteShardsMinimal() && (shards_bank > 0 && shards_bank != shards_total || deposits_bank > 0 && deposits_bank != shards_total)
        )) {
            panel.getChildren().add(LineComponent.builder()
                .left(CamdozaalStrings.barronite_shards_bank).leftColor(Color.orange)
                .right(deposits_bank > 0 ? String.format(CamdozaalStrings.barronite_shards_deposits, shards_bank, deposits_bank) : String.valueOf(shards_bank)).rightColor(Color.white)
                .build()
            );
        }

        return panel.render(graphics);
    }

    private int getForgeShards() {
        return client.getVarpValue(VARP_FORGE_SHARDS);
    }

    private int getInventoryShards() {
        return client.getItemContainer(InventoryID.INVENTORY).count(ItemID.BARRONITE_SHARDS);
    }

	private int getInventoryShardsDeposits() {
		return client.getItemContainer(InventoryID.INVENTORY).count(ItemID.BARRONITE_DEPOSIT) * BARRONITE_DEPOSIT_SHARDS_AVERAGE;
	}

    private int getBankShards() {
        return config.getBankBarroniteShardsCount();
    }

	private int getBankDeposits() {
		return config.getBankBarroniteDepositsCount() * BARRONITE_DEPOSIT_SHARDS_AVERAGE;
	}
}
