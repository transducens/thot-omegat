package org.omegat.plugins.autocomplete.thot.preferences;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.omegat.plugins.autocomplete.thot.iface.ThotInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThotPreferencesDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JLabel lblThotClient;
	private JTextField tbThotClient;
	private JTextField tbThotIP;
	private JLabel lblThotIp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ThotPreferencesDialog dialog = new ThotPreferencesDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ThotPreferencesDialog() {
		final JDialog pan = this;
		setBounds(100, 100, 450, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ThotPreferences.update(tbThotClient.getText(), tbThotIP.getText());
						pan.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						pan.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
				.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		{
			lblThotClient = new JLabel("Thot client");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblThotClient, 10, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblThotClient, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblThotClient);
		}
		{
			tbThotClient = new JTextField();
			tbThotClient.setText(ThotInterface.whereIsThotClient);
			tbThotClient.setToolTipText("Absolute path to thot_client. Can be \"thot_client\" if in PATH.");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tbThotClient, -2, SpringLayout.NORTH, lblThotClient);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tbThotClient, 6, SpringLayout.EAST, lblThotClient);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tbThotClient, 338, SpringLayout.EAST, lblThotClient);
			contentPanel.add(tbThotClient);
			tbThotClient.setColumns(10);
		}
		{
			tbThotIP = new JTextField();
			tbThotIP.setText(ThotInterface.ipThot);
			tbThotClient.setToolTipText("IP where thot_server is running. 127.0.0.1 if running in your own machine.");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tbThotIP, 6, SpringLayout.SOUTH, tbThotClient);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tbThotIP, 0, SpringLayout.WEST, tbThotClient);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tbThotIP, 0, SpringLayout.EAST, tbThotClient);
			tbThotIP.setColumns(10);
			contentPanel.add(tbThotIP);
		}
		{
			lblThotIp = new JLabel("Thot IP");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblThotIp, 6, SpringLayout.SOUTH, lblThotClient);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblThotIp, 10, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblThotIp);
		}
		getContentPane().setLayout(groupLayout);
	}
}
