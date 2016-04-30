package nl.sander.mieras.listener;

import java.text.DecimalFormat;

public abstract class AbstractItemListener {
	
	private static final String PATTERN = ",###";	
	protected static final double NANO_TO_SECOND_DIVIDER = 1_000_000_000.0;	
	
	private long startTime;
	private int logInterval = 1000;
	private int currentCount;

	protected int startCount = 0;
	protected int totalCount;
	protected long timeElapsed;
	protected DecimalFormat decimalFormat = new DecimalFormat(PATTERN);	
	
	public void beforeRead() {
		startTime = System.nanoTime();				
	}
	
	public void beforeProcess(Object arg0) {
		startTime = System.nanoTime();			
	}

	public void afterRead(Object item) {
		performAfterActions();		
	}

	public void afterProcess(Object arg0, Object arg1) {
		performAfterActions();		
	}
	
	private void performAfterActions() {
		updateTimeElapsed();		
		if (currentCount == logInterval) {			
			displayMessage();
			updateStartCount();
			resetCount();
		} else {
			increaseCount();
		}
	}
		
	public abstract void displayMessage();

	private void updateTimeElapsed() {
		timeElapsed += System.nanoTime() - startTime;
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

	public void onReadError(Exception arg0) {
		// NO-OP	
	}
	
	public void onProcessError(Object arg0, Exception arg1) {
		// NO-OP		
	}
	
	public void setLogInterval(int logInterval){
		this.logInterval = logInterval;
	}

}
