public class SpaceEfficient{

    /**
     * Finds the longest common substring between two strings.
     * Uses dynamic programming with O(min(m,n)) space (two rows of size n+1).
     * @param X First string
     * @param Y Second string
     * @return Longest common substring, or "-1" if none exists
     */
    public static String lcSubstr(String X, String Y) {
        int m = X.length();
        int n = Y.length();

        int result = 0;   // length of longest common substring
        int end = 0;      // ending index of the substring in X

        // array size is [2][n+1] so that j can safely go from 0..n
        int[][] len = new int[2][n + 1];
        int currRow = 0;  // 0 or 1

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    len[currRow][j] = 0;
                } else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    len[currRow][j] = len[1 - currRow][j - 1] + 1;

                    if (len[currRow][j] > result) {
                        result = len[currRow][j];
                        end = i - 1;   // position in X
                    }
                } else {
                    len[currRow][j] = 0;
                }
            }
            // Swap rows for the next iteration
            currRow = 1 - currRow;
        }

        if (result == 0) {
            return "-1";
        }
        return X.substring(end - result + 1, end + 1);
    }

    public static void main(String[] args) {
        String X = "GeeksforGeeks";
        String Y = "GeeksQuiz";

        System.out.println("Method 1 (Space-optimized): " + lcSubstr(X, Y));

        System.out.println("\nAdditional Test Cases:");
        System.out.println("Test 1: " + lcSubstr("ABCDGH", "ACDGHR")); // common part e.g. "CDGH"
        System.out.println("Test 2: " + lcSubstr("abc", "xyz"));      // -1
        System.out.println("Test 3: " + lcSubstr("HELLO", "ELLO"));   // ELLO
        System.out.println("Test 4: " + lcSubstr("short", "longershortexample")); // short
    }
}