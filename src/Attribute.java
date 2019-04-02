/**
 * Abstract class that will represent type of atribut
 **/

public abstract class Attribute {
    /**
     * Name of attribute
     */
    String name;

    /**
     * Constructor for name initialization
     *
     * @param name an initial attribute name
     */
    Attribute(String name) {
        this.name = name;
    }

    /**
     * Method that override toString and return name of this attribute
     *
     * @return attribute name
     */

    @Override
    public String toString() {
        return name;
    }

    /**
     * Method that will return the specific object of each subclass
     *
     * @return an object that will represent attribute value
     */
    public abstract Object getValue();

    /**
     * Method that will update the specific value of each subclass
     *
     * @param s a new value for attribute
     */
    public void updateValue(String s) {
    }
}
