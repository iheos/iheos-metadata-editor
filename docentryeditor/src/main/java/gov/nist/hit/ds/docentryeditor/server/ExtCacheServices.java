package gov.nist.hit.ds.docentryeditor.server;

import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.testkitutilities.TestDefinition;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onh2 on 3/31/17.
 */
public class ExtCacheServices implements Serializable{
    /**
     * Retrieve a list of transaction type for which the editor can handle metadata files.
     * (PnR and Register testdata metadata files)
     * @return list of transaction types
     */
    public List<String> getTransactionTypes(){
        List<String> types=new ArrayList<String>();
        for (TestDefinition.TransactionType type : TestDefinition.TransactionType.values()) {
            types.add(type.toString());
        }
        return types;
    }

    /**
     * Returns a list of all the testdata instances for a given transaction type in a specific environment/session configuration.
     * @param environment
     * @param session
     * @param transactionType
     * @return
     */
    public List<String> getTestNames(String environment, String session, String transactionType){
        List<String> testNames= new ArrayList<String>();
        File testdataRepo=getTestdataRepository(environment, session, transactionType);
        for (File test:testdataRepo.listFiles()){
            if (!test.getName().startsWith(".")) {
                testNames.add(test.getName());
            }
        }
        return testNames;
    }

    public List<String> getSectionNames(String environment, String session, String transactionType, String selectedTestdataInstance){
        List<String> testNames= new ArrayList<String>();
        File testdataRepo=getTestdataRepository(environment, session, transactionType);
        File testdataFile = new File(testdataRepo,selectedTestdataInstance);
        for (File test:testdataFile.listFiles()){
            if (test.isDirectory() && !test.getName().startsWith(".")) {
                testNames.add(test.getName());
            }
        }
        return testNames;
    }


    private File getTestdataRepository(String environment, String session, String transactionType) {
        File env= Installation.instance().environmentFile(environment);
        File sessionFile = new File(new File(env,"testkits"),session);
        return new File(sessionFile, TestDefinition.TransactionType.fromString(transactionType).getTransactionTypeTestRepositoryName());
    }
}
