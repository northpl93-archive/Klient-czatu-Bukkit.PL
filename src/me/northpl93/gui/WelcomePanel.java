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
import javax.swing.JCheckBox;

public class WelcomePanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JCheckBox chckbxConsoleDebug;
	private JCheckBox chckbxChatDebug;
	private JCheckBox chckbxRollOnNewPost;

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
				Main.debugOnConsole = chckbxConsoleDebug.isSelected();
				Main.debugOnChat = chckbxChatDebug.isSelected();
				Main.rollOnNewPost = chckbxRollOnNewPost.isSelected();
				
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
					Main.usersListener.start();
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
				Main.usersListener.start();
				return;
			}
		});
		
		btnWbijam.setBounds(198, 244, 85, 23);
		add(btnWbijam);
		
		textField = new JTextField();
		textField.setBounds(93, 87, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(93, 118, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(37, 90, 46, 14);
		add(lblNick);
		
		JLabel lblHaso = new JLabel("Has\u0142o");
		lblHaso.setBounds(37, 121, 46, 14);
		add(lblHaso);
		
		JLabel lblZalogujSiDo = new JLabel("Zaloguj si\u0119 do Bukkit.PL");
		lblZalogujSiDo.setBounds(37, 62, 142, 14);
		add(lblZalogujSiDo);
		
		JLabel lblUycieNiepoprawnychDanych = new JLabel("U\u017Cycie niepoprawnych danych spowoduje, \u017Ce nie b\u0119dziesz m\u00F3g\u0142 pisa\u0107!");
		lblUycieNiepoprawnychDanych.setHorizontalAlignment(SwingConstants.CENTER);
		lblUycieNiepoprawnychDanych.setVerticalAlignment(SwingConstants.TOP);
		lblUycieNiepoprawnychDanych.setBounds(37, 210, 440, 23);
		add(lblUycieNiepoprawnychDanych);
		
		JLabel lblSkonfigurujOpcje = new JLabel("Skonfiguruj opcje");
		lblSkonfigurujOpcje.setBounds(322, 62, 110, 14);
		add(lblSkonfigurujOpcje);
		
		chckbxConsoleDebug = new JCheckBox("Debuguj na konsole");
		chckbxConsoleDebug.setBounds(281, 86, 196, 23);
		add(chckbxConsoleDebug);
		
		chckbxChatDebug = new JCheckBox("Debuguj na czat");
		chckbxChatDebug.setBounds(281, 112, 196, 23);
		add(chckbxChatDebug);
		
		chckbxRollOnNewPost = new JCheckBox("Przewijaj gdy pojawi si\u0119 nowy post");
		chckbxRollOnNewPost.setSelected(true);
		chckbxRollOnNewPost.setBounds(281, 138, 196, 23);
		add(chckbxRollOnNewPost);

	}
}
