package com.swingdating.Pages;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.swingdating.System.AppDesign;
import com.swingdating.App;
import com.swingdating.Components.Button;
import com.swingdating.Pages.RegisterSubTiles.RST_Address;
import com.swingdating.Pages.RegisterSubTiles.RST_Birthdate;
import com.swingdating.Pages.RegisterSubTiles.RST_Birthplace;
import com.swingdating.Pages.RegisterSubTiles.RST_EyeColor;
import com.swingdating.Pages.RegisterSubTiles.RST_FavoriteSubject;
import com.swingdating.Pages.RegisterSubTiles.RST_Gender;
import com.swingdating.Pages.RegisterSubTiles.RST_HairColor;
import com.swingdating.Pages.RegisterSubTiles.RST_HeightWeight;
import com.swingdating.Pages.RegisterSubTiles.RST_Layout;
import com.swingdating.Pages.RegisterSubTiles.RST_MusicPreference;
import com.swingdating.Pages.RegisterSubTiles.RST_Name;
import com.swingdating.Pages.RegisterSubTiles.RST_Nationality;
import com.swingdating.Pages.RegisterSubTiles.RST_Password;
import com.swingdating.Pages.RegisterSubTiles.RST_Religion;
import com.swingdating.Pages.RegisterSubTiles.RST_Sexuality;
import com.swingdating.Pages.RegisterSubTiles.RST_Username;

public class PageRegister extends JPanel {
    public static String pagename = "SWINGDATING - REGISTER";
    private AppDesign appdesign;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel rootpanel;
    private List<RST_Layout> RST_PagesList = new ArrayList<>();
    private int pageCounter = 0;

    private Button prevButton;
    private Button nextButton;

    public PageRegister(AppDesign appdesign) {
        // Bacis page setup
        this.appdesign = appdesign;
        loadRSTPages();
        setName(pagename);
        setBackground(appdesign.Color_BackgroundMain);
        setLayout(new GridBagLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        for (RST_Layout rst_LayoutPage : RST_PagesList) {
            cardPanel.add(rst_LayoutPage, rst_LayoutPage.getName());
        }
        cardLayout.show(cardPanel, RST_PagesList.get(pageCounter).getName());
        App.setWindowTitle("register   >   " + RST_PagesList.get(pageCounter).getName());

        // Navigation Button panel
        prevButton = new Button("- / -", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> prevBAC());
        nextButton = new Button("Next", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> nextBAC());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(prevButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(nextButton);

        // UI Compelation
        rootpanel = new JPanel();
        rootpanel.setOpaque(false);
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS));
        rootpanel.add(cardPanel);
        rootpanel.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(rootpanel, gbc);
    }
    
    // Page Navigation Button stuff
    private void prevBAC() {
        navigateBAC(-1);
    }
    private void nextBAC() {
        navigateBAC(1);
    }
    private void navigateBAC(int direction) {
        int newPageCounter = pageCounter + direction;
        boolean validDataonPage = RST_PagesList.get(pageCounter).valid();
        if (newPageCounter >= RST_PagesList.size()) {
            if (App.getAppUser().savetoDB()) {
                App.switchToPage(PageHome.pagename);
            } else {
                System.out.println("THIS SHOULD NEVER HAPPEN");
            }
        }
        if (newPageCounter < 0 || newPageCounter >= RST_PagesList.size() || !validDataonPage) {
            return;
        }
        pageCounter = newPageCounter;
        cardLayout.show(cardPanel, RST_PagesList.get(pageCounter).getName());
        App.setWindowTitle("register   >   " + RST_PagesList.get(pageCounter).getName());
        setButtonText();

    }
    private void setButtonText() {
        prevButton.setText(pageCounter <= 0 ? "- / -" : "Previous");
        nextButton.setText(pageCounter >= RST_PagesList.size() - 1 ? "Create" : "Next");
    }

    // Load all the RST Pages into the list
    private void loadRSTPages() {
        RST_Layout rst_eyeColor = new RST_EyeColor(appdesign);
        RST_Layout rst_favoriteSubject = new RST_FavoriteSubject(appdesign);
        RST_Layout rst_gender = new RST_Gender(appdesign);
        RST_Layout rst_hairColor = new RST_HairColor(appdesign);
        RST_Layout rst_musicPreference = new RST_MusicPreference(appdesign);
        RST_Layout rst_name = new RST_Name(appdesign);
        RST_Layout rst_nationality = new RST_Nationality(appdesign);
        RST_Layout rst_password = new RST_Password(appdesign);
        RST_Layout rst_religion = new RST_Religion(appdesign);
        RST_Layout rst_sexuality = new RST_Sexuality(appdesign);
        RST_Layout rst_username = new RST_Username(appdesign);
        RST_Layout rst_birthdate = new RST_Birthdate(appdesign);
        RST_Layout rst_heightweight = new RST_HeightWeight(appdesign);
        RST_Layout rst_address = new RST_Address(appdesign);
        RST_Layout rst_birthplace = new RST_Birthplace(appdesign);

    RST_PagesList.addAll(Arrays.asList(
        rst_username,
        rst_password,
        rst_name,
        rst_address,
        rst_birthdate,
        rst_birthplace,
        rst_gender,
        rst_nationality,
        rst_sexuality,
        rst_religion,
        rst_hairColor,
        rst_eyeColor,
        rst_heightweight,
        rst_favoriteSubject,
        rst_musicPreference
    ));

        for (RST_Layout rst_page : RST_PagesList) {
            rst_page.onSubmit(() -> nextBAC());
        }
    }    

}