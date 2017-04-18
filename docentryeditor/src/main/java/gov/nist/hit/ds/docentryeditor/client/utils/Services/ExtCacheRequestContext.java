package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import gov.nist.hit.ds.docentryeditor.server.ExtCacheServices;
import gov.nist.hit.ds.docentryeditor.server.GenericServiceLocator;

import java.util.List;

/**
 * Created by onh2 on 3/31/17.
 */
@Service(value = ExtCacheServices.class, locator = GenericServiceLocator.class)
public interface ExtCacheRequestContext extends RequestContext {
    Request<List<String>> getTransactionTypes();
    Request<List<String>> getTestNames(String environment, String session, String transactionType);
    Request<List<String>> getSectionNames(String environment, String session, String transactionType,String testdataFileName);
}