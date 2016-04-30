package nl.sander.mieras.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class BeanValidator implements Validator, InitializingBean {
    
	private static final Logger LOG = LoggerFactory.getLogger(BeanValidator.class);
    private javax.validation.Validator validator;

    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }
    
    public void validate(Object target) throws ValidationException {
        
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
        
        if(constraintViolations.size() > 0) {
            logInvalidRecord(constraintViolations, target);
        }
    }    

    private void logInvalidRecord(Set<ConstraintViolation<Object>> constraintViolations, Object invalidRecord) {    	
    	StringBuilder constraintMessage = new StringBuilder();        
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            constraintMessage.append(constraintViolation.getMessage());
        }
        LOG.info(String.format("The following record did not pass validation because column %s: \n %s", constraintMessage.toString(),invalidRecord.toString()));        
    }
}