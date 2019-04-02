import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represent a DataBase
 */
class DataBase {
    /**
     * HasMap of entities
     */
    HashMap<String, Entity> entities = new HashMap<>();
    /**
     * DataBase name
     */
    private String dbName;
    /**
     * Max capacity of a Node from DataBase
     */
    private int maxCapacityN;
    /**
     * Array of nodes
     */
    private ArrayList<Node> nodes;

    /**
     * Constructor that will createa a DataBase
     *
     * @param dbName       DataBase name
     * @param nr_nodes     Number of node that will be in this DataBase
     * @param maxCapacityN Maximum capacity of a node
     */
    DataBase(String dbName, int nr_nodes, int maxCapacityN) {
        this.dbName = dbName;
        this.maxCapacityN = maxCapacityN;
        this.nodes = new ArrayList<>(nr_nodes);
    }


    /**
     * Method that insert Instances in array of Nodes
     *
     * @param inst new Instance for insert
     */
    void addInNodes(Instance inst) {
        int n = 0;
        int i = 0;
        //insert Instance in "n" nodes, "n" represent replication factor of this instance
        while (n < inst.replicationFactor) {
            //if we have to create a new node
            if (i >= this.nodes.size())
                this.nodes.add(new Node());
            //insert in a node if it`s have space for new instance
            if (this.nodes.get(i).instances.size() < maxCapacityN) {
                this.nodes.get(i).insertInNodes(inst);
                n++;
                i++;
            } else i++;
            //else we will create a new node
        }

    }

    /**
     * Method that will print in a file all elements of DataBase
     *
     * @param writer file for printing
     */
    void printDB(PrintWriter writer) {
        int a = 0;
        if (!nodes.isEmpty()) {
            for (int i = 0; i < nodes.size(); i++) {
                if (!nodes.get(i).instances.isEmpty()) {
                    writer.print("Nod" + (i + 1) + "\n");
                    nodes.get(i).printNode(writer);
                    a = 1;
                }
            }
        }
        if (a == 0) writer.print("EMPTY DB\n");
    }

    /**
     * Method that add an entity in HashMap of entities where the key is the name of this entity
     *
     * @param name name of this entity
     * @param ent  new created entity
     */
    void createEntity(String name, Entity ent) {
        entities.put(name, ent);
    }

    /**
     * Method that delete an instance from DataBase
     *
     * @param nameEntity name of entity
     * @param primaryKey primary key of this entity
     * @param writer     file for printing
     */
    void deleteInstanceN(String nameEntity, String primaryKey, PrintWriter writer) {
        boolean existInstace = false;
        //parse array of nodes and call delete method from class Instance
        for (Node temp : nodes) {
            boolean rezult = temp.deleteInstance(nameEntity, primaryKey);
            if (rezult) existInstace = true;
        }
        if (!existInstace)
            writer.print("NO INSTANCE TO DELETE\n");

    }

    /**
     * Method that update attributes of an instance
     *
     * @param parts array of strings with the name and new value of attributes
     */
    void updateInstances(String[] parts) {
        //parse array of nodes and call method for update instance for all instances
        for (Node temp : nodes) {
            temp.updateInstance(parts);
        }


    }

    /**
     * Method that print in a file an instance and all nodes that contains it
     *
     * @param nameEntity          name of entity
     * @param primaryKeyForSearch primary key of instance
     * @param writer              file for printing
     */
    void getEntity(String nameEntity, String primaryKeyForSearch, PrintWriter writer) {
        Instance instToPrint = null;
        for (int i = 0; i < nodes.size(); i++) {
            //in all nodes search specific instance
            Instance existInstance = nodes.get(i).search(nameEntity, primaryKeyForSearch);
            if (existInstance != null) {
                writer.print("Nod" + (i + 1) + " ");
                instToPrint = existInstance;
            }
        }
        if (instToPrint != null)
            instToPrint.printInstance(writer);
        else writer.print("NO INSTANCE FOUND\n");
    }

    /**
     * Method that will delete all instances that was created earlier for a certain time
     *
     * @param dbName DataBase name
     * @param time   time the time after which we will look for the courts to be deleted
     */
    void cleanUp(String dbName, String time) {
        if (dbName.equals(dbName)) {
            for (Node temp : nodes) {
                temp.cleanUpNode(Long.parseLong(time));
            }
        }
    }
}
