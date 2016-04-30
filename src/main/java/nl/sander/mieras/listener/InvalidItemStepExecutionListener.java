package nl.sander.mieras.listener;

import java.text.DecimalFormat;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidItemStepExecutionListener extends StepExecutionListenerSupport {
	
	private static final String PATTERN = ",###";
	private DecimalFormat decimalFormat = new DecimalFormat(PATTERN);
	
	private static final Logger LOG = LoggerFactory.getLogger(InvalidItemStepExecutionListener.class);

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(String.format("From the total items read [%s] a total amount of [%s] invalid records were found and writen to the configured file", 
				decimalFormat.format(stepExecution.getReadCount()),
				decimalFormat.format(stepExecution.getWriteCount())));
		return super.afterStep(stepExecution);
	}
	
	

}
