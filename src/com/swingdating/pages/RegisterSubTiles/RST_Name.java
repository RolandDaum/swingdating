package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import javax.swing.Box;

import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;

public class RST_Name extends RST_Layout {
    InputField firstNameIF;
    InputField lastNameIF;

    public RST_Name(AppDesign appdesign) {
        super(appdesign);
        setName("officialname");
        
        firstNameIF = new InputField(appdesign);
        lastNameIF = new InputField(appdesign);
        firstNameIF.onType((value) -> System.out.println(firstNameIF.getValue()));
        firstNameIF.onSubmit((value) -> lastNameIF.setFocus());
        lastNameIF.onSubmit((value) -> {
            if (onSubmit != null) {
                onSubmit.run();
            }
        });

        rootpanel.add(new InputLabel("Fist name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(firstNameIF);
        rootpanel.add(new InputLabel("Last name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootpanel.add(lastNameIF);
        rootpanel.add(Box.createVerticalGlue());

    }
    @Override
    public boolean valid() {
        System.out.println(firstNameIF == null);
        // firstNameIF.setValue("Roland");
        System.out.println(firstNameIF.getValue());
        System.out.println(lastNameIF.getValue());
        boolean valid = appuser.setName(firstNameIF.getValue(), lastNameIF.getValue());

        if (valid) {
            lastNameIF.setInvalidValue(false);
        } else {
            lastNameIF.setInvalidValue(true);
        }

        return valid;
    }
}