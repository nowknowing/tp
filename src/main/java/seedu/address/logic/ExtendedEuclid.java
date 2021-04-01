package seedu.address.logic;

//FIRST METHOD FROM https://introcs.cs.princeton.edu/java/99crypto/ExtendedEuclid.java.html

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

    public static int[] findMinPosXNegY(int a, int b, int c, int u, int v, int d) {
        int ap = a / d;
        int bp = b / d;
        int cp = c / d;
        int t;
        if (d > 0) {
            t = (int) Math.max(
                    Math.ceil((double) v * (double) cp / (double) ap),
                    Math.ceil((1 - (double) u * (double) cp) / (double) bp));
        } else {
            t = (int) Math.max(
                    Math.floor((double) v * (double) cp / (double) ap),
                    Math.floor((1 - (double) u * (double) cp) / (double) bp));
        }
        int x = bp * t + u * cp;
        int negY = ap * t - v * cp;
        assert(a * x - b * negY == c);
        return new int[]{x, negY};
    }
}
