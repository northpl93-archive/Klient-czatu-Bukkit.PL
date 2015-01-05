package me.northpl93.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.northpl93.gui.PanelsEnum;
import me.northpl93.gui.WelcomePanel;

public class LoginPanelSwingKeyListener implements KeyListener
{

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			((WelcomePanel) PanelsEnum.WELCOME_PANEL.getInstance())
					.loginStuff();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

}
