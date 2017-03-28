package gov.nist.hit.ds.docentryeditor.server;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import gov.nist.hit.ds.docentryeditor.shared.RequestContext;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.toolkit.installation.Installation;
import gov.nist.toolkit.testkitutilities.TestDefinition;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * This class enables to create a xml metadata file's content on the server and
 * retrieve the name of the generated file.
 *
 * @author Olivier
 */
public class SaveFileService implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(SaveFileService.class.getName());
    private static final long serialVersionUID = 1L;
    private static File FILE_REPOSITORY;

    public SaveFileService(){
        String rootDirPath;
        if (RequestFactoryServlet.getThreadLocalServletContext()!=null){
            // ~ For realsed version. System.getProperty("user.dir") returns null on our deployment system.
            rootDirPath = RequestFactoryServlet.getThreadLocalServletContext().getRealPath("/");
        }else {
            // ~ For tests purpose
            rootDirPath = System.getProperty("user.dir");
        }
        LOGGER.info("Root Path: " + rootDirPath);
        File fileFolder=new File(new File(rootDirPath),"/files/");
        if(!fileFolder.exists()) {
            LOGGER.info("Create storage folder 'files''");
            fileFolder.mkdir();
            LOGGER.info("... storage folder 'files' created.");
        }
        FILE_REPOSITORY=fileFolder;
        LOGGER.info("New storage folder is: " + FILE_REPOSITORY.getAbsolutePath());
    }

    /**
     * Distant method that save a String into a generated xml file in the
     * server. It returns the generated file name.
     *
     * @param fileContent
     * @return String filename
     */
    public String saveAsXMLFile(String fileContent) {
        LOGGER.info("Saving xml file...");

        // Random name created for save on server
        String filename = UUID.randomUUID().toString() + ".xml";

        // Save xml file content into "files" repository
        LOGGER.info("Metadata xml file creation...");

        // return created file's name
        return saveAsXMLFile(filename,fileContent.replace("displayName","name"));
    }

    /**
     * Distant method that save a String into a generated xml file in the
     * server. It returns the generated file name.
     *
     * @param fileContent
     * @param filename
     * @return String filename
     */
    public String saveAsXMLFile(String filename, String fileContent) {
        LOGGER.info("Saving xml file...");

        // Random name created for save on server
        String fileName = filename;
        if (fileName == null || fileName.isEmpty()) {
            fileName = UUID.randomUUID().toString();
        }
        if (!(fileName.matches(".*(\\.[0-9A-Za-z]+)$") || fileName.matches(".*\\.xml$"))) {
            fileName += ".xml";
        }

        // Save xml file content into "files" repository
        LOGGER.info("Metadata xml file creation...");

        FileOutputStream out;

        // TODO Remove hard coded xml when submission set and association are done
        String outS= fileContent.replace("displayName","name");

        try {
            out = new FileOutputStream(new File(FILE_REPOSITORY, fileName));
            LOGGER.info("... writing file (" + fileName + ") in " + FILE_REPOSITORY.getAbsolutePath() + "...");
            out.write(outS.getBytes());
            out.close();
        } catch (IOException e) {
            LOGGER.warning("Error when writing metadata file on server.\n" + e.getMessage());
        }
        LOGGER.fine("... temporary file created: " + FILE_REPOSITORY + "/" + fileName);

        // return created file's name
        return fileName;
    }

    public void saveInExternalCache(RequestContext context, XdsMetadata metadata, TestDefinition.TransactionType type, String testName){
        File testkitFile=new File(Installation.instance().environmentFile(context.getEnvironmentName()),"testkits");
        File testFile=new File(new File(new File(testkitFile,context.getSessionName()), type.getTransactionTypeTestRepositoryName()),testName);

        if (!testFile.exists()){
            testFile.mkdirs();
        }

        FileOutputStream out;
//
//        // TODO Remove hard coded xml when submission set and association are done
//        String outS= fileContent.replace("displayName","name");
//
        try {
            out = new FileOutputStream(new File(testFile, "index.idx"));
            LOGGER.info("... writing file (" + "index.idx" + ") in " + testFile.getAbsolutePath() + "...");
            out.write("submit".getBytes());
            out.close();
            File submitFile=new File(testFile,"submit");
            submitFile.mkdir();
            out = new FileOutputStream(new File(submitFile, "testplan.xml"));
            LOGGER.info("... writing file (" + "testplan.xml" + ") in " + submitFile.getAbsolutePath() + "...");
            out.write(createTestplanTemplate(type,testName).getBytes());
            out.close();
            out = new FileOutputStream(new File(submitFile, "readme.txt"));
            LOGGER.info("... writing file (" + "readme.txt" + ") in " + submitFile.getAbsolutePath() + "...");
            out.write(createTemplateReadmeDocument().getBytes());
            out.close();
            out = new FileOutputStream(new File(submitFile, "metadata.xml"));
            XdsMetadataParserServicesImpl metadataParserServices=new XdsMetadataParserServicesImpl();
            LOGGER.info("... writing file (" + "metadata.xml" + ") in " + submitFile.getAbsolutePath() + "...");
            String m="<lcm:SubmitObjectsRequest xmlns:lcm=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\">\n" +
                    "        <rim:RegistryObjectList xmlns:rim=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\">";
            m+=metadataParserServices.toEbRim(metadata);
            m+="</rim:RegistryObjectList>\n" +
                    "    </lcm:SubmitObjectsRequest>";
            out.write(m.getBytes());
            out.close();
        } catch (IOException e) {
            LOGGER.warning("Error when writing metadata file on server.\n" + e.getMessage());
        }
    }

    private String createTestplanTemplate(TestDefinition.TransactionType type, String testName) {
        StringBuilder testplanBuilder=new StringBuilder();
        testplanBuilder.append("<TestPlan>\n");
        testplanBuilder.append("<Test>"+testName+"</Test>\n");
        testplanBuilder.append("<TestStep id=\"submit\">\n");
        testplanBuilder.append("<ExpectedStatus>Success</ExpectedStatus>\n");
        testplanBuilder.append("<"+type.toString()+"Transaction>\n");
        testplanBuilder.append("<XDSb/>\n");
        testplanBuilder.append("<MetadataFile>metadata.xml</MetadataFile>\n");
        if (type.equals(TestDefinition.TransactionType.PnR)) {
            testplanBuilder.append("<Document id=\"Document01\">readme.txt</Document>\n");
        }
        testplanBuilder.append("</"+type.toString()+"Transaction>\n");
        testplanBuilder.append("</TestStep>\n");
        testplanBuilder.append("</TestPlan>\n");
        return testplanBuilder.toString();
    }

    private String createTemplateReadmeDocument(){
        String readmeFileContent="This is my document.\n\n"+
                "It is great!\n\n";
        return readmeFileContent;
    }

}
