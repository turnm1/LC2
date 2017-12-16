package at.sms.business.sdk.exception;

/**
 * Is thrown when connection and authorization was successful, but
 * the request invalididates program-logic on the server, so it returned
 * an error-code for evaluating.
 * 
 * @author Sebastian Wilhelm
 *
 */
public class ApiException extends Exception {
	
	public int statuscode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1377188904596343681L;

	public ApiException(String statusMessage, int statuscode){
		
		super("Server returned status-code:" + statuscode + " (" + statusMessage + ")");
		this.statuscode = statuscode;
	}
	
}
