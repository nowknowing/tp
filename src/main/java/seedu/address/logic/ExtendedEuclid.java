package seedu.address.logic;

//CODE FROM https://introcs.cs.princeton.edu/java/99crypto/ExtendedEuclid.java.html

/** Takes two positive arguments p and q and compute the greatest
 *  common divisor of p and q using the extended Euclid's algorithm.
 *  Also prints out integers a and b such that a(p) + b(q) = gcd(p, q).
 *
 */
public class ExtendedEuclid {

    /**
     * Carries out the extended Euclid's algorithm
     * @param p input
     * @param q input
     * @return array [d, a, b] such that d = gcd(p, q), ap + bq = d
     */
    public static int[] gcd(int p, int q) {
        if (q == 0) {
            return new int[]{p, 1, 0};
        }
        int[] vals = gcd(q, p % q);
        int d = vals[0];
        int a = vals[2];
        int b = vals[1] - (p / q) * vals[2];
        return new int[]{d, a, b};
    }
}
