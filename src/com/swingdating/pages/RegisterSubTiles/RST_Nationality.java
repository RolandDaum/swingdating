package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_Nationality;

public class RST_Nationality extends RST_Layout {
    DropDownMenu<APU_Nationality> dropdownmenu;

    public RST_Nationality(AppDesign appdesign) {
        super(appdesign);
        setName("nationality");
    
        rootAdd(new InputLabel("Nationality", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_Nationality.values());
        rootAdd(dropdownmenu);
    }
    @Override
    public boolean valid() {
        return appuser.setNationality((APU_Nationality) dropdownmenu.getSelectedEnum());
    }
}