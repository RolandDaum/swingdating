package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.InputField;
import com.swingdating.System.AppDesign;

public class RST_HeightWeight extends RST_Layout {
    InputField inputFieldHeight;
    InputField inputFieldWeight;

    public RST_HeightWeight(AppDesign appdesign) {
        super(appdesign);
        setName("bodystats");
    
        rootAdd(new InputLabel("Height in cm", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        inputFieldHeight = new InputField(appdesign, "Height in cm", true);
        rootAdd(inputFieldHeight);
        rootAdd(new InputLabel("Weight in kg", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        inputFieldWeight = new InputField(appdesign, "Weight in kg", true);
        rootAdd(inputFieldWeight);
    }
    @Override
    public boolean valid() {
        if (inputFieldWeight.getValue().isEmpty() || inputFieldHeight.getValue().isEmpty()) {
            inputFieldWeight.setInvalidValue(true);
            inputFieldHeight.setInvalidValue(true);
            return false;
        }
        boolean wValid = appuser.setWeight(Integer.parseInt(inputFieldWeight.getValue()));
        boolean hValid = appuser.setHeight(Integer.parseInt(inputFieldHeight.getValue()));
        if (!wValid) {
            inputFieldWeight.setInvalidValue(true);
        } else {
            inputFieldWeight.setInvalidValue(false);
        }
        if (!hValid) {
            inputFieldHeight.setInvalidValue(true);
        } else {
            inputFieldHeight.setInvalidValue(false);
        }

        return Boolean.logicalAnd(wValid, hValid);
    }
}