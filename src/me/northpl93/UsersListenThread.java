package me.northpl93;

import java.util.ArrayList;
import java.util.Arrays;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;
import me.northpl93.utils.JsonUsersResponseParser;
import me.northpl93.utils.NotificationWindow;
import me.northpl93.utils.PostExecute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class UsersListenThread extends Thread
{
	public String cacheXfToken       = "";
	private ArrayList<String> latestUsers = null;
	private ArrayList<String> newUsers    = null;
	
	@SuppressWarnings({ "deprecation", "unchecked" })
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
				if(s.equalsIgnoreCase(Main.getLoggedUserName()))
				{
					((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).list.add(s+" (Ty)");
				}
				else
				{
					((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).list.add(s);
				}
			}
			
			newUsers = new ArrayList<String>(Arrays.asList(users));
			
			if(latestUsers != null)
			{	
				for(String s : latestUsers)
				{
					if(!newUsers.contains(s))
					{
						if(!Main.getMainWindow().isActive())
						{
							new NotificationWindow("Czat bukkit.pl", "U¿ytkownik "+s+" opuœci³ czat!", NotificationWindow.Icons.USER.getIco());
						}
						((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).addMessage("[INFO] - U¿ytkownik "+s+" opuœci³ czat\n");
					}
				}
				
				for(String s : newUsers)
				{
					if(!latestUsers.contains(s))
					{
						if(!Main.getMainWindow().isActive())
						{
							new NotificationWindow("Czat bukkit.pl", "U¿ytkownik "+s+" do³¹czy³ do czatu!", NotificationWindow.Icons.USER.getIco());
						}
						((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).addMessage("[INFO] + U¿ytkownik "+s+" do³¹czy³ do czatu\n");
					}
				}
			}
			
			latestUsers = (ArrayList<String>) newUsers.clone();
			newUsers = null;
			
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
