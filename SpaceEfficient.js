const readline = require("readline");

/**
     * Finds the longest string that is a SUBSEQUENCE of X and a SUBSTRING of Y.
     * Space complexity: O( min(m, n) ) – two rows of length n+1.
     *
     * @param X string treated as the "subsequence" source
     * @param Y string treated as the "substring" source
     * @return the longest common subsequence‑substring, or "-1" if none exists
     */

function longestSubseqSubstr(X, Y) {
    const m = X.length;
    const n = Y.length;

    let maxLen = 0;
    let endY = 0;

    // Two-row DP table
    let len = [
        new Array(n + 1).fill(0),
        new Array(n + 1).fill(0)
    ];

    let currRow = 0;

    for (let i = 0; i <= m; i++) {
        for (let j = 0; j <= n; j++) {

            if (i === 0 || j === 0) {
                len[currRow][j] = 0;
            } else if (X.charAt(i - 1) === Y.charAt(j - 1)) {

                // Match: extend the diagonal
                len[currRow][j] = len[1 - currRow][j - 1] + 1;

                if (len[currRow][j] > maxLen) {
                    maxLen = len[currRow][j];
                    endY = j - 1;
                }

            } else {
                // Skip character in X
                len[currRow][j] = len[1 - currRow][j];
            }
        }

        // Switch rows
        currRow = 1 - currRow;
    }

    if (maxLen === 0) {
        return "-1";
    }

    return Y.substring(endY - maxLen + 1, endY + 1);
}

// ----------------------
// Test Cases
// ----------------------
console.log("Longest subsequence of X and substring of Y:");
console.log(longestSubseqSubstr("GeeksforGeeks", "GeeksQuiz")); // Geeks
console.log(longestSubseqSubstr("ABCDGH", "ACDGHR"));
console.log(longestSubseqSubstr("abc", "xyz"));
console.log(longestSubseqSubstr("HELLO", "ELLO"));
console.log(longestSubseqSubstr("short", "longershortexample"));

// ----------------------
// Interactive Input
// ----------------------
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function askX() {
    rl.question("\nEnter string X (or type 'quit' to exit): ", (x) => {
        if (x.toLowerCase() === "quit") {
            rl.close();
            return;
        }

        rl.question("Enter string Y (or type 'quit' to exit): ", (y) => {
            if (y.toLowerCase() === "quit") {
                rl.close();
                return;
            }

            const result = longestSubseqSubstr(x, y);
            console.log(`Result: ${result}`);

            askX();
        });
    });
}

askX();