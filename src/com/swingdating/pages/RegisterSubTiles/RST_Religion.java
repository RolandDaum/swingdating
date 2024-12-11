package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_Religion;

public class RST_Religion extends RST_Layout {
    DropDownMenu<APU_Religion> dropdownmenu;

    public RST_Religion(AppDesign appdesign) {
        super(appdesign);
        setName("religion");
    
        rootAdd(new InputLabel("Religion", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_Religion.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_Religion.fromCode(appuser.getReligion().getCode()).getName());
    }
    @Override
    public boolean valid() {
        return appuser.setReligion((APU_Religion) dropdownmenu.getSelectedEnum());
    }
}