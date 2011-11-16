package net.codjo.crypto.common;
import junit.framework.TestCase;
/**
 *
 */
public class StringEncrypterTest extends TestCase {
    private StringEncrypter stringEncrypter;
    static final String TEST_KEY = "clef de cryptage des login/pwd pour client/server";


    public void test_cryptDecrypt() throws StringEncrypterException {
        String input = "mon password";
        String expected = "XyAsnJl7OL/4hLQNjGAEUA==";

        String pwdCrypted = stringEncrypter.encrypt(input);
        assertEquals(expected, pwdCrypted);
        assertEquals(input, stringEncrypter.decrypt(expected));
    }


    public void test_cases() throws Exception {
        checkEncryption("");
        checkEncryption("aaa");
        checkEncryption("&é\"'(-èS€G\\n\\n$éçà\\n'");
        checkEncryption("123456789\n"
                        + "123456789\n"
                        + "123456789\n"
                        + "123456789\n"
                        + "123456789\n"
                        + "123456789");
    }


    public void test_tempo() throws Exception {
        // Si le test ne passe c'est que la clef generique a changé (il faut penser a DELRECO).
        stringEncrypter = new StringEncrypter(TEST_KEY);
        assertEquals("GONNOT", stringEncrypter.decrypt("+wuvepcZoZ0="));
    }


    public void test_decrypt_ko() throws StringEncrypterException {
        String password = "3vncvNgJX9gc1ADGHlVBg==";
        try {
            stringEncrypter.decrypt(password);
            fail("Password indécryptable !");
        }
        catch (StringEncrypterException ex) {
            assertEquals("Impossible de decrypter la chaine '" + password
                         + "' : Given final block not properly padded",
                         ex.getLocalizedMessage());
        }
    }


    @Override
    protected void setUp() throws Exception {
        stringEncrypter = new StringEncrypter("thiskey");
    }


    private void checkEncryption(String string) {
        String encrypted = stringEncrypter.encrypt(string);
        assertEquals(string, stringEncrypter.decrypt(encrypted));
    }
}
