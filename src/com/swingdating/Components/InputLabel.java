package com.swingdating.Components;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.swingdating.System.AppDesign;

public class InputLabel extends JLabel {
    public InputLabel(String label, AppDesign appdesign) {
        this(label, appdesign, new Insets(0, 0, 0, 0));
    }
    public InputLabel(String label, AppDesign appdesign, Insets margin) {
        super(label);
        setFont(new Font("Roboto Bold", Font.PLAIN, 16));
        setForeground(appdesign.Color_FontPrimary);
        setBorder(BorderFactory.createEmptyBorder(margin.top, margin.left, margin.bottom, margin.right));
    }

}
