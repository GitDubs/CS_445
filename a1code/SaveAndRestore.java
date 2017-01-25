import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * An demonstration of saving and restoring Serializable objects
 */
public class SaveAndRestore {
    /* The data that will be saved and restored */
	ToyData myData = null;

    /**
     * Attempts to restore from a previous save
     * @param fileName The filename of the save
     * @return true on success, false on failure
     */
	boolean restore(String filename) {
		try {
			ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(filename));
			myData = (ToyData)restoreStream.readObject();
		}
		catch(FileNotFoundException e) {
			System.err.println(filename + " does not exist.");
			return false;
		}
		catch(IOException e) {
			System.err.println("Error restoring from " + filename);
			return false;
		}
		catch(ClassNotFoundException e) {
			System.err.println("Error restoring from " + filename);
			return false;
		}
        return true;
    }

    /**
     * Attempts to save the myData object to a file
     * @param fileName The filename of the save
     */
    void save(String filename) {
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            saveStream.writeObject(myData);
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Generates new data for the myData object
     */
    void newData() {
        myData = new ToyData();
    }

    /**
     * A simple toString that simply returns myData's string
     */
    public String toString() {
        return myData.toString();
    }

    /**
     * A test main to demonstrate the usage of test() and restore()
     */
    public static void main(String[] args) {
        String testFile = "test.bin";
        SaveAndRestore test = new SaveAndRestore();
        if(test.restore(testFile)) {
            System.out.println("Restored from " + testFile + ", found:");
            System.out.println(test);
        } else {
            System.out.println("Generating new data:");
            test.newData();
            System.out.println(test);
            System.out.println("Saving data...");
            test.save(testFile);
        }
    }

    /**
     * A toy class that will be saved and restored. Note that this class AND ALL OF ITS DATA MEMBERS
     * must implement Serializable in order to be written with ObjectOutputStream.
     */
    static class ToyData implements Serializable {
        /* The contents of the toy data, a simple array */
        int[] contents;

        /**
         * A constructor that generates 10 random integers 0-100
         */
        ToyData() {
            contents = new int[10];
            Random rand = new Random();

            for(int i = 0 ; i < contents.length; i++) {
                contents[i] = rand.nextInt(100);
            }
        }

        /**
         * Convert to string by appending each integer in the contents array
         */
        public String toString() {
            String ret = "";
            for(int i : contents) {
                ret += i + " ";
            }
            return ret;
        }
    }
}

