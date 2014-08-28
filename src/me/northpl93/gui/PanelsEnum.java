package me.northpl93.gui;

import javax.swing.JPanel;

public enum PanelsEnum {
	WELCOME_PANEL(new WelcomePanel()),
	CHAT_PANEL   (new ChatPanel());
	
	JPanel panelInstance = null;
	
	private PanelsEnum(JPanel _panelInstance)
	{
		panelInstance = _panelInstance;
	}
	
	public JPanel getInstance()
	{
		return panelInstance;
	}
}
