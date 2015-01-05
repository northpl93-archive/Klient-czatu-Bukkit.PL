package me.northpl93.cmd.commands;

import java.util.ArrayList;
import java.util.List;

import me.northpl93.cmd.CommandExecutor;
import me.northpl93.cmd.CommandManager;
import me.northpl93.utils.NotificationWindow;

public class FakeNotification implements CommandExecutor
{
	@Override
	public String getCommandInfo()
	{
		return "Wyświetla okienko z powiadomieniem";
	}

	@Override
	public List<String> getCommandTemplates()
	{
		List<String> templates = new ArrayList<String>();
		templates.add("fakenotification");
		templates.add("fakenotifi");
		templates.add("fakenoti");
		return templates;
	}

	@Override
	public void init(CommandManager cmdManager)
	{

	}

	@Override
	public void execute(String[] args)
	{
		new NotificationWindow("Test", "test",
				NotificationWindow.Icons.CHAT.getIco());
	}
}
