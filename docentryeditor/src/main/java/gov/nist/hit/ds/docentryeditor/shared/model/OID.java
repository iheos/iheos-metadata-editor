package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <b> This class represents an OID.</b>
 * </p>
 * <p>
 * An OID has the following parameter:
 * <ul>
 * <li>{@link #oid}: The oid ({@link String256}).</li>
 * </ul>
 * </p>
 * <p>
 * <p/>
 * It contains toString() method to return the OID.<br>
 * This class also contains getters/setters.</br> In addition, it has verify
 * method to check its syntax.
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toString() method toString} <br>
 * </p>
 *
 * @see String256 class String256
 * @see String256Exception class String256Exception
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement
 */
public class OID /*extends String256*/ implements ModelElement, Serializable {

    private static final long serialVersionUID = -7932949245243225415L;
    /**
     * <b>OID oid</b> - The oid [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see String256 class String256
     * @see OID class OID
     */
    @NotNull
    @MatchesPattern(value = "[0-9](\\.[0-9]+)*(\\^[0-9]+)?")
    private String256 oid;

    public OID() {
        oid = new String256();
    }

    public OID(String256 oid) {
        this.oid = oid;
    }

    public String256 getOid() {
        return oid;
    }

    public OID setOid(String256 oid) {
        this.oid = oid;
        return this;
    }

    /**
     * <p>
     * <b>Method verify</b><br>
     * This method verify if the String respects the {@link OID} syntax given by
     * the regular expression below
     * </p>
     *
     * @return boolean: true if it's an OID else false
     * @throws String256Exception if the String is larger than 256 characters
     * @see String256Exception class String256Exception
     * @see OID class OID
     */
    @Override
    public boolean verify() throws String256Exception {
        if (oid.verify()) {
            if (oid.toString().matches("[0-9](\\.[0-9]+)*(\\^[0-9]+)?")) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * <b>Method toXML</b></br>
     * <p/>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * {@link OID}.<br>
     * </p>
     *
     * @return String which contains the OID
     * @see OID class OID
     */
    @Override
    public String toString() {
        return oid.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OID)) return false;

        OID oid1 = (OID) o;

        if (oid != null ? !oid.equals(oid1.oid) : oid1.oid != null) return false;

        return true;
    }

    public OID copy() {
        return new OID(this.oid.copy());
    }
}
