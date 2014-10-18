package me.northpl93.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.UsersListenThread;
import me.northpl93.WathDogThread;
import me.northpl93.utils.XenForoUtils;

import javax.swing.JCheckBox;
import java.awt.Color;

public class WelcomePanel extends JPanel {
	private static final long serialVersionUID = -434001661639687739L;
	private JTextField textField; //Login
	private JTextField textField_1; //Haslo
	private JCheckBox chckbxChatDebug;
	private JCheckBox chckbxRollOnNewPost;
	private JCheckBox chckbxCzyZapamietacSesje;

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
				Main.switchPanel(PanelsEnum.LOADING_PANEL.getInstance());
				Main.debugOnChat = chckbxChatDebug.isSelected();
				Main.rollOnNewPost = chckbxRollOnNewPost.isSelected();
				Main.config.setSessionStored(chckbxCzyZapamietacSesje.isSelected());
				
				if(textField.getText() == null || textField_1.getText() == null || textField.getText().equalsIgnoreCase("") || textField_1.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Któreœ z podanych pól pozostawi³eœ puste! Nie bêdziesz móg³ pisaæ wiadomoœci :C");
					
					Main.config.setSessionStored(false); //Zeby nie probowalo przy kolejnym starcie wczytywac pustej sesji...
					Main.config.setStoredNick("");
					Main.config.setStoredPassword("");
					Main.loggedUserName = "";
					
					if(Main.chatListener == null)
					{
						Main.chatListener = new ChatListenThread();
					}
					((ChatListenThread) Main.chatListener).setLoggedUser("");
					Main.chatListener.start(); //Odpalanie pobierania postow z shoutboxa
					if(Main.usersListener == null)
					{
						Main.usersListener = new UsersListenThread();
					}
					Main.usersListener.start();
					Main.wathDogThread = new WathDogThread();
					Main.wathDogThread.start();
					Main.switchPanel(PanelsEnum.CHAT_PANEL.getInstance());
					return;
				}
				
				((ChatListenThread) Main.chatListener).setLoggedUser(XenForoUtils.loginUser(textField.getText(), textField_1.getText()));
				Main.loggedUserName = textField.getText(); //Zapisanie nazwy zalogowanego u¿ytkownika
				Main.config.setStoredNick(textField.getText());
				Main.config.setStoredPassword(textField_1.getText());
				Main.chatListener.start(); //Odpalanie pobierania postow z shoutboxa
				Main.usersListener.start();
				Main.wathDogThread = new WathDogThread();
				Main.wathDogThread.start();
				Main.switchPanel(PanelsEnum.CHAT_PANEL.getInstance());
				return;
			}
		});
		
		btnWbijam.setBounds(289, 267, 85, 23);
		add(btnWbijam);
		
		textField = new JTextField();
		textField.setBounds(93, 87, 124, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField(); //Pole na has³o
		textField_1.setBounds(93, 118, 124, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(37, 90, 46, 14);
		add(lblNick);
		
		JLabel lblHaso = new JLabel("Has\u0142o");
		lblHaso.setBounds(37, 121, 46, 14);
		add(lblHaso);
		
		JLabel lblZalogujSiDo = new JLabel("Zaloguj si\u0119 do Bukkit.PL");
		lblZalogujSiDo.setBounds(75, 62, 142, 14);
		add(lblZalogujSiDo);
		
		JLabel lblUycieNiepoprawnychDanych = new JLabel("U\u017Cycie niepoprawnych danych spowoduje, \u017Ce nie b\u0119dziesz m\u00F3g\u0142 pisa\u0107!");
		lblUycieNiepoprawnychDanych.setHorizontalAlignment(SwingConstants.CENTER);
		lblUycieNiepoprawnychDanych.setVerticalAlignment(SwingConstants.TOP);
		lblUycieNiepoprawnychDanych.setBounds(103, 233, 440, 23);
		add(lblUycieNiepoprawnychDanych);
		
		JLabel lblSkonfigurujOpcje = new JLabel("Skonfiguruj opcje");
		lblSkonfigurujOpcje.setBounds(457, 62, 110, 14);
		add(lblSkonfigurujOpcje);
		
		chckbxChatDebug = new JCheckBox("Debuguj na czat");
		chckbxChatDebug.setBounds(414, 86, 230, 23);
		add(chckbxChatDebug);
		
		chckbxRollOnNewPost = new JCheckBox("Przewijaj gdy pojawi si\u0119 nowy post");
		chckbxRollOnNewPost.setSelected(true);
		chckbxRollOnNewPost.setBounds(414, 112, 230, 23);
		add(chckbxRollOnNewPost);
		
		JLabel lblWersjaLol = new JLabel("Wersja "+Main.VERSION);
		lblWersjaLol.setForeground(Color.LIGHT_GRAY);
		lblWersjaLol.setBounds(0, 303, 152, 14);
		add(lblWersjaLol);
		
		chckbxCzyZapamietacSesje = new JCheckBox("Zapami\u0119ta\u0107 sesj\u0119?");
		chckbxCzyZapamietacSesje.setBounds(75, 145, 142, 23);
		add(chckbxCzyZapamietacSesje);

	}
}
