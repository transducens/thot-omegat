package org.omegat.plugins.autocomplete.thot;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.CookieHandler;
import java.net.CookieManager;

import org.omegat.core.Core;
import org.omegat.core.machinetranslators.BaseTranslate;
import org.omegat.gui.editor.autocompleter.AutoCompleterItem;
import org.omegat.plugins.autocomplete.thot.gui.ThotAutoCompleteView;
import org.omegat.plugins.autocomplete.thot.iface.ThotInterface;
import org.omegat.plugins.autocomplete.thot.preferences.ThotPreferences;
import org.omegat.util.Language;

/**
 * Main class of the OmegaT plugin. Extends BaseTranslate for creating the
 * translation segments whenever the user proceeds to the next sentence.
 * 
 * @author Daniel Torregrosa
 * 
 */
public class ThotPTS extends BaseTranslate {

	private static ThotPTS self;

	public static void useSuggestion(AutoCompleterItem item) {
		// if (item != null)
		// self.iface.select(new SelectionInput(item.extras[0], 0));
	}

	public static boolean isEnabled()
	{
		return self.enabled;
	}
	
	/**
	 * Initialize and add the plugin
	 */
	public ThotPTS() {
		self = this;
		new ThotMenu();
		addThotView();
//		reflection();
		ThotPreferences.init();
	}

//	protected void reflection() {
//		try {
//			Field max_popup_width = org.omegat.gui.editor.autocompleter.AutoCompleter.class
//					.getDeclaredField("MAX_POPUP_WIDTH");
//			max_popup_width.setAccessible(true);
//
//			Field modifiersField = Field.class.getDeclaredField("modifiers");
//			modifiersField.setAccessible(true);
//			modifiersField.setInt(max_popup_width, max_popup_width.getModifiers() & ~Modifier.FINAL);
//			
//			max_popup_width.set(null, 100000);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	@Override
	protected String getPreferenceName() {
		return "Thot";
	}

	public String getName() {
		return "Thot";
	}

	@Override
	protected String translate(Language sLang, Language tLang, String text) {
		return ThotInterface.ThotTranslate(text);
	}

	public static void addThotView() {
		Core.getEditor().getAutoCompleter().addView(new ThotAutoCompleteView());
	}
}
