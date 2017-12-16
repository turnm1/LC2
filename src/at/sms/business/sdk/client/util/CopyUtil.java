package at.sms.business.sdk.client.util;


import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import at.sms.business.sdk.exception.ParameterValidationException;




/**
 * Util class for copying files with the same method-names.
 * @author Sebastian Wilhelm
 *
 */
public class CopyUtil {

	public static <E, D> List<E> constructAListFromAObject(List<D> a, Class<E> b)
			throws ParameterValidationException {

		List<E> listOfConstructedObjects = new ArrayList<E>();

		for (D item : a) {

			listOfConstructedObjects.add(constructAClassFromAObject(item, b));

		}
		return listOfConstructedObjects;
	}

	public static <E> E constructAClassFromAObject(Object a, Class<E> b)
			throws ParameterValidationException {

		try {
			E instanceOfConstruct = b.newInstance();

			copyTwoClassesByMethod(a, instanceOfConstruct);

			return instanceOfConstruct;

		} catch (Exception e) {
			throw new ParameterValidationException(e.toString());
		}

	}

	public static void copyTwoClassesByMethod(Object a, Object b)
			throws ParameterValidationException {

		Method[] fromMethods = a.getClass().getMethods();

		try {
			for (Method fromMethod : fromMethods) {

				if (isGetter(fromMethod)) {

					String getterString = fromMethod.getName();
					String setterString = "s" + getterString.substring(1);

					try {
						

						if (fromMethod.getReturnType().equals(Timestamp.class)) {

							Method setter = b.getClass().getMethod(setterString,
									long.class);
							
							if(setter == null )
								setter = b.getClass().getMethod(setterString,
										Long.class);
								
							if (fromMethod.invoke(a) != null) {
								if (isSetter(setter)) {
									setter.invoke(b, ((Timestamp) fromMethod
											.invoke(a)).getTime());
								}
							} 
							
							
							
						} else {
							Method setter = b.getClass().getMethod(setterString,
									fromMethod.getReturnType());
							
							if (fromMethod.invoke(a) != null)
								if (isSetter(setter)) {

									setter.invoke(b, fromMethod.invoke(a));

								}
						}
					} catch (NoSuchMethodException e) {
						continue;

					}

				}

				if (isBooleanGetter(fromMethod)) {
					String getterString = fromMethod.getName();
					String setterString = "set" + getterString.substring(2);

					try {
						Method setter = b.getClass().getMethod(setterString,
								fromMethod.getReturnType());

						if (fromMethod.invoke(a) != null)
							if (isSetter(setter)) {

								setter.invoke(b, fromMethod.invoke(a));

							}
					} catch (NoSuchMethodException e) {
						continue;

					}
				}

			}
		} catch (Exception e) {

			throw new ParameterValidationException(e.toString());
		}

	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set"))
			return false;
		if (method.getParameterTypes().length != 1)
			return false;
		return true;
	}

	public static boolean isGetter(Method method) {
		if (!method.getName().startsWith("get"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	public static boolean isBooleanGetter(Method method) {
		if (!method.getName().startsWith("is"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}
}
