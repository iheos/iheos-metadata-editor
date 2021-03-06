package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gov.nist.hit.ds.docentryeditor.shared.EnvSessionRequestContext;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.toolkit.xdsexception.client.XdsInternalException;

/**
 * Asynchronous RPC Services interface to parse an XDS document (ebRim XML File).
 */
public interface XdsParserServicesAsync {
    /**
     * Asynchronous method that parses a XDS document (ebRim XML file) and return a manipulable java object
     * with all the file content.
     * @param fileContent XML ebRim file to parse.
     * @param async asynchronous callback containing the java object set.
     */
    public void parseXdsMetadata(String fileContent, AsyncCallback<XdsMetadata> async);
    public void toEbRim(XdsMetadata xdsDocumentEntry,AsyncCallback<String> async);
    public void saveInExternalCache(SaveInExtCacheRequest context,AsyncCallback<Void> async);
    void retrieveFromExtCache(EnvSessionRequestContext context, String selectedTransactionType, String selectedTestdata, String selectedSection, AsyncCallback<XdsMetadata> async);

    void updateMetadataInExtCache(SaveInExtCacheRequest saveRequest, AsyncCallback<Void> asyncCallback);
}
