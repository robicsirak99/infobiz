package RSA;

import java.math.BigInteger;

//Miller-Rabin prímtesztet végez
public class Prime {

    public boolean is_prime(BigInteger n){
        ModPow modPow = new ModPow();

        BigInteger mod_pow;

        int S = calc_S(n); //kiszámítjuk az S-t
        BigInteger d = calc_d(n, S); //kiszámítjuk a d-t
        int a = 1; //az eslő bázist az 1-es szám után keressük

        //8 bázisra tesztelünk
        outerloop:
        for (int i=1; i<=8; i++){
            //kiválasztjuk a következő bázist
            a = get_nextbase(n,a);

            if(modPow.mod_power(BigInteger.valueOf(a),d,n).compareTo(BigInteger.valueOf(1)) == 0){
              break outerloop;
            } else {
                mod_pow = modPow.mod_power(BigInteger.valueOf(a), d.multiply(power(BigInteger.valueOf(2),BigInteger.valueOf(0))), n);
                for (int r=0; r<=S-1; r++){
                    //a^(d∗(2^r)) == n-1 (mod n)
                    if(mod_pow.compareTo(n.subtract(BigInteger.valueOf(1)))==0){
                        break outerloop;
                    }
                    mod_pow = mod_pow.multiply(mod_pow).mod(n);
                }
                return false;
            }
        }
        return true;
    }

    public int get_nextbase(BigInteger n, int previousbase){
        int pot_base = previousbase + 1;
        while (true){
            if(lnko(n,BigInteger.valueOf(pot_base)).compareTo(BigInteger.valueOf(1)) == 0){
                break;
            } else pot_base++;
        }
        return pot_base;
    }

    public BigInteger lnko(BigInteger a, BigInteger b){
        if (a.compareTo(BigInteger.valueOf(0)) == 0) return b;
        if (b.compareTo(BigInteger.valueOf(0)) == 0) return a;
        if (a.compareTo(b) == 1) return lnko(a.mod(b),b);
        else return lnko(a, b.mod(a));
    }

    //S = max{r : 2^r|(n−1)}
    public int calc_S(BigInteger n){
        int r = 0;
        do { r++; } while (n.subtract(BigInteger.valueOf(1)).mod(power(BigInteger.valueOf(2),BigInteger.valueOf(r))).longValue() == 0);

        return --r;
    }

    // d = (n−1)/2^S
    public BigInteger calc_d(BigInteger n, int S){
        return n.subtract(BigInteger.valueOf(1)).divide(power(BigInteger.valueOf(2),BigInteger.valueOf(S)));
    }


    public BigInteger power(BigInteger a, BigInteger b){
        return a.pow((b.intValue()));
    }

}
