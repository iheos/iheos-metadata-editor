package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gov.nist.hit.ds.docentryeditor.shared.EnvSessionRequestContext;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

import java.io.IOException;

/**
 * RPC Services interface to parse an XDS document (ebRim XML File).
 */
@RemoteServiceRelativePath("xdsparser")
public interface XdsParserServices extends RemoteService{
    XdsMetadata parseXdsMetadata(String fileContent);
    String toEbRim(XdsMetadata xdsDocumentEntry) ;
    void saveInExternalCache(SaveInExtCacheRequest context) ;
    XdsMetadata retrieveFromExtCache(EnvSessionRequestContext context, String selectedTransactionType, String selectedTestdata, String selectedSection) throws Exception;
    void updateMetadataInExtCache(SaveInExtCacheRequest saveRequest) throws IOException;
}
