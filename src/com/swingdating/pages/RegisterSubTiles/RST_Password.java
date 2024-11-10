package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;

import javax.swing.Box;

import com.swingdating.Components.Button;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

public class RST_Password extends RST_Layout {
    public RST_Password(AppDesign appdesign) {
        super(appdesign);
        setName("password");

        InputField passwordInputField1 = new InputField(appdesign, true);
        InputField passwordInputField2 = new InputField(appdesign, true);

        rootpanel.add(new InputLabel("Password", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(passwordInputField1);
        rootpanel.add(new InputLabel("Validation", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(passwordInputField2);
        rootpanel.add(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {
        return true;
    }
}