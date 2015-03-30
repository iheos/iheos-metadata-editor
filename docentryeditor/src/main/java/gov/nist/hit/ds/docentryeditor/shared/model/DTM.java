package gov.nist.hit.ds.docentryeditor.shared.model;

import com.google.gwt.i18n.shared.DateTimeFormat;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <b> This class represents a DTM (special format's date).</b>
 * </p>
 * <p>
 * A DTM has the following parameter:
 * <ul>
 * <li>{@link #dtm}: The date as a String256 ( {@link String256}) ;</li>
 * </ul>
 * </p>
 * <p>
 * It contains a toString method to return the DTM in String format.
 * </p>
 * <p/>
 * <p>
 * This class also contains getters/setters.<br>
 * In addition, it has verify method to check its syntax.</br>
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toString() method toString} <br>
 * </p>
 *
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement </p>
 */
public class DTM implements ModelElement, Serializable {

    private static final long serialVersionUID = -3711731576590925716L;
    /**
     * <b>DTM dtm</b> - The date as a String256 [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see DTM
     * @see XdsDocumentEntry
     */
    @NotNull
    @MatchesPattern(value = "[0-9]{4}([0-p]{2}){4,}")// FIXME check regexp pattern I don't get it on first sight
    private Date dtm;

    public DTM() {
        dtm = new Date();
    }

    public DTM(Date date) {
        dtm = date;
    }

    public Date getDtm() {
        return dtm;
    }

    public DTM setDtm(Date dtm) {
        this.dtm = dtm;
        return this;
    }


    /**
     * <p>
     * <b>Method toString</b> <br>
     * This method will be called to build a XML file by the
     * {@link NameValueDTM} (called itself by the {@link XdsDocumentEntry}) with
     * the information taken from the local {@link DTM}.<br>
     * </p>
     *
     * @return String which contains the DTM
     * @see DTM class DTM
     */
    @Override
    public String toString() {
        DateTimeFormat dtf = DateTimeFormat.getFormat("yyyyMMddHHmmss");
        return dtf.format(dtm);
    }


    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's {@link DTM} is
     * correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see DTM class DTM
     */
    @Override
    public boolean verify() throws String256Exception {
//		if (dtm.getString().length() < 13) {
//			if (dtm.getString().matches("[0-9]*")) {
        if (dtm.toString().length() < 13) {
            if (dtm.toString().matches("[0-9]*")) {
                return true;
            }
        }
        return false;
    }

    public DTM copy() {
        return new DTM(new Date(this.dtm.getTime()));
    }
}
