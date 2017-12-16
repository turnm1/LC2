package at.sms.business.sdk.http.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.sms.business.api.domain.sms.SmsSendResponse;
import at.sms.business.sdk.client.util.DeleteUnderscoreAtTheBeginning;
import at.sms.business.sdk.client.util.StatusCodes;
import at.sms.business.sdk.exception.ApiException;
import at.sms.business.sdk.http.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Is responsible for parsing and evaluating the response when user sends an
 * SmsSendRequest.
 * 
 * @author Sebastian Wilhelm
 * 
 */
public class SendSmsResponseHandler implements ResponseHandler<SmsSendResponse> {


	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ApiException responseException = null;
	private SmsSendResponse reponseRessource = null;

	
	/**
	 * Returns the response if it was set. If a exception was set, the 
	 * exception will be thrown.
	 * @return SmsSendResponse The ressource if the server returned an success-status-code(2000,2001,2002).
	 * @throws ApiException Will be thrown if the server returned an status-code which indicates an error. 
	 */
	@Override
	public SmsSendResponse getResponse() throws ApiException {

		if (responseException != null)
			throw responseException;
		else
			return reponseRessource;
	}

	
	/**
	 * Parses and evaluates the response of the SendSmsRequest
	 * 
	 * @param stringResponse
	 *            The response of the request.
	 */
	@Override
	public void process(String stringResponse) {

		GsonBuilder builder = new GsonBuilder();
		builder.setFieldNamingStrategy(new DeleteUnderscoreAtTheBeginning());
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		SmsSendResponse response = gson.fromJson(stringResponse,
				SmsSendResponse.class);

		switch (response.getStatusCode()) {
		case StatusCodes.PARAMETER_MISSING:
			responseException = new ApiException(response
					.getStatusMessage(), response
					.getStatusCode());
			break;
		case StatusCodes.OK:
		case StatusCodes.OK_QUEUED:
		case StatusCodes.OK_TEST:
			this.reponseRessource = response;
			break;

		default:
			responseException = new ApiException(response
					.getStatusMessage(), response
					.getStatusCode());
			break;
		}

	}



}
