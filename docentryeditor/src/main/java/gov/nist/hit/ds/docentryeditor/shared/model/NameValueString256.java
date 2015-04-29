package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <b> This class represents a NameValue for type String256.</b><br>
 * </p>
 *
 * @see ModelElement class ModelElement
 */
public class NameValueString256 implements ModelElement, Serializable {
    private static final long serialVersionUID = -6036404246643751647L;

    /**
     * <b>String256 name</b> - The name of the NameValueString256 [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     */
    @NotNull
    private String256 name;

    /**
     * A list of values [Mandatory].<br/>
     */
    @NotNull
    @NotEmpty
    private List<String256> values = new ArrayList<String256>();

    /**
     * Default constructor
     */
    public NameValueString256() {
        name = new String256();
    }

    /**
     * Getter that return a list of String256 objects
     * @return list of String256 objects
     */
    public List<String256> getValues() {
        return values;
    }

    /**
     * This a method is a setter to assign a new list of values (String256)
     * @param values a list of String256 objects
     */
    public void setValues(List<String256> values) {
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
     * This method will be called to build a XML file (custom format).
     * </p>
     *
     * @return String which contains the NameValueString256 in XML format
     */
    public String toXML() {
        String answer = "\t\t<namevalue>\n\t\t\t<name>" + name.toString() + "</name>\n\t\t\t<values>\n";
        for (String256 str : values) {
            answer = answer + "\t\t\t\t<value>" + str.toString().replace("&","&amp;") + "</value>\n";
        }
        answer = answer + "\t\t\t</values>\n\t\t</namevalue>\n";

        return answer;
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax of the String256 NameValue object
     * is correct or not. </br>
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
        if (!(o instanceof NameValueString256)){
            return false;
        }
        NameValueString256 that = (NameValueString256) o;
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (values != null && values.size() == that.getValues().size()) {
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).equals(that.getValues().get(i))) {
                    return false;
                }
            }
        } else{
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
        return "NameValueString256{" +
                "name=" + name +
                ", values=" + values +
                '}';
    }

    public NameValueString256 copy() {
        NameValueString256 cp = new NameValueString256();
        cp.setName(this.getName().copy());
        for (String256 s : this.values){
            cp.getValues().add(s.copy());
        }
        return cp;
    }
}
