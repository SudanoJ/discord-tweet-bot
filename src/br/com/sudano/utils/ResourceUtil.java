package br.com.sudano.utils;

import java.net.URL;

/**
 * 
 * @author https://github.com/rinaldodev
 *
 */
public class ResourceUtil {
	
	@SuppressWarnings("rawtypes")
	public static URL getResource(String resourceName, Class clazz) {
		URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(resourceName);

		if (resourceUrl == null) {
			resourceUrl = ResourceUtil.class.getClassLoader().getResource(resourceName);
		}

		if (resourceUrl == null) {
			ClassLoader classLoader = clazz.getClassLoader();
			if (classLoader != null) {
				resourceUrl = classLoader.getResource(resourceName);
			}
		}

		if ((resourceUrl == null) && (resourceName != null) && (resourceName.charAt(0) != '/')) {
			return getResource('/' + resourceName, clazz);
		}

		return resourceUrl;
	}
}