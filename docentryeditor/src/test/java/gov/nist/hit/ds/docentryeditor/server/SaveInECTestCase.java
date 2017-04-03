package gov.nist.hit.ds.docentryeditor.server;

import gov.nist.hit.ds.docentryeditor.shared.RequestContext;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.testkitutilities.TestDefinition;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by onh2 on 3/23/17.
 */
public class SaveInECTestCase extends TestCase {
    @Test
    public void testSave() {
        XdsMetadataParserServicesImpl service = new XdsMetadataParserServicesImpl();
        String extCacheLocation="/Users/onh2/external_cache/";
        String environmentName="NA2015";
        String sessionName="default";
        String testName="OlivierCreationTest";

        Installation.instance().warHome(Installation.instance().warHome());
        File metadataFile=new File(getClass().getResource("/PnR1Doc.xml").getPath());
        XdsMetadataParserServicesImpl parserServices=new XdsMetadataParserServicesImpl();
        try {
            XdsMetadata metadata=parserServices.parseXdsMetadata(IOUtils.toString(new FileReader(metadataFile)));
            service.saveInExternalCache(new SaveInExtCacheRequest(new RequestContext(environmentName,sessionName,extCacheLocation),metadata, TestDefinition.TransactionType.PnR.toString(),testName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        service.saveInExternalCache(new RequestContext(environmentName,sessionName,extCacheLocation),);
//        String filename = service.saveAsXMLFile("test.xml", ServerTestHelper.INSTANCE.docEntryToXML(ServerTestHelper.INSTANCE.getDocEntry()));
//        assertNotNull("No written filename return from server.", filename);
//        assertFalse("Filename returned from server is empty", filename.isEmpty());
//        assertTrue("Filename's extension is wrong (not .xml)", filename.contains(".xml"));
//        String rootDirPath = System.getProperty("user.dir");
//        assertNotNull("Impossible to locate file directory", rootDirPath);
//        assertFalse("Impossible to locate file directory", rootDirPath.isEmpty());
//        File fileFolder = new File(new File(rootDirPath), "/files/");
//        File file = new File(fileFolder + "/test.xml");
//        assertNotNull("File is not found on server", file);
//        assertTrue("File does not exists on server", file.exists());
    }
}
