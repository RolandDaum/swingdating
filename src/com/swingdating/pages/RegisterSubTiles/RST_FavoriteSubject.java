package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_EyeColor;
import com.swingdating.System.AppUserEnums.APU_FavoriteSubject;

public class RST_FavoriteSubject extends RST_Layout {
    DropDownMenu<APU_FavoriteSubject> dropdownmenu;

    public RST_FavoriteSubject(AppDesign appdesign) {
        super(appdesign);
        setName("favoritesubject");
    
        rootAdd(new InputLabel("Favorite Subject", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_FavoriteSubject.values());
        rootAdd(dropdownmenu);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenu.setSelectedItem(APU_FavoriteSubject.fromCode(appuser.getFavoriteSubject().getCode()).getName());
    }
    @Override
    public boolean valid() {
        return appuser.setFavoriteSubject((APU_FavoriteSubject) dropdownmenu.getSelectedEnum());
    }
}