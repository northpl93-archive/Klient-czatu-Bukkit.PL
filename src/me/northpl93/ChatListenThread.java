package me.northpl93;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;
import me.northpl93.utils.JsonResponseParser;
import me.northpl93.utils.NotificationWindow;
import me.northpl93.utils.PostExecute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class ChatListenThread extends Thread
{
	private String account            = "";
	private int latestMessage         = 0;
	private Gson gson                 = null;
	private JsonResponseParser obj2   = null;
	private Document document         = null;
	private Elements as               = null;
	private String rawData            = "";
	private String lol                = "";
	
	public ChatListenThread(){}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		gson = new Gson();
		
		while(true)
		{
			rawData =
					"_xfToken="+account+"&"
					+ "lastrefresh="+latestMessage;
			
			lol = PostExecute.excutePost("http://bukkit.pl/index.php/taigachat/list.json", rawData);
			
			if(lol == null) //B³¹d z pobieraniem zawartoœci
			{
				Main.debug("Nie mo¿na pobraæ nowych wiadomoœci: Wyst¹pi³ problem z po³¹czeniem. W¹tek zostanie zatrzymany...\n");
				this.stop();
				return;
			}

			obj2 = gson.fromJson(lol, JsonResponseParser.class);
			
			document = Jsoup.parse(obj2.getHtml());
			as = document.select("li");
			
			
			for(Element element : as)
			{
				/*System.out.println("debug2");
				if(getFirstStringObject(element.getElementsByClass("username")).equalsIgnoreCase(""))
				{
					System.out.println("debug3");
					break;
				}
				System.out.println("debug4");
				
				*/
				
				StringBuilder sb = new StringBuilder();
				
				/*
				
				sb.append(getFirstStringObject(element.getElementsByClass("username")));
				sb.append(": ");*/
				
				for(Element el : element.getElementsByClass("username"))
				{
					if(!Main.loggedUserName.equalsIgnoreCase(el.text()) && !Main.window.isActive())
					{
						new NotificationWindow("Czat bukkit.pl", "Nowa wiadomoœæ od u¿ytkownika "+el.text(), NotificationWindow.Icons.CHAT.getIco());
					}
					
					sb.append(el.text());
					sb.append(": ");
				}
				
				/*Elements chatMessage = element.getElementsByClass("taigachat_messagetext");
				sb.append(getFirstStringObject(chatMessage));
				if(getFirstElement(chatMessage) != null)
				{
					for(Element ell : getFirstElement(chatMessage).getElementsByTag("img"))
					{
						sb.append(" ");
						sb.append(ell.attr("alt"));
					}
				}
				
				sb.append("\n");*/
				
				for(Element el : element.getElementsByClass("taigachat_messagetext"))
				{
					sb.append(el.text());
					
					for(Element ell : el.getElementsByTag("img"))
					{
						sb.append(" ");
						sb.append(ell.attr("alt"));
					}
					
					sb.append("\n");
				}
				
				((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).textArea.append(sb.toString());
				if(Main.rollOnNewPost)
				{
					((ChatPanel)PanelsEnum.CHAT_PANEL.getInstance()).scrollToDown();
				}
				Main.window.revalidate();
			}
			
			latestMessage = obj2.getLatestPostId();
			
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public String getLoggedUser()
	{
		return account;
	}
	
	public void setLoggedUser(String xfToken)
	{
		account = xfToken;
	}
	
	public int getLatestMessage()
	{
		return latestMessage;
	}
	
	public void setLatestMessage(int newLatestMessage)
	{
		latestMessage = newLatestMessage;
	}
	
	@SuppressWarnings("unused")
	private String getFirstStringObject(Elements elements)
	{
		if(elements.size() == 0)
		{
			return "";
		}
		return elements.get(0).text();
	}
	
	@SuppressWarnings("unused")
	private Element getFirstElement(Elements elements)
	{
		if(elements.size() == 0)
		{
			return null;
		}
		return elements.get(0);
	}
}
