package vendi.rest.util.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import vendi.rest.util.logging.Logged.Level;

/**
 * Provides a simple interceptor to log input and output for a method. Uses
 * ObjectMapper to serialize the objects.
 * 
 * @author anantharam.v
 *
 */
public class LoggingInterceptor implements MethodInterceptor {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		StringBuilder logMessage = new StringBuilder();
		Logged loggedAnnotation = methodInvocation.getMethod().getAnnotation(
				Logged.class);
		Level loggerLevel = loggedAnnotation.level();
		Class className = methodInvocation.getMethod().getDeclaringClass();
		Logger logger = LoggerFactory.getLogger(className);

		try {
			if (loggedAnnotation.request()) {
				logMessage.append("Request:{");
				logMessage.append("method: "+methodInvocation.getMethod().getName()+" ");
				for (Object argument : methodInvocation.getArguments()) {
					logMessage.append(serializeForLogging(argument));
				}
				logMessage.append("}");
			}

			Object response = methodInvocation.proceed();

			if (loggedAnnotation.response()) {
				logMessage.append("Response:{");
				logMessage.append(serializeForLogging(response));
				logMessage.append("}");
			}
			logAtLevel(logger, loggerLevel, logMessage.toString(), null);
			return response;
		} catch (Throwable e) {
			if (loggedAnnotation.response()) {
				logMessage.append("Exception:{");
				logMessage.append(e.getMessage());
				logMessage.append("}");
			}
			logAtLevel(logger, loggerLevel, logMessage.toString(), e);
			throw e;
		}
	}

	private StringBuilder serializeForLogging(Object argument) {
		StringBuilder message = new StringBuilder();
		if (argument == null) {
			message.append("null");
		} else {
			message.append(argument.getClass()).append("{");
			if (objectMapper.canSerialize(argument.getClass())) {
				try {
					message.append(objectMapper.writeValueAsString(argument));
				} catch (Exception e) {
					message.append("Exception while serializing.").append(
							e.getMessage());
				}
			} else {
				message.append("Cannot serialize object.");
			}
			message.append("}");
		}
		return message;
	}

	private void logAtLevel(Logger logger, Level level, String logMessage,
			Throwable e) {

		if (StringUtils.isEmpty(logMessage)) {
			return;
		}
		switch (level) {
		case TRACE:
			logger.trace(logMessage, e);
			break;
		case DEBUG:
			logger.debug(logMessage, e);
			break;
		case INFO:
			logger.info(logMessage, e);
			break;
		case WARN:
			logger.warn(logMessage, e);
			break;
		case ERROR:
			logger.error(logMessage, e);
			break;
		}
	}
}
