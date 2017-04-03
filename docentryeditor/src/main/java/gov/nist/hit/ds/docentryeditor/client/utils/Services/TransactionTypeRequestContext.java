package gov.nist.hit.ds.docentryeditor.client.utils.Services;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import gov.nist.hit.ds.docentryeditor.server.GenericServiceLocator;
import gov.nist.hit.ds.docentryeditor.server.TransactionTypeServices;

import java.util.List;

/**
 * Created by onh2 on 3/31/17.
 */
@Service(value = TransactionTypeServices.class, locator = GenericServiceLocator.class)
public interface TransactionTypeRequestContext extends RequestContext {
    public Request<List<String>> getTransactionTypes();
}
