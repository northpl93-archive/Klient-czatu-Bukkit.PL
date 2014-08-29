package me.northpl93;

import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;
import me.northpl93.utils.JsonResponseParser;
import me.northpl93.utils.PostExecute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class ChatListenThread extends Thread
{
	private String account;
	private int latestMessage = 0;
	private Gson gson = null;
	
	public ChatListenThread(){}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		gson = new Gson();
		
		while(true)
		{
			String rawData =
					"_xfToken="+account+"&"
					+ "lastrefresh="+latestMessage;
			
			String lol = PostExecute.excutePost("http://bukkit.pl/index.php/taigachat/list.json", rawData);
			
			if(lol == null) //B³¹d z pobieraniem zawartoœci
			{
				Main.debug("Nie mo¿na pobraæ nowych wiadomoœci: Wyst¹pi³ problem z po³¹czeniem. W¹tek zostanie zatrzymany...\n");
				this.stop();
				return;
			}

			JsonResponseParser obj2 = gson.fromJson(lol, JsonResponseParser.class);
			
			Document document = Jsoup.parse(obj2.getHtml());
			Elements as = document.select("li");
			
			for(Element element : as)
			{
				StringBuilder sb = new StringBuilder();

				for(Element el : element.getElementsByClass("username"))
				{
					sb.append(el.text());
					sb.append(": ");
				}
				
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
			
			document = null;
			obj2 = null;
			as = null;
			lol = null;
			rawData = null;
			
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
}
