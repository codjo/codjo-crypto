package net.codjo.crypto.gui;
import javax.swing.JFrame;
import org.uispec4j.Button;
import org.uispec4j.TextBox;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;
/**
 *
 */
public class CryptWindowTest extends UISpecTestCase {
    private TextBox keyField;
    private TextBox inputText;
    private TextBox resultText;
    private Button encryptButton;
    private Button decryptButton;
    private TextBox keyLabel;
    private CryptWindow cryptWindow;


    public void test_cipher() throws Exception {
        assertTrue(keyField.textIsEmpty());
        assertTrue(inputText.textIsEmpty());
        assertTrue(resultText.textIsEmpty());

        keyField.setText("clef de cryptage");
        inputText.setText("texte à crypter");
        encryptButton.click();
        assertTrue(resultText.textEquals("s81oJPBWfNoB2ozz4pDxvuz9uRBqZf7H"));
    }


    public void test_cryptError() throws Exception {
        keyField.setText("clé de cryptage");
        inputText.setText("texte à crypter");
        WindowInterceptor.init(new Trigger() {
            public void run() throws Exception {
                encryptButton.click();
            }
        }).process(new WindowHandler() {
            @Override
            public Trigger process(Window window) throws Exception {
                window.assertTitleEquals("Erreur de cryptage");
                return window.getButton().triggerClick();
            }
        }).run();
    }


    public void test_decipher() throws Exception {
        keyField.setText("clef de cryptage");
        inputText.setText("s81oJPBWfNoB2ozz4pDxvuz9uRBqZf7H");
        decryptButton.click();
        assertTrue(resultText.textEquals("texte à crypter"));
    }


    public void test_decryptError() throws Exception {
        keyField.setText("clé de cryptage");
        inputText.setText("texte invalide");
        WindowInterceptor.init(new Trigger() {
            public void run() throws Exception {
                decryptButton.click();
            }
        }).process(new WindowHandler() {
            @Override
            public Trigger process(Window window) throws Exception {
                window.assertTitleEquals("Erreur de décryptage");
                return window.getButton().triggerClick();
            }
        }).run();
    }


    public void test_setKey() throws Exception {
        assertEquals("", cryptWindow.getKey());
        assertTrue(keyLabel.isVisible());
        assertTrue(keyField.isVisible());

        cryptWindow.setKey("my key");

        assertEquals("my key", cryptWindow.getKey());
        assertFalse(keyLabel.isVisible());
        assertFalse(keyField.isVisible());

        cryptWindow.setKey(null);
        assertEquals("", cryptWindow.getKey());
        assertTrue(keyLabel.isVisible());
        assertTrue(keyField.isVisible());
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        JFrame frame = new JFrame();
        cryptWindow = new CryptWindow();
        frame.add(cryptWindow.getMainPanel());
        Window window = new Window(frame);
        keyLabel = window.getTextBox("keyLabel");
        keyField = window.getTextBox("keyField");
        inputText = window.getTextBox("inputText");
        resultText = window.getTextBox("resultText");
        encryptButton = window.getButton("encryptButton");
        decryptButton = window.getButton("decryptButton");
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
