package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import javax.swing.Box;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

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

        usernameInputField.setValue(appuser != null ? appuser.getUsername() : "");
        rootpanel.add(new InputLabel("Username", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(usernameInputField);
        rootpanel.add(errorLabel);
        rootpanel.add(Box.createVerticalGlue());



    }
    @Override
    public boolean valid() {
        boolean valid = appuser.setUsername(usernameInputField.getValue());
        System.out.println(usernameInputField.getValue());
        if (valid) {
            usernameInputField.setInvalidValue(false);
        } else {
            usernameInputField.setInvalidValue(true);
        }
        return valid;
    }
}
