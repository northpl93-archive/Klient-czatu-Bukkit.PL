package me.northpl93.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;

public class SwingKeyListener implements KeyListener
{

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			ChatPanel cp = (ChatPanel) PanelsEnum.CHAT_PANEL.getInstance();
			
			if(cp.wiadomoscDoWyslania.getText() == null || cp.wiadomoscDoWyslania.getText().equalsIgnoreCase(""))
			{
				JOptionPane.showMessageDialog(null, "Musisz wpisaæ wiadomoœæ!");
				return;
			}
			
			if(cp.wiadomoscDoWyslania.getText().startsWith("/"))
			{
				Main.getCommandManager().handleCommand(cp.wiadomoscDoWyslania.getText().substring(1));
				cp.wiadomoscDoWyslania.setText("");
				return;
			}
			
			XenForoUtils.sendMessage(((ChatListenThread)Main.chatListener).getLoggedUser(), cp.wiadomoscDoWyslania.getText());
			cp.wiadomoscDoWyslania.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e){}
}
