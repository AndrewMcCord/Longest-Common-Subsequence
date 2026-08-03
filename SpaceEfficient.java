import javax.swing.JOptionPane;

public class SpaceEfficient {

    /**
     * Finds the longest string that is a subsequence of X and a substring of Y.
     * Space complexity: O( min(m, n) ) – two rows of length n+1.
     *
     * @param X string treated as the "subsequence" source
     * @param Y string treated as the "substring" source
     * @return the longest common subsequence‑substring, or "-1" if one doesn't exist
     */
    public static String longestSubseqSubstr(String X, String Y) {
    
        int m = X.length(); // length of string x
        int n = Y.length(); // length of string y

        int maxLen = 0;          // length of the optimal string
        int endY = 0;            // ending index (inclusive) of the substring inside Y

        // DP table with two rows: len[0][j] = previous row, len[1][j] = current row
        int[][] len = new int[2][n + 1];
        int currRow = 0;         // 0 or 1

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    len[currRow][j] = 0;
                } else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    // match: extend the diagonal
                    len[currRow][j] = len[1 - currRow][j - 1] + 1;

                    if (len[currRow][j] > maxLen) {
                        maxLen = len[currRow][j];
                        endY = j - 1;        // j-1 because j is 1‑based in DP
                    }
                } else {
                    // mismatch: skip the character in X, keep Y's position
                    len[currRow][j] = len[1 - currRow][j];
                }
            }
            // swap rows for the next i
            currRow = 1 - currRow;
        }

        if (maxLen == 0) {
            return "-1";
        }

        // The optimal string is a substring of Y, ending at endY
        return Y.substring(endY - maxLen + 1, endY + 1);
    }

    // Below is a test of the space-efficient algorithm
    public static void main(String[] args) {
        String X = "GeeksforGeeks";
        String Y = "GeeksQuiz";

        System.out.println("Longest subsequence of X and substring of Y: " +
                longestSubseqSubstr(X, Y));  // Result: "Geeks"

        // Additional tests and outputs
        System.out.println(longestSubseqSubstr("ABCDGH", "ACDGHR"));  // Result: "CDGH" or "ACDG"?
        System.out.println(longestSubseqSubstr("abc", "xyz"));        // Result: "-1"
        System.out.println(longestSubseqSubstr("HELLO", "ELLO"));     // Result: "ELLO"
        System.out.println(longestSubseqSubstr("short", "longershortexample")); // Result: "short"

        // JOptionPane's interactive user input 
        while (true) {
            // JOptionPane prompts the user for string X
            String xUser = JOptionPane.showInputDialog(
                    null,
                    "Enter string X,\nor Cancel / type 'quit' to exit:",
                    "Input X",
                    JOptionPane.QUESTION_MESSAGE
            );

            // JOptionPane breaks on quit message for x
            if (xUser == null || xUser.trim().equalsIgnoreCase("quit")) {
                break;
            }
            
            // JOptionPane prompts the user for string Y
            String yUser = JOptionPane.showInputDialog(
                    null,
                    "Enter string Y,\nor Cancel / type 'quit' to exit:",
                    "Input Y",
                    JOptionPane.QUESTION_MESSAGE
            );

             // JOptionPane breaks on quit message for y
            if (yUser == null || yUser.trim().equalsIgnoreCase("quit")) {
                break;
            }

            // result of space-efficient algorithm for longest common subsequence-substring
            String result = longestSubseqSubstr(xUser, yUser);

            // JOptionPane shows the longest common subsequence-substring result of the user prompt
            JOptionPane.showMessageDialog(
                    null,
                    "Longest subsequence of X and substring of Y:\n" + result,
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}