package nl.sander.mieras.listener;

import org.springframework.batch.core.ItemProcessListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ConcreteItemProcessorListener extends AbstractItemListener implements ItemProcessListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConcreteItemProcessorListener.class);	
	
	public void displayMessage() {
		LOG.info(String.format("Processed records [%s] to [%s] in average %.2f seconds", 
				decimalFormat.format(startCount), 
				decimalFormat.format(totalCount), 
				timeElapsed / NANO_TO_SECOND_DIVIDER));		
	}
}
