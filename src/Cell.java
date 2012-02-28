
/**
 * Represents a unit that can be drawn.
 * @author Brook
 *
 */
public class Cell 
{
	private boolean isAlive;
	
	private int row;
	
	private int column;
	
	public Cell(boolean isAlive, int row, int column)
	{
		this.isAlive = isAlive;
		this.row = row;
		this.column = column;
	}
	
	public boolean getIsAlive()
	{
		return isAlive;
	}
	
	public void setIsAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	

}
