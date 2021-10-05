package tictac7x.camdozaal.overlays;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.Client;
import net.runelite.api.WallObject;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import tictac7x.camdozaal.CamdozaalConfig;
import tictac7x.camdozaal.CamdozaalPlugin;

public class CamdozaalOverlayRocks extends CamdozaalOverlay {
    private final Set<WallObject> barronite_rocks = new HashSet<>();
    private final Set<Integer> BARRONITE_ROCKS = ImmutableSet.of(41547, 41548);

    @Inject
    public CamdozaalOverlayRocks(final Client client, final CamdozaalPlugin plugin, final CamdozaalConfig config, final OverlayManager overlays, final ModelOutlineRenderer outlines) {
		super(client, plugin, config, overlays, outlines);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public void barroniteRockSpawned(final WallObject wall) {
        if (BARRONITE_ROCKS.contains(wall.getId())) {
            barronite_rocks.add(wall);
        }
    }

    public void barroniteRockDespawned(final WallObject wall) {
        if (BARRONITE_ROCKS.contains(wall.getId())) {
            barronite_rocks.remove(wall);
        }
    }

    public void clear() {
        barronite_rocks.clear();
    }

    @Override
    public Dimension render(Graphics2D graphics) {
		if (plugin.inCamdozaal() && config.highlightBarroniteRocks()) {
			for (final WallObject barronite_rock : barronite_rocks) {
				renderOutline(barronite_rock, config.getBarroniteRocksHighlightColor());
			}
		}

        return null;
    }
}
