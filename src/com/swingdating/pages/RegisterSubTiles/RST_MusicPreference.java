package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_MusicPreference;

public class RST_MusicPreference extends RST_Layout {
    DropDownMenu<APU_MusicPreference> dropdownmenu;

    public RST_MusicPreference(AppDesign appdesign) {
        super(appdesign);
        setName("musicpref");
    
        rootAdd(new InputLabel("Music Preference", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        dropdownmenu = new DropDownMenu<>(appdesign, APU_MusicPreference.values());
        rootAdd(dropdownmenu);
    }
    @Override
    public boolean valid() {
        return appuser.setMusicPreference((APU_MusicPreference) dropdownmenu.getSelectedEnum());
    }
}