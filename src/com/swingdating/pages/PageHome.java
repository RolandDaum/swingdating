package com.swingdating.pages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.swingdating.App;
import com.swingdating.System.AppDesign;

public class PageHome extends JPanel {
    public static String pagename = "SWINGDATING - HOME";

    private AppDesign appdesign;

    public PageHome(AppDesign appdesign) {
        this.appdesign = appdesign;

        setName(pagename);
        setBackground(appdesign.Color_BackgroundMain);


        JButton loginButton = new JButton("Go to Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchToPage(PageLogin.pagename);
                // App.setWindowsTitle("Hello world Title");

            }
        });
        loginButton.setBounds(0, 0, 55, 55);
        add(loginButton);

    }
}
