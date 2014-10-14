package me.northpl93.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.HttpCookie;
import java.util.ArrayList;

import javax.swing.JFrame;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.utils.HttpCookieWrapper;
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
            	Main.debug("Otrzymano polecenie zamkniêcia programu... Sprawdzanie czy nie ma klucza sesji...");
                if(Main.chatListener != null)
                {
                	if(((ChatListenThread)Main.chatListener).getLoggedUser() != null)
                	{
	                	if(((ChatListenThread)Main.chatListener).getLoggedUser().length() > 0)
	                	{
	                		Main.debug("Znaleziono aktywny klucz sesji.");
	                		if(Main.saveSession)
	                		{
	                			Main.debug("W³¹czono opcjê zapisywania sesji. Polecenie wylogowania nie zostanie wys³ane...");
	                			
	                			ArrayList<HttpCookieWrapper> tempCookieWrapper = new ArrayList<HttpCookieWrapper>(Main.cookieHandler.getCookieStore().getCookies().size());
	                			
	                			for(HttpCookie ckts : Main.cookieHandler.getCookieStore().getCookies())
	                			{
	                				tempCookieWrapper.add(new HttpCookieWrapper(ckts));
	                			}
	                			
	                			Main.config.setStoredCookies(tempCookieWrapper);
	                			Main.config.setStoredXfToken(((ChatListenThread)Main.chatListener).getLoggedUser());
	                			Main.saveConfig();
	                			e.getWindow().dispose();
	                			return;
	                		}
	                		Main.debug("Zapamiêtywanie sesji nie jest w³¹czone. Wysy³anie polecenia wylogowania...");
	                		Main.config.setStoredNick(null);
	                		Main.config.setStoredXfToken(null);
	                		Main.config.setStoredCookies(new ArrayList<HttpCookieWrapper>());
	                		XenForoUtils.logout(((ChatListenThread)Main.chatListener).getLoggedUser());
	                		e.getWindow().dispose();
	                		Main.saveConfig();
	                		return;
	                	}
                	}
                }
                Main.debug("Nie znaleziono klucza sesji... Nie wys³ano polecenia wylogowania, ani nie zapisano sesji do configu");
                Main.saveConfig();
                e.getWindow().dispose();
            }
        });
	}
}
