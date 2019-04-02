/**
 * Class that extends class Attribute and will represent an integer attribute
 */
public class IntegerAttribute extends Attribute {
    /**
     * Integer value of this attribute
     */
    private Integer value;

    /**
     * Constructor that will set the name and value of this attribute
     * @param name name of this attribute
     * @param value value of this attribute
     */
    IntegerAttribute(String name, int value) {
        super(name);
        this.value = value;
    }

    /**
     * Get value of this attribute
     * @return Integer value of this attribute
     */
    public Integer getValue(){
        return this.value;
    }

    /**
     * Method that update attribute value
     * @param s a new value for attribute
     */
    @Override
    public void updateValue(String s) {
        //Convert from String to Integer
        this.value = Integer.parseInt(s);
    }

    /**
     * Method that override toString() method
     * @return name and value attribute in a string format
     */
    @Override
    public String toString() {
        return super.toString() + ":" + value;
    }
}
