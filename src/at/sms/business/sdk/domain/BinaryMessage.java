package at.sms.business.sdk.domain;

/**
 * A Domain-Object used for specifying a binary-message.
 * @author Sebastian Wilhelm
 *
 */
public class BinaryMessage extends Message {

	private byte[][] binaryMessageContent;
	private boolean userDataHeaderPresent;
	
	/**
	 * Constructor for creating a binary-message.
	 * 
	 * @param recipientAddressList
	 *            The list of recipients for the message.
	 * @param binaryMessageContent
	 *            The content of the binary-message as array of byte-arrays.
	 *            Every byte-array outlines an sms to be send. (e.g.: user sends an
	 *            array consisting of 3 byte-arrays. 3 sms with binary-content will be send. 
	 */
	public BinaryMessage(long[] recipientAddressList,
			byte[][] binaryMessageContent) {
		super.setRecipientAddressList(recipientAddressList);
		this.binaryMessageContent = binaryMessageContent;
	}

	/**
	 * Gets the content of the binary-message.
	 * @return The content of the binary-message as array of byte-arrays.
	 *   
	 */
	public byte[][] getBinaryMessageContent() {
		return binaryMessageContent;
	}

	/**
	 * Sets the content of the binary-message.
	 * @param binaryMessageContent The content of the binary-message as array of byte-arrays.
	 */
	public void setBinaryMessageContent(byte[][] binaryMessageContent) {
		this.binaryMessageContent = binaryMessageContent;
	}

	/**
	 * Returns the boolean if a user-data-header in the binary is present.
	 * @return True if the user-data-header in the present is present.
	 */
	public boolean isUserDataHeaderPresent() {
		return userDataHeaderPresent;
	}

	/**
	 * Sets the boolean if a user-data-header in the binary is present.
	 * @param userDataHeaderPresent The boolean if a user-data-header in the binary is present.
	 */
	public void setUserDataHeaderPresent(boolean userDataHeaderPresent) {
		this.userDataHeaderPresent = userDataHeaderPresent;
	}

}
