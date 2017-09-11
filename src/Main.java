/**
 * Created by Лев on 05.08.2017.
 */

import java.io.File;
import java.io.IOException;

import com.sleepycat.je.*;

public class Main {
    private static final double TOSECONDS = 0.000000001;
    private static final String DIR = "C://somedir";
    private static final int N = 10000;


    public static void main(String[] args) throws IOException {

        Environment myDbEnvironment;
        Database myDatabase;
        Database myClassDb;

        try {
            DatabaseConfig myDbConfig = new DatabaseConfig();
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            myDbEnvironment = new Environment(new File(DIR), envConfig);
            myDbConfig.setAllowCreate(true);
            myDatabase = myDbEnvironment.openDatabase(null,
                    "sampleDatabase", myDbConfig);
            myClassDb = myDbEnvironment.openDatabase(null, "sampleClassDatabase", myDbConfig);


            long start = System.nanoTime();
            for (Integer i = 0; i < N; i++) {
                DbController.rec(myDatabase, myClassDb, Integer.toString(i), "Test name " + Integer.toString(i) ,Math.random() * 10000);
            }
            long finish = System.nanoTime();
            long timeForAdd = finish - start;
            System.out.print("Time to record " + timeForAdd * TOSECONDS);

            start = System.nanoTime();
            for (Integer i = 0; i < N; i++) {
                MyData retrievedData = DbController.find(myDatabase, myClassDb, Integer.toString(i));
            }
            finish = System.nanoTime();
            long timeForAll = finish - start;
            System.out.print("\n\nTime to find " + timeForAll * TOSECONDS);

            start = System.nanoTime();
            for (Integer i = 0; i < N; i++) {
                DbController.delete(myDatabase, Integer.toString(i));
            }
            finish = System.nanoTime();
            timeForAll = finish - start;
            System.out.println("\n\nTime to delete " + timeForAll * TOSECONDS);

            DbController.close(myDatabase, myClassDb, myDbEnvironment);

        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }

    }

}