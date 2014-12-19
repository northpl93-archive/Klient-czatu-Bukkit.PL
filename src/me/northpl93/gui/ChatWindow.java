package me.northpl93.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.utils.XenForoUtils;

public class ChatWindow extends JFrame
{
	private static final long serialVersionUID = 4165050661670347724L;
	
	public ChatWindow()
	{
		super("Klient czatu Bukkit.PL (By NorthPL)");
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(650, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	Main.switchPanel(PanelsEnum.LOADING_PANEL.getInstance());
            	Main.debug("Otrzymano polecenie zamknięcia programu... Sprawdzanie czy nie ma klucza sesji...");
                if(Main.chatListener != null)
                {
                	if(((ChatListenThread)Main.chatListener).getLoggedUser() != null)
                	{
	                	if(((ChatListenThread)Main.chatListener).getLoggedUser().length() > 0)
	                	{
	                		Main.debug("Znaleziono aktywny klucz sesji.");
	                		if(Main.getConfig().isSessionStored())
	                		{
	                			Main.debug("Włączono opcję zapisywania sesji.");
	                			XenForoUtils.logout(((ChatListenThread)Main.chatListener).getLoggedUser());
	                			Main.saveConfig();
	                			e.getWindow().dispose();
	                			return;
	                		}
	                		Main.debug("Zapamiętywanie sesji nie jest właczone. Wysyłanie polecenia wylogowania...");
	                		Main.getConfig().setStoredNick("");
	                		Main.getConfig().setStoredPassword("");
	                		Main.getConfig().setSessionStored(false);
	                		XenForoUtils.logout(((ChatListenThread)Main.chatListener).getLoggedUser());
	                		Main.saveConfig();
	                		e.getWindow().dispose();
	                		return;
	                	}
                	}
                }
                Main.debug("Nie znaleziono klucza sesji... Nie wys�ano polecenia wylogowania, ani nie zapisano sesji do configu");
                Main.saveConfig();
                e.getWindow().dispose();
                Main.getTray().hide();
            }
        });
	}
}
