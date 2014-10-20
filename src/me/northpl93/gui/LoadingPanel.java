package me.northpl93.gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import me.northpl93.Main;

public class LoadingPanel extends JPanel
{
	private static final long serialVersionUID = -6519018955576319897L;

	/**
	 * Create the panel.
	 */
	public LoadingPanel() {
		setLayout(null);
		
		JLabel lblProszCzeka = new JLabel("Prosz\u0119 czeka\u0107...");
		lblProszCzeka.setBounds(262, 160, 110, 14);
		add(lblProszCzeka);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(194, 210, 227, 14);
		progressBar.setIndeterminate(true);
		add(progressBar);
		
		JLabel lblWersjaLol = new JLabel("Wersja "+Main.getVersion());
		lblWersjaLol.setForeground(Color.LIGHT_GRAY);
		lblWersjaLol.setBounds(0, 303, 152, 14);
		add(lblWersjaLol);

	}
}
