package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

/**
 * RPC Services interface to retrieve a standard properties.
 */
@RemoteServiceRelativePath("stdprop")
public interface StandardPropertiesServices extends RemoteService{
    Map<String,String> getStandardProperties(String standard);
}
