package com.swingdating.Components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.swingdating.System.AppDesign;

public class CheckBoxFHP extends JPanel {
    private JCheckBox checkBox;
    private String label;
    private AppDesign appdesign;

    public CheckBoxFHP(AppDesign appdesign, String label, Insets paddingInsets) {
        this.appdesign = appdesign;
        this.label = label;

        setLayout(new BorderLayout());
        setBackground(appdesign.Color_BackgroundMain);
        setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        setOpaque(false);
        setMaximumSize(new Dimension(175, 100));

        checkBox = new JCheckBox(label);
        checkBox.setBackground(appdesign.Color_BackgroundContainer);
        checkBox.setForeground(appdesign.Color_FontPrimary);
        checkBox.setFont(new Font("Roboto Bold", Font.PLAIN, 16)); 
        checkBox.setFocusPainted(false); 

        checkBox.setBorder(BorderFactory.createEmptyBorder(paddingInsets.top, paddingInsets.left, paddingInsets.bottom, paddingInsets.right));
        checkBox.setIcon(new RoundedCheckBoxIcon(appdesign, false)); // Setze das ungeprüfte Icon
        checkBox.setSelectedIcon(new RoundedCheckBoxIcon(appdesign, true)); 
        add(checkBox, BorderLayout.CENTER);
        
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        g2.setColor(appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        g2.setColor(appdesign.Color_BorderLight);
        g2.setStroke(new BasicStroke(appdesign.BorderThickness));
        g2.drawRoundRect(appdesign.BorderThickness/2, appdesign.BorderThickness/2, getWidth()- appdesign.BorderThickness, getHeight() - appdesign.BorderThickness, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        super.paintComponent(g2);
        g2.dispose();
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }
    public void setSelected(boolean selected) {
        checkBox.setSelected(selected);
    }
    public void toggle() {
        checkBox.setSelected(!checkBox.isSelected());
    }
    public void addActionListener(ActionListener listener) {
        checkBox.addActionListener(listener);
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
        checkBox.setText(label); // Update das Label der Checkbox
    }
}

class RoundedCheckBoxIcon implements Icon {
    private final int size = 20; // Größe der Checkbox
    private final boolean selected; // Status der Checkbox (ausgewählt oder nicht)
    private AppDesign appdesign;
    public RoundedCheckBoxIcon(AppDesign appdesign, boolean selected) {
        this.appdesign = appdesign;
        this.selected = selected;
    }
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(appdesign.Color_BackgroundOnContainer);
        g2d.fillRoundRect(x, y, size, size, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);

        g2d.setColor(selected ? appdesign.Color_AccentPrimary : appdesign.Color_BorderLight);
        g2d.setStroke(selected ? new BasicStroke(0) : new BasicStroke(2));
        g2d.drawRoundRect(x, y, size, size, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);

        if (selected) {
            g2d.setColor(appdesign.Color_AccentPrimary); // Die Farbe, wenn sie ausgewählt ist
            g2d.fillRoundRect(x , y , size, size, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
        }
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}