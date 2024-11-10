package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;

import javax.swing.Box;

import com.swingdating.Components.Button;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

public class RST_Name extends RST_Layout {
    public RST_Name(AppDesign appdesign) {
        super(appdesign);
        setName("officialname");
        
        InputField firstnameInputField = new InputField(appdesign);
        InputField lastnameInputField = new InputField(appdesign);

        rootpanel.add(new InputLabel("Fist name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(firstnameInputField);
        rootpanel.add(new InputLabel("Last name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(lastnameInputField);
        rootpanel.add(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {
        return true;
    }
}