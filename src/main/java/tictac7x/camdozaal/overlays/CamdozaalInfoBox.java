package tictac7x.camdozaal.overlays;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import tictac7x.camdozaal.CamdozaalPlugin;

public class CamdozaalInfoBox extends InfoBox {
	private final Supplier<Boolean> render_condition;
	private final Supplier<String> text;
	private final Supplier<Color> color;

	public CamdozaalInfoBox(
		final BufferedImage image,
		final String tooltip,
		final Supplier<Boolean> render_condition,
		final Supplier<String> text,
		final Supplier<Color> color,
		final CamdozaalPlugin plugin
	) {
		super(image, plugin);
		this.render_condition = render_condition;
		this.text = text;
		this.color = color;
		this.setTooltip(tooltip);
	}

	@Override
	public String getText() {
		return text.get();
	}

	@Override
	public Color getTextColor() {
		return color.get();
	}

	@Override
	public boolean render() {
		return render_condition.get();
	}
}
