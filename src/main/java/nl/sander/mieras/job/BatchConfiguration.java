package nl.sander.mieras.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import nl.sander.mieras.tokenizer.HeaderTokenizer;
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
	            .chunk(1000)
	            .reader(reader())	            
	            .writer(writer())
	            .build();
	}
	
	@Bean 
	public HeaderTokenizer tokenizeHeader(){
		HeaderTokenizer tokenizer = new HeaderTokenizer();
		tokenizer.setDelimiter(",");
		return tokenizer;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean	
    public FlatFileItemReader reader() {
        FlatFileItemReader reader = new FlatFileItemReader(); 
        reader.setLinesToSkip(1);        
        reader.setSkippedLinesCallback(tokenizeHeader());
        reader.setResource(new ClassPathResource("Master.csv"));
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(tokenizeHeader());
            setFieldSetMapper(new PassThroughFieldSetMapper());
        }});
        return reader;
    }    
    
    @Bean
    public DummyItemWriter writer(){
    	DummyItemWriter writer = new DummyItemWriter();
    	return writer;
    }
}
