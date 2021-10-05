package tictac7x.camdozaal.overlays;

import com.google.common.collect.ImmutableSet;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.camdozaal.CamdozaalConfig;
import tictac7x.camdozaal.CamdozaalPlugin;

public class CamdozaalOverlayVault extends CamdozaalOverlay {
	private final Set<GameObject> barriers = new HashSet<>();

	private final Set<Integer> LOCKBOXES = ImmutableSet.of(41379, 41380, 41381, 41382, 41383, 41384, 41385, 41386, 41387, 41388, 41389, 41390, 41391, 51544);
	private final Set<Integer> BARRIERS = ImmutableSet.of();

	private final List<CamdozaalLockbox> lockboxes = Arrays.asList(
		new CamdozaalLockbox(client, 2956, 5829, 12078, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2960, 5832, 12079, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2972, 5837, 12080, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2976, 5829, 12081, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2982, 5833, 12082, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2984, 5825, 12083, CamdozaalLockbox.Type.SIMPLE),
		new CamdozaalLockbox(client, 2959, 5842, 12084, CamdozaalLockbox.Type.ELABORATE),
		new CamdozaalLockbox(client, 2964, 5838, 12085, CamdozaalLockbox.Type.ELABORATE),
		new CamdozaalLockbox(client, 2974, 5845, 12086, CamdozaalLockbox.Type.ELABORATE),
		new CamdozaalLockbox(client, 2982, 5839, 12087, CamdozaalLockbox.Type.ELABORATE),
		new CamdozaalLockbox(client, 2955, 5855, 12088, CamdozaalLockbox.Type.ORNATE),
		new CamdozaalLockbox(client, 2969, 5855, 12089, CamdozaalLockbox.Type.ORNATE),
		new CamdozaalLockbox(client, 2984, 5855, 12090, CamdozaalLockbox.Type.ORNATE)
	);

	private final int VAULT_X_LEFT = 2947;
	private final int VAULT_X_RIGHT = 2992;
	private final int VAULT_Y_BOTTOM = 5812;
	private final int VAULT_Y_TOP = 5866;

    @Inject
    public CamdozaalOverlayVault(final Client client, final CamdozaalPlugin plugin, final CamdozaalConfig config, final OverlayManager overlays, final ModelOutlineRenderer outlines) {
		super(client, plugin, config, overlays, outlines);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

	public void lockboxOrBarrierSpawned(final GameObject object) {
		final int id = object.getId();

		// Lockboxes.
		if (LOCKBOXES.contains(id)) {
			final WorldPoint location = object.getWorldLocation();
			lockboxes.stream().filter(
				lockbox ->
					lockbox.getX() == location.getX() &&
					lockbox.getY() == location.getY()
			).findFirst().ifPresent(
				lockbox -> lockbox.setLockbox(object)
			);
		}

		// Barriers.
		if (BARRIERS.contains(id)) barriers.add(object);
	}

	public void lockboxOrBarrierDespawned(final GameObject object) {
		final int id = object.getId();

		// Lockboxes.
		if (LOCKBOXES.contains(id)) {
			lockboxes.stream().filter(
				lockbox -> lockbox.getX() == object.getX() && lockbox.getY() == object.getY()
			).findFirst().ifPresent(
				lockbox -> lockbox.setLockbox(null)
			);
		}

		// Barriers.
		if (BARRIERS.contains(id)) barriers.remove(object);
	}

    public void clear() {
		barriers.clear();

		for (final CamdozaalLockbox lockbox : lockboxes) {
			lockbox.setLockbox(null);
		}
    }

    @Override
    public Dimension render(Graphics2D graphics) {
		final boolean in_camdozaal = plugin.inCamdozaal();

		// Lockboxes.
		if (in_camdozaal && nearVault()) {
			for (final CamdozaalLockbox lockbox : lockboxes) {
				final GameObject object = lockbox.getLockbox();
				if (object == null) continue;

				if (lockbox.isEmpty() && config.highlightVaultEmptyPedestals()) {
					renderClickbox(graphics, object, config.getVaultEmptyPedestalsColor());
				} else if (lockbox.isSimple() && config.highlightVaultLockboxesSimple()) {
					renderClickbox(graphics, object, config.getVaultLockboxesSimpleColor());
				} else if (lockbox.isElaborate() && config.highlightVaultLockboxesElaborate()) {
					renderClickbox(graphics, object, config.getVaultLockboxesElaborateColor());
				} else if (lockbox.isOrnate() && config.highlightVaultLockboxesOrnate()) {
					renderClickbox(graphics, object, config.getVaultLockboxesOrnateColor());
				}
			}
		}

        return null;
    }

	private boolean nearVault() {
		final Player player = client.getLocalPlayer();

		if (player != null) {
			final WorldPoint location = player.getWorldLocation();
			final int x = location.getX();
			final int y = location.getY();

			return (
				VAULT_X_LEFT <= x && x <= VAULT_X_RIGHT &&
				VAULT_Y_BOTTOM <= y && y <= VAULT_Y_TOP
			);
		}

		return false;
	}
}
