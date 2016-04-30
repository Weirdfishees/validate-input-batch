package nl.sander.mieras.job;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceArrayPropertyEditor;

import nl.sander.mieras.aggregator.HeaderAggregator;
import nl.sander.mieras.domain.Person;
import nl.sander.mieras.domain.Ranking;
import nl.sander.mieras.listener.ConcreteItemProcessorListener;
import nl.sander.mieras.listener.ConcreteItemReaderListener;
import nl.sander.mieras.listener.InvalidItemStepExecutionListener;
import nl.sander.mieras.processor.PassThroughValidatingItemProcessor;
import nl.sander.mieras.tokenizer.HeaderTokenizer;
import nl.sander.mieras.validator.BeanValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	private static final String TARGET_FILE = "target/invalidRecord.csv";

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
	            .reader(rankingReader())	            
	            .listener(readerListener())
	            .processor(processor())
	            .listener(processorListener())
	            .writer(writer())
	            .listener(invalidItemListener())
	            .build();
	}
	
	// Reader - Processor - Writer
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public MultiResourceItemReader multiReader(){
		MultiResourceItemReader reader = new MultiResourceItemReader();
		reader.setResources(getResources("src/main/resources"));
		reader.setDelegate(rankingReader());
		return reader;
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean	
    public FlatFileItemReader rankingReader() {
        FlatFileItemReader reader = new FlatFileItemReader();        
        reader.setLinesToSkip(1);        
        reader.setSkippedLinesCallback(tokenizeHeader());
        reader.setResource(getInputFile());
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(tokenizeHeader());
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Ranking>() {{
                setTargetType(Ranking.class);
            }});
        }});       
        return reader;
    }	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean	
	public FlatFileItemReader personReader() {
		FlatFileItemReader reader = new FlatFileItemReader();        
		reader.setLinesToSkip(1);        
		reader.setSkippedLinesCallback(tokenizeHeader());
		reader.setResource(getInputFile());
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
	public PassThroughValidatingItemProcessor processor(){
		PassThroughValidatingItemProcessor processor = new PassThroughValidatingItemProcessor<>();
		processor.setValidator(validator());
		return processor;
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FlatFileItemWriter writer(){
		FlatFileItemWriter writer = new FlatFileItemWriter();
		writer.setResource(new FileSystemResource(new File(TARGET_FILE)));
		writer.setLineAggregator(aggregateHeader());		
		return writer;
	}    
    
    // Support Beans
	/*@Bean
	public Resource[] getResources(String stagingDirectory) {
		ResourceArrayPropertyEditor resourceLoader = new ResourceArrayPropertyEditor();
		resourceLoader.setAsText("file:" + stagingDirectory + "/*.csv");
		Resource[] resources = (Resource[]) resourceLoader.getValue();
		return resources;
	}*/
	
	@Bean
	public ClassPathResource getInputFile(){
		return new ClassPathResource("rankings.csv");		
	}
	
    @Bean 
    public HeaderTokenizer tokenizeHeader(){
    	HeaderTokenizer tokenizer = new HeaderTokenizer();
    	//optional setting, custom delimiter is set to ','
    	//tokenizer.setDelimiter(",");
    	tokenizer.setInputResource(getInputFile());
    	return tokenizer;
    }
    
    @Bean
    public HeaderAggregator aggregateHeader(){
    	HeaderAggregator aggregator = new HeaderAggregator();
    	aggregator.setHeaderTokenizer(tokenizeHeader());
    	return aggregator;
    }
    
    @Bean
    public BeanValidator validator(){
    	BeanValidator validator = new BeanValidator();
    	//optional setting, logging is disable by standard
    	//validator.setEnableLogging(true);
    	return validator;
    }    
    
    @Bean
    public ConcreteItemReaderListener readerListener(){
    	ConcreteItemReaderListener listener = new ConcreteItemReaderListener();
    	//optional setting, custom logging is set to 1000, increase for less verbose logging
    	listener.setLogInterval(100000);
    	return listener;
    }
    
    @Bean
    public ConcreteItemProcessorListener processorListener(){
    	ConcreteItemProcessorListener listener = new ConcreteItemProcessorListener();
    	//optional setting, custom logging is set to 1000, increase for less verbose logging
    	listener.setLogInterval(100000);
    	return listener;
    }
    
    @Bean
    public InvalidItemStepExecutionListener invalidItemListener(){
    	return new InvalidItemStepExecutionListener();    	
    }
}
