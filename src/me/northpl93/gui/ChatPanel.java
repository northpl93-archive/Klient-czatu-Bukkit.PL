package me.northpl93.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.northpl93.ChatListenThread;
import me.northpl93.Main;
import me.northpl93.utils.SwingKeyListener;
import me.northpl93.utils.XenForoUtils;

public class ChatPanel extends JPanel  {
	private SwingKeyListener skl = new SwingKeyListener();
	
	public JTextField wiadomoscDoWyslania;
	public JTextArea textArea;
	private JScrollPane jScrollPane;
	private JScrollBar vertical;
	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		setLayout(null);
		
		wiadomoscDoWyslania = new JTextField();
		wiadomoscDoWyslania.setBounds(0, 251, 427, 20);
		add(wiadomoscDoWyslania);
		wiadomoscDoWyslania.setColumns(10);
		wiadomoscDoWyslania.addKeyListener(skl);
		
		JButton przyciskOdWysylania = new JButton("Wy\u015Blij!");
		przyciskOdWysylania.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(wiadomoscDoWyslania.getText() == null || wiadomoscDoWyslania.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Musisz wpisaæ wiadomoœæ!");
					return;
				}
				XenForoUtils.sendMessage(((ChatListenThread)Main.chatListener).getLoggedUser(), wiadomoscDoWyslania.getText());
				wiadomoscDoWyslania.setText("");
			}
		});
		przyciskOdWysylania.setBounds(426, 250, 74, 23);
		przyciskOdWysylania.addKeyListener(skl);
		add(przyciskOdWysylania);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 500, 251);
		//add(textArea);
		
        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setBounds(0, 0, 500, 251);
        jScrollPane.addKeyListener(skl);
        add(jScrollPane);
        
        vertical = jScrollPane.getVerticalScrollBar();
	}
	
	public void scrollToDown()
	{
        vertical.setValue( vertical.getMaximum() );
	}
}
