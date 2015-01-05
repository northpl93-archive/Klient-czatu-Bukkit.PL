package me.northpl93.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.UsersListenThread;
import me.northpl93.gui.ChatPanel;
import me.northpl93.gui.PanelsEnum;

public class SwingKeyListener implements KeyListener
{

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			ChatPanel cp = (ChatPanel) PanelsEnum.CHAT_PANEL.getInstance();

			if (cp.wiadomoscDoWyslania.getText() == null
					|| cp.wiadomoscDoWyslania.getText().equalsIgnoreCase(""))
			{
				JOptionPane.showMessageDialog(null, "Musisz wpisać wiadomość!");
				return;
			}

			if (cp.wiadomoscDoWyslania.getText().startsWith("/"))
			{
				Main.getCommandManager().handleCommand(
						cp.wiadomoscDoWyslania.getText().substring(1));
				cp.wiadomoscDoWyslania.setText("");
				return;
			}

			XenForoUtils.sendMessage(
					((ChatListenThread) Main.chatListener).getLoggedUser(),
					cp.wiadomoscDoWyslania.getText());
			cp.wiadomoscDoWyslania.setText("");
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			JTextField tb = ((ChatPanel) PanelsEnum.CHAT_PANEL.getInstance()).wiadomoscDoWyslania;
			char[] textCharArray = tb.getText().toCharArray();

			int extractedTextStart = tb.getCaretPosition();
			int actualCursorLocation = tb.getCaretPosition();

			while (true)
			{
				int actuallyCheckedChar = extractedTextStart - 1;
				if (actuallyCheckedChar <= 0)
				{
					break;
				}
				if (textCharArray[actuallyCheckedChar] == ' ')
				{
					return; // Dla bezpieczenstwa
				}
				if (textCharArray[actuallyCheckedChar] == '@')
				{
					break;
				}
				actuallyCheckedChar--;
				extractedTextStart--;
			}

			String extractedText = tb.getText().substring(extractedTextStart,
					actualCursorLocation);
			for (String fullNick : ((UsersListenThread) Main.usersListener).publicUsers)
			{
				if (fullNick.toLowerCase()
						.contains(extractedText.toLowerCase()))
				{
					tb.setText(tb.getText().replace("@" + extractedText,
							"@" + fullNick));
					return;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}
