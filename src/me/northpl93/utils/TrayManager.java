package me.northpl93.utils;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import me.northpl93.Main;

public class TrayManager
{
	private PopupMenu popup;
	private TrayIcon trayIcon;
	private SystemTray tray;
	
	public TrayManager()
	{
		setup();
	}
	
	private void setup()
	{
		Main.debug("TrayManager -> Setup()");
		if (!SystemTray.isSupported())
		{
			Main.debug("SystemTray is not supported");
			return;
		}
		popup = new PopupMenu();
		try
		{
			trayIcon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/images/chat.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		tray = SystemTray.getSystemTray();
       
		MenuItem showhide = new MenuItem("Pokaż/ukryj okno");
		MenuItem settings = new MenuItem("Ustawienia");
		MenuItem exit = new MenuItem("Zamknij czat");
       
		popup.add(showhide);
		popup.add(settings);
		popup.add(exit);
		trayIcon.setPopupMenu(popup);
	}
	
	public void show()
	{
		try
		{
			tray.add(trayIcon);
		}
		catch (AWTException e)
		{
			Main.debug("TrayIcon could not be added.");
		}
	}
	
	public void hide()
	{
		tray.remove(trayIcon);
	}
	
	public void showMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				trayIcon.displayMessage(Main.getMsgHeader(), message, TrayIcon.MessageType.INFO);
			}
		});
	}
}
