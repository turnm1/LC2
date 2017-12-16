package at.sms.business.sdk.domain;

/**
 * Domain-Object used for specifying a text-message.
 * @author Sebastian Wilhelm
 *
 */
public class TextMessage extends Message {

	private String messageContent;

	/**
	 * Constructor for creating a text-message.
	 * @param recipientAddressList The list of recipients for the message.
	 * @param messageContent The content of the message.
	 */
	public TextMessage(long[] recipientAddressList, String messageContent) {
		super.setRecipientAddressList(recipientAddressList);
		this.messageContent = messageContent;
	}
	
	/**
	 * Returns the content of the message.
	 * @return the content of the message.
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * Sets the textual content of the message.Has to be utf-8 encoded. 
	 * @param messageContent the content of the message.
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
}
