package me.northpl93.cmd.commands;

import java.util.ArrayList;
import java.util.List;

import me.northpl93.cmd.CommandExecutor;
import me.northpl93.cmd.CommandManager;

public class Help implements CommandExecutor
{
	CommandManager cmdMngr = null;
	
	@Override
	public String getCommandInfo()
	{
		return "Wyœwietla listê komend";
	}

	@Override
	public List<String> getCommandTemplates()
	{
		List<String> templates = new ArrayList<String>();
		templates.add("help");
		templates.add("chelp");
		templates.add("pomoc");
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
		cmdMngr.sendMessage("Lista wszystkich komend: ");
		for(CommandExecutor exe : cmdMngr.getCommands())
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("* /");
			sb.append(exe.getCommandTemplates().get(0)); //Jako g³ówna komenda jest wyœwietlana pierwsza opcja z listy dostêpnych
			sb.append(" - ");
			sb.append(exe.getCommandInfo());
			cmdMngr.sendMessage(sb.toString());
			sb = null;
		}
	}
}
