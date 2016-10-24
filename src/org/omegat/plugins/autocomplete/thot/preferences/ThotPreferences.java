package org.omegat.plugins.autocomplete.thot.preferences;

import org.omegat.plugins.autocomplete.thot.ThotPTS;
import org.omegat.plugins.autocomplete.thot.iface.ThotInterface;
import org.omegat.util.Preferences;

public class ThotPreferences {

	public static String THOT_THOT_CLIENT = "thot_thot_client";
	public static String THOT_THOT_IP = "thot_thot_ip";
	
	public static void update(String thot_client, String thot_ip)
	{
		Preferences.setPreference(THOT_THOT_CLIENT, thot_client);
		Preferences.setPreference(THOT_THOT_IP, thot_ip);
		
		init();
	}
	
	public static void init()
	{
		if (!Preferences.existsPreference(THOT_THOT_CLIENT))
			Preferences.setPreference(THOT_THOT_CLIENT, "thot_client");
		if (!Preferences.existsPreference(THOT_THOT_IP))
			Preferences.setPreference(THOT_THOT_IP, "127.0.0.1");
		
		ThotInterface.whereIsThotClient = Preferences.getPreference(THOT_THOT_CLIENT);
		ThotInterface.ipThot = Preferences.getPreference(THOT_THOT_IP);
	}
}
