package at.sms.business.sdk.client.util;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;

/**
 * Need for transforming the domain-objects with gson into a suitable json-format.
 * @author Sebastian Wilhelm
 *
 */
public class DeleteUnderscoreAtTheBeginning implements FieldNamingStrategy {

	@Override
	public String translateName(Field field) {

		String nameOfField = field.getName();
		if (nameOfField.startsWith("_"))
			return nameOfField.substring(1);
		return nameOfField;
		
	}

}
