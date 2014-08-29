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
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	Main.debug("Otrzymano polecenia zamkniêcia programu... Sprawdzanie czy nie ma klucza sesji...");
                if(Main.chatListener != null)
                {
                	if(((ChatListenThread)Main.chatListener).getLoggedUser() != null)
                	{
	                	if(((ChatListenThread)Main.chatListener).getLoggedUser().length() > 0)
	                	{
	                		Main.debug("Znaleziono aktywny klucz sesji. Wysy³anie polecenia wylogowania...");
	                		XenForoUtils.logout(((ChatListenThread)Main.chatListener).getLoggedUser());
	                		e.getWindow().dispose();
	                		return;
	                	}
                	}
                }
                Main.debug("Nie znaleziono klucza sesji... Nie wys³ano polecenia wylogowania");
                e.getWindow().dispose();
            }
        });
	}
}
