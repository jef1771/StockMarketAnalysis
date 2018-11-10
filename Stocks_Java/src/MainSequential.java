import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

public class MainSequential extends Task {
	
	public void main(String[] arg0) throws Exception 
	{				
		System.out.println("Hello World");
		Stock s = new Stock("Sample", "../stock_database/A.json");
	}
	
	/**
	 * requires one core for this task.
	 */
	protected static int coresRequired() {
		return 1;
	}
	
}
