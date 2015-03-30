package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <b> This class represents an Identifier.</b>
 * </p>
 * <p>
 * An Identifier has the following parameters:
 * <ul>
 * <li>{@link #value}: The value of the identifier ({@link String256}) ;</li>
 * <li>{@link #idType}: The id type ({@link String256});</li>
 * </li>
 * </ul>
 * </p>
 * <p>
 * </p>
 * <p>
 * It contains a toXML method to return the Identifier in XML format.<br>
 * This class also contains getters/setters.</br> In addition, it has verify
 * method to check its syntax.
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toXML() method toXML} <br>
 * </p>
 *
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement <
 */
public class IdentifierString256 implements ModelElement, Serializable {

    private static final long serialVersionUID = -1717453248217705389L;

    /**
     * <p>
     * <p/>
     * <b>String256 value</b> - The value of the identifier [Mandatory].<br>
     * Type: {@link String256}</br>
     * </p>
     *
     * @see String256 class String256
     * @see IdentifierString256
     */
    @NotNull
    private String256 value;

    /**
     * <b>String256 idType</b> - The type of the identifier [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see IdentifierString256
     */
    @NotNull
    private String256 idType;

    public IdentifierString256(String256 valueId, String256 idTypeId) {
        value = valueId;
        idType = idTypeId;
    }

    public IdentifierString256() {
        value = new String256();
        idType = new String256();
    }

    public String256 getValue() {
        return value;
    }

    public void setValue(String256 value) {
        this.value = value;
    }

    public String256 getIdType() {
        return idType;
    }

    public void setIdType(String256 element) {
        idType = element;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * Identifier.<br/>
     * </p>
     *
     * @return String which contains the Identifier in XML format
     * @see IdentifierString256
     */
    public String toXML() {
        String answer = null;
        answer = "\t\t<identifier>\n\t\t\t<value>" + value.getString().replace("&","&amp;") + "</value>\n\t\t\t<idtype>" + idType.toString()
                + "</idtype>\n\t\t</identifier>\n";

        return answer;
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's
     * {@link IdentifierString256} is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see IdentifierString256
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code
        boolean answer = true;
        answer = value.verify();
        answer = idType.verify();
        return answer;
    }

    @Override
    public String toString() {
        return "IdentifierString256{" +
                "value=" + value +
                ", idType=" + idType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifierString256)) return false;

        IdentifierString256 that = (IdentifierString256) o;

        if (idType != null ? !idType.equals(that.idType) : that.idType != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    public IdentifierString256 copy() {
        return new IdentifierString256(this.value.copy(),this.getIdType().copy());
    }
}
