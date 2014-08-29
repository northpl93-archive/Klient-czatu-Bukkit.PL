package me.northpl93;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;
import me.northpl93.utils.JsonUsersResponseParser;
import me.northpl93.utils.PostExecute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class UsersListenThread extends Thread
{
	public String cacheXfToken = "";
	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		Gson gson = new Gson();
		
		while(true)
		{
			String xfToken = "";
			if(Main.chatListener != null)
			{
				xfToken = ((ChatListenThread)Main.chatListener).getLoggedUser();
			}
			else
			{
				xfToken = cacheXfToken;
			}
			
			String content = PostExecute.excutePost("http://bukkit.pl/shoutbox", "_xfToken="+xfToken+"&_xfResponseType=json");
			
			if(content == null)
			{
				Main.debug("Nie mo¿na pobraæ listy u¿ytkowników: Wyst¹pi³ problem z po³¹czeniem. W¹tek zostanie zatrzymany...\n");
				this.stop();
				return;
			}
			JsonUsersResponseParser decoded_json = gson.fromJson(content, JsonUsersResponseParser.class);
			
			Document document = Jsoup.parse(decoded_json.getSidebarHtml());
			
			Elements as = document.getElementsByAttributeValue("class", "section membersOnline userList");
			int i = 0;
			String nonSplittedUsers = "";
			
			for(Element e : as)
			{
				for(Element users : e.getElementsByAttributeValue("class", "listInline"))
				{
					if(i == 1)
					{
						nonSplittedUsers = users.text().toString().replace(" ", "");
					}
				}
				i++;
			}
			
			String[] users = nonSplittedUsers.split(",");
			((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).list.removeAll();
			for(String s : users)
			{
				((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).list.add(s);
			}
			
			try
			{
				Thread.sleep(15000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
