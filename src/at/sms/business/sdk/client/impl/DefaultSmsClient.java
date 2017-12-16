package at.sms.business.sdk.client.impl;

import java.io.UnsupportedEncodingException;

import at.sms.business.api.domain.sms.BinarySmsSendRequest;
import at.sms.business.api.domain.sms.SmsSendResponse;
import at.sms.business.api.domain.sms.TextSmsSendRequest;
import at.sms.business.sdk.client.SmsClient;
import at.sms.business.sdk.client.util.Base64Util;
import at.sms.business.sdk.client.util.CopyUtil;
import at.sms.business.sdk.domain.BinaryMessage;
import at.sms.business.sdk.domain.TextMessage;
import at.sms.business.sdk.exception.ApiException;
import at.sms.business.sdk.exception.AuthorizationFailedException;
import at.sms.business.sdk.exception.HttpConnectionException;
import at.sms.business.sdk.exception.ParameterValidationException;
import at.sms.business.sdk.http.HttpDispatcher;
import at.sms.business.sdk.http.ResponseHandler;
import at.sms.business.sdk.http.impl.DefaultHttpDispatcher;
import at.sms.business.sdk.http.impl.SendSmsResponseHandler;

/**
 * Default implementation of the SmsClient.
 * 
 * @author Sebastian Wilhelm
 */
public class DefaultSmsClient implements SmsClient {

    /*
     * library version number
     */
    public static String VERSION = "1.0.1";
    
	private HttpDispatcher httpDispatcher;
	private String username;
	private String password;
	
	/**
	 * The constructor of the SmsClient.
	 * 
	 * @param username
	 *            The username of the account.
	 * @param password
	 *            The password of the account.
	 * @param baseUrl
	 *            The url to the websms-api.
	 * @throws AuthorizationFailedException
	 */
	public DefaultSmsClient(String username, String password, String baseUrl)
			throws AuthorizationFailedException {

		if (username == null || password == null || username.isEmpty()
				|| password.isEmpty())
			throw new AuthorizationFailedException(
					"A username and password has to be provided for proper functioning.");
		
		if(baseUrl.endsWith("/"))
			baseUrl = baseUrl.substring(0, baseUrl.length()-1);
			
		httpDispatcher = new DefaultHttpDispatcher(baseUrl);
		this.username = username;
		this.password = password;

	}

	/**
	 * The constructor of the SmsClient.
	 * 
	 * @param username
	 *            The username of the account.
	 * @param password
	 *            The password of the account.
	 * @param baseUrl
	 *            The url to the websms-api.
	 * @param readTimeout
	 *            milliseconds how long the client is allowed to wait for the
	 *            response.
	 * @throws AuthorizationFailedException
	 */
	public DefaultSmsClient(String username, String password, String baseUrl,
			int readTimeout) throws AuthorizationFailedException {

		if (username == null || password == null || username.isEmpty()
				|| password.isEmpty())
			throw new AuthorizationFailedException(
					"A username and password has to be provided for proper functioning.");

		httpDispatcher = new at.sms.business.sdk.http.impl.DefaultHttpDispatcher(
				baseUrl, readTimeout);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int send(BinaryMessage binaryMessage, boolean test) throws ApiException,
			ParameterValidationException, AuthorizationFailedException,
			HttpConnectionException {

		if (binaryMessage.getBinaryMessageContent() == null || binaryMessage.getBinaryMessageContent().length == 0)
			throw new ParameterValidationException(
					"No binary-message-content was specified for the sms-message.");

		if (binaryMessage.getRecipientAddressList()== null || binaryMessage.getRecipientAddressList().length == 0) {
			throw new ParameterValidationException(
					"No receivers were specified for the sms-message.");
		}

		if (username == null || password == null || username.isEmpty()
				|| password.isEmpty())
			throw new AuthorizationFailedException(
					"A username and password has to be provided for proper functioning.");

		if (!(binaryMessage.getPriority() >= 0))
			throw new ParameterValidationException(
					"The parameter priority has to be nonnegative.");

		byte[][] binaryMessageContent = binaryMessage.getBinaryMessageContent();
		
		String[] base64EncodedByteArray = new String[binaryMessageContent.length];
		for (int i = 0; i < binaryMessageContent.length; i++) {
			try {
				base64EncodedByteArray[i] = new String(
						Base64Util.encode(binaryMessageContent[i]), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new ParameterValidationException(
						"Binary array can't be encoded into UTF-8.");

			}
		}

		BinarySmsSendRequest request = CopyUtil.constructAClassFromAObject(binaryMessage, BinarySmsSendRequest.class);
		request.setMessageContent(base64EncodedByteArray);
		request.setTest(test);

		ResponseHandler<SmsSendResponse> responseHandler = new SendSmsResponseHandler();
		httpDispatcher.sendPostRequest(username, password,
				"/json/smsmessaging/binary", request, responseHandler, null);

		SmsSendResponse response = responseHandler.getResponse();
		return response.getStatusCode();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int send(TextMessage textMessage, int maxSmsPerMessage, boolean test)
			throws ApiException, ParameterValidationException,
			AuthorizationFailedException, HttpConnectionException {

		if (textMessage.getMessageContent() == null
				|| textMessage.getMessageContent().isEmpty())
			throw new ParameterValidationException(
					"No message-content was specified for the sms-message.");

		if (textMessage.getRecipientAddressList() == null
				|| textMessage.getRecipientAddressList().length == 0) {
			throw new ParameterValidationException(
					"No receivers were specified for the sms-message.");
		}

		if (textMessage.getPriority() < 0)
			throw new ParameterValidationException(
					"The parameter priority has to be nonnegative.");

		TextSmsSendRequest request = CopyUtil.constructAClassFromAObject(textMessage, TextSmsSendRequest.class);
		request.setMaxSmsPerMessage(maxSmsPerMessage);
		request.setTest(test);
		
		ResponseHandler<SmsSendResponse> responseHandler = new SendSmsResponseHandler();
		httpDispatcher.sendPostRequest(username, password,
				"/json/smsmessaging/text", request, responseHandler, null);

		SmsSendResponse response = responseHandler.getResponse();

		return response.getStatusCode();
	}



}
