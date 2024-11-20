package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.InputField;
import com.swingdating.System.AppDesign;

public class RST_Address extends RST_Layout {
    InputField postalCodeField;
    InputField cityField;
    InputField districtField;

    public RST_Address(AppDesign appdesign) {
        super(appdesign);
        setName("address");
          
        rootAdd(new InputLabel("Postal Code", appdesign, new Insets(0, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        postalCodeField = new InputField(appdesign, "5 Digit ZIP Code", true);
        rootAdd(postalCodeField);

        rootAdd(new InputLabel("City", appdesign, new Insets(0, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        cityField = new InputField(appdesign, "City name");
        rootAdd(cityField);
    
        rootAdd(new InputLabel("District", appdesign, new Insets(0, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        districtField = new InputField(appdesign, "District name");
        rootAdd(districtField);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        postalCodeField.setValue(String.valueOf(appuser.getPostalCode()));
        cityField.setValue(appuser.getCity());
        districtField.setValue(appuser.getDistrict() == null || appuser.getDistrict().equals("null") ? "" : appuser.getDistrict());

    }
    @Override
    public boolean valid() {
        boolean validPostalCode = appuser.setPostalCode(postalCodeField.getValue().isEmpty() ? 0 : Integer.parseInt(postalCodeField.getValue()));
        boolean validCity = appuser.setCity(cityField.getValue());
        boolean validDistrict = appuser.setDistrcit(districtField.getValue());
        if (!validPostalCode) { postalCodeField.setInvalidValue(true);  } 
        else {  postalCodeField.setInvalidValue(false); }
        if (!validCity) {    cityField.setInvalidValue(true);    } 
        else {  cityField.setInvalidValue(false);   }
        if (!validDistrict) {    districtField.setInvalidValue(true);    } 
        else {  districtField.setInvalidValue(false);   }
        return Boolean.logicalAnd(Boolean.logicalAnd(validPostalCode, validCity), validDistrict);
    }
}