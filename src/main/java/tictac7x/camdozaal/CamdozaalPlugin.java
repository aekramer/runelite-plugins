package tictac7x.camdozaal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.camdozaal.overlays.CamdozaalOverlayRocks;
import tictac7x.camdozaal.overlays.CamdozaalOverlayBoost;
import tictac7x.camdozaal.overlays.CamdozaalOverlayShards;
import tictac7x.camdozaal.overlays.CamdozaalOverlayVault;

@Slf4j
@PluginDescriptor(
        name = CamdozaalStrings.plugin_name,
        description = CamdozaalStrings.plugin_description,
        tags = {
            CamdozaalStrings.plugin_tags_ruins,
            CamdozaalStrings.plugin_tags_camdozaal,
            CamdozaalStrings.plugin_tags_barronite,
            CamdozaalStrings.plugin_tags_shards,
			CamdozaalStrings.plugin_tags_vault
        }
)
public class CamdozaalPlugin extends Plugin {
	private final Set<Integer> CAMDOZAAL_MAP_REGIONS = ImmutableSet.of(11609, 11610, 11611, 11865, 11866, 11867, 12121, 12122, 12123);
	private boolean in_camdozaal = false;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlays;

	@Inject
	private InfoBoxManager infoboxes;

    @Inject
    private ConfigManager configs;

	@Inject
	private ItemManager items;

    @Inject
    private CamdozaalOverlayRocks rocks;

    @Inject
    private CamdozaalOverlayShards shards;

    @Inject
    private CamdozaalOverlayBoost boost;

	@Inject
	private CamdozaalOverlayVault vault;

    @Inject
    private CamdozaalConfig config;

    @Provides
    CamdozaalConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(CamdozaalConfig.class);
    }

    @Override
    protected void startUp() {
		rocks.add();
		shards.add();
		boost.add();
		vault.add();
    }

    @Override
    protected void shutDown() {
		rocks.remove();
		shards.remove();
        boost.remove();
		vault.remove();
    }

    @Subscribe
    public void onWallObjectSpawned(WallObjectSpawned event) {
		if (in_camdozaal) rocks.barroniteRockSpawned(event.getWallObject());
    }

    @Subscribe
    public void onWallObjectDespawned(WallObjectDespawned event) {
		if (in_camdozaal) rocks.barroniteRockDespawned(event.getWallObject());
    }

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event) {
		if (in_camdozaal) {
			final GameObject object = event.getGameObject();
			vault.lockboxOrBarrierSpawned(object);
			shards.barroniteCrusherSpawned(object);
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event) {
		if (in_camdozaal) {
			final GameObject object = event.getGameObject();
			vault.lockboxOrBarrierDespawned(object);
			shards.barroniteCrusherDespawned(object);
		}
	}

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
		if (in_camdozaal) shards.bankUpdated(event);
    }

	@Subscribe
	public void onGameStateChanged(GameStateChanged event) {
		if (event.getGameState() == GameState.LOADING) {
			in_camdozaal = getInCamdozaal();
			rocks.clear();
			vault.clear();
		} else if (event.getGameState() == GameState.LOGIN_SCREEN) {
			in_camdozaal = false;
		}
	}

	private boolean getInCamdozaal() {
		final int[] regions = client.getMapRegions();

		for (final int region : regions) {
			if (!CAMDOZAAL_MAP_REGIONS.contains(region)) {
				return false;
			}
		}

		return true;
	}

	public boolean inCamdozaal() {
		return in_camdozaal;
	}
}
