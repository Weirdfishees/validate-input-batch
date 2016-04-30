package nl.sander.mieras.validator;

public interface PassThroughValidator<T> {
	
	void validate(T value);
	T passthrough(T value); 

}
