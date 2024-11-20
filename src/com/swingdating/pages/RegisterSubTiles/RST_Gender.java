package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_EyeColor;
import com.swingdating.System.AppUserEnums.APU_Gender;

public class RST_Gender extends RST_Layout {
    DropDownMenu<APU_Gender> dropdownmenu;

    public RST_Gender(AppDesign appdesign) {
        super(appdesign);
        setName("gender");
    
        rootAdd(new InputLabel("Gender", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_Gender.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_Gender.fromCode(appuser.getGender().getCode()).getName());
    }
    @Override
    public boolean valid() {
        APU_Gender nationality = (APU_Gender) dropdownmenu.getSelectedEnum();
        return appuser.setGender(nationality);
    }
}