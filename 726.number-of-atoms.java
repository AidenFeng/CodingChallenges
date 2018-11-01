/*
 * [726] Number of Atoms
 *
 * https://leetcode.com/problems/number-of-atoms/description/
 *
 * algorithms
 * Hard (43.69%)
 * Total Accepted:    7.6K
 * Total Submissions: 17.5K
 * Testcase Example:  '"H2O"'
 *
 * Given a chemical formula (given as a string), return the count of each
 * atom.
 * 
 * An atomic element always starts with an uppercase character, then zero or
 * more lowercase letters, representing the name.
 * 
 * 1 or more digits representing the count of that element may follow if the
 * count is greater than 1.  If the count is 1, no digits will follow.  For
 * example, H2O and H2O2 are possible, but H1O2 is impossible.
 * 
 * Two formulas concatenated together produce another formula.  For example,
 * H2O2He3Mg4 is also a formula.  
 * 
 * A formula placed in parentheses, and a count (optionally added) is also a
 * formula.  For example, (H2O2) and (H2O2)3 are formulas.
 * 
 * Given a formula, output the count of all elements as a string in the
 * following form: the first name (in sorted order), followed by its count (if
 * that count is more than 1), followed by the second name (in sorted order),
 * followed by its count (if that count is more than 1), and so on.
 * 
 * Example 1:
 * 
 * Input: 
 * formula = "H2O"
 * Output: "H2O"
 * Explanation: 
 * The count of elements are {'H': 2, 'O': 1}.
 * 
 * 
 * 
 * Example 2:
 * 
 * Input: 
 * formula = "Mg(OH)2"
 * Output: "H2MgO2"
 * Explanation: 
 * The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.
 * 
 * 
 * 
 * Example 3:
 * 
 * Input: 
 * formula = "K4(ON(SO3)2)2"
 * Output: "K4N2O14S4"
 * Explanation: 
 * The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.
 * 
 * 
 * 
 * Note:
 * All atom names consist of lowercase letters, except for the first character
 * which is uppercase.
 * The length of formula will be in the range [1, 1000].
 * formula will only consist of letters, digits, and round parentheses, and is
 * a valid formula as defined in the problem.
 * 
 */
class Solution {
    private int i; // Use member viriable to share the same i in different levels

    public String countOfAtoms(String formula) {
        StringBuilder ans = new StringBuilder(); // For building the answer
        i = 0;
        Map<String, Integer> countsMap = countOfAtoms(formula.toCharArray()); // the mapping of the results
        // For the answer
        for (String name : countsMap.keySet()) {
            ans.append(name);
            int count = countsMap.get(name);
            if (count > 1)
                ans.append("" + count);
        }
        return ans.toString(); // From StringBuilder -> String
    }

    private Map<String, Integer> countOfAtoms(char[] f) {
        Map<String, Integer> formulaMap = new TreeMap<String, Integer>(); // Tree-map: Sort based on atom's name
        while (i != f.length) {
            if (f[i] == '(') { // -> Next Recursion
                ++i;
                Map<String, Integer> tmp = countOfAtoms(f); // Recursion to the next level
                int count = getCount(f); // the first count after "()" -> ()*k
                for (Map.Entry<String, Integer> entry : tmp.entrySet()) {
                    formulaMap.put(entry.getKey(), formulaMap.getOrDefault(entry.getKey(), 0) // get existed Count
                            + entry.getValue() * count);
                }
            } else if (f[i] == ')') {
                ++i;
                return formulaMap;
            } else { // Name
                String name = getName(f);
                formulaMap.put(name, formulaMap.getOrDefault(name, 0) // get existed Count
                        + getCount(f));
            }
        }
        return formulaMap;
    }

    private String getName(char[] f) {
        String name = "" + f[i++];
        while (i < f.length && 'a' <= f[i] && f[i] <= 'z') {
            name += f[i++];
        }
        return name;
    }

    private int getCount(char[] f) {
        int count = 0;
        while (i < f.length && '0' <= f[i] && f[i] <= '9') {
            count = count * 10 + (f[i] - '0');
            ++i;
        }
        return count == 0 ? 1 : count;
    }
}
