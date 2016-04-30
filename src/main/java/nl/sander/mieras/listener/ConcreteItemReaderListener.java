package nl.sander.mieras.listener;

import org.springframework.batch.core.ItemReadListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ConcreteItemReaderListener extends AbstractItemListener implements ItemReadListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(ConcreteItemReaderListener.class);	

	public void displayMessage() {
		LOG.info(String.format("Read records [%s] to [%s] in average %.2f seconds", 
				decimalFormat.format(startCount), 
				decimalFormat.format(totalCount), 
				timeElapsed / NANO_TO_SECOND_DIVIDER));		
	}	
}
