package nl.sander.mieras.processor;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.util.Assert;

import nl.sander.mieras.validator.PassThroughValidator;

public class PassThroughValidatingItemProcessor<T> extends ValidatingItemProcessor<T> {
	
	private PassThroughValidator<T> validator;	
	
	@Override
	public T process(T item) throws ValidationException {
		validator.validate(item);
		return validator.passthrough(item);
	}	
	
	public void setValidator(PassThroughValidator<T> validator) {
		this.validator = validator;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(validator, "Validator must not be null.");
	}	
}
