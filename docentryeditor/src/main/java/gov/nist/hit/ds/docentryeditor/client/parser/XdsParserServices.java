package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

/**
 * RPC Services interface to parse an XDS document (ebRim XML File).
 */
@RemoteServiceRelativePath("xdsparser")
public interface XdsParserServices extends RemoteService{
    public XdsMetadata parseXdsMetadata(String fileContent);

    String toEbRim(XdsMetadata xdsDocumentEntry);
}
