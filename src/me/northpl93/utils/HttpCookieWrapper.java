package me.northpl93.utils;

import java.io.Serializable;
import java.net.HttpCookie;

public class HttpCookieWrapper implements Serializable
{
	private static final long serialVersionUID = -2276494427062789943L;

	private String name;
	private String value;
	private String domain;
	private String path;
	
	public HttpCookieWrapper(HttpCookie cok)
	{
		this.name = cok.getName();
		this.value = cok.getValue();
		this.domain = cok.getDomain();
		this.path = cok.getPath();
	}
	
	public HttpCookie returnHttpCookie()
	{
		HttpCookie tempCookie = new HttpCookie(this.name, this.value);
		
		tempCookie.setDomain(this.domain);
		tempCookie.setPath(this.path);
		
		return tempCookie;
	}
}
