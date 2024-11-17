package com.swingdating.Components;
import com.swingdating.App;
import com.swingdating.System.AppDesign;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Button extends JButton {

    private AppDesign appdesign;
    private boolean isHoverd = false;

    public Button(String title, AppDesign appdesign, Runnable onclick) {
        this(title, appdesign, new Dimension((appdesign.inputFieldWidth/2),appdesign.inputFieldHeight), onclick);
    }
    public Button(String title, AppDesign appdesign, Dimension Dimension, Runnable onclick) {
        super(title);
        this.appdesign = appdesign;

        setMinimumSize(Dimension);
        setMaximumSize(Dimension);
        setPreferredSize(Dimension);

        setLayout(null);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorderPainted(false);
        setForeground(appdesign.Color_FontPrimary);
        setFont(new Font("Roboto Medium", Font.PLAIN, 14));

        // Hover-Effekt
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHoverd = true;
                App.getAppInstance().setCursor(Cursor.HAND_CURSOR);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHoverd = false;
                App.getAppInstance().setCursor(Cursor.DEFAULT_CURSOR);
                repaint();
            }
        });
        
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onclick != null) {
                    onclick.run();
                }

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Hintergrund zeichnen
        g2.setColor(isHoverd ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        // Border zeichnen mit angepasster Dicke
        g2.setColor(appdesign.Color_BorderLight);
        g2.setStroke(new BasicStroke(appdesign.BorderThickness)); // Setze die Strichbreite für den Rahmen
        g2.drawRoundRect(appdesign.BorderThickness/2, appdesign.BorderThickness/2, getWidth()- appdesign.BorderThickness, getHeight() - appdesign.BorderThickness, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
        setForeground(isHoverd ? appdesign.Color_FontSecondary : appdesign.Color_FontPrimary);

        super.paintComponent(g2);
        g2.dispose();
    }
}