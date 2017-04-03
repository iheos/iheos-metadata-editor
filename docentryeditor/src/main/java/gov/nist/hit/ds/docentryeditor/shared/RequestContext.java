package gov.nist.hit.ds.docentryeditor.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Created by onh2 on 3/20/17.
 */
public class RequestContext implements Serializable, IsSerializable{
    private String environmentName;
    private String sessionName;
    private String externalCache;

    /**
     * This constructor is mandatory when using Serializable class.
     * Prefer to use the other constructor.
     */
    public RequestContext() {
    }

    public RequestContext(String environmentName, String sessionName){
        this.environmentName=environmentName;
        this.sessionName=sessionName;
    }

    public RequestContext(String environmentName, String sessionName, String externalCachePath){
        this(environmentName,sessionName);
        this.externalCache=externalCachePath;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getExternalCache() {
        return externalCache;
    }

    public void setExternalCache(String externalCache) {
        this.externalCache = externalCache;
    }
}
