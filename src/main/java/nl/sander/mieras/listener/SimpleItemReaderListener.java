package nl.sander.mieras.listener;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class SimpleItemReaderListener<Item> implements ItemReadListener<Item>{
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleItemReaderListener.class);
	private static final double NANO_TO_SECOND_DIVIDER_NUMBER = 1_000_000_000.0;	
	private static final String PATTERN = ",###";	
	
	private int startCount = 0;
	private int logInterval = 1000;
	private int currentCount;
	private int totalCount;
	private long timeElapsed;
	private long startTime;
	private DecimalFormat decimalFormat = new DecimalFormat(PATTERN);	

	@Override
	public void beforeRead() {
		startTime = System.nanoTime();				
	}

	@Override
	public void afterRead(Item item) {
		updateTimeElapsed();		
		if (currentCount == logInterval) {			
			displayMessage();
			updateStartCount();
			resetCount();
		} else {
			increaseCount();
		}		
	}

	private void updateTimeElapsed() {
		timeElapsed += System.nanoTime() - startTime;
	}

	private void displayMessage() {
		LOG.info(String.format("Read records [%s] to [%s] in average %.2f seconds", 
				decimalFormat.format(startCount), 
				decimalFormat.format(totalCount), 
				timeElapsed / NANO_TO_SECOND_DIVIDER_NUMBER));		
	}
	
	private void updateStartCount() {
		startCount += currentCount;
	}
	
	private void resetCount() {
		currentCount = 0;
		timeElapsed = 0;
	}
	
	private void increaseCount() {
		currentCount++;
		totalCount++;
	}

	@Override
	public void onReadError(Exception arg0) {
		// NO-OP	
	}
	
	public void setLogInterval(int logInterval){
		this.logInterval = logInterval;
	}
}
