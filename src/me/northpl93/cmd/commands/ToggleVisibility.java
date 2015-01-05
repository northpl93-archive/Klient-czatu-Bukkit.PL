package me.northpl93.cmd.commands;

import java.util.ArrayList;
import java.util.List;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.cmd.CommandExecutor;
import me.northpl93.cmd.CommandManager;
import me.northpl93.utils.XenForoUtils;

public class ToggleVisibility implements CommandExecutor
{
	CommandManager cmdMngr = null;

	@Override
	public String getCommandInfo()
	{
		return "Zmienia twoją widoczność";
	}

	@Override
	public List<String> getCommandTemplates()
	{
		List<String> templates = new ArrayList<String>();
		templates.add("togglevisibility");
		templates.add("toggle-visibility");
		templates.add("togglev");
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
			cmdMngr.sendMessage("Użycie: /togglevisibility true/false");
			return;
		}

		if (args.length > 1)
		{
			cmdMngr.sendMessage("Użycie: /togglevisibility true/false");
			return;
		}

		if (Main.getLoggedUserName().isEmpty()
				|| ((ChatListenThread) Main.chatListener).getLoggedUser()
						.isEmpty())
		{
			cmdMngr.sendMessage("Musisz być zalogowany, aby użyć tej komendy");
			return;
		}

		if (args[0].equalsIgnoreCase("true"))
		{
			cmdMngr.sendMessage(XenForoUtils.toggleVisibility(
					((ChatListenThread) Main.chatListener).getLoggedUser(),
					true));
		}
		else if (args[0].equalsIgnoreCase("false"))
		{
			cmdMngr.sendMessage(XenForoUtils.toggleVisibility(
					((ChatListenThread) Main.chatListener).getLoggedUser(),
					false));
		}
		else
		{
			cmdMngr.sendMessage("Użycie: /togglevisibility true/false");
			return;
		}
	}

}
