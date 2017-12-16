package at.sms.business.sdk.http;

import at.sms.business.sdk.exception.ApiException;

/**
 * A response-handler-interface which can be derived for use in the http-dispatcher.
 * 
 * @author Sebastian Wilhelm
 *
 * @param <T> An untyped response object.
 */
public interface ResponseHandler<T> {

	/**
	 * Returns the response if it was set. If a exception was set, the 
	 * exception will be thrown.
	 * @return T The resource that should be returned.
	 * @throws ApiException Will be thrown if the api returned an status-code which indicates an exception. 
	 */
	public T getResponse() throws ApiException;

	/**
	 * Parses and evaluates a successful response.
	 * @param response
	 */
	public void process(String response);

}
