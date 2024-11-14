package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import com.swingdating.App;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;

public class RST_Layout extends JScrollPane {
    public JPanel rootpanel;
    public AppUser appuser = App.getAppUser();
    public Runnable onSubmit;
    public RST_Layout(AppDesign appdesign) {
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(null);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        rootpanel = new JPanel();
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS));
        rootpanel.setOpaque(false);
        rootpanel.setMaximumSize(new Dimension(appdesign.inputFieldWidth, (int) rootpanel.getMaximumSize().getHeight()));

        setViewportView(rootpanel);

        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = appdesign.Color_BackgroundContainer;
                this.trackColor = appdesign.Color_BackgroundMain;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                // Entferne die Default-Buttons für das Scrollen
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));  // Setze Größe auf 0, um sie unsichtbar zu machen
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                // Entferne die Default-Buttons für das Scrollen
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));  // Setze Größe auf 0, um sie unsichtbar zu machen
                return button;
            }

            @Override
            protected void paintThumb(java.awt.Graphics g, javax.swing.JComponent c, java.awt.Rectangle thumbBounds) {
                g.setColor(thumbColor);
                g.fillRoundRect(thumbBounds.x, thumbBounds.y, appdesign.ScrollBarThickness, thumbBounds.height, appdesign.ScrollBarThickness, appdesign.ScrollBarThickness);
                g.setColor(appdesign.Color_BorderLight);  // Farbe für die Border
                g.drawRoundRect(thumbBounds.x, thumbBounds.y, appdesign.ScrollBarThickness, thumbBounds.height, appdesign.ScrollBarThickness, appdesign.ScrollBarThickness);
            }
        });
    }

    /**
     * Returns true if the inserted data is valid and save to use. Only then continue.
     * @return Default return value is false, to prevent currupted data from being inserted into AppUser obj and later into the database
     */
    public boolean valid() {
        return false;
    }

    public void onSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }
}
