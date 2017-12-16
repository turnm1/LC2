package at.sms.business.sdk.domain;

import at.sms.business.api.domain.sms.SenderAddressType;

/**
 * A basic Message-Domain object 
 * @author Sebastian Wilhelm
 *
 */
public class Message {

	private long[] recipientAddressList;
	private String senderAddress;
	private SenderAddressType senderAddressType;
	private boolean sendAsFlashSms;
	private String notificationCallbackUrl;
	private String clientMessageId;
	private int priority;
	
	/**
	 * A message should ony be instanced by the derived classes.
	 */
	protected Message() {
		
	}
	
	
	/**
	 * Returns the recipient-address-list. 
	 * 
	 * @return recipientAddressList
	 *            A list of receiver-telephone-numbers. of international format e.g.:49xxxxxxxx.
	 **/
	public long[] getRecipientAddressList() {
		return recipientAddressList;
	}
	/**
	 * Sets the recipient-address-list. The attribute is mandatory.
	 * 
	 * @param recipientAddressList
	 *            A list of receiver-telephone-numbers of international format e.g.:49301234567.
	 **/
	public void setRecipientAddressList(long[] recipientAddressList) {
		this.recipientAddressList = recipientAddressList;
	}
	
	/**
	 * Returns the address of the sender
	 * @return senderAddress
	 *            The address of the sender  (e.g. The number to which the receiver can answer).
	 */
	public String getSenderAddress() {
		return senderAddress;
	}
	
	/**
	 * Sets the address of the sender 
	 * @param senderAddress
	 * 				The address of the sender  (e.g. The number to which the receiver can answer).
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	
	/**
	 * Returns the Type of the Sender-Address. Usually the type of sender address is obtained automatically. if
	 *         unsure sender address type can be set.
	 * @return senderAddressType Enum to specify the SenderAddressType (e.g. national,international,alphanumeric,shortcode).
	 */
	public SenderAddressType getSenderAddressType() {
		return senderAddressType;
	}
	
	/**
	 *  Sets the Type of the Sender-Address. Usually the type of sender address is obtained automatically. if
	 *         unsure sender address type can be set.
	 * @param senderAddressType Enum to specify the SenderAddressType (e.g. national,international,alphanumeric,shortcode).
	 *         
	 */
	public void setSenderAddressType(SenderAddressType senderAddressType) {
		this.senderAddressType = senderAddressType;
	}
	
	/**
	 * Returns the boolean if the message is sent as flash-sms.
	 * @return sendAsFlashSms True if it should be send as flash-SMS.
	 */
	public boolean isSendAsFlashSms() {
		return sendAsFlashSms;
	}
	
	/**
	 * Sets the boolean if the message is sent as flash-sms.
	 * @param sendAsFlashSms True if it should be send as flash-SMS.
	 */
	public void setSendAsFlashSms(boolean sendAsFlashSms) {
		this.sendAsFlashSms = sendAsFlashSms;
	}
	
	/**
	 * Returns the url for the notification-callback.
	 * @return notificationCallbackUrl The url that receives the confirmation-receipt.
	 */
	public String getNotificationCallbackUrl() {
		return notificationCallbackUrl;
	}
	
	/**
	 * Sets the url for the notification-callback.
	 * @param notificationCallbackUrl The url that receives the confirmation-receipt.
	 */
	public void setNotificationCallbackUrl(String notificationCallbackUrl) {
		this.notificationCallbackUrl = notificationCallbackUrl;
	}
	
	/**
	 * Returns the id of the client-message.
	 * @return
	 *   An id of the message choosen by the client.
	 */
	public String getClientMessageId() {
		return clientMessageId;
	}
	
	/**
	 * Sets the id of the client-message.
	 * @param clientMessageId An id of the message choosen by the client.
	 */
	public void setClientMessageId(String clientMessageId) {
		this.clientMessageId = clientMessageId;
	}
	
	/**
	 * Returns the priority of the message.
	 * @return priority  The priority of the message. Should be between 0-9 and default
	 *            is 0.
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * Sets the priority of the message.
	 * @param priority The priority of the message. Should be between 0-9 and default
	 *            is 0.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
