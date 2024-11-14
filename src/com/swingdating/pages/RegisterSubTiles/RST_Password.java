package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;

import javax.swing.Box;

import com.swingdating.Components.Button;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;
import com.swingdating.System.CredentialHash;

public class RST_Password extends RST_Layout {
    InputField pwIF1;
    InputField pwIF2;
    public RST_Password(AppDesign appdesign) {
        super(appdesign);
        setName("password");

        pwIF1 = new InputField(appdesign, true);
        pwIF2 = new InputField(appdesign, true);
        pwIF1.onSubmit((value) -> {
            pwIF2.setFocus();
        });
        pwIF2.onSubmit((value) -> {
            if (onSubmit != null) {
                onSubmit.run();
            }
        });

        rootpanel.add(new InputLabel("Password", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(pwIF1);
        rootpanel.add(new InputLabel("Validation", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(pwIF2);
        rootpanel.add(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {
        boolean valid = false;
        if (pwIF1.getValue().equals(pwIF2.getValue()) && !pwIF1.getValue().trim().isEmpty()) {
            valid = appuser.setCDHash(new CredentialHash(pwIF1.getValue()));
        }
        if (valid) {
            pwIF2.setInvalidValue(false);
        } else {
            pwIF2.setInvalidValue(true);
        }
        return valid;
    }
}