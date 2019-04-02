/**
 * Class that extends class Attribute and will represent a string attribute
 */
public class StringAttribute extends Attribute {
    /**
     * String value of this attribute
     */
    private String value;

    /**
     * Constructor that will set the name and value of this attribute
     *
     * @param name  name of this attribute
     * @param value value of this attribute
     */
    StringAttribute(String name, String value) {
        super(name);
        this.value = value;
    }

    /**
     * Get value of this attribute
     *
     * @return String value of this attribute
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Method that update attribute value
     *
     * @param s a new value for attribute
     */

    @Override
    public void updateValue(String s) {
        this.value = s;
    }

    /**
     * Method that override toString() method
     *
     * @return name and value attribute in a string format
     */
    @Override
    public String toString() {
        return super.toString() + ":" + value;
    }
}
