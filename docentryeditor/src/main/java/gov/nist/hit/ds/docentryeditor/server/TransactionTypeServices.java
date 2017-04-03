package gov.nist.hit.ds.docentryeditor.server;

import gov.nist.toolkit.testkitutilities.TestDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onh2 on 3/31/17.
 */
public class TransactionTypeServices {
    public List<String> getTransactionTypes(){
        List<String> types=new ArrayList<String>();
        for (TestDefinition.TransactionType type : TestDefinition.TransactionType.values()) {
            types.add(type.toString());
        }
        return types;
    }
}
