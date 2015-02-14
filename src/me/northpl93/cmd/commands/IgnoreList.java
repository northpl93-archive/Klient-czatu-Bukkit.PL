package me.northpl93.cmd.commands;

import java.util.ArrayList;
import java.util.List;

import me.northpl93.Main;
import me.northpl93.cmd.CommandExecutor;
import me.northpl93.cmd.CommandManager;

public class IgnoreList implements CommandExecutor
{
	CommandManager cmdMngr = null;
	
	@Override
	public String getCommandInfo()
	{
		return "Lista ignorowanych userów";
	}

	@Override
	public List<String> getCommandTemplates()
	{
		List<String> templates = new ArrayList<String>();
		templates.add("ignorelist");
		templates.add("ignorelista");
		templates.add("listignore");
		templates.add("listaignore");
		templates.add("ignorujlist");
		templates.add("ignorujlista");
		return templates;
	}

	@Override
	public void init(CommandManager cmdManager)
	{
		cmdMngr = cmdManager;
	}

	@Override
	public void execute(String[] args)
	{
		StringBuffer sb = new StringBuffer();
		for(String s : Main.getConfig().getBlockedUsers())
		{
			sb.append(s);
			sb.append(", ");
		}
		cmdMngr.sendMessage(sb.toString());
		sb = null;
	}
}