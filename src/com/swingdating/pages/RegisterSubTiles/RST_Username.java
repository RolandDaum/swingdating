package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import javax.swing.Box;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_Sexuality;

public class RST_Username extends RST_Layout {
    InputField usernameInputField;
    public RST_Username(AppDesign appdesign) {
        super(appdesign);
        setName("username");
        usernameInputField = new InputField(appdesign);
        InputLabel errorLabel = new InputLabel("", appdesign);
        usernameInputField.onSubmit((value) -> {
            if (onSubmit != null) {
                onSubmit.run();
            }
        });

        rootAdd(new InputLabel("Username", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootAdd(usernameInputField);
        rootAdd(errorLabel);
        rootAdd(Box.createVerticalGlue());
            loadUserDate();
    }
    private void loadUserDate() {
        if (appuser.getUsername().isEmpty()) {return;}
        usernameInputField.setValue(appuser.getUsername());
    }
    @Override
    public boolean valid() {
        boolean valid = appuser.setUsername(usernameInputField.getValue());
        if (valid) {
            usernameInputField.setInvalidValue(false);
        } else {
            usernameInputField.setInvalidValue(true);
        }
        return valid;
    }
}
