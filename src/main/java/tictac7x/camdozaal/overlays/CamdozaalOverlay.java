package tictac7x.camdozaal.overlays;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Perspective;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.camdozaal.CamdozaalConfig;
import tictac7x.camdozaal.CamdozaalPlugin;

public abstract class CamdozaalOverlay extends Overlay {
	protected final Client client;
	protected final CamdozaalPlugin plugin;
	protected final CamdozaalConfig config;
	protected final OverlayManager overlays;
	protected final ModelOutlineRenderer outlines;

	public CamdozaalOverlay(final Client client, final CamdozaalPlugin plugin, final CamdozaalConfig config, final OverlayManager overlays, final ModelOutlineRenderer outlines) {
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.overlays = overlays;
		this.outlines = outlines;
	}

	public void add() {
		overlays.add(this);
	}

	public void remove() {
		overlays.remove(this);
	}

	protected void renderTile(final Graphics2D graphics, final GameObject object, final Color color) {
		Polygon polygon = Perspective.getCanvasTilePoly(client, object.getLocalLocation());
		if (polygon != null) OverlayUtil.renderPolygon(graphics, polygon, color);
	}

	protected void renderClickbox(final Graphics2D graphics, final GameObject object, final Color color) {
		final Shape clickbox = object.getClickbox();
		if (clickbox != null) {

			// Area border.
			graphics.setColor(color);
			graphics.draw(clickbox);

			// Area fill.
			Color fill = new Color(color.getRed(), color.getGreen(), color.getBlue(), 30);
			graphics.setColor(fill);
			graphics.fill(clickbox);
		}

	}

	protected void renderOutline(final TileObject object, final Color color) {
		outlines.drawOutline(object, 4, color, 4);
	}
}
