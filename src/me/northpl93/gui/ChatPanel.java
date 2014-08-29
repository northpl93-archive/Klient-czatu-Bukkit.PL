package me.northpl93.gui;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private static final long serialVersionUID = -4902262492595144810L;
	private SwingKeyListener skl = new SwingKeyListener();
	private boolean isListVisible = true;
	
	public JTextField wiadomoscDoWyslania;
	public final JTextArea textArea;
	public final List list;
	private JScrollPane jScrollPane;
	private JScrollBar vertical;
	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		setLayout(null);
		
		wiadomoscDoWyslania = new JTextField();
		wiadomoscDoWyslania.setBounds(0, 302, 511, 20);
		add(wiadomoscDoWyslania);
		wiadomoscDoWyslania.setColumns(10);
		wiadomoscDoWyslania.addKeyListener(skl);
		
		JButton przyciskOdWysylania = new JButton("Wy\u015Blij");
		przyciskOdWysylania.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(wiadomoscDoWyslania.getText() == null || wiadomoscDoWyslania.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Musisz wpisa� wiadomo��!");
					return;
				}
				XenForoUtils.sendMessage(((ChatListenThread)Main.chatListener).getLoggedUser(), wiadomoscDoWyslania.getText());
				wiadomoscDoWyslania.setText("");
			}
		});
		przyciskOdWysylania.setBounds(510, 301, 95, 23);
		przyciskOdWysylania.addKeyListener(skl);
		add(przyciskOdWysylania);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(0, 0, 511, 301);
		//add(textArea);
		
        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setBounds(0, 0, 511, 301);
        jScrollPane.addKeyListener(skl);
        add(jScrollPane);
        
        list = new List();
        list.setBounds(510, 0, 150, 301);
        add(list);
        
        JButton btnHideUsersList = new JButton("X");
        btnHideUsersList.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(isListVisible)
        		{
        			remove(list);
        			textArea.setBounds(0, 0, 649, 301);
        			jScrollPane.setBounds(0, 0, 649, 301);
                	repaint();
                	scrollToDown();
        			isListVisible = false;
        		}
        		else
        		{
        			add(list);
        			textArea.setBounds(0, 0, 511, 301);
        			jScrollPane.setBounds(0, 0, 511, 301);
                	repaint();
                	scrollToDown();
        			isListVisible = true;
        		}
        	}
        });
        btnHideUsersList.setBounds(604, 301, 46, 23);
        add(btnHideUsersList);
        
        vertical = jScrollPane.getVerticalScrollBar();
	}
	
	public void scrollToDown()
	{
        vertical.setValue( vertical.getMaximum() );
	}
}
