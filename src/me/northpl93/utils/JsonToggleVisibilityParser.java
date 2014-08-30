package me.northpl93.utils;

public class JsonToggleVisibilityParser
{
	private String _redirectStatus;
	private String _redirectTarget;
	private String _redirectMessage;
	private int _visitor_conversationsUnread;
	private int _visitor_alertsUnread;
	
	public JsonToggleVisibilityParser(){}

	public String get_redirectStatus()
	{
		return _redirectStatus;
	}

	public String get_redirectTarget()
	{
		return _redirectTarget;
	}

	public String get_redirectMessage()
	{
		return _redirectMessage;
	}

	public int get_visitor_conversationsUnread()
	{
		return _visitor_conversationsUnread;
	}

	public int get_visitor_alertsUnread()
	{
		return _visitor_alertsUnread;
	}
}
