package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import gov.nist.hit.ds.docentryeditor.client.resources.AppResources;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;
import gov.nist.hit.ds.docentryeditor.shared.model.CodingScheme;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is the service that parses and enables to retrieve existing codes
 * from an application file.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes
 */
public enum PredefinedCodesParser {
    // this class is a singleton.
    INSTANCE;

    // Class logger.
    @SuppressWarnings("GwtInconsistentSerializableClass")
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    // name of the root node of the file containing the existing codes.
    private static String CONFIG_FILE_ROOT_NODE = "CodeType";

    // XML Parsed document.
    @SuppressWarnings("GwtInconsistentSerializableClass")
    private final static Document dom = XMLParser.parse(PreParse.getInstance().doPreParse(
            AppResources.INSTANCE.codes().getText()));

    // list of nodes corresponding to each type of codes
    @SuppressWarnings("GwtInconsistentSerializableClass")
    private static final NodeList nodes = dom.getElementsByTagName(CONFIG_FILE_ROOT_NODE);

    // list of existing content type codes.
    private static final List<CodedTerm> contentTypeCodes = new ArrayList<CodedTerm>();
    // list of existing class codes.
    private static final List<CodedTerm> classCodes = new ArrayList<CodedTerm>();
    // list of existing format codes.
    private static final List<CodedTerm> formatCodes = new ArrayList<CodedTerm>();
    // list of existing healthcare facility type codes.
    private static final List<CodedTerm> healthcareFacilityTypeCodes = new ArrayList<CodedTerm>();
    // list of existing practice setting codes.
    private static final List<CodedTerm> practiceSettingCodes = new ArrayList<CodedTerm>();
    // list of existing type codes.
    private static final List<CodedTerm> typeCodes = new ArrayList<CodedTerm>();
    // list of existing confidentiality codes.
    private static final List<CodedTerm> confidentialityCodes = new ArrayList<CodedTerm>();
    // list of existing event codes.
    private static final List<CodedTerm> eventCodes = new ArrayList<CodedTerm>();
    // list of existing mime types.
    private static final List<String256> mimeTypes = new ArrayList<String256>();

