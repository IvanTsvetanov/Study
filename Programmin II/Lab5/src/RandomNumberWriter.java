import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.io.RandomIO;

import java.io.*;
import java.util.Random;

/*
Writes random integers to files using byte-based & char-based streams.
Compares the sizes of the files.

In answer to the question:
Q: "Compare the file sizes created by the two different approaches."
A: The file sizes are the same, as expected.
 */

public class RandomNumberWriter implements RandomIO {

    //Used to seed the object's random number generator.
    private long seed;

    //Used to generate a random number using the seed.
    private Random random = new Random(seed);

    //Default Constructor.
    public RandomNumberWriter() {}

    //Custom Constructor which sets the seed.
    public RandomNumberWriter(long seed) {
        this.seed = seed;
    }

    //Writes 10 000 random integers to a file, using a char-based stream.
    @Override
    public void writeRandomChars(String s) throws IOException {
        //Get the file ready.
        File charFile = new File(s);

        //Set up the writer.
        Writer writer = new FileWriter(charFile);

        //Generate the numbers and write them.
        String valueHolder;

        for(int i = 0; i < 10000; i++) {
            //Converts the integer to a String and stores it.
            valueHolder = Integer.toString(random.nextInt(100000));

            //Writes to the file.
            writer.write(valueHolder);
        }

        //Close the stream.
        writer.close();
    }

    //Writes 10 000 random integers to a file, using a byte-based stream.
    @Override
    public void writeRandomByte(String s) throws IOException {
        //Get the file ready.
        File byteFile = new File(s);

        //Set up the stream.
        OutputStream outputStream = new FileOutputStream(byteFile);

        //Generate the numbers and write them.
        byte[] bytesHolder;
        String valueHolder;

        for(int i = 0; i < 10000; i++) {
            //Converts the integer to a String and stores it.
            valueHolder = Integer.toString(random.nextInt(100000));

            //Gets the bytes and writes them to the file.
            bytesHolder = valueHolder.getBytes();
            outputStream.write(bytesHolder);
        }

        //Close the stream.
        outputStream.close();
    }
}
