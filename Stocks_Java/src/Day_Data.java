import java.util.Date;  


public class Day_Data {
	public float open;
	public float close;
	public float high;
	public float low;
	public double volume;
	public Date date;

	/**
	 * Day_data struct contains the daily price for each stock
	 * 
	 * @param date	stock date in "YYYY-MM-DD" format
	 * @param open	Daily open stock price
	 * @param close	Daily closing stock price
	 * @param high  Daily higest stock price
	 * @param low   Daily lowest stock price
	 * @param volume Number of daily transaction
	 * @return		none
	 */
	public Day_Data(
			Date date,
			float open,
			float close,
			float high,
			float low,
			double volume
	)
	{
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.date = date;
	}
}
