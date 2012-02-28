/**
 * A runnable for painting the cells.
 * @author Brook
 *
 */
public class GameThread implements Runnable
{
	/** Sleep for 500 ms after each call to paint */
	public static int DEFAULT_SLEEPTIME = 1000;
	
	/** Shorter sleep time */
	public static int FASTER_SLEEPTIME = 500;
	
	/** Shortest sleep time*/
	public static int FASTEST_SLEEPTIME = 100;
	
	/** The sleep time to actually use */
	private static int sleeptime = DEFAULT_SLEEPTIME;
	
	/** After all cells have died, then sleep for this long */
	private static int OVER_SLEEPTIME = 1500;
	
	private GameOfLife gameOfLife;
	
	
	public static void setSleepTime(int time)
	{
		sleeptime = time; 
	}
	
	
	public GameThread(GameOfLife gameOfLife)
	{
		this.gameOfLife= gameOfLife;
	}
	
	public void run()
	{
		while(true){					
			gameOfLife.updateLife();
			gameOfLife.repaint();
			//if(gameOfLife.getNumAlive() == 0)break;
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				return;
			}
			if(gameOfLife.getCellWorld().getNumAlive() == 0)break;
		}
		try {
			Thread.sleep(OVER_SLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		gameOfLife.getCellWorld().initLiveCells();
		gameOfLife.repaint();		
		return;
	}
	

}
