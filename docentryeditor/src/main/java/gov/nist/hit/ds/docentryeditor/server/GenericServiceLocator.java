package gov.nist.hit.ds.docentryeditor.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

import java.util.logging.Logger;

public class GenericServiceLocator implements ServiceLocator {

	private static final Logger LOGGER = Logger.getLogger(GenericServiceLocator.class.getName());

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			LOGGER.info("getInstance for Service : " + clazz);
			Object service;
			service = clazz.newInstance();
			LOGGER.info("serviceLocator returned : " + service);
			return service;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
