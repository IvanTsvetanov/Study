import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.io.ConcatenateJavaFiles;

import java.io.*;

/*
Concatenates several Java files into one.
 */

public class JavaFileUtil implements ConcatenateJavaFiles {

    //Used to write and read to/from files.
    private BufferedReader fileReader;
    private BufferedWriter fileWriter;

    //Concatenates all Java file (from dirName) into a single file (fileName).
    @Override
    public void concatenateJavaFiles(String dirName, String fileName) throws IOException {
        //Get the directory files ready.
        try {
            File directoryPath = new File(dirName);

            //Check if the given directory for the ".java" files is valid.
            if(directoryPath.isDirectory() == false) {
                throw new IllegalArgumentException();
            }

            File holderPath = new File(dirName + File.separator + fileName);

            //Get all files that end with ".java".
            File[] javaFiles = directoryPath.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".java");
                }
            });

            //Get the file writer ready
            fileWriter = new BufferedWriter(new FileWriter(holderPath));

            //Read each ".java" file and concatenate it to the main file (fileName).
            for (File javaFile : javaFiles) {
                fileReader = new BufferedReader(new FileReader(javaFile));

                //Write each line from the ".java" files into the new file.
                String line;
                while ((line = fileReader.readLine()) != null) {
                    fileWriter.write(line);
                    fileWriter.write("\n");
                }
                //Close the file which we read from.
                fileReader.close();
            }
            //Close the writer stream.
            fileWriter.close();
        }
        catch (IllegalArgumentException exception) {
            System.out.println("The entered argument for a directory is not valid!");

            //Rethrowing the exception.
            throw exception;
        }
    }
}
