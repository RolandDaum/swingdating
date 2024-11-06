package com.swingdating.Components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.swingdating.System.AppDesign;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat.Style;
import java.util.function.Consumer;

public class InputField extends JPanel {

    private Consumer<String> onType;    // Changed to Consumer<String>
    private Runnable onActive;
    private Runnable onInactive;
    private Consumer<String> onSubmit;

    private JTextField inputfield;
    private JPasswordField inputfieldPassword;

    private AppDesign appdesign;

    // TODO: Add multiple constructors
    // TODO: Add input data types
    public InputField(AppDesign appdesign, Boolean passwordfield) {
        this.appdesign = appdesign;
   
        setLayout(new GridBagLayout());
        setBackground(null);
        setPreferredSize(new Dimension(appdesign.inputFieldWidth,appdesign.inputFieldHeight));
        setMaximumSize(new Dimension(appdesign.inputFieldWidth,appdesign.inputFieldHeight));

        if (passwordfield) {
            inputfieldPassword = new JPasswordField() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(appdesign.Color_BackgroundContainer);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            inputfield = inputfieldPassword;
        } else {
            inputfield = new JTextField() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(appdesign.Color_BackgroundContainer);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
                    g2.dispose();
                    super.paintComponent(g);
                }};
        }
        // Set incognito char for password field
        if (passwordfield) {
            inputfieldPassword.setEchoChar(appdesign.PasswordFiledChar);
        }
        
        inputfield.setOpaque(false);
        inputfield.setPreferredSize(new Dimension(appdesign.inputFieldWidth-2, appdesign.inputFieldHeight-2));
        inputfield.setBorder(BorderFactory.createEmptyBorder(0, appdesign.inputFieldHeight/3, 0, appdesign.inputFieldHeight/3)); // Inner padding
        inputfield.setForeground(appdesign.Color_FontPrimary);
        inputfield.setFont(appdesign.fonts.get("Roboto Bold").deriveFont(16f));
        inputfield.setCaretColor(appdesign.Color_FontPrimary);
        inputfield.setBounds(1, 1, getWidth()-2, getHeight()-2);

        // on insert/remove/update Listener
        inputfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (onType != null) {
                    onType.accept(inputfield.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (onType != null) {
                    onType.accept(inputfield.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (onType != null) {
                    onType.accept(inputfield.getText());
                }
            }
        });
        // On focus gained/lost Listener
        inputfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (onActive != null) {
                    onActive.run();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (onInactive != null) {
                    onInactive.run();
                }
            }
        });
        // On Submit Listener
        inputfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onSubmit != null) {
                    onSubmit.accept(inputfield.getText());
                }
            }
        });


        add(inputfield);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(appdesign.Color_BorderLight); // This changes the bordre color
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
        g2.dispose();

    }

    // Methoden zum Setzen der Events
    public void onType(Consumer<String> onType) { // Change method signature
        this.onType = onType;
    }
    public void onActive(Runnable onActive) {
        this.onActive = onActive;
    }
    public void onInactive(Runnable onInactive) {
        this.onInactive = onInactive;
    }
    public void onSubmit(Consumer<String> onSubmit) {
        this.onSubmit = onSubmit;
    }
    
    public String getValue() {
        return inputfield.getText();    
    }
}
