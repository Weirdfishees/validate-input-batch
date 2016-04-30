package nl.sander.mieras.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import nl.sander.mieras.domain.Person;
import nl.sander.mieras.listener.SimpleItemReaderListener;
import nl.sander.mieras.tokenizer.HeaderTokenizer;
import nl.sander.mieras.validator.BeanValidator;
import nl.sander.mieras.writer.DummyItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {	

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
	public Job importUserJob() {
	    return jobBuilderFactory.get("importUserJob")            
	            .flow(validateInput())
	            .end()
	            .build();
	}

	@SuppressWarnings("unchecked")
	@Bean
	public Step validateInput() {
	    return stepBuilderFactory.get("validateInput")
	            .chunk(100_000)	           
	            .reader(reader())	            
	            .listener(listener())
	            .processor(processor())
	            .writer(writer())
	            .build();
	}
	
	// Reader - Processor - Writer
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean	
    public FlatFileItemReader reader() {
        FlatFileItemReader reader = new FlatFileItemReader();        
        reader.setLinesToSkip(1);        
        reader.setSkippedLinesCallback(tokenizeHeader());
        reader.setResource(new ClassPathResource("us-500.csv"));
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(tokenizeHeader());
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});       
        return reader;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ValidatingItemProcessor processor(){
		ValidatingItemProcessor processor = new ValidatingItemProcessor<>();
		processor.setValidator(validator());
		return processor;
	}
    
    @Bean
    public DummyItemWriter writer(){
    	DummyItemWriter writer = new DummyItemWriter();
    	return writer;
    }
    
    // Support Beans	
    @Bean 
    public HeaderTokenizer tokenizeHeader(){
    	HeaderTokenizer tokenizer = new HeaderTokenizer();
    	//optional setting, custom delimiter is set to ','
    	//tokenizer.setDelimiter(",");
    	return tokenizer;
    }
    
    @Bean
    public BeanValidator validator(){
    	BeanValidator validator = new BeanValidator();
    	return validator;
    }
    
    @SuppressWarnings("rawtypes")
    @Bean
    public SimpleItemReaderListener listener(){
    	SimpleItemReaderListener listener = new SimpleItemReaderListener<>();
    	//optional setting, custom logging is set to 1000, increase for less verbose logging
    	listener.setLogInterval(100);
    	return listener;
    }
}
