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

        pwIF1 = new InputField(appdesign, true, "Enter password");
        pwIF2 = new InputField(appdesign, true, "Enter password again");
        pwIF1.onSubmit((value) -> {
            pwIF2.setFocus();
        });
        pwIF2.onSubmit((value) -> {
            if (onSubmit != null) {
                onSubmit.run();
            }
        });

        rootAdd(new InputLabel("Password", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootAdd(pwIF1);
        rootAdd(new InputLabel("Validation", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootAdd(pwIF2);
        rootAdd(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {

        boolean valid = false;
        boolean pwif1IsEmpty = pwIF1.getValue().trim().isEmpty();
        boolean pwif2IsEmpty = pwIF2.getValue().trim().isEmpty();
        boolean pwif1pwif2Same = pwIF1.getValue().equals(pwIF2.getValue());
        if (appuser.getCDHash() != null && pwif1IsEmpty && pwif2IsEmpty) {
            return true;
        }
        if (!pwif1IsEmpty && pwif1pwif2Same) {
            valid = appuser.setCDHash(new CredentialHash(pwIF1.getValue()));
        }
        if (!valid) {
            pwIF1.setInvalidValue(true);
            pwIF2.setInvalidValue(true);
            if (!pwif1IsEmpty && pwif2IsEmpty) {
                pwIF1.setInvalidValue(false);
            } 
            if (pwif1IsEmpty && !pwif2IsEmpty) {
                pwIF2.setInvalidValue(false);
            }
        } else {
            pwIF1.setInvalidValue(false);
            pwIF2.setInvalidValue(false);
        }
        
        return valid;
    }
}