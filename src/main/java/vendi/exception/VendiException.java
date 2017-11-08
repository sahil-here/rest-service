package vendi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class VendiException extends Exception {

	private static final long serialVersionUID = -2021197902810537770L;

	protected static final Logger logger = LoggerFactory.getLogger(VendiException.class);

    private Integer code;
    private String message;

    public VendiException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public VendiException(Integer code, String message, Exception ex) {
        this.code = code;
        this.message = message;
        logger.error("Exception Cause : " + ex.getMessage());
        logger.error("Exception Stacktrace : " +  Arrays.asList(ex.getStackTrace()).toString());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
