package gov.nist.hit.ds.docentryeditor.server;


import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

/**
 * Created by onh2 on 10/2/2014.
 */
public class SaveFileTest extends TestCase {

    @Test
    public void testSave() {
        SaveFileService service = new SaveFileService();
        String filename = service.saveAsXMLFile("test.xml", ServerTestHelper.instance.docEntryToXML(ServerTestHelper.instance.getDocEntry()));
        assertNotNull("No written filename return from server.", filename);
        assertFalse("Filename returned from server is empty", filename.isEmpty());
        assertTrue("Filename's extension is wrong (not .xml)", filename.contains(".xml"));
        String rootDirPath = System.getProperty("user.dir");
        assertNotNull("Impossible to locate file directory", rootDirPath);
        assertFalse("Impossible to locate file directory", rootDirPath.isEmpty());
        File fileFolder = new File(new File(rootDirPath), "/files/");
        File file = new File(fileFolder + "/test.xml");
        assertNotNull("File is not found on server", file);
        assertTrue("File does not exists on server", file.exists());
    }
}
