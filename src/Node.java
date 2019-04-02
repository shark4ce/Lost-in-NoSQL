import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Class Node will represent a node of DataBase
 */
class Node {
    /**
     * A TreeSet of instances that will represent all instance from a node
     */
    TreeSet<Instance> instances;

    /**
     * Constructor that will create a new Node with a TreeSet
     */
    Node() {
        this.instances = new TreeSet<>();
    }

    /**
     * Method that will insert in TreeSet of this Node a new Instance
     * TreeSet alaways will be sorted, after which insertion
     * For sort we use interface Comparamble which method is override is class Instance
     *
     * @param inst new Instance object
     */
    void insertInNodes(Instance inst) {
        this.instances.add(inst);
    }

    /**
     * Method that will print all Instances of this Node
     *
     * @param writer File where will be printed all Instances of this Node
     */
    void printNode(PrintWriter writer) {
        //parse TreeSet of Instances and call print method for each Instance
        for (Instance temp : instances) {
            temp.printInstance(writer);
        }
    }

    /**
     * Method that search in this Node a specific instance by the name of Entity and a specific key
     * @param nameEntity name of entity
     * @param keyForSearch key for instance
     * @return address of the Instance if it was found or return null if not
     */
    Instance search(String nameEntity, String keyForSearch) {
        if (!instances.isEmpty()) {
            for (Instance aux : instances) {
                /*select the primary key of each instance which always
                    is the first attribute of an instance*/
                Object primaryKey = aux.attributes.get(0).getValue();
                if (primaryKey.toString().equals(keyForSearch) && aux.nameEntity.equals(nameEntity))
                    return aux;
            }
        }
        return null;
    }

    /**
     * Method that delete a specific Instance which is searched by name of entity and primary key
     * @param nameEntity name of entity
     * @param keyForDelete primary key
     * @return true if we found and delete this instance or false if not
     */
    boolean deleteInstance(String nameEntity, String keyForDelete) {

        Instance instForDelete = search(nameEntity, keyForDelete);
        if (instForDelete != null) {
            instances.remove(instForDelete);
            return true;
        } else return false;
    }

    /**
     * Method that update value of attributes of an instance
     * @param parts array of strings with name and new value for attributes
     */
    void updateInstance(String[] parts) {
        Instance instForUpdate = search(parts[1], parts[2]);
        if (instForUpdate != null) {
            boolean a = instForUpdate.updateAttributes(parts);
            //if an instance was updated, we set new timeStamp for it, delete and insert again in TreeSet
            if (a) {
                Instance temp = instForUpdate;
                instances.remove(instForUpdate);
                temp.setNewTimeStamp(System.nanoTime());
                instances.add(temp);
            }
        }

    }

    /**
     * Method that will delete all instances that was created earlier for a certain time
     * @param time time the time after which we will look for the courts to be deleted
     */
    void cleanUpNode(long time) {
        if (!instances.isEmpty()) {
            //search in this node appropriate instandes and put their addresses in an arraylist
            ArrayList<Instance> elementsForDelete = new ArrayList<>();
            for (Instance aux : instances) {
                if (aux.timeStamp < time)
                    elementsForDelete.add(aux);
            }
            //parse arraylist of addreses and delete it
            for (Object anElementsForDelete : elementsForDelete) {
                instances.remove(anElementsForDelete);
            }
        }
    }
}
