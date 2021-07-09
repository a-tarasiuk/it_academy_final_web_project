package by.tarasiuk.ct.util;

import by.tarasiuk.ct.exception.CryptException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AesCrypt {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AesCrypt instance = new AesCrypt();
    private static final String VECTOR = "ItsIsOurBigSecret";
    private static final String KEY = "Sd@fGsAsd2Sq@4S3";

    private AesCrypt() {
    }

    public static AesCrypt getInstance() {
        return instance;
    }

    public String encrypt(String password) throws CryptException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            return new String(Hex.decodeHex(password.toCharArray()));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | DecoderException e) {
            LOGGER.error("Cant' create a crypt.");
            throw new CryptException("Can't create a crypt.", e);
        }
    }

    public String decrypt(String password) throws CryptException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            return new String(cipher.doFinal(Hex.decodeHex(password.toCharArray())));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | DecoderException e) {
            LOGGER.error("Cant' create a crypt.");
            throw new CryptException("Can't create a crypt.", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.error("Can't decrypt password.");
            throw new CryptException("Can't decrypt password.", e);
        }
    }
}
