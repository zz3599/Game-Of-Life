import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Neighboring cells are the eight surrounding cells, which may be less if the cell is in the 
 * corner. The rules of the Game of Life are as follows:
 * 
 * 1. If a cell is alive and : (a) 2 or 3 surrounding cells that are alive, it lives (b) otherwise it dies. 
 * 2. If a cell is dead and there it has exactly 3 surrounding cells that are alive, it lives. 
 * @author Brook
 *
 */
public class GameOfLife extends JPanel 
{
	private static int WIDTH = 900; 
	private static int HEIGHT = 600;
	private Color color; 
	public static int numRows = HEIGHT/15;
	public static int numColumns = WIDTH/15;
	
	
	/** Grid of the cell world */
	private CellWorld cellWorld = new CellWorld(numRows, numColumns);
	
	/** Keeps track of the number of neighbors around */
	private int neighborCounter[][];
	
	/**
	 * Construct a new game of life.
	 */
	public GameOfLife()
	{
		this.setBackground(Color.GRAY);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new FlowLayout());
		setColor(Color.WHITE);
		neighborCounter = new int[numRows][numColumns];	
		this.addMouseListener(new MouseListener());
	}

	
	public synchronized void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		int cellHeight = HEIGHT/numRows;
		int cellWidth = WIDTH/numColumns;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				if(cellWorld.isCellAlive(i, j)){
					g2.fillOval(j*cellWidth, i*cellHeight, cellWidth, cellHeight);						
				} 
			}
		}		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * Takes care of updating to the next generation.
	 */
	public synchronized void updateLife()
	{
		revalidate();
		nextGeneration();
	}
	
	/**
	 * Set the color of the cells.
	 * @param color The color to set.
	 */
	public void setColor(Color color)
	{
		this.color = color;		
	}
	
	/**
	 * Calculate the next generation of the world based on the number of neighbors 
	 * of each cell. I use arraylists to temporarily store the cells to be removed or 
	 * added for the next generation so that the current cell calculations do not get messed up.
	 */
	public void nextGeneration()
	{
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				neighborCounter[i][j] = countLiveNeighbors(i, j);
			}
		}
		ArrayList<Cell>cellsToAdd = new ArrayList<Cell>();
		ArrayList<Cell>cellsToRemove = new ArrayList<Cell>();
		
		//Based on the number of neighbors that are alive, calculate next generation.
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				int numAliveNeighbors = neighborCounter[i][j];//the number of alive neighbors
				if(cellWorld.isCellAlive(i, j)){//cell is alive
					if(numAliveNeighbors < 2 || numAliveNeighbors > 3){
						cellsToRemove.add(new Cell(false, i , j));
					}					
				} else {//cell is dead
					if(neighborCounter[i][j] == 3){
						cellsToAdd.add(new Cell(true, i, j));
					}
				}				
			}
		}		
		cellWorld.addLiveCells(cellsToAdd);
		cellWorld.removeCells(cellsToRemove);
	}
	
	/**
	 * Get the cell world representing this world.
	 * @return The cell world.
	 */
	public CellWorld getCellWorld()
	{
		return cellWorld;
	}
	
	/**
	 * Counts the number of neighbors that are alive
	 */
	private int countLiveNeighbors(int i, int j)
	{
		int numLiveNeighbors = 0; 
		for(int x = i - 1; x < i + 2; x++){
			for(int y = j - 1; y < j + 2; y++){
				if(x == i && y == j) continue;
				numLiveNeighbors += isAlive(x,y);
			}
		}
		return numLiveNeighbors;		
	}
	
	/**
	 * Finds if the given cell is going to live or not.
	 * @param i Row number
	 * @param j Column number
	 * @return
	 */
	private int isAlive(int i, int j)
	{
		if((i < 0) || (i >= numRows)) return 0; 
		if((j < 0) || (j >= numColumns)) return 0; 
		if(cellWorld.isCellAlive(i, j)) return 1;
		return 0; 
	}
	
	private class MouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			int column = x /( WIDTH / numColumns);
			int row = y /( HEIGHT / numRows);
			if(!cellWorld.isCellAlive(row, column))	{
				cellWorld.setCellAlive(row, column);
			} else {//cell is already alive, so set it to dead
				cellWorld.setCellDead(row, column);
			}
			repaint();
		}
		
	}
}
