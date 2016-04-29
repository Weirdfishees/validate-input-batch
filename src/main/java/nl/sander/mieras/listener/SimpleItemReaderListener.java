package nl.sander.mieras.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class SimpleItemReaderListener<Item> implements ItemReadListener<Item>{
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleItemReaderListener.class);	
	private int logInterval = 1000;
	
	private int currentCount;
	private int totalCount;

	@Override
	public void afterRead(Item item) {
		if (currentCount == logInterval) {
			LOG.info(String.format("Read %d records", totalCount));
			currentCount = 0;
		} else {
			currentCount++;
			totalCount++;
		}		
	}

	@Override
	public void beforeRead() {
				
	}

	@Override
	public void onReadError(Exception arg0) {
				
	}
	
	public void setLogInterval(int logInterval){
		this.logInterval = logInterval;
	}
}
