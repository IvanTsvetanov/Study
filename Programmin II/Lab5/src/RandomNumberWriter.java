import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.io.RandomIO;

import java.io.*;
import java.util.Random;

/*
Writes random integers to files using byte-based & char-based streams.

In answer to the question:
Q: "Compare the file sizes created by the two different approaches."
A: The file size of the byte stream is SMALLER than the file size of the char stream.
That is because when we use the byte-based writer, we store the information added as bytes, not as chars.
And the size of an int stored as bytes is smaller than the size of an int stored as a char.
 */

public class RandomNumberWriter implements RandomIO {

    //Used to seed the object's random number generator.
    private long seed;

    //Used to generate a random number using the seed.
    private Random random = new Random();

    //Default Constructor.
    public RandomNumberWriter() {}

    //Custom Constructor which sets the seed.
    public RandomNumberWriter(long seed) {
        this.seed = seed;
        random.setSeed(seed);
    }

    //Writes 10 000 random integers to a file using a char-based stream.
    @Override
    public void writeRandomChars(String fileName) throws IOException {
        //Get the file ready.
        File charFile = new File(fileName);

        //Set up the writer.
        Writer writer = new FileWriter(charFile);

        //Generate the numbers and write them.
        String valueHolder;

        for(int i = 0; i < 10000; i++) {
            //Converts the integer to a String and stores it.
            valueHolder = Integer.toString(random.nextInt(100000));

            //Writes to the file.
            writer.write(valueHolder + "\n");
        }

        //Close the stream.
        writer.close();
    }

    //Writes 10 000 random integers to a file using a byte-based stream.
    @Override
    public void writeRandomByte(String fileName) throws IOException {
        //Get the file ready.
        File byteFile = new File(fileName);

        //Set up the stream.
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(byteFile));

        //Generate the numbers and write them.
        for(int i = 0; i < 10000; i++) {
            //The method writes an int to the underlying output stream as four bytes.
            outputStream.writeInt(random.nextInt(100000));
        }

        //Close the stream.
        outputStream.close();
    }

    public static void main(String[] args) {
        RandomNumberWriter randomNumberWriter = new RandomNumberWriter(1);
        try {
            randomNumberWriter.writeRandomByte("ByteFile.txt");
            randomNumberWriter.writeRandomChars("CharFile.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
