/**
 * Finds the longest common substring between two strings
 * Uses dynamic programming with optimized space complexity (O(n))
 * @param {string} X - First string
 * @param {string} Y - Second string
 * @returns {string} - Longest common substring, or "-1" if none exists
 */
function lcSubstr(X, Y) {
  // Find length of both strings
  const m = X.length;
  const n = Y.length;

  // Variable to store length of longest common substring
  let result = 0;

  // Variable to store ending point of longest common substring in X
  let end = 0;

  // Matrix to store result of two consecutive rows at a time
  // Using 2D array to optimize space: we only need current and previous row
  const len = Array(2).fill(null).map(() => Array(m).fill(0));

  // Variable to represent which row of matrix is current row
  let currRow = 0;

  // For a particular value of i and j,
  // len[currRow][j] stores length of longest common substring
  // in String X[0..i] and Y[0..j]
  for (let i = 0; i <= m; i++) {
    for (let j = 0; j <= n; j++) {
      if (i === 0 || j === 0) {
        len[currRow][j] = 0;
      } else if (X.charAt(i - 1) === Y.charAt(j - 1)) {
        len[currRow][j] = len[1 - currRow][j - 1] + 1;

        if (len[currRow][j] > result) {
          result = len[currRow][j];
          end = i - 1;
        }
      } else {
        len[currRow][j] = 0;
      }
    }

    // Make current row as previous row and
    // previous row as new current row
    currRow = 1 - currRow;
  }

  // If there is no common substring, return "-1"
  if (result === 0) {
    return "-1";
  }

  // Longest common substring is from index (end - result + 1) to index end in X
  return X.substring(end - result + 1, end + 1);
}

/**
 * Alternative implementation using Map for better readability
 * Time Complexity: O(m*n), Space Complexity: O(m*n)
 */
function lcSubstrAlt(X, Y) {
  const m = X.length;
  const n = Y.length;

  // Create DP table
  const dp = Array(m + 1).fill(null).map(() => Array(n + 1).fill(0));

  let maxLen = 0;
  let endPos = 0;

  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (X[i - 1] === Y[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1] + 1;

        if (dp[i][j] > maxLen) {
          maxLen = dp[i][j];
          endPos = i;
        }
      }
    }
  }

  return maxLen === 0 ? "-1" : X.substring(endPos - maxLen, endPos);
}

// Driver Code
const X = "GeeksforGeeks";
const Y = "GeeksQuiz";

console.log("Method 1 (Space-optimized):", lcSubstr(X, Y));
console.log("Method 2 (Full DP table):", lcSubstrAlt(X, Y));

// Additional test cases
console.log("\nAdditional Test Cases:");
console.log("Test 1:", lcSubstr("ABCDGH", "ACDGHR")); // Expected: "ACDGH" or "CDG" etc
console.log("Test 2:", lcSubstr("abc", "xyz")); // Expected: "-1"
console.log("Test 3:", lcSubstr("HELLO", "ELLO")); // Expected: "ELLO"