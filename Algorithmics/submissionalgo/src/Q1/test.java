package Q1;

public class test {
    public int[] proc2(int[] array) {
        for(int j = 0; j < array.length - 1; j++) {
            int min = j;
            for(int i = j + 1; i < array.length; i++) {
                if(array[i] < array[min]) {
                    min = i;
                }
            }
            int temp = array[j];
            array[j] = array[min];
            array[min] = temp;
        }
        return array;
    }

}
