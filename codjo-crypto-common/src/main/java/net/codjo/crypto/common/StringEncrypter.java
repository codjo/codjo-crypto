package net.codjo.crypto.common;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 *
 */
public class StringEncrypter {

    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final String TRANSFORMATION = "PBEWithMD5AndDES/CBC/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private char[] keyChars;
    private static final byte[] SALT =
          new byte[]{
                (byte)0xa3, (byte)0x21, (byte)0x24, (byte)0x2c,
                (byte)0xf2, (byte)0xd2, (byte)0x3e, (byte)0x19
          };
    private Cipher encryptCipher;
    private Cipher decryptCipher;


    public StringEncrypter(String encryptionKey) throws StringEncrypterException {
        keyChars = encryptionKey.toCharArray();
        initEncrypter();
    }


    public String encrypt(String input) throws StringEncrypterException {
        try {
            byte[] utf8 = input.getBytes(CHARSET_NAME);
            byte[] encrypted = encryptCipher.doFinal(utf8);

            return new String(new BASE64Encoder().encode(encrypted).getBytes(CHARSET_NAME), CHARSET_NAME);
        }
        catch (Exception e) {
            throw new StringEncrypterException("Impossible de crypter la chaine '" + input + "' : "
                                               + e.getMessage());
        }
    }


    public String decrypt(String input) throws StringEncrypterException {
        try {
            byte[] dec = new BASE64Decoder().decodeBuffer(input);
            byte[] utf8 = decryptCipher.doFinal(dec);

            return new String(utf8, CHARSET_NAME);
        }
        catch (Exception e) {
            throw new StringEncrypterException("Impossible de decrypter la chaine '" + input + "' : "
                                               + e.getMessage());
        }
    }


    private void initEncrypter() throws StringEncrypterException {
        try {
            PBEParameterSpec parameterSpec = new PBEParameterSpec(SALT, 20);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = secretKeyFactory.generateSecret(new PBEKeySpec(keyChars));

            encryptCipher = Cipher.getInstance(TRANSFORMATION);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            decryptCipher = Cipher.getInstance(TRANSFORMATION);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        }
        catch (Exception e) {
            throw new StringEncrypterException("Impossible d'initialiser le crypteur : "
                                               + e.getMessage());
        }
    }
}
