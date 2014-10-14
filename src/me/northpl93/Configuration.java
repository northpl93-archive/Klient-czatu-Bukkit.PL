package me.northpl93;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.northpl93.utils.HttpCookieWrapper;

public class Configuration implements Serializable
{
	private static final long serialVersionUID    = 1; //Wersja configu 1
	
	private List<String> blockedUsers      = null;
	private String storedNick              = null;
	private String storedXfToken           = null;
	private List<HttpCookieWrapper> storedCookies = null;          
	
	/**
	 * Metoda która ustawi wartoœci na domyœlne
	 */
	public void setDefaults()
	{
		storedXfToken = "";
		blockedUsers = new ArrayList<String>();
		storedCookies = new ArrayList<HttpCookieWrapper>();
		storedNick = "";
	}
	
	public List<String> getBlockedUsers()
	{
		return blockedUsers;
	}
	
	public void setBlockedUsers(List<String> blockedUsers)
	{
		this.blockedUsers = blockedUsers;
	}
	
	public List<HttpCookieWrapper> getStoredCookies()
	{
		return storedCookies;
	}
	
	public void setStoredCookies(List<HttpCookieWrapper> list)
	{
		this.storedCookies = list;
	}
	
	public String getStoredNick()
	{
		return storedNick;
	}
	
	public void setStoredNick(String storedNick)
	{
		this.storedNick = storedNick;
	}

	public String getStoredXfToken()
	{
		return storedXfToken;
	}

	public void setStoredXfToken(String storedXfToken)
	{
		this.storedXfToken = storedXfToken;
	}
}
