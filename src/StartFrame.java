import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;

/** 
 * Main application frame
 * @author Brook
 *
 */
public class StartFrame extends JFrame
{
	private GameOfLife gameOfLife;
	
	private Thread t; 
	public StartFrame(GameOfLife gameOfLife)
	{
		super("Game of Life Simulator");
		this.gameOfLife = gameOfLife;
		initGUI();
		getContentPane().add(gameOfLife);
		validate();
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setResizable(false);
	}
	
	private void initGUI ()
	{
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		//Load shapes menu
		JMenu load = new JMenu("Load Shapes");
		menubar.add(load);
		JMenuItem clear = new JMenuItem("Clear");
		load.add(clear);
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.getCellWorld().clearWorld();
				gameOfLife.repaint();
			}
		});
		JMenuItem glider = new JMenuItem("Glider");
		load.add(glider);
		glider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.getCellWorld().loadGlider();
				gameOfLife.repaint();
			}
		});
		JMenuItem gun = new JMenuItem("Gosper Glider Gun");
		load.add(gun);
		gun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.getCellWorld().loadGliderGun();
				gameOfLife.repaint();
			}
		});
		JMenuItem random = new JMenuItem("Random");
		load.add(random);
		random.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.getCellWorld().loadRandomShape();
				gameOfLife.repaint();
			}
		});
		//Run/stop menu
		JMenu run = new JMenu("Run");
		menubar.add(run);
		JMenuItem start = new JMenuItem("Start");
		run.add(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(t == null || t.getState() == Thread.State.TERMINATED){
					GameThread runnable = new GameThread(gameOfLife);
					t = new Thread(runnable);
					t.start();
				} else {
					JOptionPane.showMessageDialog(gameOfLife, "It's already running");
				}
			}
		});
		
		JMenuItem chooseColor = new JMenuItem("Stop");
		run.add(chooseColor);
		chooseColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					t.interrupt();
				} catch (Exception x){
					JOptionPane.showMessageDialog(gameOfLife, "There's nothing to stop!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//Specify sleep times
		JMenu speed = new JMenu("Speed");
		menubar.add(speed);
		JMenuItem normal = new JMenuItem("Normal");
		speed.add(normal);
		normal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GameThread.setSleepTime(GameThread.DEFAULT_SLEEPTIME);
			}
		});
		
		JMenuItem fast = new JMenuItem("Fast");
		speed.add(fast);
		fast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GameThread.setSleepTime(GameThread.FASTER_SLEEPTIME);
			}
		});
		
		JMenuItem fastest = new JMenuItem("Fastest");
		speed.add(fastest);
		fastest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GameThread.setSleepTime(GameThread.FASTEST_SLEEPTIME);
			}
		});
		
		//Color menu
		JMenu color = new JMenu("Color");
		menubar.add(color);
		JMenuItem white = new JMenuItem("White");
		color.add(white);
		white.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.setColor(Color.WHITE);
				gameOfLife.repaint();
			}
		});
		JMenuItem red = new JMenuItem("Red");
		color.add(red);	
		red.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.setColor(Color.RED);
				gameOfLife.repaint();
			}
		});
		JMenuItem blue = new JMenuItem("Blue");
		color.add(blue);
		blue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.setColor(Color.BLUE);
				gameOfLife.repaint();
			}
		});
		JMenuItem green = new JMenuItem("Green");
		color.add(green);
		green.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.setColor(Color.GREEN);
				gameOfLife.repaint();
			}
		});
		JMenuItem black = new JMenuItem("Black");
		color.add(black);
		black.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameOfLife.setColor(Color.BLACK);
				gameOfLife.repaint();
			}
		});
		
		//About menu
		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(gameOfLife, "Game of Life Simulator version 1.0\n" +
						"\tCreated by Brook Zhou\n" + "\t2011-LiveInCode.com", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem howTo = new JMenuItem("How To");
		help.add(howTo);
		howTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String text = "You can adjust add/remove cells as\nyou like by clicking on " +
					"any\nlocation inside the window. You can\nstop at anytime and resume by going\nto the Run menu.\n\n" +
					"You can also select pre-set shapes \nfrom theLoad menu. Adjust the speed\n as you wish";
				JOptionPane.showMessageDialog(gameOfLife, text, "How To Use", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
	}
	
	
		
	
	
	public static void main(String[] args) {
		GameOfLife life = new GameOfLife();
		StartFrame frame = new StartFrame(life);

	}

}
