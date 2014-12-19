package me.northpl93.utils;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import me.northpl93.Main;

public class XenForoUtils
{
	public static String loginUser(String username, String password)
	{
		String rawData =
				  "login="+username+"&"
				+ "password="+password+"&"
				+ "cookie_check=0&"
				+ "register=0&"
				+ "remember=0";
		String lol = PostExecute.excutePost("http://bukkit.pl/login/login", rawData);
		int positionOfToken = lol.indexOf("_xfToken");
		
		int startPosition = positionOfToken+17;
		int endPosition   = lol.indexOf("\"", startPosition);
		
		Main.debug("XenForoUtils -> loginUser -> positionOfToken: "+positionOfToken);
		Main.debug("XenForoUtils -> loginUser -> startPosition: "+startPosition);
		Main.debug("XenForoUtils -> loginUser -> endPosition: "+endPosition);
		
		return lol.substring(startPosition, endPosition);
	}
	
	public static void sendMessage(String xfToken, String message)
	{
		String rawData =
				  "message="+message+"&"
				+ "_xfToken="+xfToken+"&"
				+ "_xfResponseType=xml";
		String response = PostExecute.excutePost("http://bukkit.pl/taigachat/post.json", rawData);
		if(response.contains("Security error occurred.") || response.contains("You do not have permission to post messages."))
		{
			JOptionPane.showMessageDialog(null, "Wystąpił problem podczas wysyłania wiadomości. Prawdopodobnie się nie zalogowałeś (lub podałeś złe dane)");
		}
		Main.debug("XenForoUtils -> sendMessage -> response: "+response);
	}
	
	public static void logout(String xfToken)
	{
		String rawData =
				"_xfToken="+xfToken+"&"
				+ "_xfResponseType=json";
		String response = PostExecute.excutePost("http://bukkit.pl/logout/", rawData);
		Main.debug("XenForoUtils -> logout -> response: "+response);
	}
	
	public static String toggleVisibility(String xfToken, boolean visible)
	{
		String rawData =
				"_xfToken="+xfToken+"&"
				+ "_xfNoRedirect=1&"
				+ "_xfResponseType=json";
		if(visible)
		{
			rawData += "&visible=1";
		}
		String response = PostExecute.excutePost("http://bukkit.pl/account/toggle-visibility", rawData);
		Gson gson = new Gson();
		JsonToggleVisibilityParser object = gson.fromJson(response, JsonToggleVisibilityParser.class);
		Main.debug("XenForoUtils -> toggleVisibility -> response: "+response);
		return object.get_redirectMessage();
	}
	
	public static void removeMessage(String xfToken, String messageId)
	{
		String rawData =
				"_xfToken="+xfToken+"&"
				+ "_xfResponseType=json";
		String response = PostExecute.excutePost("http://bukkit.pl/taigachat/"+messageId+"/delete", rawData);
		Main.debug("XenForoUtils -> removeMessage -> response: "+response);
	}
}
