package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * XdsAssociation represents a the link between two XDS model elements (Folder, Document entry and submission set)
 * submitted together.
 *
 * Created by onh2 on 2/24/2015.
 */
public class XdsAssociation implements Serializable, XdsModelElement{

    // not null, not empty?
    private String256 id; // UUID
    @NotNull
    @NotEmpty
    private XdsAssociationType type; // URN => use an enum
    private String256 source; // UUID
    private String256 target; // UUID
    private AvailabilityStatus availabilityStatus; // URN[0..1]
    private SubmissionSetStatus submissionSetStatus;

    public XdsAssociation(){
        id=new String256();
        type=XdsAssociationType.HAS_MEMBER;
        availabilityStatus=AvailabilityStatus.APPROVED;
        submissionSetStatus=SubmissionSetStatus.ORIGINAL;
    }

    @Override
    public String256 getId() {
        return id;
    }

    public void setId(String256 id) {
        this.id = id;
    }

    public XdsAssociationType getType() {
        return type;
    }

    public void setType(XdsAssociationType type) {
        this.type = type;
    }

    public String256 getSource() {
        return source;
    }

    public void setSource(String256 source) {
        this.source = source;
    }

    public String256 getTarget() {
        return target;
    }

    public void setTarget(String256 target) {
        this.target = target;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public SubmissionSetStatus getSubmissionSetStatus() {
        return submissionSetStatus;
    }

    public void setSubmissionSetStatus(SubmissionSetStatus submissionSetStatus) {
        this.submissionSetStatus = submissionSetStatus;
    }

    @Override
    public boolean verify() {
        return false;
    }

    @Override
    public XdsModelElement copy() {
        return null;
    }

    public enum XdsAssociationType {
        HAS_MEMBER("urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember"),
        RPLC("urn:ihe:iti:2007:AssociationType:RPLC"),// replace?
        XFRM("urn:ihe:iti:2007:AssociationType:XFRM"),// transform?
        APND("urn:ihe:iti:2007:AssociationType:APND"),// append?
        XFRM_RPLC("urn:ihe:iti:2007:AssociationType:XFRM_RPLC"),// TRANSFORM_REPLACE?
        SIGNS("urn:ihe:iti:2007:AssociationType:signs");
        // TODO check this, some types might be missing
        private final String text;

        /**
         * @param text
         */
        XdsAssociationType(final String text) {
            this.text = text;
        }

        public static XdsAssociationType getValueOf(String value){
            if (value.equals(HAS_MEMBER.toString()) || value.contains("HasMember") ){
                return HAS_MEMBER;
            }
            if (value.equals(APND.toString()) || value.contains("APND") ){
                return APND;
            }
            if (value.equals(RPLC.toString()) || value.contains("RPLC")){
                return RPLC;
            }
            if (value.equals(SIGNS.toString()) || value.contains("signs")){
                return SIGNS;
            }
            if (value.equals(XFRM.toString()) || value.contains("XFRM")){
                return XFRM;
            }
            if (value.equals(XFRM_RPLC.toString()) || value.contains("XFRM_RPLC")){
                return XFRM_RPLC;
            }
            return null;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }
}
