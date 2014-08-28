package me.northpl93.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.utils.XenForoUtils;

public class WelcomePanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		setLayout(null);
		
		JButton btnWbijam = new JButton("Wbijam!");
		btnWbijam.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(textField.getText() == null || textField_1.getText() == null || textField.getText().equalsIgnoreCase("") || textField_1.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Któreœ z podanych pól pozostawi³eœ puste! Nie bêdziesz móg³ pisaæ wiadomoœci :C");
			        
					SwingUtilities.invokeLater(new Runnable()
					{
			            @Override
			            public void run()
			            {
							Main.window.setContentPane(PanelsEnum.CHAT_PANEL.getInstance());
							Main.window.revalidate();
			            }
			        });
					
					((ChatListenThread) Main.chatListener).setLoggedUser("");
					Main.chatListener.start(); //Odpalanie pobierania postow z shoutboxa
					return;
				}
				
				SwingUtilities.invokeLater(new Runnable()
				{
		            @Override
		            public void run()
		            {
						Main.window.setContentPane(PanelsEnum.CHAT_PANEL.getInstance());
						Main.window.revalidate();
		            }
		        });
				
				((ChatListenThread) Main.chatListener).setLoggedUser(XenForoUtils.loginUser(textField.getText(), textField_1.getText()));
				Main.chatListener.start(); //Odpalanie pobierania postow z shoutboxa
				return;
			}
		});
		
		btnWbijam.setBounds(199, 206, 85, 23);
		add(btnWbijam);
		
		textField = new JTextField();
		textField.setBounds(233, 87, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(233, 118, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(177, 90, 46, 14);
		add(lblNick);
		
		JLabel lblHaso = new JLabel("Has\u0142o");
		lblHaso.setBounds(177, 121, 46, 14);
		add(lblHaso);
		
		JLabel lblZalogujSiDo = new JLabel("Zaloguj si\u0119 do Bukkit.PL");
		lblZalogujSiDo.setBounds(177, 62, 142, 14);
		add(lblZalogujSiDo);
		
		JLabel lblUycieNiepoprawnychDanych = new JLabel("U\u017Cycie niepoprawnych danych spowoduje, \u017Ce nie b\u0119dziesz m\u00F3g\u0142 pisa\u0107!");
		lblUycieNiepoprawnychDanych.setHorizontalAlignment(SwingConstants.CENTER);
		lblUycieNiepoprawnychDanych.setVerticalAlignment(SwingConstants.TOP);
		lblUycieNiepoprawnychDanych.setBounds(37, 172, 440, 23);
		add(lblUycieNiepoprawnychDanych);

	}
}
