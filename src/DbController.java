import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.*;

import java.io.UnsupportedEncodingException;


/**
 * Created by Лев on 09.08.2017.
 */
public class DbController {
    private static DatabaseEntry theKey;
    private static DatabaseEntry theData;
    private static MyData data2Store = new MyData();


    public static void rec (Database myDatabase, Database myClassDb, String aKey, String name, double price) throws UnsupportedEncodingException {
        StoredClassCatalog classCatalog = new StoredClassCatalog(myClassDb);
        EntryBinding<MyData> dataBinding = new SerialBinding<>(classCatalog,
                MyData.class);
        theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
        data2Store.setName(name);
        data2Store.setPrice(price);
        theData = new DatabaseEntry();
        dataBinding.objectToEntry(data2Store, theData);
        myDatabase.put(null, theKey, theData);
    }


    public static MyData find (Database myDatabase, Database myClassDb, String aKey) throws UnsupportedEncodingException {
        StoredClassCatalog classCatalog = new StoredClassCatalog(myClassDb);
        EntryBinding<MyData> dataBinding = new SerialBinding<>(classCatalog,
                MyData.class);
        theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
        theData = new DatabaseEntry();
        myDatabase.get(null, theKey, theData, LockMode.DEFAULT);
        MyData retrievedData = dataBinding.entryToObject(theData);
        return retrievedData;
    }


    public static void delete (Database myDatabase, String aKey) throws UnsupportedEncodingException {
        DatabaseEntry theKey1 = new DatabaseEntry(aKey.getBytes("UTF-8"));
        myDatabase.delete(null, theKey1);
    }


    public static void close(Database myDatabase, Database myClassDb, Environment myDbEnvironment) {
        if (myDatabase != null) {
            myDatabase.close();
        }

        if (myClassDb != null) {
            myClassDb.close();
        }

        if (myDbEnvironment != null) {
            myDbEnvironment.close();
        }
    }
}
