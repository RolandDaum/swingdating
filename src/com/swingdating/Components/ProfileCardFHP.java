package com.swingdating.Components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.swingdating.App;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;

public class ProfileCardFHP extends JPanel {

    AppDesign appdesign = App.getAppDesign();

    public ProfileCardFHP(AppUser appuser, JFrame parentFrame) {
        AppDesign appdesign = App.getAppDesign();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setBackground(appdesign.Color_BackgroundContainer);
        setOpaque(false);
        setMaximumSize(new Dimension(450, 10000));

        JLabel titleLabel = new JLabel(appuser.getFirstName() + " " + appuser.getLastName());
        titleLabel.setFont(new Font("Roboto Black", Font.BOLD, 18));
        titleLabel.setForeground(appdesign.Color_FontSecondary);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        JLabel descriptionLabel = new JLabel(appuser.getAge() + " y   |   " + appuser.getGender().getName() + "   |   " + appuser.getCity());
        descriptionLabel.setForeground(appdesign.Color_FontPrimary);
        descriptionLabel.setFont(new Font("Roboto Medium Italic", Font.PLAIN, 14));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Button detailsButton = new Button("Details", appdesign, null);
        detailsButton.setBackground(appdesign.Color_BackgroundOnContainer);
        detailsButton.addActionListener(e -> showProfileDetails(appuser, parentFrame));

        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(descriptionLabel);
        add(Box.createVerticalStrut(15));
        add(detailsButton);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        g2.setColor(appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        g2.setColor(appdesign.Color_BorderLight);
        g2.setStroke(new BasicStroke(appdesign.BorderThickness));
        g2.drawRoundRect(appdesign.BorderThickness/2, appdesign.BorderThickness/2, getWidth()- appdesign.BorderThickness, getHeight() - appdesign.BorderThickness, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        super.paintComponent(g2);
        g2.dispose();
    }

    private static void showProfileDetails(AppUser appuser, JFrame parentFrame) {
        AppDesign appdesign = App.getAppDesign();
        JDialog dialog = new JDialog(parentFrame, "Profile Details", true);
        dialog.setUndecorated(true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setBackground(appdesign.Color_BorderLight);
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), appdesign.BorderRadiusWindow, appdesign.BorderRadiusWindow));

        JPanel rootpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), appdesign.titlebarHeight, appdesign.titlebarHeight));
                g2.setColor(appdesign.Color_BackgroundMain);
                g2.fillRoundRect(appdesign.BorderThicknessWindow, appdesign.BorderThicknessWindow, getWidth()-appdesign.BorderThicknessWindow*2, getHeight()-appdesign.BorderThicknessWindow*2, appdesign.BorderRadiusWindow-appdesign.BorderThicknessWindow, appdesign.BorderRadiusWindow-appdesign.BorderThicknessWindow);
            }
        };
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS));
        rootpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertikale Anordnung
        contentPanel.setOpaque(false);

        for (int i = 0; i < 20; i++) {
            JLabel label = new JLabel("Text item " + (i + 1));
            label.setForeground(appdesign.Color_FontPrimary);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        rootpanel.add(scrollPane);

        Button exitButton = new Button("exit", appdesign, new Dimension(dialog.getWidth()-2*appdesign.BorderThicknessWindow, appdesign.inputFieldHeight) , dialog::dispose);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootpanel.add(exitButton, 1);

        dialog.add(rootpanel);
        dialog.setVisible(true);
    }
}