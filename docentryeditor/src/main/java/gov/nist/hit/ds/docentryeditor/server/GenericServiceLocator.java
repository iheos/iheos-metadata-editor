package gov.nist.hit.ds.docentryeditor.server;

import java.util.logging.Logger;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class GenericServiceLocator implements ServiceLocator {

	private final Logger logger = Logger.getLogger(GenericServiceLocator.class.getName());

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			logger.info("getInstance for Service : " + clazz);
			Object service;
			service = clazz.newInstance();
			logger.info("serviceLocator returned : " + service);
			return service;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
