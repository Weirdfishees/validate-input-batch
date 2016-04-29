package nl.sander.mieras.tokenizer;

import java.util.List;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderTokenizer extends DelimitedLineTokenizer implements LineCallbackHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(HeaderTokenizer.class);
	
	private String delimiter = ",";
	private String[] names;	

	@Override
	public void handleLine(String line) {
		this.names = StringUtils.delimitedListToStringArray(line, delimiter);
		LOG.info(String.format("The header %s, was succesfully tokenized", line));
	}

	@Override
	protected List<String> doTokenize(String line) {
		setNames(this.names);
		return super.doTokenize(line);
	}
	
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}	
}
