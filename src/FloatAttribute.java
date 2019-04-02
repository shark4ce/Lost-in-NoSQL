/**
 * Class that extends class Attribute and will represent a float attribute
 */
public class FloatAttribute extends Attribute {
    /**
     * Float value of this attribute
     */
    private float value;

    /**
     * Constructor that will set the name and value of this attribute
     * @param name name of this attribute
     * @param value value of this attribute
     */
    FloatAttribute(String name, float value) {
        super(name);
        this.value = value;
    }

    /**
     * Get value of this attribute
     * @return value of this attribute
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Method that update attribute value
     * @param s a new value for attribute
     */
    @Override
    public void updateValue(String s) {
        //Covert from string to float
        this.value = Float.valueOf(s);
    }

    /**
     * Method that override toString() method
     * @return name and value attribute in a string format
     */
    @Override
    public String toString() {
        if (value == (long) value) {
            //if the number`s decimal is 0 will print it like a long one
            return super.toString() + ":" + String.format("%d",(long)value);
        } else
            return super.toString() + ":" + value;
    }
}
