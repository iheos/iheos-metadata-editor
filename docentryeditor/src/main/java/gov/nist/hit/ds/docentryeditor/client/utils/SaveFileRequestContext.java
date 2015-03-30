package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import gov.nist.hit.ds.docentryeditor.server.GenericServiceLocator;
import gov.nist.hit.ds.docentryeditor.server.SaveFileService;

@Service(value = SaveFileService.class, locator = GenericServiceLocator.class)
public interface SaveFileRequestContext extends RequestContext {
    public Request<String> saveAsXMLFile(String name);

    public Request<String> saveAsXMLFile(String filename, String filecontent);
}
