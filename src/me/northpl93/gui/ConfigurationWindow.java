package me.northpl93.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ConfigurationWindow extends JFrame
{
	private static final long serialVersionUID = -6319705537023472514L;
	private JPanel contentPane;

	public ConfigurationWindow()
	{
		super("Konfiguracja");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

}
