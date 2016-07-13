package dronePKG;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private File f = null;
	private int rows;
	/**
	 * Launch the application.
	 */
	public void makeWindow(){
		System.out.println(f.toString());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageDisplay frame = new ImageDisplay(f,rows);
					frame.setVisible(true);
					frame.setSize(1600, 1000);
					BufferedImage img = ScreenImage.createImage(contentPane);
					String fileLocation = new String (f.toString()+"\\stitch.jpg");
					ScreenImage.writeImage(img, fileLocation);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void view(){
		makeWindow();
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
			
			int cols = images.size()/rows;
			
			BackgroundPanel[][] grid = new BackgroundPanel[rows][cols];
			
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){
			            URL imageurl = getClass().getResource("/resource/niceDroneVector.jpg");//assuming your package name is resource 
			            Image img = Toolkit.getDefaultToolkit().getImage(imageurl);
			            grid[i][j] = new BackgroundPanel(img);
				}
			}
			
			DroneSort(grid, images, rows, cols);
			
			//goes through the panels in grid and adds them into the content pane
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){
					contentPane.add(grid[i][j]);
				}
			}
			
		}
	}
	
	//Goes through the list of pictures and selects index = to num rows-1 because that
	//picture should always be in the first cell, that index num is 'current'. It then moves 
	//based on the following priority (all controlled through booleans) first is the rows
	//below current. From there it resets to current and moves right once and the to the
	//bottom. Once it hits the bottom it goes right one then up and vice versa. This loops
	//until the grid is filled. Only requires the number of rows because col can be derived
	//by the filesize/rows
	private void DroneSort(BackgroundPanel[][] grid, ArrayList<File> images, int rows, int cols){
		int current = rows-1;
		int x = 0;
		int y = 0;
		
		boolean first = true;
		boolean right = true;
		boolean down  = true;
		
		putImage(grid, images.get(current), x, y);
		
		for (int i = 0; i < (images.size()-1); i++){
			if (first){
				if (y < (rows-1)){
					y++;
					current--;
					putImage(grid, images.get(current), x, y);
					System.out.println(current);
				}
				else {
					first = false;
					current = rows-1;
					y = 0;
					i--;
				}
			}
			
			else if (right){
				if (x < cols-1){
					current++;
					x++;
					putImage(grid, images.get(current), x, y);
					right = false;
				}
			}
			
			else if(down){
				if (y < rows-1){
					current++;
					y++;
					putImage(grid, images.get(current),x,y);
				}
				else{
					down = false;
					right = true;
					i--;
				}
			}
			
			else /*up*/{
				if(y > 0){
					current++;
					y--;
					putImage(grid, images.get(current),x,y);
				}
				else{
					down = true;
					right = true;
					i--;
				}
			}
		}
	}
	
	//places an image into the cell x,y on grid
	private void putImage(BackgroundPanel[][] grid, File f, int x, int y){
        BufferedImage img = null;
        try {
             
        	img = ImageIO.read(f);
            grid[y][x] = new BackgroundPanel(img);
             
        } catch (Exception e) {
            
        	System.out.println("Cannot read file: " + e);
        	
        }
	}
}
