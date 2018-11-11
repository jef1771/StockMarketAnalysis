import java.util.*;

public class Calculations {
	
	/**
	 * average	Calculate the average price of the given range
	 * 			this is a helper function for sma and ema
	 * 
	 * @param s		the stock to calculate with
	 * @param start	the start index
	 * @param end	the end index
	 * @return		the average price
	 */
	public static float average(Stock s, int start, int end) {
		float total = 0;
		for(int i = start ;i < end; i++) {
			total += s.data.get(i).close;
		}
		return total / (end - start);
	}
	
	/**
	 * ema	Calculate the Exponential Moving Average
	 * 		of a given Stock
	 * 
	 * @param s		The stock to calculate with
	 * @param span	The time span in days
	 * @return		The exponential moving average
	 */
	public static List<Float> ema(Stock s, int span) {
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		int size = s.data.size() - 1;
		
		//EMA multiplier
		float multiplier = (2f / (span + 1f));
		
		//Walk through the days starting at the span index
		int index = 0;
		for(int i = span; i <= size; i++) {
			if(i == span) {
				values.add(average(s, index, span));
			}
			else {
				values.add(((s.data.get(i).close - values.get(index - 1)) * multiplier) + values.get(index - 1));
			}
			index++;
		}
		return values;
	}
	
	
	
	/**
	 * sma	Calculate the Simple Moving Average
	 * 		of a given Stock
	 * 
	 * @param s		The stock to calculate with
	 * @param span	The time span in days
	 * @return		The simple moving average
	 */
	public static List<Float> sma(Stock s, int span) {
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		int size = s.data.size() - 1;
		
		//walk backward through the data and calculate the averages
		for(; size >= span; size--) {
			float a = average(s, size - span, size);
			//System.out.println(a);
			values.add(a);
		}
		
		return values;
	}


	/**
	 * averageGainLoss finds the average gain and average loss of a given stock
	 * in a given range of days. This is a utility function for calculating RSI
	 *
	 * @param s the stock to do the calculation with
	 * @param start the index of the day to start at
	 * @param end   the index of the day to end at
	 *
	 * @return A list of float values where the gain is at index 0 and the loss at index 1
	 */
	public static List<Float> averageGainLoss(Stock s, int start, int end){
		float gain = 0f;
		float loss = 0f;
		for(int i = start; i < end; i++){
			float change = s.data.get(i).close - s.data.get(i).open;
			if(change > 0){
				gain += change;
			}
			else{
				loss -= change;
			}
		}
		gain = gain / (end - start);
		loss = loss / (end - start);
		return new ArrayList<Float>(Arrays.asList(gain, loss));
	}

	/**
	 * rsi_range finds the rsi for a given day range
	 *
	 * @param s the stock to do the calculations on 
	 * @param span the day range to do the calculations on
	 *
	 * @return A List of float values representing the RSI for each day.
	 */
	public static List<Float> rsi_range(Stock s, int span){
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		List<List<Float>> gains = new ArrayList<List<Float>>(s.data.size() - span);
		int begin = 0;
		for(int i = span; i < s.data.size(); i++){
			if(begin > 0){
				float change = s.data.get(i).close - s.data.get(i).open;
				float g = change > 0f ? change : 0f;
				float l = change < 0f ? change : 0f;
				gains.add(averageGainLoss(s, begin, i));
				float smoothedUp = (((gains.get(begin - 1).get(0) * (span - 1)) + g) / span);
				float smoothedDown = (((gains.get(begin - 1).get(1) * (span - 1)) + l) / span);
				float rs = smoothedUp / smoothedDown;
				values.add((100f - (100f/(1f + rs))));
			}
			else{
				List<Float> UD = averageGainLoss(s, begin, i);
				float rs = UD.get(0) / UD.get(1);
				gains.add(UD);
				values.add((100f - (100f/(1f + rs))));
			}
			begin++;

		}
		return values;
	}
	
	/**
	 * rsi returns the default rsi which is calculated with day range of 14 
	 *
	 * @param s the stock to do the calculations on 
	 * @return A List of Float values representing the RSI of each day
	 */
	public static List<Float> rsi(Stock s) {
		return rsi_range(s, 14);
	}
	

	/**
	 * highestHighLowestLow finds the highest high value and lowest low value in a 
	 * given range of days for a given stock
	 *
	 * @param s The stock to do the calculations with
	 * @param start the first day in the range
	 * @param end the last day in the range
	 *
	 * @return a list of floats where index 0 holds the high, and index 1 the low
	 */
	public static List<Float> highestHighLowestLow(Stock s, int start, int end){
		float high = 0f;
		float low = 999999999f;
		for(;start < end; start++){
			high = high < s.data.get(start).high ? s.data.get(start).high : high;
			low = low > s.data.get(start).low ? s.data.get(start).low : low;
		}

		return new ArrayList<Float>(Arrays.asList(high, low));
	}

	/**
	 * stochastic oscillator calculation at a given range of days
	 * 
	 * @param s The stock to do the calculations with
	 * @param span the range of days to do the calculation with
	 *
	 * @return The List of float values representing the SO at each day
	 */
	public static List<Float> so_range(Stock s, int span){
		List<Float> values = new ArrayList<Float>(s.data.size() - span);
		int begin = 0;
		for(int i = span; i < s.data.size(); i++){
			List<Float> hl = highestHighLowestLow(s, begin, i);
			float val = (s.data.get(i).close - hl.get(1)) / (hl.get(0) - hl.get(1)) * 100;
			System.out.println(val);
			values.add(val);
			begin++;

		}
		return values;
	}

	/**
	 * stochastic oscillator calculation at default range of days which is 14
	 * 
	 * @param s The stock to do the calculations with
	 *
	 * @return The List of float values representing the SO at each day
	 */
	public static List<Float> so(Stock s) {
		return so_range(s, 14);
	}
}
