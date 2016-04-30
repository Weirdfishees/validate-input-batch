package nl.sander.mieras.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.OutputCapture;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import nl.sander.mieras.job.Application;

public class BatchApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultSettings() throws Exception {
		assertThat(SpringApplication.exit(SpringApplication.run(Application.class))).isEqualTo(0);
		String output = this.outputCapture.toString();
		assertThat(output).contains("completed with the following parameters");
	}

}