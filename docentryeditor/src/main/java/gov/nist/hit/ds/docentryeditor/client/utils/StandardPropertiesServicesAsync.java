package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

/**
 * Asynchronous RPC Services interface to retrieve standard properties.
 */
public interface StandardPropertiesServicesAsync {
    void getStandardProperties(String standard, AsyncCallback<Map<String, String>> result);
}