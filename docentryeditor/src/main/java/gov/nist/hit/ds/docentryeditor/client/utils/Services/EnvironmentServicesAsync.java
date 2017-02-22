package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;

/**
 * Asynchronous RPC Services interface to retrieve standard properties.
 */
public interface EnvironmentServicesAsync {
    void retrieveEnvironmentNames(AsyncCallback<List<String>> result);
}