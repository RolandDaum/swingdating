package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_Sexuality;

public class RST_Sexuality extends RST_Layout {
    DropDownMenu<APU_Sexuality> dropdownmenu;

    public RST_Sexuality(AppDesign appdesign) {
        super(appdesign);
        setName("sexuality");
    
        rootAdd(new InputLabel("Sexuality", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_Sexuality.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_Sexuality.fromCode(appuser.getSexuality().getCode()).getName());
    }
    @Override
    public boolean valid() {
        return appuser.setSexuality((APU_Sexuality) dropdownmenu.getSelectedEnum());
    }
}