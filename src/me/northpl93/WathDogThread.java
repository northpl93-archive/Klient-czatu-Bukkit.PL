package me.northpl93;

public class WathDogThread extends Thread
{
	String xfTokenCache = "";
	int lastMsgCache = 0;

	@Override
	public void run()
	{
		while (true)
		{
			if (!Main.chatListener.isAlive())
			{
				Main.chatListener = null;
				Main.chatListener = new ChatListenThread();
				((ChatListenThread) Main.chatListener)
						.setLoggedUser(xfTokenCache);
				((ChatListenThread) Main.chatListener)
						.setLatestMessage(lastMsgCache);
				Main.chatListener.start();
				Main.debug("Wątek odświeżania wiadomości został uruchomiony ponownie");
			}
			else
			{
				xfTokenCache = ((ChatListenThread) Main.chatListener)
						.getLoggedUser();
				lastMsgCache = ((ChatListenThread) Main.chatListener)
						.getLatestMessage();
			}

			if (!Main.usersListener.isAlive())
			{
				Main.usersListener = null;
				Main.usersListener = new UsersListenThread();
				((UsersListenThread) Main.usersListener).cacheXfToken = xfTokenCache;
				Main.usersListener.start();
				Main.debug("Wątek odświeżania listy userów został uruchomiony ponownie");
			}

			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
