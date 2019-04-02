import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class which contain the main method
 */
public class Main {
    public static void main(String[] args) throws IOException {

        DataBase DB = null;

        File file = new File(args[0]);
        Scanner sc = new Scanner(file);

        FileWriter fileWriter = new FileWriter(args[0] + "_out");
        PrintWriter writer = new PrintWriter(fileWriter);

        Entity ent;
        Instance inst;
        String read;
        while (sc.hasNextLine()) {
            read = sc.nextLine();
            String[] parts = read.split(" ");
            switch (parts[0]) {
                case "CREATE":
                    //create new entity and add in database
                    ent = new Entity(parts);
                    assert DB != null;
                    DB.createEntity(parts[1], ent);
                    break;
                case "INSERT":
                    assert DB != null;
                    //create new instance and add in database
                    inst = new Instance(parts, DB.entities);
                    DB.addInNodes(inst);
                    break;
                case "CREATEDB":
                    //createa new database
                    DB = new DataBase(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    break;
                case "SNAPSHOTDB":
                    //print database
                    assert DB != null;
                    DB.printDB(writer);
                    break;
                case "DELETE":
                    //delete an instance
                    assert DB != null;
                    DB.deleteInstanceN(parts[1], parts[2], writer);
                    break;
                case "UPDATE":
                    //updatea an instance
                    assert DB != null;
                    DB.updateInstances(parts);
                    break;
                case "GET":
                    //get an instance
                    assert DB != null;
                    DB.getEntity(parts[1], parts[2], writer);
                    break;
                case "CLEANUP":
                    //delete instances selected by a specific timestamp
                    assert DB != null;
                    DB.cleanUp(parts[1], parts[2]);
                    break;
            }
        }
        fileWriter.close();
        writer.close();
    }
}
