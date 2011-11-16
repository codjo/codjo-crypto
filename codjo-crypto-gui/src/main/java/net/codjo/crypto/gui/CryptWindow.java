package net.codjo.crypto.gui;
import net.codjo.crypto.common.StringEncrypter;
import net.codjo.crypto.common.StringEncrypterException;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 */
public class CryptWindow {
    private JLabel keyLabel;
    private JTextField keyField;
    private JTextArea inputText;
    private JTextArea resultText;
    private JButton encryptButton;
    private JButton decryptButton;
    private JPanel mainPanel;


    public CryptWindow() {
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                encrypt();
            }
        });
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                decrypt();
            }
        });
    }


    private void encrypt() {
        try {
            resultText.setText(new StringEncrypter(keyField.getText()).encrypt(inputText.getText()));
        }
        catch (StringEncrypterException exception) {
            JOptionPane.showMessageDialog(mainPanel, exception.getLocalizedMessage(),
                                          "Erreur de cryptage",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }


    private void decrypt() {
        try {
            resultText.setText(new StringEncrypter(keyField.getText()).decrypt(inputText.getText()));
        }
        catch (StringEncrypterException exception) {
            JOptionPane.showMessageDialog(mainPanel, exception.getLocalizedMessage(),
                                          "Erreur de décryptage",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public String getKey() {
        return keyField.getText();
    }


    public String getInputText() {
        return inputText.getText();
    }


    public String getResultText() {
        return resultText.getText();
    }


    public void setKey(String key) {
        keyField.setText(key);
        boolean showKeyField = (key == null || "".equals(key));
        keyLabel.setVisible(showKeyField);
        keyField.setVisible(showKeyField);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Cryptage de chaînes de caractères");
        frame.setContentPane(new CryptWindow().getMainPanel());
        frame.setPreferredSize(new Dimension(500, 300));
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
