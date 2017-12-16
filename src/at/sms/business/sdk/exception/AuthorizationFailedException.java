package at.sms.business.sdk.exception;

/**
 * Is thrown when the authorization of the username or password failed.
 * @author Sebastian Wilhelm
 *
 */
public class AuthorizationFailedException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public AuthorizationFailedException(String message) {
		super(message);
	}

}
