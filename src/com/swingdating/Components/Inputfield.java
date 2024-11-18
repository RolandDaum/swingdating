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
import java.util.function.Consumer;

public class InputField extends JPanel {

    private Consumer<String> onType;    // Changed to Consumer<String>
    private Runnable onActive;
    private Runnable onInactive;
    private Consumer<String> onSubmit;

    private JTextField inputfield;
    private JPasswordField inputfieldPassword;

    private boolean invalidValue = false;

    private boolean numbersonly = false;
    private int minNumber = 0;
    private int maxNumber = 0;

    private AppDesign appdesign;

    private Dimension preferedSize;

    public InputField(AppDesign appdesign) {
        this(appdesign, false, null, false);
    }
    public InputField(AppDesign appdesign, String tooltip) {
        this(appdesign, false, tooltip, false);
    }
    public InputField(AppDesign appdesign, String tooltip, boolean numbersonly) {
        this(appdesign, false, tooltip, numbersonly);
    }
    public InputField(AppDesign appdesign, String tooltip, boolean numbersonly, Dimension preferedSize) {
        this(appdesign, false, tooltip, numbersonly, preferedSize);
    }
    public InputField(AppDesign appdesign, Boolean passwordfield, String tooltip) {
        this(appdesign, passwordfield, tooltip, false);
    }
    public InputField(AppDesign appdesign, Boolean passwordfield, String tooltip, boolean numbersonly) {
        this(appdesign, passwordfield, tooltip, numbersonly, new Dimension(appdesign.inputFieldWidth, appdesign.inputFieldHeight));
    }
    public InputField(AppDesign appdesign, Boolean passwordfield, String tooltip, boolean numbersonly, Dimension preferedSize) {
        this.appdesign = appdesign;
        this.numbersonly = numbersonly;
        this.preferedSize = preferedSize;
   
        setLayout(null);
        setOpaque(false);
        setPreferredSize(preferedSize);
        setSize(getPreferredSize());

        if (passwordfield) {
            inputfieldPassword = new JPasswordField() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(appdesign.Color_BackgroundContainer);
                    g2.fillRoundRect(0, 0, preferedSize.width-(appdesign.BorderThickness*2), preferedSize.height-(appdesign.BorderThickness*2), appdesign.BorderRadiusComponents-appdesign.BorderThickness, appdesign.BorderRadiusComponents-appdesign.BorderThickness);
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
                    g2.fillRoundRect(0, 0, preferedSize.width-(appdesign.BorderThickness*2), preferedSize.height-(appdesign.BorderThickness*2), appdesign.BorderRadiusComponents-appdesign.BorderThickness, appdesign.BorderRadiusComponents-appdesign.BorderThickness);
                    g2.dispose();
                    super.paintComponent(g);
                }};
        }
        // Set incognito char for password field
        if (passwordfield) {
            inputfieldPassword.setEchoChar(appdesign.PasswordFiledChar);
        }
        
        inputfield.setToolTipText(tooltip);
        inputfield.setOpaque(false);
        // inputfield.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputfield.setPreferredSize(new Dimension(preferedSize.width-2, preferedSize.height-2));
        inputfield.setBorder(BorderFactory.createEmptyBorder(0, preferedSize.height/3, 0, preferedSize.height/3)); // Inner padding
        inputfield.setForeground(appdesign.Color_FontPrimary);
        inputfield.setFont(appdesign.fonts.get("Roboto Bold").deriveFont(16f));
        inputfield.setCaretColor(appdesign.Color_FontPrimary);
        inputfield.setBounds(appdesign.BorderThickness, appdesign.BorderThickness, getWidth()-(appdesign.BorderThickness*2), getHeight()-(appdesign.BorderThickness*2));


        // on insert/remove/update Listener
        inputfield.getDocument().addDocumentListener(new DocumentListener() {          
            @Override
            public void insertUpdate(DocumentEvent e) {
                inputTypeChecker();
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
        // super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(invalidValue ? appdesign.Color_AccentSecondary : appdesign.Color_BorderLight); // This changes the bordre color
        g2.fillRoundRect(0, 0, preferedSize.width, preferedSize.height, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
        g2.dispose();
    }

    private void inputTypeChecker() {
        String value = inputfield.getText();
        if (!this.numbersonly || value.isEmpty()) {return;}
        try {
            Integer.parseInt(value);
        } catch (Exception error) {
            // Must run after the "insertUpdate" event finished else there will be an error.
            SwingUtilities.invokeLater(() -> {
                inputfield.setText(value.substring(0, value.length()-1));
                repaint();
            });
            return;
        }
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
    public void setValue(String value) {
        inputfield.setText(value);
    }
    public void setFocus() {
        inputfield.requestFocus();
    }
    public void removeFocus() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();   
    }
    public void toggleInvalidValue() {
        invalidValue = !invalidValue;
        repaint();
    }
    public void setInvalidValue(boolean invalidValue) {
        this.invalidValue = invalidValue;
        repaint();
    }
}