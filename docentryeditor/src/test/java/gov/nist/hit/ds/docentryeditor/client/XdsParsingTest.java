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
        MetadataEditorGinInjector injector = MetadataEditorGinInjector.instance;
        XdsParser xdsParser = injector.getXdsParser();
        XdsDocumentEntry model = xdsParser.parse(PreParse.getInstance().doPreParse(docentry));
        assertNotNull("Parsed Document Entry is null", model);
        assertEquals("There has been a problem while parsing authors", model.getAuthors(), ClientTestHelper.instance.getDocEntry().getAuthors());
        assertEquals("There has been a problem while parsing class code", ClientTestHelper.instance.getDocEntry().getClassCode(), model.getClassCode());
        assertEquals("There has been a problem while parsing format code", ClientTestHelper.instance.getDocEntry().getFormatCode(), model.getFormatCode());
        assertEquals("There has been a problem while parsing healthcare facility type", ClientTestHelper.instance.getDocEntry().getHealthcareFacilityType(), model.getHealthcareFacilityType());
        assertEquals("There has been a problem while parsing comments", ClientTestHelper.instance.getDocEntry().getComments(), model.getComments());
        assertEquals("There has been a problem while parsing titles", ClientTestHelper.instance.getDocEntry().getTitles(), model.getTitles());
        assertEquals("There has been a problem while parsing confidentiality codes", ClientTestHelper.instance.getDocEntry().getConfidentialityCodes(), model.getConfidentialityCodes());
        assertEquals("There has been a problem while parsing creation time", ClientTestHelper.instance.getDocEntry().getCreationTime(), model.getCreationTime());
        assertEquals("There has been a problem while parsing service start time", ClientTestHelper.instance.getDocEntry().getServiceStartTime(), model.getServiceStartTime());
        assertEquals("There has been a problem while parsing service stop time", ClientTestHelper.instance.getDocEntry().getServiceStopTime(), model.getServiceStopTime());
        assertEquals("There has been a problem while parsing event codes", ClientTestHelper.instance.getDocEntry().getEventCode(), model.getEventCode());
        assertEquals("There has been a problem while parsing uuid", ClientTestHelper.instance.getDocEntry().getId(), model.getId());
        assertEquals("There has been a problem while parsing mime type", ClientTestHelper.instance.getDocEntry().getMimeType(), model.getMimeType());
        assertEquals("There has been a problem while parsing language code", ClientTestHelper.instance.getDocEntry().getLanguageCode(), model.getLanguageCode());
        assertEquals("There has been a problem while parsing legal authenticator", ClientTestHelper.instance.getDocEntry().getLegalAuthenticator(), model.getLegalAuthenticator());
        assertEquals("There has been a problem while parsing patient id", ClientTestHelper.instance.getDocEntry().getPatientID(), model.getPatientID());
        assertEquals("There has been a problem while parsing practice setting code", ClientTestHelper.instance.getDocEntry().getPracticeSettingCode(), model.getPracticeSettingCode());
        assertEquals("There has been a problem while parsing repository unique id", ClientTestHelper.instance.getDocEntry().getRepoUId(), model.getRepoUId());
        assertEquals("There has been a problem while parsing size", ClientTestHelper.instance.getDocEntry().getSize(), model.getSize());
        assertEquals("There has been a problem while parsing source patient id", ClientTestHelper.instance.getDocEntry().getSourcePatientId(), model.getSourcePatientId());
        assertEquals("There has been a problem while parsing source patient info", ClientTestHelper.instance.getDocEntry().getSourcePatientInfo(), model.getSourcePatientInfo());
        assertEquals("There has been a problem while parsing type code", ClientTestHelper.instance.getDocEntry().getTypeCode(), model.getTypeCode());
        assertEquals("There has been a problem while parsing uri", ClientTestHelper.instance.getDocEntry().getUri(), model.getUri());
        assertEquals("There has been a problem while parsing unique id", ClientTestHelper.instance.getDocEntry().getUniqueId(), model.getUniqueId());
        assertEquals("There has been a problem while parsing hash", ClientTestHelper.instance.getDocEntry().getHash(), model.getHash());
    }

    public void testSaveFile() {
        final String docentry = AppResources.INSTANCE.xdsPrefill().getText();
        MetadataEditorGinInjector injector = MetadataEditorGinInjector.instance;
        XdsParser xdsParser = injector.getXdsParser();
        XdsDocumentEntry model = xdsParser.parse(PreParse.getInstance().doPreParse(docentry));
    }


}
