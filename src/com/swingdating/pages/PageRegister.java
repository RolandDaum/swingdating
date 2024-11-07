package com.swingdating.Pages;

import javax.swing.JPanel;

import com.swingdating.System.AppDesign;

public class PageRegister extends JPanel {
    public static String pagename = "SWINGDATING - REGISTER";

    public PageRegister(AppDesign appdesign) {
        setName(pagename);
        setBackground(appdesign.Color_AccentPrimary);
    }
}
