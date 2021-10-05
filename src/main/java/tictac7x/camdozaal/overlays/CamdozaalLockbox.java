package tictac7x.camdozaal.overlays;

import javax.annotation.Nullable;
import net.runelite.api.Client;
import net.runelite.api.GameObject;

public class CamdozaalLockbox {
	public enum Type { SIMPLE, ELABORATE, ORNATE }

	private final Client client;
	private final int x;
	private final int y;
	private final int varbit;
	private final Type type;
	private GameObject lockbox = null;

	private final int EMPTY = 0;

	public CamdozaalLockbox(final Client client, final int x, final int y, final int varbit, final Type type) {
		this.client = client;
		this.x = x;
		this.y = y;
		this.varbit = varbit;
		this.type = type;
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public boolean isEmpty() {
		return getVarbit() == EMPTY;
	}

	public boolean isSimple() {
		return !isEmpty() && type == Type.SIMPLE;
	}

	public boolean isElaborate() {
		return !isEmpty() && type == Type.ELABORATE;
	}

	public boolean isOrnate() {
		return !isEmpty() && type == Type.ORNATE;
	}

	public void setLockbox(final @Nullable GameObject lockbox) {
		this.lockbox = lockbox;
	}

	@Nullable
	public GameObject getLockbox() {
		return lockbox;
	}

	private int getVarbit() {
		return client.getVarbitValue(varbit);
	}
}
