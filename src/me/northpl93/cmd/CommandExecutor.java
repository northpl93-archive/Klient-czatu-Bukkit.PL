package me.northpl93.cmd;

import java.util.List;

public interface CommandExecutor
{
	public String getCommandInfo();
	public List<String> getCommandTemplates();
	public void init(CommandManager cmdManager);
	public void execute(String[] args);
}
