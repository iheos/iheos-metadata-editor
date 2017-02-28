package gov.nist.hit.ds.docentryeditor.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.toolkit.installation.Installation;

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

    public EnvironmentServicesImpl(){
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
            PROPERTIES.load(inputStream);
            Installation.instance().warHome(new File(getClass().getResource("/war/war.txt").getFile()).getParentFile());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public List<String> retrieveEnvironmentNames() {
        return getEnvironmentNames();
    }

    /**
     * Code taken out of Session module.
\     * @return
     */
    private List<String> getEnvironmentNames() {
        LOGGER.info( ": " + "getEnvironmentNames");
        List<String> names = new ArrayList<String>();

        File k = Installation.instance().environmentFile();     //propertyServiceManager().getPropertyManager().getExternalCache() + File.separator + "environment");
        if (!k.exists() || !k.isDirectory())
            return names;
        File[] files = k.listFiles();
        for (File file : files)
            if (file.isDirectory() && !(file.getName().equals("TestLogCache"))) {
                names.add(file.getName());
            }
        return names;
    }

    @Override
    public List<String> retrieveSessionNames() {
        return getMesaTestSessionNames();
    }

    /**
     * Code taken out of Session module.
     * @return
     */
    private List<String> getMesaTestSessionNames() {
        LOGGER.info( ": " + "getMesaSessionNames");
        List<String> names = new ArrayList<String>();
        File cache;
        try {
            cache = Installation.instance().propertyServiceManager().getTestLogCache();
        } catch (Exception e) {
            LOGGER.warning("getMesaTestSessionNames "+ e.getMessage());
            //  throw new Exception(e.getMessage());
            return new ArrayList<String>();
        }

        String[] namea = cache.list();

        for (int i=0; i<namea.length; i++) {
            File dir = new File(cache, namea[i]);
            if (!dir.isDirectory()) continue;
            if (!namea[i].startsWith("."))
                names.add(namea[i]);

        }

        if (names.size() == 0) {
            names.add("default");
            File def = new File(cache, "default");
            def.mkdirs();
        }

        LOGGER.info("testSession names are " + names);
        return names;
    }
}
