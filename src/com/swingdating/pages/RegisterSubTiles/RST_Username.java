package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

public class RST_Username extends RST_Layout {
    public RST_Username(AppDesign appdesign) {
        super(appdesign);
        setName("username");
        InputField usernameInputField = new InputField(appdesign);
        InputLabel errorLabel = new InputLabel("", appdesign);

        rootpanel.add(new InputLabel("Username", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(usernameInputField);
        rootpanel.add(errorLabel);
        rootpanel.add(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {
        return true;
    }
}
