package  com.swingdating.Components;
import  com.swingdating.System.AppDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Inputfield extends JTextField {
    private Color borderColor = Color.BLACK;
    private int borderRadius = 20;
    private int fontSize = 14;
    private AppDesign appdesign;

    public Inputfield(AppDesign appdesign) {
        this.appdesign = appdesign;
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, fontSize));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                onInput();
            }
        });
        
        // Listener für Focus-Events
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                onFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {
                onUnfocus();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Hintergrundfarbe und abgerundete Ecken zeichnen
        g2.setColor(appdesign.Color_BackgroundSecondary);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.borderRadius, this.borderRadius);

        // Schriftfarbe festlegen
        setForeground(appdesign.Color_FontPrimary);

        super.paintComponent(g);
        g2.dispose();
    }
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(appdesign.Color_Accent);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, this.borderRadius, this.borderRadius);

        g2.dispose();
    }

    protected void onInput() {
        // System.out.println("Input detected: " + getText());
    }
    protected void onFocus() {
        // System.out.println("Input field focused.");
    }
    protected void onUnfocus() {
        // System.out.println("Input field unfocused.");
    }
}
