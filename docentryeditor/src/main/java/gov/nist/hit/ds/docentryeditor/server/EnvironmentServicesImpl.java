package gov.nist.hit.ds.docentryeditor.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.session.server.Session;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by oherrmann on 8/28/15.
 */
public class EnvironmentServicesImpl extends RemoteServiceServlet implements EnvironmentServices{
    private final static Logger LOGGER = Logger.getLogger(EnvironmentServicesImpl.class.getName());

    private Session session;

    // Class constructor to init ext. cache location
    public EnvironmentServicesImpl(){
        LOGGER.info("hello");Installation.instance().warHome(Installation.instance().warHome());
    }

    // method to provide a Session object (from toolkit project, Session module)
    private Session session(){
        if (session==null){
            session = new Session(Installation.instance().warHome());
        }
        return session;
    }

    /**
     * Method that retrieves a list of existing environment names from the server (External cache).
     * @return list of environment names
     */
    @Override
    public List<String> retrieveEnvironmentNames() {
        LOGGER.info("retrieve Environment Names");
        return Session.getEnvironmentNames();
    }

    /**
     * Method that retrieves a list of existing session names from the server (External cache).
     * @return list of session names
     */
    @Override
    public List<String> retrieveSessionNames() throws Exception {
        LOGGER.info("retrieve session names");
        return session().xdsTestServiceManager().getMesaTestSessionNames();
    }

    /**
     * Retrieves the path to the ext. cache on the server from the toolkit.properties file in the project's war.
     * @return file path to the ext. cache as a String.
     */
    @Override
    public String retrieveExternalCachePathProperty() {
        return Installation.instance().propertyServiceManager().getPropertyManager().getExternalCache();
    }

    /**
     * Saves a new value for the external cache file path in the properties file.
     * @param extCachePathText New ext. cache file path value.
     */
    @Override
    public void saveExternalCachePath(String extCachePathText) {
        Installation.instance().propertyServiceManager().getPropertyManager().saveProperties();
    }

}
