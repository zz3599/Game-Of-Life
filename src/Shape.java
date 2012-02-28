import java.util.*;
/**
 * A preset shape for the user to choose from.
 * @author Brook
 *
 */
public class Shape 
{	
	private String shapeName;
	
	private int[][] configuration;	
	
	public Shape(String name, int[][] data)
	{
		shapeName = name;
		configuration = data;
	}
	
	public void printShape()
	{
		int i = 1; 
		for(int row[] : configuration){
			//for(int element : row){
				System.out.println(i++);				
			//}
		}
	}
	
	/**
	 * Gets the configuration of the shape as cells inside an 
	 * array list.
	 * @return
	 */
	public ArrayList<Cell> getShape()
	{
		ArrayList<Cell>cells = new ArrayList<Cell>();
		for(int row[] : configuration){
			int j = row[0] + 8;
			int i = row[1] + 8;
			cells.add(new Cell(true, i, j));
		}
		return cells;
	}
	
	public String getName()
	{
		return shapeName;
	}
	
	

}
