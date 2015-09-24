package gov.nist.hit.ds.docentryeditor.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.nist.hit.ds.docentryeditor.client.utils.StandardPropertiesServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by oherrmann on 8/28/15.
 */
public class StandardPropertiesServiceImpl extends RemoteServiceServlet implements StandardPropertiesServices{
    @Override
    public Map<String,String> getStandardProperties(String standard){
        Map<String,String> properties=new HashMap<String,String>();
        Properties prop=new Properties();
        InputStream is = getClass().getResourceAsStream("/properties/"+standard+".properties");
        try {
            prop.load(is);
            properties.put("subSetEntryUUID", prop.getProperty("subSetEntryUUID"));
            properties.put("subSetUniqueID", prop.getProperty("subSetUniqueID"));
            properties.put("subSetSourceID", prop.getProperty("subSetSourceID"));
            properties.put("subSetPatientID", prop.getProperty("subSetPatientID"));
            properties.put("subSetContentTypeCode", prop.getProperty("subSetContentTypeCode"));
            properties.put("subSetSubmissionTime", prop.getProperty("subSetSubmissionTime"));
            properties.put("subSetAvailabilityStatus", prop.getProperty("subSetAvailabilityStatus"));
            properties.put("subSetHomeCommunityId", prop.getProperty("subSetHomeCommunityId"));
            properties.put("subSetAuthors", prop.getProperty("subSetAuthors"));
            properties.put("subSetIntendedRecipient", prop.getProperty("subSetIntendedRecipient"));
            properties.put("subSetTitles", prop.getProperty("subSetTitles"));
            properties.put("subSetComments", prop.getProperty("subSetComments"));
            properties.put("docEntryEntryUUID", prop.getProperty("docEntryEntryUUID"));
            properties.put("docEntryUniqueID", prop.getProperty("docEntryUniqueID"));
            properties.put("docEntryPatientID", prop.getProperty("docEntryPatientID"));
            properties.put("docEntryLanguageCode", prop.getProperty("docEntryLanguageCode"));
            properties.put("docEntryMimeType", prop.getProperty("docEntryMimeType"));
            properties.put("docEntryClassCode", prop.getProperty("docEntryClassCode"));
            properties.put("docEntryTypeCode", prop.getProperty("docEntryTypeCode"));
            properties.put("docEntryFormatCode", prop.getProperty("docEntryTypeCode"));
            properties.put("docEntryHealthcareFacilityCode", prop.getProperty("docEntryHealthcareFacilityCode"));
            properties.put("docEntryPracticeSettingCode", prop.getProperty("docEntryPracticeSettingCode"));
            properties.put("docEntryCreationTime", prop.getProperty("docEntryCreationTime"));
            properties.put("docEntryHash", prop.getProperty("docEntryHash"));
            properties.put("docEntrySize", prop.getProperty("docEntrySize"));
            properties.put("docEntryURI", prop.getProperty("docEntryURI"));
            properties.put("docEntryRepositoryUniqueID", prop.getProperty("docEntryRepositoryUniqueID"));
            properties.put("docEntryTitles", prop.getProperty("docEntryTitles"));
            properties.put("docEntryComments", prop.getProperty("docEntryComments"));
            properties.put("docEntryAuthors", prop.getProperty("docEntryAuthors"));
            properties.put("docEntryLegalAuthenticator", prop.getProperty("docEntryLegalAuthenticator"));
            properties.put("docEntrySourcePatientID", prop.getProperty("docEntrySourcePatientID"));
            properties.put("docEntrySourcePatientInfo", prop.getProperty("docEntrySourcePatientInfo"));
            properties.put("docEntryConfidentialityCodes", prop.getProperty("docEntryConfidentialityCodes"));
            properties.put("docEntryEventCodes", prop.getProperty("docEntryEventCodes"));
            properties.put("docEntryServiceStartTime", prop.getProperty("docEntryServiceStartTime"));
            properties.put("docEntryServiceStopTime", prop.getProperty("docEntryServiceStopTime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
