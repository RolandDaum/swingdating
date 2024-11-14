package com.swingdating.Components;

import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DropDownMenuButton extends JButton {
    public DropDownMenuButton() {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("src/com/swingdating/assets/Icon_DropdownmenuArrow.png"));
            setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        setMargin(new Insets(0, 0, 0, 0));
    }
}
