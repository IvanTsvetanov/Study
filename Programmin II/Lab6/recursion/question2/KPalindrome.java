import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.recursion.PalindromeChecker;

//Checks if a Word is a k-palindrome.
public class KPalindrome implements PalindromeChecker {

    /**
     * Checks if a word in a k-palindrome.
     * @param word - the String to be checked.
     * @param k - number of letters to be removed.
     * @return true if the input String is a k-palindrome.
     */
    @Override
    public boolean isKPalindrome(String word, int k) {
        String reversedWord = word;
        reversedWord = reverse(reversedWord);
        int wordLength = word.length();

        //Check if the word (and reversedWord) is a k-palindrome.
        return (isKPalRec(word, reversedWord, wordLength, wordLength) <= k * 2);
    }

    static int isKPalRec(String word, String reversedWord, int m, int n)
    {
        if (m == 0) {
            return n;
        }

        if (n == 0) {
            return m;
        }

        if (word.charAt(m - 1) ==
                reversedWord.charAt(n - 1)) {
            return isKPalRec(word, reversedWord,
                    m - 1, n - 1);
        }

        return 1 + Math.min(isKPalRec(word, reversedWord, m - 1, n),
                isKPalRec(word, reversedWord, m, n - 1));
    }

    //Reverses a String
    static String reverse(String input)
    {
        StringBuilder input1 = new StringBuilder();

        //Append a string into StringBuilder input1.
        input1.append(input);

        //Reverse StringBuilder input1.
        input1 = input1.reverse();

        //Return the reversed input.
        return String.valueOf(input1);
    }
}
