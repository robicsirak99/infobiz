package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

    public static ModPow modPow = new ModPow();
    public static Prime prime = new Prime();
    public static Euclid euclid = new Euclid();

    public static void main(String[] args) {

        //két random szám generálása
        BigInteger p = getBigPrime(1024);
        BigInteger q = getBigPrime(1024);
        System.out.println("p: "+p);
        System.out.println("q: "+q);

        //n és Fi(n) kiszámítása
        BigInteger n = p.multiply(q);
        BigInteger Fn = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        System.out.println("n: "+n);
        System.out.println("Fn: "+Fn);

        //e és d kiszámítása
        BigInteger e = getE(Fn,1024);
        euclid.calculate(Fn,e);
        BigInteger d = euclid.getY();
        if(d.compareTo(BigInteger.valueOf(0))==-1) d = d.add(Fn);
        System.out.println("e: "+e);
        System.out.println("d: "+d);

        //lekódolás és visszafejtés
        BigInteger encrypted = Enc(new BigInteger("98765432123456789"), e, n);
        //BigInteger decrypted = Dec(encrypted,d,n);
        BigInteger decrypted = asd(p,q,encrypted,d);
        System.out.println("enc: "+encrypted);
        System.out.println("des: "+decrypted);

    }

    public static BigInteger Enc(BigInteger val, BigInteger e, BigInteger n){
        return modPow.mod_power(val,e,n);
    }

    public static BigInteger Dec(BigInteger val, BigInteger d, BigInteger n){
        return modPow.mod_power(val,d,n);
    }

    //size méretű random prím számot küld vissza
    public static BigInteger getBigPrime(int size){
        while (true) {
            BigInteger rnd = getBigNumber(size);
            if (prime.is_prime(rnd)) {
                return rnd;
            }
        }
    }

    //RSA algoritmushoz generál random e számot
    public static BigInteger getE(BigInteger Fn, int size){
        BigInteger e = getRandomBigInteger(size);
        while (true){
            if(prime.lnko(e,Fn).compareTo(BigInteger.valueOf(1)) == 0)
                break;
            else e = e.add(BigInteger.valueOf(1));
        }
        return e;
    }

    //max size méretű random számot küld vissza
    public static BigInteger getRandomBigInteger(int size) {
        Random rand = new Random();
        BigInteger result = new BigInteger(size, rand);
        return result;
    }

    //size méretű random számot küld vissza
    public static BigInteger getBigNumber(int size){
        BigInteger rnd = new BigInteger(size, new SecureRandom());
        if (rnd.remainder(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0))==0) return rnd.add(BigInteger.valueOf(1));
        else return rnd;
    }

    //kínai maradék
    public static BigInteger asd(BigInteger p, BigInteger q, BigInteger c, BigInteger d){
        BigInteger c1 = modPow.mod_power(c,d.mod(p.subtract(BigInteger.valueOf(1))),p);
        BigInteger c2 = modPow.mod_power(c,d.mod(q.subtract(BigInteger.valueOf(1))),q);
        BigInteger M = p.multiply(q);
        BigInteger M1 = q;
        BigInteger M2 = p;
        euclid.calculate(q,p);
        BigInteger y1 = euclid.getX();
        BigInteger y2 = euclid.getY();

        BigInteger m = c1.multiply(y1).multiply(M1).add(c2.multiply(y2).multiply(M2)).mod(M);

        return m;
    }

}
