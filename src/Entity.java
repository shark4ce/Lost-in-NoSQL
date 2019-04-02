import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that will represent an entity type
 */
class Entity {
    /**
     * replication factor of this entity
     */
    int replicationFactor;
    /**
     * the array of name and types of attributes of this instance
     */
    ArrayList<String> typeOfAttributes;

    /**
     * number of attributes that this instance will contain
     */
    private int nr_attributes;

    /**
     * Constructor that will create an entity
     *
     * @param parts array of strings that contain name and types of attributes
     */
    Entity(String[] parts) {
        this.replicationFactor = Integer.parseInt(parts[2]);
        this.nr_attributes = Integer.parseInt(parts[3]);
        this.typeOfAttributes = new ArrayList<>();
        //add in array of attributes
        typeOfAttributes.addAll(Arrays.asList(parts).subList(4, 2 * nr_attributes + 4));
    }
}
