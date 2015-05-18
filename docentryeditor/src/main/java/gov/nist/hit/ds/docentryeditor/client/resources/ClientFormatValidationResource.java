package gov.nist.hit.ds.docentryeditor.client.resources;

import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

/**
 * Created by onh2 on 5/18/2015.
 */
public enum ClientFormatValidationResource {
    INSTANCE;

    public RegExValidator getHashCodeRegExpValidator() {
        return new RegExValidator("^[0-9a-fA-F]+$", "Value is not correct. It is supposed to be a hexadecimal value.");
    }

    public RegExValidator getLegalAuthenticatorRegExpValidator() {
        return new RegExValidator("^[0-9]+\\^(([A-Za-z]+\\.?\\s?)+\\^){3,7}\\^{2}&[0-9]+(\\.[0-9]+)*(&ISO)$","This value is not correctly formatted. \nIt should be formatted as defined in HL7 v2.5 XCN data type");
    }

    public RegExValidator getPatientIDRegExpValidator() {
        return new RegExValidator("^(([A-Za-z])|([1-9]))*[0-9A-z]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "Value's format is not a correct. \nIt should be like this: 6578946^^^&1.3.6.1.4.1.21367.2005.3.7&ISO.");
    }

    public RegExValidator getRepoOIDValidation() {
        return new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+$", "Value is not correct. A repository unique ID is supposed to be a suite of numbers separated by periods.");
    }

    public RegExValidator getSourcePatientIDRegExpValidator() {
        // RegExValidator: "^[A-Za-z]*[0-9]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "This value is not a correct source patient id."
        return new RegExValidator("^(([A-Za-z])|([1-9]))*[0-9A-z]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "This value is not a correct source patient id.");
    }

    public RegExValidator getSourcePatientInfoRegExpValidator() {
        return new RegExValidator("^PID-(((3|5|11|2|4|12|19)\\|)|(8\\|(M|F|O|U)$)|(7\\|(((19|20)\\d\\d)(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])))$)", "This value is not a correct source patient info.");
    }

    public RegExValidator getUniqueIdRegExpValidator() {
        return new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods.");
    }

    public RegExValidator getUriRegExpValidator() {
        return new RegExValidator("^(http(s)?://|www\\.)","This is not a valid uri. It has to start with \"http://\", \"https://\" or \"www\"");
    }

    public RegExValidator getSourceIdRegExpValidator() {
        return new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods.");
    }

    public RegExValidator getHomeCommunityIdRegExpValidator() {
        return new RegExValidator("^urn:oid:[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods preceded by \"urn:oid:\".");
    }

}
