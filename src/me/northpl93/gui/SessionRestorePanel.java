package me.northpl93.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.UsersListenThread;
import me.northpl93.WathDogThread;
import me.northpl93.utils.HttpCookieWrapper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SessionRestorePanel extends JPanel
{
	private static final long serialVersionUID = -4971220544246617432L;

	/**
	 * Create the panel.
	 */
	public SessionRestorePanel() {
		setLayout(null);
		
		JLabel lblCzyChceszPrzywrci = new JLabel("Czy chcesz przywr\u00F3ci\u0107 poprzedni\u0105 sesj\u0119?");
		lblCzyChceszPrzywrci.setHorizontalAlignment(SwingConstants.CENTER);
		lblCzyChceszPrzywrci.setBounds(10, 133, 630, 14);
		add(lblCzyChceszPrzywrci);
		
		JButton btnTak = new JButton("Tak");
		btnTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.debug("User chce przwróciæ sesjê...");
				Main.saveSession = true;
				if(Main.chatListener == null)
				{
					Main.chatListener = new ChatListenThread();
				}
				((ChatListenThread) Main.chatListener).setLoggedUser(Main.config.getStoredXfToken());
				Main.debug("Przywrócony xfToken: "+Main.config.getStoredXfToken());
				Main.loggedUserName = Main.config.getStoredNick();
				Main.debug("Przywrócony nick: "+Main.config.getStoredNick());
				Main.debug(Main.cookieHandler.getCookieStore().getURIs().toString());
				for(HttpCookieWrapper cookie : Main.config.getStoredCookies())
				{
					HttpCookie javaCookie = cookie.returnHttpCookie();
					Main.debug("Przywracanie ciasteczka: "+javaCookie+" ("+javaCookie.getDomain()+", "+javaCookie.getPath()+", "+javaCookie.getName()+", "+javaCookie.getValue()+")");
					try
					{
						Main.cookieHandler.getCookieStore().add(new URI(javaCookie.getPath()), javaCookie);
					}
					catch (URISyntaxException e1)
					{
						e1.printStackTrace();
					}
				}
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
        		Main.config.setStoredNick(null);
        		Main.config.setStoredXfToken(null);
        		Main.config.setStoredCookies(new ArrayList<HttpCookieWrapper>());
				Main.switchPanel(PanelsEnum.WELCOME_PANEL.getInstance());
			}
		});
		btnNie.setBounds(360, 158, 89, 23);
		add(btnNie);

	}
}
