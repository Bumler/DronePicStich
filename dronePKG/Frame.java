package dronePKG;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Frame extends JFrame {

	private BackgroundPanel contentPane;
	private JButton stitch;
	private JPanel bufferNorth;
	private JPanel panel_1;
	private JPanel bufferSouth;
	private JPanel bufferWest;
	private JPanel bufferEast;
	private JLabel lblDronePicStich;
	private JPanel selectFilesLayout;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel colLabel;
	private JTextField colSize;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setLF();
					Frame frame  = new Frame();
					frame.setSize(900, 450);
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void setLF(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Frame() throws IOException {
        BufferedImage img = null;
        try {
             File f = new File("niceDroneVector.jpg");
             img = ImageIO.read(f);
             System.out.println("File " + f.toString());
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e);
        }
         
        BackgroundPanel background = new BackgroundPanel(img);

        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = background;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		setContentPane(contentPane);
		
		bufferNorth = new JPanel();
		contentPane.add(bufferNorth, BorderLayout.NORTH);
		bufferNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.CENTER);
		btnPanel.setLayout(new GridLayout(0, 1, 0, 10));
		
		lblDronePicStich = new JLabel("Drone Pic Stich");
		lblDronePicStich.setForeground(new Color(0, 0, 0));
		lblDronePicStich.setFont(new Font("Calisto MT", Font.BOLD, 48));
		lblDronePicStich.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(lblDronePicStich);
		
		selectFilesLayout = new JPanel();
		selectFilesLayout.setBackground( new Color(0, 0, 0, 0) );
		btnPanel.add(selectFilesLayout);
		
		JButton fileSelector = new JButton("Select Files");
		fileSelector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectFilesLayout.add(fileSelector);
		
		lblNewLabel = new JLabel("No files selected");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblNewLabel.setForeground(new Color(128, 128, 128));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(lblNewLabel);
		
		panel_2 = new JPanel();
		panel_2.setBackground( new Color(0, 0, 0, 0) );
		btnPanel.add(panel_2);
		
		colLabel = new JLabel("Column Size");
		colLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		colLabel.setForeground(new Color(128, 128, 128));
		panel_2.add(colLabel);
		
		colSize = new JTextField();
		colSize.setEnabled(false);
		panel_2.add(colSize);
		colSize.setColumns(2);
		
		stitch = new JButton("Stitch!");
		stitch.setFont(new Font("Tahoma", Font.PLAIN, 28));
		stitch.setEnabled(false);
		btnPanel.add(stitch);
		
		bufferSouth = new JPanel();
		contentPane.add(bufferSouth, BorderLayout.SOUTH);
		bufferSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		bufferWest = new JPanel();
		contentPane.add(bufferWest, BorderLayout.WEST);
		bufferWest.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		
		bufferEast = new JPanel();
		contentPane.add(bufferEast, BorderLayout.EAST);
		bufferEast.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
	}

}
