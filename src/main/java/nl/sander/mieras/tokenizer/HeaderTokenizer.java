package nl.sander.mieras.tokenizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class HeaderTokenizer extends DelimitedLineTokenizer implements LineCallbackHandler, LineAggregator {
	
	private static final Logger LOG = LoggerFactory.getLogger(HeaderTokenizer.class);	
	
	private BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
	private ClassPathResource inputResource;
	private String delimiter = ",";
	private String[] names;	

	@Override
	public void handleLine(String line) {
		Resource resource = inputResource;
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(resource.getFile()));
			this.names = reader.readNext();
		} catch (IOException e) {			
			e.printStackTrace();
		}			
		//this.names = StringUtils.delimitedListToStringArray(line, delimiter);
		LOG.info(String.format("The header %s, was succesfully tokenized", line));
	}

	@Override
	protected List<String> doTokenize(String line) {
		setNames(this.names);
		return super.doTokenize(line);
	}	

	@SuppressWarnings("unchecked")
	@Override
	public String aggregate(Object item) {
		Assert.notNull(item);
		fieldExtractor.setNames(names);
		Object[] fields = this.fieldExtractor.extract(item);
		
		//
		// Replace nulls with empty strings
		//
		Object[] args = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] == null) {
				args[i] = "";
			}
			else {
				args[i] = fields[i];
			}
		}
		return StringUtils.arrayToDelimitedString(fields, this.delimiter);
	}	
	
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public void setInputResource(ClassPathResource inputResource) {
		this.inputResource = inputResource;
	}
	
	
}
