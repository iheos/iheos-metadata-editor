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
    @NotNull
    @NotEmpty
    private String256 source; // UUID
    @NotNull
    @NotEmpty
    private String256 target; // UUID
    private String256 status; // URN[0..1]

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

    public String256 getStatus() {
        return status;
    }

    public void setStatus(String256 status) {
        this.status = status;
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
        RPLC("urn:ihe:iti:2007:AssociationType:RPLC"),// rename replace?
        XFRM("urn:ihe:iti:2007:AssociationType:XFRM"),// rename transform?
        APND("urn:ihe:iti:2007:AssociationType:APND"),// rename append?
        XFRM_RPLC("urn:ihe:iti:2007:AssociationType:XFRM_RPLC"),// rename TRANSFORM_REPLACE?
        SIGNS("urn:ihe:iti:2007:AssociationType:signs");
        // TODO check this, some types might be missing
        private final String text;

        /**
         * @param text
         */
        XdsAssociationType(final String text) {
            this.text = text;
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
