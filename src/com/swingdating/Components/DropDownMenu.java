package com.swingdating.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

import com.swingdating.System.AppDesign;

public class DropDownMenu extends JComboBox<String> {
    private AppDesign appdesign = new AppDesign(true);
    public DropDownMenu(AppDesign appdesign, String[] items) {
        super(items);

        UIManager.put("ComboBox.foreground", appdesign.Color_FontPrimary);
        UIManager.put("ComboBox.background", appdesign.Color_BackgroundContainer);
        UIManager.put("ComboBox.selectionForeground", appdesign.Color_FontPrimary);
        UIManager.put("ComboBox.selectionBackground", appdesign.Color_BackgroundContainer);
        UIManager.put("ComboBox.inactiveForeground", appdesign.Color_FontPrimary);
        UIManager.put("textHighlightColor", Color.RED);
        UIManager.put("ComboBox.textInactiveText", appdesign.Color_FontPrimary);  

        UIManager.put("ComboBox.buttonDarkShadow", appdesign.Color_BackgroundContainer);
        UIManager.put("ComboBox.buttonBackground", appdesign.Color_BackgroundOnContainer);
        UIManager.put("ComboBox.buttonHighlight",  appdesign.Color_FontPrimary);
        UIManager.put("ComboBox.buttonShadow",     appdesign.Color_FontPrimary);

        UIManager.put("ComboBox.disabledForeground", appdesign.Color_FontPrimary); // Farbe für inaktiven Zustand
        UIManager.put("ComboBox.foreground", appdesign.Color_FontPrimary); 

        
        // setUI(new BasicComboBoxUI());
        setUI(new CustomComboBoxUI(new ImageIcon("src/com/swingdating/assets/Icon_DropdownmenuArrow.png"), appdesign));
        setForeground(appdesign.Color_FontPrimary);
        setBackground(appdesign.Color_BackgroundContainer);
        setBorder(new RoundedCornerBorder());
        addPopupMenuListener(new HeavyWeightContainerListener());
        configurePopup();
        setPreferredSize(new Dimension(appdesign.inputFieldWidth, appdesign.inputFieldHeight));
        setSize(new Dimension(appdesign.inputFieldWidth, appdesign.inputFieldHeight));
        setMaximumSize(new Dimension(appdesign.inputFieldWidth, appdesign.inputFieldHeight));

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // c.setBackground(isSelected ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer);
                c.setForeground(isSelected ? appdesign.Color_FontSecondary : appdesign.Color_FontPrimary);
                c.setBackground(appdesign.Color_BackgroundContainer);
                // c.setForeground(appdesign.Color_FontPrimary);
                return c;
            }
        });

        addPopupMenuListener(new ToggleRotationListener());

    }

    private void configurePopup() {
        Object o = getAccessibleContext().getAccessibleChild(0);
        if (o instanceof JComponent) {
            JComponent popup = (JComponent) o;
            popup.setBorder(new RoundedCornerBorder());
            popup.setForeground(appdesign.Color_BorderLight);
            popup.setBackground(appdesign.Color_BackgroundContainer);
        }
    }
    
    private static class HeavyWeightContainerListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            EventQueue.invokeLater(() -> {
                JComboBox combo = (JComboBox) e.getSource();
                Accessible a = combo.getUI().getAccessibleChild(combo, 0);
                if (a instanceof BasicComboPopup) {
                    BasicComboPopup popup = (BasicComboPopup) a;
                    Container top = popup.getTopLevelAncestor();
                    if (top instanceof JWindow) {
                        ((JWindow) top).setBackground(new Color(0x0, true));
                    }
                }
            });
        }
    
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {}
    }
    
    private static class RoundedCornerBorder extends AbstractBorder {
    
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = 16;
            int w = width - 1;
            int h = height - 1;
    
            Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, r, r));
            if (c instanceof JPopupMenu) {
                g2.setPaint(c.getBackground());
                g2.fill(round);
            } else {
                Container parent = c.getParent();
                if (Objects.nonNull(parent)) {
                    g2.setPaint(new AppDesign(true).Color_BackgroundMain);
                    Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                    corner.subtract(round);
                    g2.fill(corner);
                }
            }
            g2.setPaint(new AppDesign(true).Color_BorderLight);
            g2.draw(round);
            g2.dispose();
        }
    
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(4, 8, 4, 8);
            return insets;
        }
    }
   
    private class ToggleRotationListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            CustomComboBoxUI ui = (CustomComboBoxUI) getUI();
            ui.rotateIcon(true);
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            CustomComboBoxUI ui = (CustomComboBoxUI) getUI();
            ui.rotateIcon(false);
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {}
    }
   
}


class CustomComboBoxUI extends BasicComboBoxUI {
    private final ImageIcon icon;
    private JButton arrowButton;
    private boolean isRotated = false;
    private AppDesign appdesign;

    public CustomComboBoxUI(ImageIcon icon, AppDesign appdesign) {
        this.icon = scaleIcon(icon, 14, 11); // Skaliert das Icon auf eine Größe von 24x24 Pixel
        this.appdesign = appdesign;
    }

    @Override
    protected JButton createArrowButton() {
        arrowButton = new JButton(icon);
        arrowButton.setBorder(BorderFactory.createEmptyBorder());
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        
        return arrowButton;
    }

    public void rotateIcon(boolean rotate) {
        arrowButton.setIcon(getRotatedIcon(icon, rotate ? 180 : 0));
        isRotated = rotate;
    }

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private ImageIcon getRotatedIcon(ImageIcon icon, int angle) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        Image image = icon.getImage();

        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(angle), w / 2.0, h / 2.0);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(rotatedImage);
    }
}