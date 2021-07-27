package by.tarasiuk.ct.util;

import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.bouncycastle.util.encoders.Hex;

public class BouncyCastle {
    private static final WhirlpoolDigest messageDigest = new WhirlpoolDigest();
    private BouncyCastle() {
    }

    public static String encoding(String value) {
        messageDigest.reset();
        final byte[] bytes = value.getBytes();
        messageDigest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[messageDigest.getDigestSize()];
        messageDigest.doFinal(hash, 0);

        return Hex.toHexString(hash).toUpperCase();
    }
}
