package com.swingdating.Components;

import java.awt.Font;

import javax.swing.JLabel;

import com.swingdating.System.AppDesign;

public class InputLabel extends JLabel {
    public InputLabel(String label, AppDesign appdesign) {
        super(label);
        setFont(new Font("Roboto Bold", Font.PLAIN, 14));
        setForeground(appdesign.Color_FontPrimary);
    }
}
