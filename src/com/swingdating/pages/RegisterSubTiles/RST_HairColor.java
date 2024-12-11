package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_HairColor;

public class RST_HairColor extends RST_Layout {
    DropDownMenu<APU_HairColor> dropdownmenu;

    public RST_HairColor(AppDesign appdesign) {
        super(appdesign);
        setName("haircolor");
    
        rootAdd(new InputLabel("Hair color", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_HairColor.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_HairColor.fromCode(appuser.getHairColor().getCode()).getName());
    }
    @Override
    public boolean valid() {
        return appuser.setHairColor((APU_HairColor) dropdownmenu.getSelectedEnum());
    }
}