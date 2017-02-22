package gov.nist.hit.ds.docentryeditor.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.StandardPropertiesServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by oherrmann on 8/28/15.
 */
public class EnvironmentServicesImpl extends RemoteServiceServlet implements EnvironmentServices{
    @Override
    public List<String> retrieveEnvironmentNames() {
        List<String> res=new ArrayList<>();
        res.add("env1");
        res.add("env2");
        res.add("env3");
        return res;
    }
}
