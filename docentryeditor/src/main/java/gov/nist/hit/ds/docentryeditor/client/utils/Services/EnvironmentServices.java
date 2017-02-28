package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * RPC Services interface to retrieve a standard properties.
 */
@RemoteServiceRelativePath("envServices")
public interface EnvironmentServices extends RemoteService{
    List<String> retrieveEnvironmentNames();
    List<String> retrieveSessionNames() throws Exception;
    String retrieveExternalCachePathProperty();
    void saveExternalCachePath(String extCachePathText);

}
