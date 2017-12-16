package at.sms.business.sdk.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

import at.sms.business.sdk.client.impl.DefaultSmsClient;
import at.sms.business.sdk.client.util.Base64Util;
import at.sms.business.sdk.client.util.DeleteUnderscoreAtTheBeginning;
import at.sms.business.sdk.exception.AuthorizationFailedException;
import at.sms.business.sdk.exception.HttpConnectionException;
import at.sms.business.sdk.http.HttpDispatcher;
import at.sms.business.sdk.http.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the HttpDispatcher-Interface
 * 
 * @author Sebastian Wilhelm
 * 
 */
public class DefaultHttpDispatcher implements HttpDispatcher {

	// If nothing is set, a standard read timeout will be taken.
	private static int STANDARD_READ_TIMEOUT = 10000;

	// The host-url
	private final String baseUrl;

	// The read-timeout in milliseconds
	private final int readTimeout;

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultHttpDispatcher.class);

	/**
	 * Constructor which initializes the base-url
	 * 
	 * @param baseUrl
	 *            The url where the http-request will be sent.
	 */
	public DefaultHttpDispatcher(String baseUrl) {
		this.baseUrl = baseUrl;
		readTimeout = STANDARD_READ_TIMEOUT;

	}

	/**
	 * Constructor which initializes the base-url and defines the read-timeout
	 * 
	 * @param baseUrl
	 *            The url where the http-request will be sent.
	 * @param readTimeout
	 *            milliseconds how long the client is allowed to wait for the
	 *            response.
	 */
	public DefaultHttpDispatcher(String baseUrl, int readTimeout) {
		this.baseUrl = baseUrl;
		this.readTimeout = readTimeout;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> void sendPostRequest(String username, String password,
			String path, Object requestRessource,
			ResponseHandler<T> responseHandler, String queryParams)
			throws AuthorizationFailedException, HttpConnectionException {

		HttpURLConnection connection = null;
		HttpURLConnection.setFollowRedirects(true);
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		StringBuilder sb = null;
		String line = null;
		URL serverAddress = null;

		GsonBuilder builder = new GsonBuilder();
		builder.setFieldNamingStrategy(new DeleteUnderscoreAtTheBeginning());
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		try {
			serverAddress = new URL(baseUrl + path);
			// set up out communications stuff
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setReadTimeout(readTimeout);
            
			if (username != null && password != null)
				if (!username.isEmpty() && !password.isEmpty())
					connection
							.setRequestProperty(
									"Authorization",
									"Basic "
											+ (new String(
													Base64Util.encode((username
															+ ":" + password)
															.getBytes("UTF-8")),
													"UTF-8")));

			connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Java SDK Client (v" + DefaultSmsClient.VERSION + ", Java " + System.getProperty("java.version") + ")");
			connection.connect();

			wr = new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8"));

			String sendThis = gson.toJson(requestRessource);
			System.out.println("REQUEST " + sendThis);
			wr.write(sendThis);
			wr.flush();
			// read the result from the server
			rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			sb = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				sb.append(line + '\n');
			}

			logger.info(sb.toString());

			responseHandler.process(sb.toString());

		} catch (MalformedURLException e) {
			throw new HttpConnectionException("Malformed server-url.");
		} catch (ProtocolException e) {

			throw new HttpConnectionException("An TCP-error occured.");
		} catch (IOException e) {

			if (e.getMessage().contains(
					"Server returned HTTP response code: 401"))
				throw new AuthorizationFailedException(
						"Wrong username or password.");
			else if (e instanceof java.net.ConnectException)
				throw new HttpConnectionException(
						"Wrong url-parameter or server is not running.");
			else if (e instanceof java.net.UnknownHostException)
				throw new HttpConnectionException("Malformed server-url.");

			throw new HttpConnectionException(e.getMessage());

		} finally {
			// close the connection, set all objects to null
			connection.disconnect();
			rd = null;
			sb = null;
			wr = null;
			connection = null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> void sendGetRequest(String username, String password,
			String path, ResponseHandler<T> responseHandler, String queryParams) {

		// TODO TBI

	}

}
