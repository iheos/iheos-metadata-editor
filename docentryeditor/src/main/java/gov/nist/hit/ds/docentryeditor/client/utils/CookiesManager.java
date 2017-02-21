package gov.nist.hit.ds.docentryeditor.client.utils;


import com.google.gwt.user.client.Cookies;
import gov.nist.hit.ds.docentryeditor.client.widgets.configuration.ApiConfigurationData;

/**
 * This is is here to manager all the Cookie calls for the application.
 * Relevant Cookie management calls are:
 * <ul>
 *     <li>Cookies.getCookie(cookieName);</li>
 *     <li>Cookies.setCookie(cookieName,value);</li>
 *     <li>Cookies.removeCookie(cookieName);</li>
 * </ul>
 * Created by onh2 on 2/17/17.
 */
public class CookiesManager {

    public static ApiConfigurationData getApiConfiguration(){
        String config=Cookies.getCookie(CookieTypes.API_CONFIGURATION_COOKIE.toString());
        return new ApiConfigurationData(config);
    }

    public static void saveApiConfiguration(ApiConfigurationData configToSave){
        if (configToSave!=null){
            Cookies.setCookie(CookieTypes.API_CONFIGURATION_COOKIE.toString(), configToSave.toString());
        }else {
            Cookies.setCookie(CookieTypes.API_CONFIGURATION_COOKIE.toString(), " ");
        }
    }

    public enum CookieTypes{
        API_CONFIGURATION_COOKIE("gov.nist.hit.des.docentryeditor.ApiConfigurationCookie");

        private final String cookieName;

        private CookieTypes(String value) {
            cookieName = value;
        }

        @Override
        public String toString() {
            return cookieName;
        }
    }
}
