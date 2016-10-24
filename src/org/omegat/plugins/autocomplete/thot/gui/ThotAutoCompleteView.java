package org.omegat.plugins.autocomplete.thot.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.apache.commons.lang.StringUtils;
import org.omegat.gui.editor.autocompleter.AutoCompleterItem;
import org.omegat.gui.editor.autocompleter.AutoCompleterListView;
import org.omegat.plugins.autocomplete.thot.ThotPTS;
import org.omegat.plugins.autocomplete.thot.iface.ThotInterface;
import org.omegat.util.StaticUtils;

public class ThotAutoCompleteView extends AutoCompleterListView {

	protected boolean partialSuggestion = false;
	ArrayList<AutoCompleterItem> lastResult = null;
	protected int itemBeingTabbed = -1;
	protected int numberWordsTabbed = 0;

	public ThotAutoCompleteView() {
		super("Thot");
	}

	@Override
	public AutoCompleterItem getSelectedValue() {
		AutoCompleterItem toRet;
		if (itemBeingTabbed >= 0) {
			AutoCompleterItem selected = lastResult.get(itemBeingTabbed);
			String words[] = selected.payload.split(" ");
			StringBuilder realSuggestion = new StringBuilder();
			int j = 0;

			while (j < numberWordsTabbed && j < words.length) {
				realSuggestion.append(words[j] + " ");
				j++;
			}
			toRet = new AutoCompleterItem(realSuggestion.toString(), new String[] { selected.extras[0] },
					selected.cursorAdjust, selected.keepSelection, selected.replacementLength);
		} else {
			toRet = super.getSelectedValue();
		}
		return toRet;
	}

	@Override
	public boolean shouldCloseOnSelection() {
		return false;
	}

	@Override
	public List<AutoCompleterItem> computeListData(String prevText, boolean contextualOnly) {

		if (!ThotPTS.isEnabled()) {
			return new ArrayList<AutoCompleterItem>();
		}
		int currentSegmentStart = prevText.length() - 1;
		int numWords = StringUtils.countMatches(prevText, " ");

		itemBeingTabbed = -1;
		numberWordsTabbed = 0;

		while (currentSegmentStart > 0 && prevText.charAt(currentSegmentStart) != ' ') {
			currentSegmentStart--;
		}

		if (currentSegmentStart != 0)
			currentSegmentStart++;

		String currentSegment = prevText.substring(currentSegmentStart);

		List<AutoCompleterItem> result = new ArrayList<AutoCompleterItem>();

		String s = ThotInterface.ThotInteractive(prevText);
		if (s != null) {
			result.add(new AutoCompleterItem(s.substring(currentSegmentStart) + " ",
					new String[] { "<html><body style=width:350px; border-bottom: 1px solid black;'>"
							+ s.substring(currentSegmentStart) + "</body></html>" },
					currentSegment.length()));
		}
		lastResult = (ArrayList<AutoCompleterItem>) result;
		return result;
	}

	protected void formatSuggestion() {
		ArrayList<AutoCompleterItem> newEntryList = new ArrayList<AutoCompleterItem>();
		AutoCompleterItem ac;
		int selectedIndex = getList().getSelectedIndex(), i, j;
		if (lastResult == null)
		{
			return;
		}
		for (i = 0; i < lastResult.size(); i++) {
			ac = lastResult.get(i);
			if (i == itemBeingTabbed) {
				String words[] = ac.payload.split(" ");
				StringBuilder toRet = new StringBuilder();
				toRet.append(
						"<html><body style=width:350px; border-bottom: 1px solid black;'><font size=-2 color=gray'>");
				toRet.append("</font><font color='red'>");
				j = 0;
				while (j < numberWordsTabbed && j < words.length) {
					toRet.append(words[j] + " ");
					j++;
				}
				toRet.append("</font>");
				while (j < words.length) {
					toRet.append(words[j] + " ");
					j++;
				}
				toRet.append("</body></html>");
				ac.extras[0] = toRet.toString();
				newEntryList.add(new AutoCompleterItem(ac.payload, ac.extras, ac.cursorAdjust, ac.keepSelection,
						ac.replacementLength));
			} else {
				ac.extras[0] = "<html><body style=width:350px; border-bottom: 1px solid black;'>" + ac.payload
						+ "</body></html>";

				newEntryList.add(ac);
			}
		}
		getList().setModel(new DefaultListModel<>());
		getList().setListData(newEntryList.toArray(new AutoCompleterItem[newEntryList.size()]));
		getList().setSelectedIndex(selectedIndex);
	}

	protected void resetTab() {
		itemBeingTabbed = -1;
		numberWordsTabbed = 0;

		formatSuggestion();
	}

	@Override
	public boolean processKeys(KeyEvent e) {
		if (StaticUtils.isKey(e, KeyEvent.VK_TAB, 0)) {
			itemBeingTabbed = getList().getSelectedIndex();
			numberWordsTabbed += 1;
			formatSuggestion();
			return true;
		} else if (!StaticUtils.isKey(e, KeyEvent.VK_ENTER, 0)) {
			resetTab();
		}

		return super.processKeys(e);
	}

	@Override
	public String itemToString(AutoCompleterItem item) {
		return item.extras[0];
	}

}
