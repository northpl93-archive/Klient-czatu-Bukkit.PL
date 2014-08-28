package me.northpl93.utils;

import javax.swing.JOptionPane;

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
		int position = lol.indexOf("_xfToken");
		return lol.substring(position+17, position+73);
	}
	
	public static void sendMessage(String xfToken, String message)
	{
		String rawData =
				  "message="+message+"&"
				+ "_xfToken="+xfToken+"&"
				+ "_xfResponseType=xml";
		if(PostExecute.excutePost("http://bukkit.pl/taigachat/post.json", rawData).contains("Security error occurred."))
		{
			JOptionPane.showMessageDialog(null, "Wyst¹pi³ problem podczas wysy³ania wiadomoœci. Prawdopodobnie siê nie zalogowa³eœ (lub poda³eœ z³e dane)");
		}
	}
}
