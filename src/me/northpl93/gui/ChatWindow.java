package me.northpl93.gui;

import javax.swing.JFrame;

public class ChatWindow extends JFrame
{
	private static final long serialVersionUID = 4165050661670347724L;
	
	public ChatWindow()
	{
		super("Klient czatu Bukkit.PL (By NorthPL)");
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(650, 350);
        setResizable(false);
	}
}
