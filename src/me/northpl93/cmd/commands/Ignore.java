package me.northpl93.cmd.commands;

import java.util.ArrayList;
import java.util.List;

import me.northpl93.Main;
import me.northpl93.cmd.CommandExecutor;
import me.northpl93.cmd.CommandManager;

public class Ignore implements CommandExecutor
{
	private CommandManager cmdMngr = null;

	@Override
	public String getCommandInfo()
	{
		return "Ignoruje danego użytkownika. Ponowne użycie usuwa z listy ignorowanych";
	}

	@Override
	public List<String> getCommandTemplates()
	{
		List<String> templates = new ArrayList<String>();
		templates.add("ignore");
		templates.add("ignoruj");
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
		if (args.length == 0)
		{
			cmdMngr.sendMessage("Uzycie: /ignore nick");
			return;
		}
		else if (args.length > 1)
		{
			cmdMngr.sendMessage("Uzycie: /ignore nick");
			return;
		}

		if (Main.getConfig().getBlockedUsers().contains(args[0]))
		{
			Main.getConfig().getBlockedUsers().remove(args[0]);
			cmdMngr.sendMessage(args[0] + " usuniety z listy ignorowanych");
		}
		else
		{
			Main.getConfig().getBlockedUsers().add(args[0]);
			cmdMngr.sendMessage(args[0] + " dodany do listy ignorowanych");
		}
	}
}
