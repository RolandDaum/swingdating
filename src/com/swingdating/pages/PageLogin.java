package  com.swingdating.pages;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.swingdating.App;
import com.swingdating.Components.*;
import  com.swingdating.System.AppDesign;

public class PageLogin extends JPanel {
    public static String pagename = "SWINGDATING - LOGIN";

    private AppDesign appdesign;

    public PageLogin(AppDesign appdesign) {
        this.appdesign = appdesign;

        setName(pagename); // Ensure unique name
        setBackground(appdesign.Color_BackgroundMain);

        JButton homeButton = new JButton("Go to Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchToPage(PageHome.pagename);
                // App.setWindowsTitle("Hello world Title");
                // App.toggleAppTheme();
            }
        });
        homeButton.setBounds(0, 0, 55, 55);

        add(homeButton);

        InputField usernameinputfield = new InputField(appdesign, false);
        usernameinputfield.onType(value -> {
            System.out.println(value);
        });
        add(usernameinputfield);

        InputField passwordinputfield = new InputField(appdesign, true);
        passwordinputfield.onType(value -> {
            System.out.println(value);
        });
        add(passwordinputfield);
    }
}
