package com.swingdating.Pages;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import com.swingdating.Components.Button;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;
import com.swingdating.System.LRManager;

public class PageLogin extends JPanel {
    public static String pagename = "SWINGDATING - LOGIN";

    private JPanel rootpanel;

    public PageLogin(AppDesign appdesign) {
        // LAYOUT AND DESIGN
        setName(pagename);
        setBackground(appdesign.Color_BackgroundMain);
        setLayout(new GridBagLayout());

        rootpanel = new JPanel();
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS)); // Hier kannst du BoxLayout belassen
        rootpanel.setBackground(appdesign.Color_BackgroundMain);

        InputLabel inpl_username = new InputLabel("username", appdesign);
        InputField inputfield_Username = new InputField(appdesign, false);
        InputLabel inpl_password = new InputLabel("password", appdesign);
        InputField inputfield_Password = new InputField(appdesign, true);
        Button button = new Button("login / register", appdesign, () -> LRManager.credentialCheck(inputfield_Username.getValue(), inputfield_Password.getValue()));

        // Setze FlowLayout für die Labels, um sie links auszurichten
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, appdesign.inputFieldHeight/3, appdesign.inputFieldHeight/6)); // Ausrichtung nach links
        usernamePanel.setBackground(appdesign.Color_BackgroundMain);
        usernamePanel.add(inpl_username);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, appdesign.inputFieldHeight/3, appdesign.inputFieldHeight/6)); // Ausrichtung nach links
        passwordPanel.setBackground(appdesign.Color_BackgroundMain);
        passwordPanel.add(inpl_password);

        rootpanel.add(usernamePanel);
        rootpanel.add(inputfield_Username);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight/2));
        rootpanel.add(passwordPanel);
        rootpanel.add(inputfield_Password);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight/2));
        rootpanel.add(new JLabel(" "));
        
        // Button mittig ausrichten
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootpanel.add(button);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight));

        add(rootpanel);


        
    }


}

