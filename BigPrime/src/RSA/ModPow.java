package RSA;

import java.math.BigInteger;

public class ModPow {

    //visszaküldi a ((base^exp) mod (mod)) értéket
    public BigInteger mod_power(BigInteger base, BigInteger exp, BigInteger mod){
        BigInteger modpow = new BigInteger("1");

        String binary = to_binary(exp);
        int k = 0;

        while (binary.length() > k){
            if((binary.charAt(binary.length()-1-k)) == '1'){
                modpow = modpow.multiply(base).mod(mod);
            }
            k++;
            base = base.multiply(base).mod(mod);
        }

        return modpow;
    }

    //Átalakítja bináris string-é a value értéket.
    public String to_binary(BigInteger value){
        return value.toString(2);
    }

}
