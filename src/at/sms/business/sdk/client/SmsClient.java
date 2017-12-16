package at.sms.business.sdk.client;

import at.sms.business.sdk.domain.BinaryMessage;
import at.sms.business.sdk.domain.TextMessage;
import at.sms.business.sdk.exception.ApiException;
import at.sms.business.sdk.exception.AuthorizationFailedException;
import at.sms.business.sdk.exception.HttpConnectionException;
import at.sms.business.sdk.exception.ParameterValidationException;

/**
 * The interface of an client for evaluating,creating and delegating
 * sms-requests to the websms-api
 * 
 * @author Sebastian Wilhelm
 * 
 */
public interface SmsClient {

	/**
	 * Delivers a text-sms-request to the websms-api.
	 * 
	 * @param textMessage
	 *            A derived message-object which holds every possible parameters
	 *            needed for sending an text-sms.
	 * @param maxSmsPerMessage
	 *            Defines an upper-limit of how many messages should be sent.
	 * @param test
	 *            True if the use of the function is for testing-purposes, and
	 *            no real-sms should be send.
	 * @return The status-code for successful requests.
	 * @throws ApiException
	 *             Returns an exception with a status-code, that was thrown at
	 *             api-level.
	 * @throws ParameterValidationException
	 *             The parameters which where provided are invalid. No request
	 *             was made to the server.
	 * @throws AuthorizationFailedException
	 *             Is thrown when the authorization by username/password failed.
	 * @throws HttpConnectionException
	 *             Is thrown when a problem occurs while connecting to the api.
	 */
	public int send(TextMessage textMessage, int maxSmsPerMessage, boolean test)
			throws ApiException, ParameterValidationException,
			AuthorizationFailedException, HttpConnectionException;

	/**
	 * Delivers a sms-request with binary-content to the websms-api.
	 * 
	 * @param binaryMessage
	 *            A derived message-object which holds every possible parameters
	 *            needed for sending an binary-sms.
	 * @param test
	 *            True if the use of the function is for testing-purposes, and
	 *            no real-sms should be send.
	 * 
	 * @return The status-code for successful requests.
	 * @throws ApiException
	 *             Returns an exception with a status-code, that was thrown at
	 *             api-level.
	 * @throws ParameterValidationException
	 *             The parameters which where provided are invalid. No request
	 *             was made to the server.
	 * @throws AuthorizationFailedException
	 *             Is thrown when the authorization by username/passsword
	 *             failed.
	 * @throws HttpConnectionException
	 *             Is thrown when connection is refused or a timeout occured.
	 */
	public int send(BinaryMessage binaryMessage, boolean test)
			throws ApiException, ParameterValidationException,
			AuthorizationFailedException, HttpConnectionException;

}
