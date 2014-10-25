package me.northpl93.cmd;

import java.util.ArrayList;
import java.util.Arrays;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;

public class CommandManager
{
	private ArrayList<CommandExecutor> cmds = null;
	
	public CommandManager()
	{
		cmds = new ArrayList<CommandExecutor>();
	}
	
	public void registerCommand(CommandExecutor exec)
	{
		cmds.add(exec);
		exec.init(this);
	}
	
	public void handleCommand(String fullText) //Tekst wpisany na czat bez slasha na pocz�tku
	{
		String[] splitted = fullText.split(" ");
		
		for(CommandExecutor e : cmds)
		{
			if(e.getCommandTemplates().contains(splitted[0]))
			{
				String[] args = {};
				if(splitted.length > 1)
				{
					args = Arrays.copyOfRange(splitted, 1, splitted.length);
				}
				e.execute(args);
				return;
			}
		}
		((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance())
		.addMessage("Nie znaleziono komendy! Wpisz /help aby uzyskać listę komend");
	}
	
	public ArrayList<CommandExecutor> getCommands()
	{
		return cmds;
	}
	
	public void sendMessage(String msg)
	{
		((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).addMessage(msg);
	}
}
