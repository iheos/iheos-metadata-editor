package gov.nist.hit.ds.docentryeditor.client;

import com.google.gwt.junit.client.GWTTestCase;
import gov.nist.hit.ds.docentryeditor.client.parser.PreParse;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.client.resources.AppResources;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorGinInjector;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

public class XdsParsingTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "gov.nist.hit.ds.docentryeditor.XDSMetadataEditor";
    }

    public void testRun() {
        assertTrue(true);
    }

    public void testLoadedDocEntry() {
        final String docentry = AppResources.INSTANCE.xdsPrefill().getText();
        assertNotNull("DocEntry File is null", docentry);
        assertTrue("DocEntry File is empty", !docentry.isEmpty());
    }

    public void testLoadedCodedTerms() {
        final String codedTerms = AppResources.INSTANCE.xdsPrefill().getText();
        assertNotNull("CodedTerms File is null", codedTerms);
        assertTrue("CodedTerms File is empty", !codedTerms.isEmpty());
    }

    public void testPreParseDocEntry() {
        final String docentry = AppResources.INSTANCE.xdsPrefill().getText();
        PreParse preParser = PreParse.getInstance();
        String preParsedDocentry = preParser.doPreParse(docentry);
        assertNotSame("PreParse did not clean escape characters", docentry, preParsedDocentry);
        String preParsedUTF8Docentry = preParser.doPreParseUTF8(docentry);
        assertNotSame("PreParse UTF8 character encoding problem", docentry, preParsedUTF8Docentry);
        assertNotSame("PreParse UTF8 character encoding problem", preParsedDocentry, preParsedUTF8Docentry);
        assertFalse("PreParse UTF8 '&' character encoding problem", preParsedUTF8Docentry.contains("&amp;"));
    }

    public void testParseDocEntry() {
        final String docentry = AppResources.INSTANCE.xdsPrefill().getText();
        MetadataEditorGinInjector injector = MetadataEditorGinInjector.INSTANCE;
        XdsParser xdsParser = injector.getXdsParser();
        XdsDocumentEntry model = xdsParser.parse(PreParse.getInstance().doPreParse(docentry));
        assertNotNull("Parsed Document Entry is null", model);
        assertEquals("There has been a problem while parsing authors", model.getAuthors(), ClientTestHelper.INSTANCE.getDocEntry().getAuthors());
        assertEquals("There has been a problem while parsing class code", ClientTestHelper.INSTANCE.getDocEntry().getClassCode(), model.getClassCode());
        assertEquals("There has been a problem while parsing format code", ClientTestHelper.INSTANCE.getDocEntry().getFormatCode(), model.getFormatCode());
        assertEquals("There has been a problem while parsing healthcare facility type", ClientTestHelper.INSTANCE.getDocEntry().getHealthcareFacilityType(), model.getHealthcareFacilityType());
        assertEquals("There has been a problem while parsing comments", ClientTestHelper.INSTANCE.getDocEntry().getComments(), model.getComments());
        assertEquals("There has been a problem while parsing titles", ClientTestHelper.INSTANCE.getDocEntry().getTitles(), model.getTitles());
        assertEquals("There has been a problem while parsing confidentiality codes", ClientTestHelper.INSTANCE.getDocEntry().getConfidentialityCodes(), model.getConfidentialityCodes());
        assertEquals("There has been a problem while parsing creation time", ClientTestHelper.INSTANCE.getDocEntry().getCreationTime(), model.getCreationTime());
        assertEquals("There has been a problem while parsing service start time", ClientTestHelper.INSTANCE.getDocEntry().getServiceStartTime(), model.getServiceStartTime());
        assertEquals("There has been a problem while parsing service stop time", ClientTestHelper.INSTANCE.getDocEntry().getServiceStopTime(), model.getServiceStopTime());
        assertEquals("There has been a problem while parsing event codes", ClientTestHelper.INSTANCE.getDocEntry().getEventCode(), model.getEventCode());
        assertEquals("There has been a problem while parsing uuid", ClientTestHelper.INSTANCE.getDocEntry().getId(), model.getId());
        assertEquals("There has been a problem while parsing mime type", ClientTestHelper.INSTANCE.getDocEntry().getMimeType(), model.getMimeType());
        assertEquals("There has been a problem while parsing language code", ClientTestHelper.INSTANCE.getDocEntry().getLanguageCode(), model.getLanguageCode());
        assertEquals("There has been a problem while parsing legal authenticator", ClientTestHelper.INSTANCE.getDocEntry().getLegalAuthenticator(), model.getLegalAuthenticator());
        assertEquals("There has been a problem while parsing patient id", ClientTestHelper.INSTANCE.getDocEntry().getPatientID(), model.getPatientID());
        assertEquals("There has been a problem while parsing practice setting code", ClientTestHelper.INSTANCE.getDocEntry().getPracticeSettingCode(), model.getPracticeSettingCode());
        assertEquals("There has been a problem while parsing repository unique id", ClientTestHelper.INSTANCE.getDocEntry().getRepoUId(), model.getRepoUId());
        assertEquals("There has been a problem while parsing size", ClientTestHelper.INSTANCE.getDocEntry().getSize(), model.getSize());
        assertEquals("There has been a problem while parsing source patient id", ClientTestHelper.INSTANCE.getDocEntry().getSourcePatientId(), model.getSourcePatientId());
        assertEquals("There has been a problem while parsing source patient info", ClientTestHelper.INSTANCE.getDocEntry().getSourcePatientInfo(), model.getSourcePatientInfo());
        assertEquals("There has been a problem while parsing type code", ClientTestHelper.INSTANCE.getDocEntry().getTypeCode(), model.getTypeCode());
        assertEquals("There has been a problem while parsing uri", ClientTestHelper.INSTANCE.getDocEntry().getUri(), model.getUri());
        assertEquals("There has been a problem while parsing unique id", ClientTestHelper.INSTANCE.getDocEntry().getUniqueId(), model.getUniqueId());
        assertEquals("There has been a problem while parsing hash", ClientTestHelper.INSTANCE.getDocEntry().getHash(), model.getHash());
    }

    public void testSaveFile() {
        final String docentry = AppResources.INSTANCE.xdsPrefill().getText();
        MetadataEditorGinInjector injector = MetadataEditorGinInjector.INSTANCE;
        XdsParser xdsParser = injector.getXdsParser();
        XdsDocumentEntry model = xdsParser.parse(PreParse.getInstance().doPreParse(docentry));
        assertNotNull(model);
    }


}
