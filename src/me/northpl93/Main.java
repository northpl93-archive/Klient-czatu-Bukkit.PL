package me.northpl93;

import java.awt.EventQueue;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.swing.JFrame;

import me.northpl93.gui.ChatWindow;
import me.northpl93.gui.PanelsEnum;

public class Main
{
	public static Thread chatListener   = null;
	public static JFrame window         = null;
	
	public static void main(String[] args)
	{
		System.out.println("start");
		CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
		
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = new ChatWindow();
                window.setContentPane(PanelsEnum.WELCOME_PANEL.getInstance());
            }
        });
        
        chatListener = new ChatListenThread();
	}
}
