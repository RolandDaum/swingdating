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
import com.swingdating.Pages.RegisterSubTiles.RST_Username;

public class PageRegister extends JPanel {
    public static String pagename = "SWINGDATING - REGISTER";
    private AppDesign appdesign;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel rootpanel;
    private List<RST_Layout> RST_PagesList = new ArrayList<>();
    private int pageCounter = 0;

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

        Button prevButton = new Button("Previous", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> {
            if (pageCounter <= 0 || !RST_PagesList.get(pageCounter).valid()) {
                return;
            } else {
                pageCounter--;
                cardLayout.show(cardPanel, RST_PagesList.get(pageCounter).getName());
            }
        });
        Button nextButton = new Button("Next", appdesign, new Dimension((appdesign.inputFieldWidth/2)-5,appdesign.inputFieldHeight), () -> {
            if (pageCounter >= RST_PagesList.size()-1 || !RST_PagesList.get(pageCounter).valid()) {
                return;
            } else {
                pageCounter++;
                cardLayout.show(cardPanel, RST_PagesList.get(pageCounter).getName());
            }
        });

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

    private void loadRSTPages() {
        RST_PagesList.add(new RST_Username(appdesign));
        RST_PagesList.add(new RST_Password(appdesign));
        RST_PagesList.add(new RST_Name(appdesign));
    }    

}
