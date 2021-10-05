package tictac7x.camdozaal.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.ProgressBarComponent;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.camdozaal.CamdozaalConfig;
import tictac7x.camdozaal.CamdozaalPlugin;
import tictac7x.camdozaal.CamdozaalStrings;


public class CamdozaalOverlayBoost extends CamdozaalOverlay {
	private final ConfigManager configs;
	private final InfoBoxManager infoboxes;

    private final PanelComponent panel = new PanelComponent();
    private final int varbit_comdooza_boost = 12104;
    private final int varbit_comdooza_boost_duration = 12106;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final CamdozaalInfoBox infobox;

	@Inject
    public CamdozaalOverlayBoost(final Client client, final ConfigManager configs, final CamdozaalPlugin plugin, final CamdozaalConfig config, final OverlayManager overlays, final InfoBoxManager infoboxes, final ItemManager items, final ModelOutlineRenderer outlines) {
		super(client, plugin, config, overlays, outlines);
		this.configs = configs;
		this.infoboxes = infoboxes;
		setPosition(OverlayPosition.TOP_LEFT);

		infobox = new CamdozaalInfoBox(
			items.getImage(ItemID.BARRONITE_SHARDS, 5, false),
			CamdozaalStrings.boost_tooltip,
			() -> plugin.inCamdozaal() && config.showBoost() && config.getOverlayBoostStatusStyle() == CamdozaalConfig.boost_style.INFOBOX,
			() -> config.showBoostCountdown() ? String.format(CamdozaalStrings.boost_minutes, getCountdownSeconds() / 60, getCountdownSeconds() % 60) : null,
			() -> getCountdownSeconds() > 0 ? Color.green : Color.red,
			plugin
		);
    }

	@Override
	public void add() {
		overlays.add(this);
		infoboxes.addInfoBox(infobox);
	}

	@Override
	public void remove() {
		overlays.remove(this);
		infoboxes.removeInfoBox(infobox);
	}

	@Override
    public Dimension render(Graphics2D graphics) {
		if (!plugin.inCamdozaal()) return null;
		statusChanged();

		if (!config.showBoost() ||config.getOverlayBoostStatusStyle() == CamdozaalConfig.boost_style.INFOBOX) return null;

		// Find boost information.
        boolean boost = isActive();
        int boost_seconds = getCountdownSeconds();
		String boost_time = String.format(CamdozaalStrings.boost_minutes, boost_seconds / 60, boost_seconds % 60);

        panel.getChildren().clear();

        // Boost status.
        if (config.getOverlayBoostStatusStyle() == CamdozaalConfig.boost_style.SIMPLE) {
            panel.getChildren().add(LineComponent.builder()
                .left(CamdozaalStrings.boost + ":").leftColor(Color.white)
                .right(boost
                    ? (config.showBoostCountdown() && boost_seconds > 0)
                        ? boost_time
                        : CamdozaalStrings.boost_active
                    : CamdozaalStrings.boost_inactive
                ).rightColor(boost ? Color.green : Color.red)
                .build()
            );
        }

        // Boost status as progressbar.
        if (config.getOverlayBoostStatusStyle() == CamdozaalConfig.boost_style.PROGRESSBAR) {
            final ProgressBarComponent progressbar = new ProgressBarComponent();
            progressbar.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.TEXT_ONLY);

            // Progressbar colors.
            if (boost) {
				if (boost_seconds > 0) {
					progressbar.setBackgroundColor(Color.black);
					progressbar.setForegroundColor(Color.green);
					progressbar.setMaximum(3600);
					progressbar.setValue(boost_seconds);
				} else {
					progressbar.setBackgroundColor(Color.green);
				}
            } else {
                progressbar.setBackgroundColor(Color.red);
            }

            // Progressbar labels.
            if (boost) {
				if (config.showBoostCountdown() && boost_seconds > 0) {
					progressbar.setLeftLabel(CamdozaalStrings.boost);
					progressbar.setRightLabel(boost_time);
				} else {
					progressbar.setCenterLabel(CamdozaalStrings.boost + " " + CamdozaalStrings.boost_active);
				}

            } else {
                progressbar.setCenterLabel(CamdozaalStrings.boost + " " + CamdozaalStrings.boost_inactive);
            }
            panel.getChildren().add(progressbar);
        }

        return panel.render(graphics);
    }

    public void statusChanged() {
        // Boost inactive, reset date and time.
        if (!isActive() && getCountdownSecondsFromConfig() > 0) {
            configs.setConfiguration(
				config.group,
				config.boost_countdown_date,
				""
            );

			configs.setConfiguration(
				config.group,
				config.boost_countdown_seconds,
				0
			);
        }

        // Boost active, set date and time.
        if (isActive() && (getCountdownSecondsFromConfig() == 0 || getCountdownSecondsfromVarbit() < getCountdownSeconds())) {

            // Set date, when the boost varbit info was read.
            configs.setConfiguration(
                config.group,
                config.boost_countdown_date,
                format.format(new Date())
            );

            // Save boost remaining time varbit into config.
            configs.setConfiguration(
                config.group,
                config.boost_countdown_seconds,
                getCountdownSecondsfromVarbit()
            );
        }
    }

    private boolean isActive() {
        return client.getVarbitValue(varbit_comdooza_boost) == 1;
    }

    private int getCountdownSecondsFromConfig() {
        return config.getOverlayBoostCountdownTime();
    }

    private Date getCountdownDateFromConfig() {
        try {
            return format.parse(config.getOverlayBoostCountdownDate());
        } catch (Exception exception) {
            return null;
        }
    }

    private int getCountdownSecondsfromVarbit() {
        return client.getVarbitValue(varbit_comdooza_boost_duration) * 60;
    }

    public int getCountdownSeconds() {
        final Date now = new Date();
        final Date start = getCountdownDateFromConfig();

        if (start != null) {
            long milliseconds = Math.abs(now.getTime() - start.getTime());
            long minutes = TimeUnit.SECONDS.convert(milliseconds, TimeUnit.MILLISECONDS);

            return Math.max(getCountdownSecondsFromConfig() - (int) Math.min(minutes, 3600), 0);
        }

		return 0;
	}
}
