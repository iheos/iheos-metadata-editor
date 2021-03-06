package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b> This class represents a NameValue.</b><br/>
 *
 * @see ModelElement class ModelElement
 */
public class NameValueDTM implements ModelElement, Serializable {
    private static final long serialVersionUID = 384235409232722676L;

    /**
     * <b>String256 name</b> - The name of the NameValue [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     */
    @NotNull
    private String256 name;

    /**
     * <b>ArrayList(DTM)</b> - A list of values [Mandatory].<br>
     * Type: ArrayList of {@link DTM}
     */
    @NotNull
    @NotEmpty
    private List<DTM> values;

    /**
     * Default constructor.
     */
    public NameValueDTM() {
        name = new String256();
        values = new ArrayList<DTM>();
        values.add(new DTM());
    }

    public List<DTM> getValues() {
        return values;
    }

    public void setValues(List<DTM> values) {
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
     * <b>Method toXML</b> <br/>
     * Generates the XML of the NameValue for DTM type that correspond to the object.
     *
     * @return String which contains the NameValue in XML format
     */
    public String toXML() {
        String answer = "\t\t<namevalue>\n\t\t\t<name>" + name.toString() + "</name>\n\t\t\t<values>\n";
        for (DTM str : values) {
            answer = answer + "\t\t\t\t<value>" + str.toString() + "</value>\n";
        }
        answer = answer + "\t\t\t</values>\n\t\t</namevalue>\n";

        return answer;
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax of NameValue for DTM type is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     */
    @Override
    public boolean verify() throws String256Exception {
        // TODO
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof NameValueDTM)){
            return false;
        }
        NameValueDTM that = (NameValueDTM) o;
        if (name != null ? !name.equals(that.name) : that.name != null){
            return false;
        }
        if (values != null && values.size() == that.getValues().size()) {
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).getDtm().equals(that.getValues().get(i).getDtm())) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASHING_KEY * result + (values != null ? values.hashCode() : 0);
        return result;
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
