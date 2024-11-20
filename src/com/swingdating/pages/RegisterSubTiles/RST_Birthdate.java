package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Insets;
import java.time.LocalDate;

import com.swingdating.Components.InputLabel;
import com.swingdating.Components.DropDownMenu;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_BirthdateDay;
import com.swingdating.System.AppUserEnums.APU_BirthdateYear;
import com.swingdating.System.AppUserEnums.APU_BrithdateMonth;

public class RST_Birthdate extends RST_Layout {
    DropDownMenu<APU_BirthdateDay> dropdownmenuDay;
    DropDownMenu<APU_BrithdateMonth> dropdownmenuMonth;
    DropDownMenu<APU_BirthdateYear> dropdownmenuYear;

    public RST_Birthdate(AppDesign appdesign) {
        super(appdesign);
        setName("birthdate");
    
        rootAdd(new InputLabel("Day", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        dropdownmenuDay = new DropDownMenu<>(appdesign, APU_BirthdateDay.values());
        rootAdd(dropdownmenuDay);

        rootAdd(new InputLabel("Month", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        dropdownmenuMonth = new DropDownMenu<>(appdesign, APU_BrithdateMonth.values());
        rootAdd(dropdownmenuMonth);

        rootAdd(new InputLabel("Year", appdesign, new Insets(appdesign.inputFieldHeight, appdesign.inputFieldHeight / 2, appdesign.inputFieldHeight / 4, 0)));
        dropdownmenuYear = new DropDownMenu<>(appdesign, APU_BirthdateYear.values());
        rootAdd(dropdownmenuYear);
        loadUserDate();
    }
    private void loadUserDate() {
        if (!appuser.validateData()) {return;}
        dropdownmenuDay.setSelectedItem(APU_BirthdateDay.fromCode(appuser.getBirthDate().getDayOfMonth()).getName());
        dropdownmenuMonth.setSelectedItem(APU_BrithdateMonth.fromCode(appuser.getBirthDate().getMonthValue()).getName());
        dropdownmenuYear.setSelectedItem(APU_BirthdateYear.fromCode(appuser.getBirthDate().getYear()).getName());
    }
    @Override
    public boolean valid() {
        LocalDate birthdate = LocalDate.of(
            ((APU_BirthdateYear) dropdownmenuYear.getSelectedEnum()).getCode(),
            ((APU_BrithdateMonth) dropdownmenuMonth.getSelectedEnum()).getCode(),
            ((APU_BirthdateDay) dropdownmenuDay.getSelectedEnum()).getCode()
        );
        return appuser.setBirthDate(birthdate);
    }
}