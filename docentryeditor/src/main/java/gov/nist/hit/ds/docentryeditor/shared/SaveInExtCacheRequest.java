package gov.nist.hit.ds.docentryeditor.shared;

import com.google.gwt.user.client.Window;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

/**
 * Created by onh2 on 3/20/17.
 */
public class SaveInExtCacheRequest extends EnvSessionRequestContext {
    private XdsMetadata metadata;
    private String testName;
    private String transactionType;
    private String filePath=null;

    /**
     * This constructor is mandatory when using Serializable class.
     * Prefer to use the other constructor.
     */
    public SaveInExtCacheRequest(){}
    public SaveInExtCacheRequest(EnvSessionRequestContext context, XdsMetadata metadata, String transactionType, String testName){
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String metadataFileInEditionPath) {
        filePath=metadataFileInEditionPath;
        Window.alert(filePath);
        String[] split=null;
        if (filePath.contains("testdata-registry")){
            split=filePath.split("testdata-registry");
            transactionType= "RegisterTransaction";
        }else if (filePath.contains("testdata-repository")){
            split=filePath.split("testdata-repository");
            transactionType="ProvideAndRegisterTransaction";
        }
        if (split!=null) {
            split = split[1].split("/");
            testName = split[1];
        }
    }
}
