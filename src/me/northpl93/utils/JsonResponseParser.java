package me.northpl93.utils;

public class JsonResponseParser
{
	private String templateHtml;
	private int reverse;
	private int lastrefresh;
	
	public JsonResponseParser()
	{
	}
	
	public String getHtml()
	{
		return templateHtml;
	}
	
	public int getLatestPostId()
	{
		return lastrefresh;
	}
	
	public int getReverse()
	{
		return reverse;
	}
}
