package at.sms.business.sdk.client.util;




/**
 * <h3>Status Codes:</h3>
 * <pre>
 * 2000: OK
 * 2001: OK_QUEUED
 * 2002: OK_TEST
 * 
 * //~ Client Error Status Codes =================================
 * 
 * 4000: MALFORMED_XML
 * 4001: INVALID_CREDENTIALS
 * 4002: INVALID_RECIPIENT
 * 4003: INVALID_SENDER
 * 4004: INVALID_MESSAGE_TYPE
 * 4005: INVALID_MESSAGE_ALPHABET
 * 4006: INVALID_MESSAGE_CLASS
 * 4007: INVALID_MESSAGE_DATA
 * 4008: INVALID_MESSAGE_ID
 * 4009: INVALID_TEXT
 * 4010: INVALID_AUTOSEGMENT
 * 4011: INVALID_COD
 * 4012: THROTTLING
 * 4013: MSG_LIMIT_EXCEEDED
 * 4014: UNAUTHORIZED_IP
 * 4015: INVALID_MESSAGE_PRIORITY
 * 4016: INVALID_COD_RETURNADDRESS
 * 4017: MULTISEGMENTS
 * 
 * // Newly added for this Java interface:
 * 
 * 4018: API_METHOD_FORBIDDEN
 * 4019: PARAMETER_MISSING
 * 4020: INVALID_API_KEY
 * 4021: INVALID_AUTH_TOKEN
 * 4022: ACCESS_DENIED
 * 4023: THROTTLING_SPAMMING_IP
 * 4024: THROTTLING_TOO_MANY_REQUESTS
 * 4025: THROTTLING_TOO_MANY_RECIPIENTS
 * 4026: MAX_SMS_PER_MESSAGE_EXCEEDED
 * 
 * //~ Server Error Status Codes ======================================
 * 
 * 5000: INTERNAL_ERROR
 * 5003: SERVICE_UNAVAILABLE
 * 
 *</pre>
 */
public class StatusCodes {

	
	/** Request Accepted OK, Message(s) sent  */
	public static final int OK							= 2000;
	
	/** Request Accepted OK, Message(s) queued  */
	public static final int OK_QUEUED					= 2001;
	
	/** Test Request Accepted OK */
	public static final int OK_TEST						= 2002;
	
	//~ Client Error Status Codes =================================
	
	/** Malformed XML, JSON, etc.. Data */
	public static final int MALFORMED_XML				= 4000;
	
	/** Invalid Credentials */
	public static final int INVALID_CREDENTIALS			= 4001;
	
	/** Invalid Recipient (200). 
	 * One or more recipients are not in the correct format 
	 * or are containing invalid msisdns*/
	public static final int INVALID_RECIPIENT			= 4002;
	
	/** Invalid Sender */
	public static final int INVALID_SENDER				= 4003;
	
	/** Invalid Message Type */
	public static final int INVALID_MESSAGE_TYPE		= 4004;
	
	/** Invalid Message Alphabet */
	public static final int INVALID_MESSAGE_ALPHABET	= 4005;
	
	/** Invalid Message Class */
	public static final int INVALID_MESSAGE_CLASS		= 4006;
	
	/** Invalid Message Data.
	 *  One of the message segments is invalid. */
	public static final int INVALID_MESSAGE_DATA		= 4007;
	
	/** Invalid Message Id */
	public static final int INVALID_MESSAGE_ID			= 4008;
	
	/** Invalid Text. Text part contains errors. */
	public static final int INVALID_TEXT				= 4009;
	
	/** Invalid Autosegment. 
	 * The given autosegment value is invalid */
	public static final int INVALID_AUTOSEGMENT			= 4010;
	
	/** Invalid COD */
	public static final int INVALID_COD					= 4011;
	
	/** Throttling. The allowed message rate exceeded */
	public static final int THROTTLING					= 4012;
	
	/** Message Limit exceeded. 
	 * The allowed sent message limit exceeded.  */
	public static final int MSG_LIMIT_EXCEEDED			= 4013;
	
	/** Unauthorized IP Address */
	public static final int UNAUTHORIZED_IP				= 4014;
	
	/** Invalid Message Priority */
	public static final int INVALID_MESSAGE_PRIORITY	= 4015;
	
	/** Invalid COD ReturnAddress */
	public static final int INVALID_COD_RETURNADDRESS	= 4016;
	
	/** Multisegments. The message would generate multi segments, 
	 * but AllowMultiSegments is not enabled */
	public static final int MULTISEGMENTS				= 4017;

	// Newly added for this Java interface:
	
	/** Method is not allowed */
	public static final int API_METHOD_FORBIDDEN			= 4018;
	
	/** 4019 A Required Parameter was not given (2) */
	public static final int PARAMETER_MISSING				= 4019;

	/** The api key is invalid (600) */
	public static final int INVALID_API_KEY					= 4020;

	/** Invalid Auth Token (100) */
	public static final int INVALID_AUTH_TOKEN				= 4021;

	/** The access to the api was denied (97) */
	public static final int ACCESS_DENIED					= 4022;
	
	/** Rate Limit Exceeded. IP SpamCheck */
	public static final int THROTTLING_SPAMMING_IP			= 4023;
	
	/** Too many requests sent in a short time (98) */
	public static final int THROTTLING_TOO_MANY_REQUESTS	= 4024;
	
	/** Transfer rate for immediate transmissions exceeded. 
	 * Too many Recipients in this Request */
	public static final int THROTTLING_TOO_MANY_RECIPIENTS	= 4025;
	
	/** Message content results in too many (automatically generated) sms segments
	 * The message content does not fit into the amount of SMS limited by MaxSmsPerMessage  
	 */
	public static final int MAX_SMS_PER_MESSAGE_EXCEEDED	= 4026;
	
	//~ Server Error Status Codes ======================================
	
	/** Internal Error */
	public static final int INTERNAL_ERROR				= 5000;
	
	/** Service Unavailable (99) */
	public static final int SERVICE_UNAVAILABLE  		= 5003;
	

}
