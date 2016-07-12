package dronePKG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;


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
	private JLabel fileDia;
	private JLabel rowLabel;
	private JTextField rowsize;
	private File f;
	private boolean open = false;
	final JFileChooser fc = new JFileChooser();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setLF();
					Frame frame  = new Frame();
					frame.setSize(900, 500);
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
		//Reads in the background image and creates a background panel
		//Background panel is a child of jpanel
        BufferedImage img = null;
        try {
             File f = new File("niceDroneVector.jpg");
             img = ImageIO.read(f);
             System.out.println("File " + f.toString());
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e);
        }
         
        BackgroundPanel background = new BackgroundPanel(img);

        //Content Pane takes a jpanel as an argument so we make it background-
        //panel. This overrides the add function so that they are added transparently
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = background;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		setContentPane(contentPane);
		
		//Below is the pane with all the functionality--------------------------------------	
		JPanel btnPanel = new JPanel();
			contentPane.add(btnPanel, BorderLayout.CENTER);
			btnPanel.setLayout(new GridLayout(0, 1, 0, 10));
		
		lblDronePicStich = new JLabel("Drone Pic Stich");
			lblDronePicStich.setForeground(new Color(0, 0, 0));
			lblDronePicStich.setFont(new Font("Calisto MT", Font.PLAIN, 64));
			lblDronePicStich.setHorizontalAlignment(SwingConstants.CENTER);
			btnPanel.add(lblDronePicStich);
		
		selectFilesLayout = new JPanel();
			selectFilesLayout.setBackground( new Color(0, 0, 0, 0) );
			btnPanel.add(selectFilesLayout);
		
		JButton fileSelector = new JButton("Select Files");
		fileSelector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectFilesLayout.add(fileSelector);
		fileSelector.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				
				//opens the file explorer and sets f = to the directory chosen
				openDirectory();
				if (open){
					setFileDia(f.getName());
					enableRow();
				}
				
			}
		});
		
		fileDia = new JLabel("No files selected");
			fileDia.setFont(new Font("Tahoma", Font.ITALIC, 18));
			fileDia.setForeground(new Color(128, 128, 128));
			fileDia.setHorizontalAlignment(SwingConstants.CENTER);
			btnPanel.add(fileDia);
		
		panel_2 = new JPanel();
			panel_2.setBackground( new Color(0, 0, 0, 0) );
			btnPanel.add(panel_2);
		
			rowLabel = new JLabel("Row Size");
				rowLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
				rowLabel.setForeground(new Color(128, 128, 128));
				panel_2.add(rowLabel);
		
			rowsize = new JTextField();
			rowsize.setFont(new Font("Tahoma", Font.PLAIN, 18));
				rowsize.setEnabled(false);
				panel_2.add(rowsize);
				rowsize.setColumns(2);
				rowsize.addActionListener(new ActionListener(){

	                public void actionPerformed(ActionEvent e){
	                	
	                	if (isInteger(rowsize.getText())){
	                		//makes sure input is an integer. If it is we can stitch
	                		stitch.setEnabled(true);
	                	}

	                }});
		
		stitch = new JButton("Stitch!");
			stitch.setFont(new Font("Tahoma", Font.PLAIN, 28));
			stitch.setEnabled(false);
			btnPanel.add(stitch);
			
			stitch.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent arg0){
					
					createStitch();
					
				}
			});
		
		//Below is entirely buffer panels------------------------------------
		bufferNorth = new JPanel();
		contentPane.add(bufferNorth, BorderLayout.NORTH);
		bufferNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
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

	//opens up file explorer and sets f = to the selected directory
	//sets the open boolen to true
	private void openDirectory(){
		int returnVal = fc.showOpenDialog(Frame.this);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
	            f = fc.getCurrentDirectory();
	            open = true;
	            //This is where a real application would open the file.
	            System.out.println("Opening: " + f.getName() + ".");
	        } else {
	            System.out.println("Open command cancelled by user.");
	        }
	}
	
	//takes in the name of the directory and changes the j label to bold-
	//black and the name of the file (argument s)
	private void setFileDia(String s){
		fileDia.setText(s);
		fileDia.setFont(new Font("Tahoma", Font.BOLD, 18));
		fileDia.setForeground(Color.BLACK);
	}
	
	//Changes the font Color of 'row size' and enables its textfield
	//also closes open
	private void enableRow(){
		rowLabel.setForeground(Color.BLACK);
		rowLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		rowsize.setEnabled(true);
		open = false;
	}
	
	//checks through the inputted string to see if it is an integer
	// taken from http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	public static boolean isInteger(String s) {
		try{
			  int num = Integer.parseInt(s);
			  // is an integer!
			} catch (NumberFormatException e) {
			  // not an integer!
				return false;
			}
		return true;
	}
	
	//we parse rowsize into an int and then pass it along with f to the new window
	public void createStitch(){
		int row = Integer.parseInt(rowsize.getText());
		ImageDisplay show = new ImageDisplay(f, row);
		show.view();
	}
}
