package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

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
    void parseXdsMetadata(String fileContent, AsyncCallback<XdsMetadata> async);
    void toEbRim(XdsMetadata xdsDocumentEntry,AsyncCallback<String> async);
}
