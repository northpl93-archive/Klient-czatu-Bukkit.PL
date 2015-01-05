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
		return "Wyświetla listę komend";
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
		cmdMngr.sendMessage("Lista wszystkich komend: \n");
		for (CommandExecutor exe : cmdMngr.getCommands())
		{
			StringBuilder sb = new StringBuilder();

			sb.append("» ");
			sb.append(exe.getCommandTemplates().get(0)); // Jako główna komenda jest wyświetlana pierwsza opcja z listy dostępnych
			sb.append(" - ");
			sb.append(exe.getCommandInfo());
			sb.append("\n");
			cmdMngr.sendMessage(sb.toString());
			sb = null;
		}
	}
}
