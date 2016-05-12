package nl.sander.mieras.tokenizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import au.com.bytecode.opencsv.CSVReader;

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
			String[] columns = reader.readNext();
			String[] columnNames = new String[columns.length];
			for (int columnIndex = 0; columnIndex < columns.length; columnIndex++)
			    columnNames[columnIndex] = columns[columnIndex].replaceAll("\\s+","");
			this.names = columnNames;
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		LOG.info(String.format("The header %s, was succesfully tokenized", line));
	}

	@Override
	protected List<String> doTokenize(String line) {
		setNames(this.names);
		return super.doTokenize(line.trim().toLowerCase());
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
