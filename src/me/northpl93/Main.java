package me.northpl93;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.time.FastDateFormat;

import me.northpl93.cmd.CommandManager;
import me.northpl93.cmd.commands.*;
import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.ChatWindow;
import me.northpl93.gui.PanelsEnum;
import me.northpl93.utils.TrayManager;

public class Main
{
	public static Thread chatListener            = null;
	public static Thread usersListener           = null;
	public static Thread wathDogThread           = null;
	private static TrayManager trayManager       = null;
	private static JFrame window                 = null;
	private static CommandManager cmdMngr        = null;
	private static CookieManager cookieHandler   = null;
	private static Configuration config          = null;
	private static File configFile               = null;
	private static File workDirectory            = null;
	
	private static String loggedUserName         = "";
	
	private static final String VERSION          = "1.4.0 INDEV";
	private static final String msgHeader        = "";
	
	
	public static void main(String[] args)
	{
		System.out.println("start "+VERSION);
		initializeConfig();
		setupLog();
		switchPanel(PanelsEnum.LOADING_PANEL.getInstance()); //Ustawienie panelu na wczytywanie
        cookieHandler = new CookieManager();
        cookieHandler.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieHandler);
        
		trayManager = new TrayManager();
		trayManager.show();
		
		chatListener = new ChatListenThread();
		usersListener = new UsersListenThread();
		cmdMngr = new CommandManager();
		registerCommands();
		
		if(config.isSessionStored())
		{
			debug("W configu jest informacja o zachowanych danych logowania");
			switchPanel(PanelsEnum.SESSION_RESTORE_PANEL.getInstance()); //Ustawienie panelu na przywracanie sesji
		}
		else
		{
			debug("W configu nie ma informacji o koniecznosci przywracania sesji");
			switchPanel(PanelsEnum.WELCOME_PANEL.getInstance()); //Ustawienie panelu na logowanie
		}
	}
	
	/**
	 * Wyświetla wiadomośc na konsoli (zawsze) i w oknie czatu (tylko gdy włączone debugowanie na czat)
	 * 
	 * @param message Wiadomość do wyświetlenia
	 */
	public static void debug(String message)
	{
		System.out.println(message);
		
		if(getConfig() != null && getConfig().isDebugOnChat())
		{
			((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).addMessage("[DEBUG] "+message);
		}
	}
	
	private static void registerCommands()
	{
		cmdMngr.registerCommand(new Help());
		cmdMngr.registerCommand(new ToggleVisibility());
	}
	
	/**
	 * Zmienia aktualnie widoczny panel
	 * 
	 * @param newPane Nowy panel do ustawienia. Najlepiej pobrany z PanelsEnum
	 * @see   PanelsEnum
	 */
	public static void switchPanel(final JPanel newPane)
	{
		if(window == null)
		{
			window = new ChatWindow();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.setContentPane(newPane);
				window.revalidate();
			}
		});
	}
	
	public static void initializeConfig()
	{
		String workingDirectory;
		String s = System.getProperty("file.separator");
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN"))
		{
		    workingDirectory = System.getenv("AppData");
		}
		else
		{
		    workingDirectory = System.getProperty("user.home");
		}
		workingDirectory += s+".bukkitpl-chat-northpl";
		
		debug("workingDirectory=="+workingDirectory);
		
		workDirectory = new File(workingDirectory);
		if(!workDirectory.exists())
		{
			debug("working directory nie istnieje. Tworzę...");
			workDirectory.mkdirs();
		}
		
		configFile = new File(workingDirectory+=s+"config.dat");
		debug("configFile=="+configFile);
		
		if(!configFile.isDirectory() && !configFile.exists())
		{
			debug("Plik configu nie istnieje. Tworzę nowy...");
			try
			{
				configFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			FileInputStream fin = new FileInputStream(configFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			config = (Configuration) ois.readObject();
			ois.close();
		}
		catch (Exception e)
		{
			debug("Błąd podczas wczytywania pliku konfiguracyjnego (pierwsze odpalenie programu?)");
			debug("Załadowany zostanie domyślny config..");
			
			config = new Configuration();
			config.setDefaults();
		}
	}
	
	public static void saveConfig()
	{
		debug("Zapisywanie configu...");
		try
		{
			FileOutputStream fout = new FileOutputStream(configFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(config);
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void setupLog()
	{
		File logFile = new File(workDirectory.getPath()+System.getProperty("file.separator")+"logs"+System.getProperty("file.separator")+FastDateFormat.getInstance("dd-MM-yyyy--HH-mm-ss").format(System.currentTimeMillis( ))+".txt");
		new File(logFile.getParent()).mkdirs();
		if(!logFile.exists())
		{
			try
			{
				logFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Log file located at: "+logFile.getAbsolutePath());
		System.out.println("Binding out stream to file...");
		PrintStream out = null;
		try
		{
			out = new PrintStream(new FileOutputStream(logFile));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		System.setOut(out);
		System.out.println("Log binded to file");
	}
	
	public static JFrame getMainWindow()
	{
		return window;
	}
	
	public static CommandManager getCommandManager()
	{
		return cmdMngr;
	}
	
	public static Configuration getConfig()
	{
		return config;
	}
	
	public static String getLoggedUserName()
	{
		return loggedUserName;
	}
	
	public static void setLoggedUserName(String _loggedUserName)
	{
		loggedUserName = _loggedUserName;
	}
	
	public static String getVersion()
	{
		return VERSION;
	}
	
	public static TrayManager getTray()
	{
		return trayManager;
	}
	
	public static String getMsgHeader()
	{
		return msgHeader;
	}
}
