package gov.nist.hit.ds.docentryeditor.server;

import gov.nist.hit.ds.docentryeditor.shared.RequestContext;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.testkitutilities.TestDefinition;
import gov.nist.toolkit.xdsexception.client.MetadataException;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by onh2 on 3/23/17.
 */
public class SaveInECTestCase {
    private String extCacheLocation;
    private static  final String ENVIRONMENT_NAME ="TestCase";
    private static final String SESSION_NAME="TestCase";
    private static final String TEST_NAME="SaveFromEditorTestCase";
    private static final XdsMetadataParserServicesImpl SERVICES = new XdsMetadataParserServicesImpl();

    @Before
    public void setUp() throws Exception {
        Installation.instance().warHome(Installation.instance().warHome());
        extCacheLocation=Installation.instance().propertyServiceManager().getPropertyManager().getExternalCache();
    }

    @Test
    public void testSave() {
        File metadataFile = new File(getClass().getResource("/PnR1Doc.xml").getPath());
        XdsMetadataParserServicesImpl parserServices = new XdsMetadataParserServicesImpl();
        try {
            XdsMetadata metadata = parserServices.parseXdsMetadata(IOUtils.toString(new FileReader(metadataFile)));
            SERVICES.saveInExternalCache(new SaveInExtCacheRequest(new RequestContext(ENVIRONMENT_NAME, SESSION_NAME, extCacheLocation),
                    metadata, TestDefinition.TransactionType.PnR.toString(), TEST_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue("The test file structure wasn't created properly.", isTestFileStructureCorrect(TestDefinition.TransactionType.PnR));
        File testfile=new File(new File(new File(new File(Installation.instance().environmentFile(ENVIRONMENT_NAME),"testkits"),
                SESSION_NAME), TestDefinition.TransactionType.PnR.getTransactionTypeTestRepositoryName()),TEST_NAME);
        File savedMetadataFile=new File(new File(testfile,"submit"),"metadata.xml");
        try {
            assertTrue("The metadata file created is empty",(IOUtils.toString(new FileReader(savedMetadataFile))!=null && !IOUtils.toString(new FileReader(savedMetadataFile)).isEmpty()));
            assertFalse("The metadata file created is not properly formated.",SERVICES.parseXdsMetadata(IOUtils.toString(new FileReader(savedMetadataFile)))==null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTestFileStructureCorrect(TestDefinition.TransactionType transactionType){
        File testkitsFile=new File(Installation.instance().environmentFile(ENVIRONMENT_NAME),"testkits");
        if (!testkitsFile.exists()) return false;
        File sessionFile=new File(testkitsFile,SESSION_NAME);
        if (!sessionFile.exists()) return false;
        File testFile = new File(new File(sessionFile, transactionType.getTransactionTypeTestRepositoryName()),TEST_NAME);
        if (!testFile.exists()) return false;
        if (!(new File(testFile,"index.idx")).exists()) return false;
        File testStepFile = new File(testFile,"submit");
        if (!testStepFile.exists() || !(new File(testStepFile,"testplan.xml")).exists() || !(new File(testStepFile,"metadata.xml")).exists()) return false;
        return true;
    }

}
