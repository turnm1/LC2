package at.sms.business.sdk.exception;

/**
 * Is thrown when a discrepancy regarding to successfull request (200 OK) happens, while connecting to the server, this might
 * range from a simple no connection could be established to a server returned http-error-code. Exception to this is if the 
 * authorization fails, in this special case an AuthorizationException is thrown.  
 * 
 * @author Sebastian Wilhelm
 *
 */
public class HttpConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public HttpConnectionException(String message) {
		super(message);
	}


}
