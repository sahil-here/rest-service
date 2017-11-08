package vendi.rest.util.logging;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation around any method to enable request and response logging.
 * Log level for logging can be set. The log will be created with the invoking
 * class name.
 * 
 * @author anantharam.v
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR })
@BindingAnnotation
public @interface Logged {

	public static enum Level {
		TRACE, DEBUG, INFO, WARN, ERROR
	}

	boolean request() default true;

	boolean response() default true;

	Level level() default Level.INFO;
}
