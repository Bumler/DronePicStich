package dronePKG;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageDisplay extends JFrame {

	private JPanel contentPane;

	private static File f = null;
	private static int rows;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageDisplay frame = new ImageDisplay(f,rows);
					frame.setVisible(true);
					frame.setSize(1600, 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void view(){
		main(null);
	}
	/**
	 * Create the frame.
	 */
	public ImageDisplay(File fileIN, int rowsIN) {
		rows = rowsIN;
		f = fileIN;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(rows, 1, 0, 0));
		
		if (f != null){
			
			ArrayList<File> images = new ArrayList<File>(Arrays.asList(f.listFiles()));
			
			for (File f : images){
				try {
		          	BufferedImage img = ImageIO.read(f);
		          	//ImageIcon icon = new ImageIcon(img);
		          	//JLabel label = new JLabel(icon);
		          	//JOptionPane.showMessageDialog(null, label);
		          	//contentPane.add(label);
		          	BackgroundPanel bp = new BackgroundPanel(img);
		          	contentPane.add(bp);
		          	
		       	} catch (IOException e) {
		    	   e.printStackTrace();
		       	}
		
			}
		}
	}

}
