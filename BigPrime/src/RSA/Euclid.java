package RSA;

import java.math.BigInteger;

public class Euclid {

    public BigInteger x;
    public BigInteger y;

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    //     | RKm1 | RK |      |                | a |  b  | - |
    //     |  -   | QK |      |       ->       | - | a/b | - |
    //     | XKm1 | XK | XKp1 |                | 1 |  0  | - |
    //     | YKm1 | YK | YKp1 |                | 0 |  1  | - |

    public void calculate(BigInteger a, BigInteger b){

        BigInteger K = BigInteger.valueOf(1);
        BigInteger RKm1 = a;
        BigInteger RK = b;
        BigInteger QK = a.divide(b);
        BigInteger XKm1, XK, XKp1;
        BigInteger YKm1, YK, YKp1;

        BigInteger temp;

        XKm1 = BigInteger.valueOf(1);
        XK = BigInteger.valueOf(0);
        YKm1 = BigInteger.valueOf(0);
        YK = BigInteger.valueOf(1);

        while (true){
            K = K.add(BigInteger.valueOf(1));

            XKp1 = XK.multiply(QK).add(XKm1);
            YKp1 = YK.multiply(QK).add(YKm1);

            temp = RK;
            RK = RKm1.mod(RK);
            RKm1 = temp;

            QK = RKm1.divide(RK);

            XKm1 = XK; XK = XKp1;
            YKm1 = YK; YK = YKp1;

            if(RK.compareTo(BigInteger.valueOf(1))==0) break;
        }

        x = BigInteger.valueOf(-1).pow(K.intValue()).multiply(XK);
        y = BigInteger.valueOf(-1).pow(K.intValue()+1).multiply(YK);
    }

}
