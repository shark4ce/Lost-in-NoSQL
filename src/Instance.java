import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that will represent an Instance
 */
public class Instance implements Comparable<Instance> {
    /**
     * Name of the entity that it represent
     */
    String nameEntity;
    /**
     * Array of attributes that this instance contains
     */
    ArrayList<Attribute> attributes;
    /**
     * timeStamp represent the time when this instance was created
     */
    long timeStamp;
    /**
     * int that represent replication factor of this instance
     */
    int replicationFactor;

    /**
     * Constructor that will create an Instance
     *
     * @param parts    array of strings which contains value parameters of this instance
     * @param entities is type of entity that this instance will represent
     */
    Instance(String[] parts, HashMap<String, Entity> entities) {
        this.nameEntity = parts[1];
        this.attributes = new ArrayList<>();
        //set the actual time of machine that will represent creation time of this instance
        this.timeStamp = System.nanoTime();
        //select the specific entity from hasmap of entities
        Entity ent = entities.get(parts[1]);
        this.replicationFactor = ent.replicationFactor;
        //method that create and set the value of the instance attibutes
        create(parts, ent);


    }

    /**
     * Method that create and set the value of the instance attibutes
     *
     * @param parts array of strings which contains value parameters of this instance
     * @param ent   is type of entity that this instance will represent
     */
    private void create(String[] parts, Entity ent) {
        /* parse the entity array that contains the name and type of attributes
         * and create attribute of specified type and set it`s value*/
        for (int i = 0, j = 2; i < ent.typeOfAttributes.size(); i = i + 2, j++) {
            Attribute temp;
            switch (ent.typeOfAttributes.get(i + 1)) {
                case "Integer":
                    temp = new IntegerAttribute(ent.typeOfAttributes.get(i), Integer.parseInt(parts[j]));
                    break;
                case "Float":
                    temp = new FloatAttribute(ent.typeOfAttributes.get(i), Float.parseFloat(parts[j]));
                    break;
                default:
                    temp = new StringAttribute(ent.typeOfAttributes.get(i), parts[j]);
                    break;
            }
            //add created attribute in array of attributes of this instance
            this.attributes.add(temp);
        }

    }

    /**
     * Method that set new timeStamp for this instance
     *
     * @param timeStamp new time for this instance
     */
    void setNewTimeStamp(long timeStamp) {
        //if this instance will be modified, calling of this method will set new timeStamp for this instance
        this.timeStamp = timeStamp;
    }

    /**
     * Method that print in a file all parameters of this instance
     *
     * @param writer file for printing
     */
    void printInstance(PrintWriter writer) {
        writer.print(nameEntity);
        //parse array of attributes and call method toString() for each of them
        for (Attribute attribute : attributes) {
            writer.print(" " + attribute.toString());
        }
        writer.print("\n");
    }

    /**
     * Method that compare that instance with another object of type Instance by the timeStamp
     *
     * @param o instance for compare
     * @return difference an int that will help to sort TreeSet of instances(in class Node)
     */
    @Override
    public int compareTo(Instance o) {
        /*Method that compare timeStamp of two Instance object.
         * The first argument of Long.compare() is the timeStamp of given object and
         * the second is timeStamp of this instance, because we want an descending
         * order*/
        return Long.compare(o.timeStamp, this.timeStamp);
    }

    /**
     * Method that update attributes of this Instance
     *
     * @param parts array of string that contains name and new value of attributes
     * @return return true if any attribute has been modified and flase if none
     */
    boolean updateAttributes(String[] parts) {
        boolean isUpdated = false;
        //parse the array of new attributes value
        for (int i = 3; i < parts.length; i = i + 2) {
            //parse the attribute vector of this instance
            for (Attribute temp : attributes) {
                /*if we found in array of new attributes value the name of one or more attributes of our instance,
                    call the method that update this attribute
                */
                if (temp.name.equals(parts[i])) {
                    temp.updateValue(parts[i + 1]);
                    isUpdated = true;
                }
            }
        }
        return isUpdated;
    }
}