    /**
     * Method that retrieves a required list of codes.
     * @param predefinedCodes type of codes required.
     * @return list of codes
     */
    public List<CodedTerm> getCodes(PredefinedCodes predefinedCodes) {
        if (predefinedCodes.equals(PredefinedCodes.CLASS_CODES)) {
            if (classCodes.size() == 0) {
                logger.info("Parsing codes file for class codes...");
                classCodes.addAll(parseNode(ConfigCodeNodes.classCode.toString()));
                logger.info("... class codes retrieved from file.");
            }
            return classCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.FORMAT_CODES)) {
            if (formatCodes.size() == 0) {
                logger.info("Parsing codes file for format codes...");
                formatCodes.addAll(parseNode(ConfigCodeNodes.formatCode.toString()));
                logger.info("... format codes retrieved from file.");
            }
            return formatCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.HEALTHCARE_FACILITY_TYPE_CODES)) {
            if (healthcareFacilityTypeCodes.size() == 0) {
                logger.info("Parsing codes file for healthcare facility type codes...");
                healthcareFacilityTypeCodes.addAll(parseNode(ConfigCodeNodes.healthcareFacilityTypeCode.toString()));
                logger.info("... healthcare facility type codes retrieved from file.");
            }
            return healthcareFacilityTypeCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.PRACTICE_SETTING_CODES)) {
            if (practiceSettingCodes.size() == 0) {
                logger.info("Parsing codes file for practice setting codes...");
                practiceSettingCodes.addAll(parseNode(ConfigCodeNodes.practiceSettingCode.toString()));
                logger.info("... practice setting codes retrieved from file.");
            }
            return practiceSettingCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.TYPE_CODES)) {
            if (typeCodes.size() == 0) {
                logger.info("Parsing codes file for type codes...");
                typeCodes.addAll(parseNode(ConfigCodeNodes.typeCode.toString()));
                logger.info("... type codes retrieved from file.");
            }
            return typeCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.CONTENT_TYPE_CODE)) {
            if (contentTypeCodes.size() == 0) {
                logger.info("Parsing codes file for content type codes...");
                contentTypeCodes.addAll(parseNode(ConfigCodeNodes.contentTypeCode.toString()));
                logger.info("... content type codes retrieved from file.");
            }
            return contentTypeCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.CONFIDENTIALITY_CODES)) {
            if (confidentialityCodes.size() == 0) {
                logger.info("Parsing codes file for confidentiality codes...");
                confidentialityCodes.addAll(parseNode(ConfigCodeNodes.confidentialityCode.toString()));
                logger.info("... confidentiality codes retrieved from file.");
            }
            return confidentialityCodes;
        } else if (predefinedCodes.equals(PredefinedCodes.EVENT_CODES)) {
            if (eventCodes.size() == 0) {
                logger.info("Parsing codes file for event codes...");
                eventCodes.addAll(parseNode(ConfigCodeNodes.eventCodeList.toString()));
                logger.info("... event codes retrieved from file.");
            }
            return eventCodes;
        }
        return null;
    }

    /**
     * Method that parses, sorts and returns a list of codes from a node
     * regardless of the type of code being parsed.
     * @param nodeName name of the nome to parser.
     * @return list of parsed and sorted codes.
     */
    private List<CodedTerm> parseNode(String nodeName) {
        int index = 0;
        List<CodedTerm> temp = new ArrayList<CodedTerm>();
        while (!(((Element) nodes.item(index)).getAttribute(ConfigCodeNodes.CodeAttributes.name.toString()).equals(nodeName))) {
            index++;
        }
        NodeList n = ((Element) nodes.item(index)).getElementsByTagName("Code");
        for (int i = 0; i < n.getLength(); i++) {
            CodedTerm code = new CodedTerm();
            code.setCode(new String256().setString(((Element) n.item(i)).getAttribute(ConfigCodeNodes.CodeAttributes.code
                    .toString())));
            code.setDisplayName(new String256().setString(((Element) n.item(i))
                    .getAttribute(ConfigCodeNodes.CodeAttributes.display.toString())));
            code.setCodingScheme(new CodingScheme().setCodingScheme(new String256().setString(((Element) n.item(i))
                    .getAttribute(ConfigCodeNodes.CodeAttributes.codingScheme.toString()))));
            if (!temp.contains(code))
                temp.add(code);
        }
        Collections.sort(temp, new Comparator<CodedTerm>() {
            @Override
            public int compare(CodedTerm o1, CodedTerm o2) {
                return o1.getDisplayName().toString().compareTo(o2.getDisplayName().toString());
            }
        });
        return temp;
    }

    /**
     * This method parses, sorts and returns a list of existing mime types from an application file.
     * @return sorted mime types.
     */
    public List<String256> getMimeTypes() {
        if (mimeTypes.size() == 0) {
            int index = 0;
            while (!(((Element) nodes.item(index)).getAttribute("name").equals("mimeType"))) {
                index++;
            }
            NodeList n = ((Element) nodes.item(index)).getElementsByTagName("Code");
            for (int i = 0; i < n.getLength(); i++) {
                String256 code = new String256().setString(((Element) n.item(i)).getAttribute("code"));

                if (!mimeTypes.contains(code))
                    mimeTypes.add(code);
            }
            Collections.sort(mimeTypes, new Comparator<String256>() {
                @Override
                public int compare(String256 o1, String256 o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
        }
        return mimeTypes;
    }

    /**
     * Enumeration of existing nodes for the codes parser.
     */
    private static enum ConfigCodeNodes {
        classCode, formatCode, healthcareFacilityTypeCode, practiceSettingCode, typeCode, confidentialityCode, eventCodeList, mimeType, contentTypeCode;

        /**
         * Enumeration of existing nodes attributes for the codes parser.
         */
        private static enum CodeAttributes {
            name, code, display, codingScheme;
        }
    }
}
