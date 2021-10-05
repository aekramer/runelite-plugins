package tictac7x.camdozaal;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CamdozaalPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(tictac7x.camdozaal.CamdozaalPlugin.class);
		RuneLite.main(args);
	}
}