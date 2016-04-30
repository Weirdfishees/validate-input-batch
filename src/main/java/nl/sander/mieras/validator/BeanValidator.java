package nl.sander.mieras.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.InitializingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class BeanValidator implements PassThroughValidator, InitializingBean {
    
	private static final Logger LOG = LoggerFactory.getLogger(BeanValidator.class);
    private javax.validation.Validator validator;
    private Object record;
    private boolean isInvalidRecord;

    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }
    
    @Override
    public void validate(Object target) throws ValidationException {
        this.record = target;
        this.isInvalidRecord = false;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
        
        if(constraintViolations.size() > 0) {
            logInvalidRecord(constraintViolations);
            this.isInvalidRecord = true;
        }
    }    

    private void logInvalidRecord(Set<ConstraintViolation<Object>> constraintViolations) {    	
    	StringBuilder constraintMessage = new StringBuilder();        
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            constraintMessage.append(constraintViolation.getMessage());
        }
        LOG.info(String.format("The following record did not pass validation because column %s: \n %s", constraintMessage.toString(),record.toString()));        
    }

	@Override
	public Object passthrough(Object value) {
		if (isInvalidRecord) {
			return value;
		} else return null;
	}    
}