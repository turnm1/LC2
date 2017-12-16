package at.sms.business.sdk.http;

import at.sms.business.sdk.exception.AuthorizationFailedException;
import at.sms.business.sdk.exception.HttpConnectionException;

/**
 * The HttpDispatcher is responsible for sending synchronous requests 
 * to the business-plattform-api. The standard media-type is <b>application/json</b>.

 * @author Sebastian Wilhelm
 *
 */
public interface HttpDispatcher {

	
	/**
	 * Is used for sending a synchronous post-request, where requestRessource is formatted as json-string.
	 * The request is using basic-authorization for accessing the server.
	 * @param username The username of the account.
	 * @param password The password of the account.
	 * @param path The path to the ressource.
	 * @param requestRessource The ressource that will be encoded into a json-string.
	 * @param responseHandler  The handler that is processing the response.
	 * @param queryParams The query-params included into the request.
	 * @throws AuthorizationFailedException Is thrown when the authorization by username password went wrong. 
	 * @throws HttpConnectionException Is thrown when a problem occurs on the http-connection-level. 
	 */
	public <T> void sendPostRequest(String username, String password,
			 String path, Object requestRessource,
			ResponseHandler<T> responseHandler, String queryParams) throws AuthorizationFailedException, HttpConnectionException;

	/**
 	 * Is used for sending a synchronous post-request, where requestRessource is formatted as json-string.
	 * The request is using basic-authorization for accessing the server.
	 * @param username The username of the account.
	 * @param password The password of the account.
	 * @param path The path to the ressource.
	 * @param responseHandler  The handler that is processing the response.
	 * @param queryParams The query-params included into the request.
	 */
	public <T> void sendGetRequest(String username, String password,
			 String path, ResponseHandler<T> responseHandler,
			String queryParams);
			


}
