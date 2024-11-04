package com.swingdating.Components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.swingdating.System.AppDesign;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class InputField extends JPanel {

    private Consumer<String> onType;    // Changed to Consumer<String>
    private Runnable onActive;
    private Runnable onInactive;
    private Runnable onSubmit;

    private JTextField inputfield;

    private AppDesign appdesign;

    // TODO: Add multiple constructors
    // TODO: Add input data types
    public InputField(AppDesign appdesign, Boolean passwordfield) {
        this.appdesign = appdesign;
        inputfield = passwordfield ? new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(appdesign.Color_BackgroundContainer);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
                g2.dispose();
                super.paintComponent(g);
            }
        } : new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(appdesign.Color_BackgroundContainer);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
                g2.dispose();
                super.paintComponent(g);
            }};
        
        inputfield.setOpaque(false);
        inputfield.setPreferredSize(new Dimension(appdesign.inputFieldWidth, appdesign.inputFieldHeight));
        inputfield.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        inputfield.setForeground(appdesign.Color_FontPrimary);
        inputfield.setFont(appdesign.fonts.get("Roboto Bold").deriveFont(16f));
        inputfield.setCaretColor(appdesign.Color_FontPrimary);

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

        setLayout(new GridBagLayout());
        setBackground(null);
        setPreferredSize(new Dimension(appdesign.inputFieldWidth + 2, appdesign.inputFieldHeight + 2));
        add(inputfield);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(appdesign.Color_BorderLight);
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
    public void onSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }

    public String getValue() {
        return inputfield.getText();    
    }
}
