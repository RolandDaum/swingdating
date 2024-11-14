package com.swingdating.Pages;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import com.swingdating.System.AppDesign;
import com.swingdating.Components.Button;
import com.swingdating.Pages.RegisterSubTiles.RST_Layout;
import com.swingdating.Pages.RegisterSubTiles.RST_Name;
import com.swingdating.Pages.RegisterSubTiles.RST_Password;
import com.swingdating.Pages.RegisterSubTiles.RST_Test;
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

        prevButton = new Button("- / -", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> prevBAC());
        nextButton = new Button("Next", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> nextBAC());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(prevButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(nextButton);

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
        if (newPageCounter < 0 || newPageCounter >= RST_PagesList.size() || !RST_PagesList.get(pageCounter).valid()) {
            return;
        }
        pageCounter = newPageCounter;
        cardLayout.show(cardPanel, RST_PagesList.get(pageCounter).getName());
        setButtonText();
    }
    private void setButtonText() {
        prevButton.setText(pageCounter <= 0 ? "- / -" : "Previous");
        nextButton.setText(pageCounter >= RST_PagesList.size() - 1 ? "Create" : "Next");
    }

    // Load all the RST Pages into the list
    private void loadRSTPages() {
        RST_Layout rst_layout = new RST_Username(appdesign);
        rst_layout.onSubmit(() -> nextBAC());
        RST_Layout rst_password = new RST_Password(appdesign);
        rst_password.onSubmit(() -> nextBAC());
        RST_Layout rst_name = new RST_Name(appdesign);
        rst_name.onSubmit(() -> nextBAC());
        RST_Layout rst_test = new RST_Test(appdesign);
        rst_test.onSubmit(() -> nextBAC());

        RST_PagesList.add(rst_layout);
        RST_PagesList.add(rst_password);
        RST_PagesList.add(rst_name);
        RST_PagesList.add(rst_test);

    }    

}
