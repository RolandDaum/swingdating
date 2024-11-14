package com.swingdating.Pages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.swingdating.App;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;

public class PageHome extends JPanel {
    public static String pagename = "SWINGDATING - HOME";

    private AppDesign appdesign;
    private AppUser appuser;

    public PageHome(AppDesign appdesign) {
        this.appdesign = appdesign;

        setName(pagename);
        setBackground(appdesign.Color_BackgroundMain);


        JButton loginButton = new JButton("Go to Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchToPage(PageLogin.pagename);
            }
        });
        loginButton.setBounds(0, 0, 55, 55);
        add(loginButton);
        add(new InputLabel(App.getAppUser() == null ? "null" : App.getAppUser().getUsername(), appdesign));

    }
    public void setUserData(AppUser appuser) {
        this.appuser = appuser;
    } 
}
