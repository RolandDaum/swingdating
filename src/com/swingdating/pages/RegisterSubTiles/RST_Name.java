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
        firstNameIF.onSubmit((value) -> lastNameIF.setFocus());
        lastNameIF.onSubmit((value) -> {
            if (onSubmit != null) {
                onSubmit.run();
            }
        });

        rootAdd(new InputLabel("Fist name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootAdd(firstNameIF);
        rootAdd(new InputLabel("Last name", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        rootAdd(lastNameIF);
        rootAdd(Box.createVerticalGlue());
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        firstNameIF.setValue(appuser.getFirstName());
        lastNameIF.setValue(appuser.getLastName());
    }
    @Override
    public boolean valid() {
        boolean valid = appuser.setName(firstNameIF.getValue(), lastNameIF.getValue());
        boolean fNameIsEmpty = firstNameIF.getValue().isEmpty();
        boolean lNameIsEmpty = lastNameIF.getValue().isEmpty();

        if (!valid) {
            firstNameIF.setInvalidValue(true);
            lastNameIF.setInvalidValue(true);
            if (!fNameIsEmpty && lNameIsEmpty) {
                firstNameIF.setInvalidValue(false);
            }
            if (fNameIsEmpty && !lNameIsEmpty) {
                lastNameIF.setInvalidValue(false);
            }
        } else {
            firstNameIF.setInvalidValue(false);
            lastNameIF.setInvalidValue(false);
        }

        return valid;
    }
}