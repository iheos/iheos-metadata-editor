package gov.nist.hit.ds.docentryeditor.shared;

import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.toolkit.testkitutilities.TestDefinition;

/**
 * Created by onh2 on 3/20/17.
 */
public class SaveInExtCacheRequest extends RequestContext {
    private XdsMetadata metadata;
    private String testName;
    private String transactionType;

    /**
     * This constructor is mandatory when using Serializable class.
     * Prefer to use the other constructor.
     */
    public SaveInExtCacheRequest(){}
    public SaveInExtCacheRequest(RequestContext context, XdsMetadata metadata, String transactionType, String testName){
        setEnvironmentName(context.getEnvironmentName());
        setSessionName(context.getSessionName());
        this.metadata=metadata;
        this.transactionType=transactionType;
        this.testName=testName;
    }

    public XdsMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(XdsMetadata metadata) {
        this.metadata = metadata;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
