package com.swingdating.Components;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;

import com.swingdating.App;
import com.swingdating.System.AppDesign;
import java.awt.*;
import java.awt.event.ActionListener;

public class Titlebar extends JPanel {
    private int mouseX;
    private int mouseY;

    private JLabel titleLabel;
    private AppDesign appdesign;
    private TitlebarActionButton maximizeButton;

    public Titlebar(JFrame frame, AppDesign appdesign, String titlelabel) {
        this(frame, appdesign);
        setTitle(titlelabel);
    }

    public Titlebar(JFrame frame, AppDesign appdesign) {
        this.appdesign = appdesign;
        setLayout(new BorderLayout());
        setBackground(appdesign.Color_BackgroundMain);
        // setBackground(appdesign.Color_BackgroundContainer);

        setPreferredSize(new Dimension(0, appdesign.titlebarHeight));

        // Title
        titleLabel = new JLabel("", SwingConstants.LEFT);
        titleLabel.setFont(appdesign.fonts.get("Orbitron Regular").deriveFont(12f));
        
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, appdesign.titlebarHeight/2, 0, 0)); // Linker Abstand von 10 Pixeln
        add(titleLabel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setOpaque(false);

        // Buttons hinzufügen
        buttonPanel.add(createButton(appdesign.AssetName_Icon_Titlebar_Minimize, e -> frame.setState(JFrame.ICONIFIED)));
        maximizeButton = (TitlebarActionButton) createButton(appdesign.AssetName_Icon_Titlebar_Maximize, e -> toggleMaximize(frame));
        buttonPanel.add(maximizeButton);
        buttonPanel.add(createButton(appdesign.AssetName_Icon_Titlebar_Close, e -> frame.dispose(), true));

        // Add Button Panel
        add(buttonPanel, BorderLayout.EAST);

        // Drag functionality
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    // Calculating the horizontal click position as a decimal number
                    float xpercentage = (1/ (float) frame.getWidth())*(float)e.getX();

                    // Changeing the frames status
                    toggleMaximize(frame);

                    // calculating the new position of the x value with a new window with
                    int value = (int) (xpercentage*frame.getWidth());
                    int marginwith = (appdesign.titlebarHeight-5)*3+3*5+15;
                    mouseX = getX()+ (value > frame.getWidth()-marginwith ? frame.getWidth()-marginwith : value); // Checks if the new x position would end up in the titlebar action buttons
                    mouseY = getY()+e.getY();

                }
                    frame.setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);

            }
        });
    }

    private JButton createButton(String assetName, ActionListener action, Boolean exitbutton) {
        TitlebarActionButton button = new TitlebarActionButton(assetName, appdesign, exitbutton);
        button.addActionListener(action);
        return button;
    }
    private JButton createButton(String assetName, ActionListener action) {
        TitlebarActionButton button = new TitlebarActionButton(assetName, appdesign);
        button.addActionListener(action);
        return button;
    }

    // TODO: Change from fullscreen to semifullscrenn with taskbar height.
    private void toggleMaximize(JFrame frame) {
        if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            frame.setExtendedState(JFrame.NORMAL);
            maximizeButton.setButtonIcon(appdesign.AssetName_Icon_Titlebar_Maximize);
            App.updateDefaultWindowShape();
        } else {

            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            maximizeButton.setButtonIcon(appdesign.AssetName_Icon_Titlebar_Exitmaximize);
            App.setWindowShape(appdesign.getFullscreenWindowsShape(frame.getWidth(), frame.getHeight()));
        }
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
