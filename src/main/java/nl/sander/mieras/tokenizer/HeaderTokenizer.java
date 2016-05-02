package nl.sander.mieras.tokenizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderTokenizer extends DelimitedLineTokenizer implements LineCallbackHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(HeaderTokenizer.class);	
	
	private ClassPathResource inputResource;
	private String delimiter = ",";
	private String[] names;	

	@Override
	public void handleLine(String line) {		
		Resource resource = inputResource;
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(resource.getFile()));
			String[] headerLine = reader.readNext();
			String[] columnNames = new String[headerLine.length];
			for (int i = 0; i < headerLine.length; i++)
			    columnNames[i] = headerLine[i].replaceAll("\\s+","");
			this.names = columnNames;
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		LOG.info(String.format("The header %s, was succesfully tokenized", line));
	}

	@Override
	protected List<String> doTokenize(String line) {
		setNames(this.names);
		return super.doTokenize(line.trim());
	}		
	
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}	

	public void setInputResource(ClassPathResource inputResource) {
		this.inputResource = inputResource;
	}
	
	public String getDelimiter(){
		return delimiter;
	}
	
	public String[] getNames() {
		return names;
	}	
}
