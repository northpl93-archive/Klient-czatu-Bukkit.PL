package me.northpl93.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.UsersListenThread;
import me.northpl93.WathDogThread;
import me.northpl93.utils.XenForoUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SessionRestorePanel extends JPanel
{
	private static final long serialVersionUID = -4971220544246617432L;

	/**
	 * Create the panel.
	 */
	public SessionRestorePanel() {
		setLayout(null);
		
		JLabel lblCzyChceszPrzywrci = new JLabel("Czy chcesz zalogowa\u0107 si\u0119 ponownie poprzednimi danymi?");
		lblCzyChceszPrzywrci.setHorizontalAlignment(SwingConstants.CENTER);
		lblCzyChceszPrzywrci.setBounds(10, 133, 630, 14);
		add(lblCzyChceszPrzywrci);
		
		JButton btnTak = new JButton("Tak");
		btnTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.debug("User chce przwróciæ sesjê...");
				Main.getConfig().setSessionStored(true);
				if(Main.chatListener == null)
				{
					Main.chatListener = new ChatListenThread();
				}
				((ChatListenThread) Main.chatListener).setLoggedUser(XenForoUtils.loginUser(Main.getConfig().getStoredNick(), Main.getConfig().getStoredPassword()));
				Main.loggedUserName = Main.getConfig().getStoredNick();
				Main.debug("Przywrócony nick: "+Main.getConfig().getStoredNick());
				Main.debug("Przywrocono sesje!");
				Main.chatListener.start(); //Odpalanie pobierania postow z shoutboxa
				if(Main.usersListener == null)
				{
					Main.usersListener = new UsersListenThread();
				}
				Main.usersListener.start();
				Main.wathDogThread = new WathDogThread();
				Main.wathDogThread.start();
				Main.switchPanel(PanelsEnum.CHAT_PANEL.getInstance());
			}
		});
		btnTak.setBounds(187, 158, 89, 23);
		add(btnTak);
		
		JButton btnNie = new JButton("Nie");
		btnNie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.debug("User nie chcia³ przywracaæ sesji.");
        		Main.getConfig().setStoredNick(null);
        		Main.getConfig().setStoredPassword("");
        		Main.getConfig().setSessionStored(false);
				Main.switchPanel(PanelsEnum.WELCOME_PANEL.getInstance());
			}
		});
		btnNie.setBounds(360, 158, 89, 23);
		add(btnNie);

	}
}
