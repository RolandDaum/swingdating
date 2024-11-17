package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;

import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

public class RST_Birthplace extends RST_Layout {
    InputField birthPlaceInputField;
    public RST_Birthplace(AppDesign appdesign) {
        super(appdesign);
        setName("Place of Birth");
        rootAdd(new InputLabel("Place of Birth", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        birthPlaceInputField = new InputField(appdesign, "Enter Cityname of birthplace");
        rootAdd(birthPlaceInputField);
    }
    @Override
    public boolean valid() {
        boolean valid = appuser.setBirthPlace(birthPlaceInputField.getValue());
        if (!valid) {
            birthPlaceInputField.setInvalidValue(true);
        } else {
            birthPlaceInputField.setInvalidValue(false);
        }
        return valid;
    }
}
