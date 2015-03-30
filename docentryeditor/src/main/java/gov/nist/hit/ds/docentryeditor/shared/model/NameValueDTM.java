package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * <b> This class represents a NameValue.</b><br>
 * A NameValue depends on a type T which is implemented during the instantiation
 * </p>
 * <p>
 * An NameValue has the following parameters:
 * <ul>
 * <li>{@link #name}: The name of the NameValue ({@link String256}) ;</li>
 * <li>{@link #values}: A list of values (ArrayList of DTM) ;</li>
 * </ul>
 * </p>
 * <p>
 * It contains a toXML method to return the NameValue in XML format.<br>
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
 * @see ModelElement class ModelElement
 */
public class NameValueDTM implements ModelElement, Serializable {
    private static final long serialVersionUID = 384235409232722676L;

    /**
     * <b>String256 name</b> - The name of the NameValue [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     *
     * @see String256 class String256
     * @see NameValueDTM
     */
    @NotNull
    private String256 name;

    /**
     * <b>ArrayList(DTM)</b> - A list of values [Mandatory].<br>
     * Type: ArrayList of {@link DTM}
     *
     * @see NameValueDTM
     */
    @NotNull
    @NotEmpty
    private ArrayList<DTM> values;

    public NameValueDTM() {
        name = new String256();
        values = new ArrayList<DTM>();
        values.add(new DTM());
    }

    public ArrayList<DTM> getValues() {
        return values;
    }

    public void setValues(ArrayList<DTM> values) {
        this.values = values;
    }

    public String256 getName() {
        return name;
    }

    public void setName(String256 name) {
        this.name = name;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * {@link NameValueDTM}.<br>
     * </p>
     *
     * @return String which contains the NameValue in XML format
     * @see NameValueDTM class NameValue
     */
    public String toXML() {
        String answer = "\t\t<namevalue>\n\t\t\t<name>" + name.toString() + "</name>\n\t\t\t<values>\n";
        for (DTM str : values) {
            answer = answer + "\t\t\t\t<value>" + str.toString() + "</value>\n";
        }
        answer = answer + "\t\t\t</values>\n\t\t</namevalue>\n";

        return answer;
    }

    // abstract public AssertionGroup validateValueType(String256 value);

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's
     * {@link NameValueDTM} is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see NameValueDTM
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code
        boolean answer = true;
        answer = name.verify();

        for (DTM t : values) {
            answer = t.verify();
        }
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameValueDTM)) return false;

        NameValueDTM that = (NameValueDTM) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (values != null && values.size() == that.getValues().size()) {
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).getDtm().equals(that.getValues().get(i).getDtm())) {
                    return false;
                }
            }
        } else
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "NameValueDTM{" +
                "name=" + name +
                ", values=" + values +
                '}';
    }

    public NameValueDTM copy() {
        NameValueDTM cp = new NameValueDTM();
        cp.setName(this.name.copy());
        for(DTM vcp : this.values) {
            cp.getValues().set(0,vcp.copy());
        }
        return cp;
    }
}
