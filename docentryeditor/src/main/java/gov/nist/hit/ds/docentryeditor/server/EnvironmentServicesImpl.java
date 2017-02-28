package gov.nist.hit.ds.docentryeditor.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.session.server.Session;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by oherrmann on 8/28/15.
 */
public class EnvironmentServicesImpl extends RemoteServiceServlet implements EnvironmentServices{
    private final static Logger LOGGER = Logger.getLogger(EnvironmentServicesImpl.class.getName());
    private final static Properties PROPERTIES=new Properties();
    private final static String PROPERTIES_FILENAME="toolkit.properties";

    private Session session;

    public EnvironmentServicesImpl(){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
            PROPERTIES.load(inputStream);
            Installation.instance().warHome(Installation.instance().warHome());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private Session session(){
        if (session==null){
            session = new Session(Installation.instance().warHome());
        }
        return session;
    }

    @Override
    public List<String> retrieveEnvironmentNames() {
        return Session.getEnvironmentNames();
    }

    @Override
    public List<String> retrieveSessionNames() throws Exception {
        return session().xdsTestServiceManager().getMesaTestSessionNames();
    }

    @Override
    public String retrieveExternalCachePathProperty() {
        return Installation.instance().propertyServiceManager().getPropertyManager().getExternalCache();
    }

    @Override
    public void saveExternalCachePath(String extCachePathText) {
        Installation.instance().propertyServiceManager().getPropertyManager().saveProperties();
    }

}
