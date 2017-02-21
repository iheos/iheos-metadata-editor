package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

/**
 * Created by onh2 on 2/15/17.
 */
public class ApiConfigurationData {
    private String host;
    private String context;
    private String port;

    public ApiConfigurationData(String host, String context, String port) {
        this.host=host;
        this.context=context;
        this.port=port;
    }

    public ApiConfigurationData(String fromCookie) {
        if (fromCookie!=null) {
            String[] split = fromCookie.split(";");
            host = split[0];
            context = split[1];
            port = split[2];
        }else{
            host="";
            context="";
            port="";
        }
    }

    public String getHost() {
        return host;
    }

    public String getContext() {
        return context;
    }

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return host+";"+context+";"+port;
    }
}