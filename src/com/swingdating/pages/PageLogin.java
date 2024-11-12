package com.swingdating.Pages;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import com.swingdating.App;
import com.swingdating.Components.Button;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.System.AppDesign;
import com.swingdating.System.DBManagerSQLite;
import com.swingdating.System.PasswordHash;

public class PageLogin extends JPanel {
    public static String pagename = "SWINGDATING - LOGIN";
    InputField inputfield_Username;
    InputField inputfield_Password;

    private JPanel rootpanel;
    private String username;
    private String password;

    public PageLogin(AppDesign appdesign) {
        // LAYOUT AND DESIGN
        setName(pagename);
        setBackground(appdesign.Color_BackgroundMain);
        setLayout(new GridBagLayout());

        inputfield_Username = new InputField(appdesign, false);
        inputfield_Password = new InputField(appdesign, true);
        Button button = new Button("login / register", appdesign, new Dimension(appdesign.inputFieldWidth/5*4, appdesign.inputFieldHeight), () -> credentialCheck());

        InputLabel inpl_username = new InputLabel("username", appdesign);
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, appdesign.inputFieldHeight/3, appdesign.inputFieldHeight/6)); // Ausrichtung nach links
        usernamePanel.setBackground(appdesign.Color_BackgroundMain);
        usernamePanel.add(inpl_username);
        inputfield_Username.onSubmit((value) -> inputfield_Password.setFocus());
        inputfield_Username.onType((username) -> this.username = username);

        InputLabel inpl_password = new InputLabel("password", appdesign);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, appdesign.inputFieldHeight/3, appdesign.inputFieldHeight/6)); // Ausrichtung nach links
        passwordPanel.setBackground(appdesign.Color_BackgroundMain);
        passwordPanel.add(inpl_password);
        inputfield_Password.onSubmit((value) -> {button.doClick(); inputfield_Password.removeFocus();});
        inputfield_Password.onType((password) -> this.password = password);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        rootpanel = new JPanel();
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS));
        rootpanel.setBackground(appdesign.Color_BackgroundMain);
        rootpanel.add(usernamePanel);
        rootpanel.add(inputfield_Username);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight/2));
        rootpanel.add(passwordPanel);
        rootpanel.add(inputfield_Password);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight/2));
        rootpanel.add(new JLabel(" "));
        rootpanel.add(button);
        rootpanel.add(Box.createVerticalStrut(appdesign.inputFieldHeight/2));

        add(rootpanel);
    }

    private void credentialCheck() {
        if (inputfield_Username.getValue().isEmpty() || inputfield_Password.getValue().isEmpty()) {return;}
        DBManagerSQLite db = App.db;
        String[][] data = db
                .get("SELECT password_hash, password_salt FROM appusers WHERE username = '" + this.username + "'");
        if (data.length <= 1) {
            App.switchToPage("SWINGDATING - REGISTER");
        } else {
            if (PasswordHash.verifyPassword(new PasswordHash(password, data[1][1]), data[1][0])) {
                App.switchToPage("SWINGDATING - HOME");
            } else {
                System.out.println("WRONG PASSWORD");
            }
        }
    }
}