package nl.sander.mieras.aggregator;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import nl.sander.mieras.tokenizer.HeaderTokenizer;

@SuppressWarnings("rawtypes")
public class HeaderAggregator implements LineAggregator {	
	
	private BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
	private HeaderTokenizer headerTokenizer;

	@SuppressWarnings("unchecked")
	@Override
	public String aggregate(Object item) {
		Assert.notNull(item);
		fieldExtractor.setNames(headerTokenizer.getNames());
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
		return StringUtils.arrayToDelimitedString(fields, headerTokenizer.getDelimiter());
	}
	
	public void setHeaderTokenizer(HeaderTokenizer headerTokenizer) {
		this.headerTokenizer = headerTokenizer;
	}	
}
