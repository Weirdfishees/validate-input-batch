package nl.sander.mieras.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class SimpleItemReaderListener<Item> implements ItemReadListener<Item>{
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleItemReaderListener.class);
	private static final double NANO_TO_SECOND = 1_000_000_000.0;
	
	private long start;
	
	private int startCount = 0;
	private int logInterval = 1000;
	private int currentCount;
	private int totalCount;
	private long timeElapsed;

	@Override
	public void afterRead(Item item) {		
		timeElapsed += System.nanoTime() - start;		
		if (currentCount == logInterval) {
			LOG.info(String.format("Read records [ %d ] to [ %d ] in average %.2f seconds", startCount, totalCount, timeElapsed / NANO_TO_SECOND));
			startCount += currentCount;
			currentCount = 0;
			timeElapsed = 0;
		} else {
			currentCount++;
			totalCount++;
		}		
	}

	@Override
	public void beforeRead() {
		start = System.nanoTime();				
	}

	@Override
	public void onReadError(Exception arg0) {
				
	}
	
	public void setLogInterval(int logInterval){
		this.logInterval = logInterval;
	}
}
