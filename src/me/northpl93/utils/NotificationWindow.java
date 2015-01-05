package me.northpl93.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class NotificationWindow extends JFrame
{
	private static final long serialVersionUID = 1925248663422133218L;
	private final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private final Insets toolHeight = Toolkit.getDefaultToolkit()
			.getScreenInsets(getGraphicsConfiguration());
	private static List<NotificationWindow> notWindows = new ArrayList<NotificationWindow>(
			1);
	private NotificationWindow instance;

	public NotificationWindow(String title, String message, Icon ico)
	{
		super(title);
		instance = this;
		notWindows.add(instance);
		setSize(300, 125);
		setLayout(new GridBagLayout());
		setUndecorated(true);
		setAlwaysOnTop(true);
		setFocusable(false);
		setLocationRelativeTo(null);
		if (notWindows.size() > 1)
		{
			int x = screenSize.width - getWidth();
			int y = screenSize.height - toolHeight.bottom - getHeight();

			for (int i = 0; i < notWindows.size() - 1; i++)
			{
				y = y - 125;
			}
			setLocation(x, y);
		}
		else
		{
			setLocation(screenSize.width - getWidth(), screenSize.height
					- toolHeight.bottom - getHeight());
		}

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel headingLabel = new JLabel(title);
		headingLabel.setIcon(ico);
		headingLabel.setOpaque(false);
		add(headingLabel, constraints);
		constraints.gridx++;
		constraints.weightx = 0f;
		constraints.weighty = 0f;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		JButton cloesButton = new JButton("X");
		cloesButton.setMargin(new Insets(1, 4, 1, 4));
		cloesButton.setFocusable(false);
		cloesButton.addActionListener(new AbstractAction("x")
		{
			private static final long serialVersionUID = 5921376430472056767L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				notWindows.remove(instance);
				dispose();
			}
		});
		add(cloesButton, constraints);
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel messageLabel = new JLabel("<HtMl>" + message);
		add(messageLabel, constraints);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);

		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				notWindows.remove(instance);
				dispose();
			};
		}.start();
	}

	public static List<NotificationWindow> getNotificationWindows()
	{
		return notWindows;
	}

	public void removeThisWindow()
	{
		this.dispose();
		notWindows.remove(this);
	}

	public enum Icons
	{
		CHAT(new ImageIcon(
				NotificationWindow.class.getResource("/images/chat.png"))), USER(
				new ImageIcon(
						NotificationWindow.class
								.getResource("/images/user.png")));

		private Icon ico = null;

		private Icons(Icon ico)
		{
			this.ico = ico;
		}

		public Icon getIco()
		{
			return ico;
		}
	}
}
