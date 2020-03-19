import java.lang.reflect.Array;
import java.util.*;


public class Question1 {
    // Driver method to test Map class
    public static void main(String[] args) {
        //Create the new HashMap, which will be implemented using separate chaining.
        HashMap<Integer, ArrayList<Integer>> myMap = new HashMap<>();
        Random rand = new Random();

        int key = 0;
        int value = 0;

        //Add 2000 numbers.
        for (int i = 0; i < 2000; i++) {
            //Generate the 2000 random numbers.
            value = rand.nextInt(100000);

            //Isolate the different digits, in order to calculate the hashing value.
            int firstDigit = (value / 10000);
            int secondDigit = (value / 1000) % 10;
            int thirdDigit = (value / 100) % 10;
            int fourthDigit = (value / 10) % 10;
            int fifthDigit = (value % 10);

            //Add the key and value to the map.
            key = (2 * firstDigit +
                    3 * secondDigit +
                    5 * thirdDigit +
                    7 * fourthDigit +
                    11 * fifthDigit) % 47;

            //Lambda expression to add the integer to its specified ArrayList.
            myMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        //Iterate through the map to see the values.
        int i = 1;
        Integer lastKey = 0;
        ArrayList<Integer> numberOfValues = new ArrayList<>();

        for (java.util.Map.Entry<Integer, ArrayList<Integer>> entry : myMap.entrySet()) {
            Integer myKey = entry.getKey();
            ArrayList<Integer> myValues = entry.getValue();
            for (Integer singleValue : myValues) {
                //Print out everything.
                //The counter is there so we can easily see the number of elements under each key.
                if (lastKey == myKey) {
                    //System.out.println("key : " + myKey + " value : " + singleValue + " number : " + i);
                    i++;
                } else {
                    i = 1;
                    //System.out.println("key : " + myKey + " value : " + singleValue + " number : " + i);
                    lastKey = myKey;
                    i++;
                }
            }
            //System.out.println("Key: " + myKey + " <-> " + "Number of values: " + i);
            numberOfValues.add(i);
        }

        //Display the amount of values under each key, sorted, so we can prove that
        //that there exists a number x between 0 and 46 for which we can find at least 43
        //numbers among the given 2000, whose hash value is exactly x.
        Collections.sort(numberOfValues, Collections.reverseOrder());
        for (Integer number : numberOfValues) {
            System.out.println(number);
        }
    }
}

    