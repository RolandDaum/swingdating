package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_BirthdateDay;
import com.swingdating.System.AppUserEnums.APU_EyeColor;

public class RST_EyeColor extends RST_Layout {
    DropDownMenu<APU_EyeColor> dropdownmenu;

    public RST_EyeColor(AppDesign appdesign) {
        super(appdesign);
        setName("eyecolor");
    
        rootAdd(new InputLabel("Eye color", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_EyeColor.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_EyeColor.fromCode(appuser.getEyeColor().getCode()).getName());
    }
    @Override
    public boolean valid() {
        return appuser.setEyeColor((APU_EyeColor) dropdownmenu.getSelectedEnum());
    }
}