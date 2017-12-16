package at.sms.business.sdk.exception;

/**
 * Is thrown when a parameter seems invalid, this also happens when mandatory parameters weren't set.
 * @author Sebastian Wilhelm
 *
 */
public class ParameterValidationException extends Exception {

	public ParameterValidationException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
