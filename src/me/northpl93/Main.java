package me.northpl93;

import java.awt.EventQueue;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.swing.JFrame;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.ChatWindow;
import me.northpl93.gui.PanelsEnum;

public class Main
{
	public static Thread chatListener     = null;
	public static Thread usersListener    = null;
	public static Thread wathDogThread    = null;
	public static JFrame window           = null;
	
	public static boolean debugOnConsole  = false;
	public static boolean debugOnChat     = false;
	public static boolean rollOnNewPost   = true;
	
	
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
		usersListener = new UsersListenThread();
	}
	
	public static void debug(String message)
	{
		if(debugOnConsole)
		{
			System.out.println(message);
		}
		
		if(debugOnChat)
		{
			((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).textArea.append("[DEBUG] "+message);
			if(Main.rollOnNewPost)
			{
				((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).scrollToDown();
			}
			Main.window.revalidate();
		}
	}
}
