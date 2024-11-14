package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import javax.swing.Box;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;

public class RST_Test extends RST_Layout {
    InputField firstNameIF;
    InputField lastNameIF;

    public RST_Test(AppDesign appdesign) {
        super(appdesign);
        setName("test");
        

        rootpanel.add(new InputLabel("IDK", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight/2, appdesign.inputFieldHeight/4, 0)));
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        DropDownMenu comboBox = new DropDownMenu(appdesign, items);
        rootpanel.add(comboBox);
    }
    // @Override
    // public boolean valid() {

    // }
}