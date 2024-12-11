package com.swingdating.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import com.swingdating.App;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUserEnums.APU_Enum;


public class DropDownMenu<T extends APU_Enum> extends JComboBox<String> {
    private AppDesign appdesign = App.getAppDesign();
    private APU_Enum[] enumValues;

    // Constructor
    public DropDownMenu(AppDesign appdesign, APU_Enum[] enumValues) {
        super(getEnumNames(enumValues));
        this.enumValues = enumValues;

        // Overrides some default ComboBox colors -> Fuck Swing
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

        UIManager.put("ComboBox.disabledForeground", appdesign.Color_FontPrimary); // Color on inactive state
        UIManager.put("ComboBox.foreground", appdesign.Color_FontPrimary); 

        
        setUI(new CustomeComboBoxButtonUI(new ImageIcon("src/com/swingdating/assets/Icon_DropdownmenuArrow.png"), appdesign));
        setForeground(appdesign.Color_FontPrimary);
        setBackground(appdesign.Color_BackgroundContainer);
        setBorder(new RoundedCornerBorder());
        addPopupMenuListener(new HeavyWeightContainerListener());
        configurePopup();
        setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), appdesign.inputFieldHeight));
        setAlignmentX(LEFT_ALIGNMENT);
        setFont(new Font("Roboto Bold", Font.PLAIN, 16));

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setForeground(isSelected ? appdesign.Color_FontPrimary : appdesign.Color_FontSecondary);
                c.setBackground(appdesign.Color_BackgroundContainer);
                if (c instanceof JComponent) {
                    JComponent jc = (JComponent) c;
                    jc.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
                    jc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
                return c;
            }
        });

        addPopupMenuListener(new CustomPopupMenuListener());

    }
    
    /**
     * @return APU_Enum which is currently selected in the DropDownMenu
     */
    public APU_Enum getSelectedEnum() {
        return (APU_Enum) enumValues[getSelectedIndex()];    
    }

    /**
     * Converts the APU_Enum List into a String list which then can be used as to construct the framework provided DropDownMenu which only accept String Values I guess
     * @param <T> APU_Enum Adapter Type
     * @param enumValues APU_Enum object list
     * @return String[] with the names provided by the getName() method of APU_Enum Adapter Type
     */
    private static <T extends APU_Enum> String[] getEnumNames(T[] enumValues) {
        String[] names = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            names[i] = enumValues[i].getName();
        }
        return names;
    }
    
    /**
     * IDK. Does some Popup configuration stuff
     */
    private void configurePopup() {
        Object o = getAccessibleContext().getAccessibleChild(0);
        if (o instanceof JComponent) {
            JComponent popup = (JComponent) o;
            popup.setBorder(new RoundedCornerBorder());
            popup.setForeground(appdesign.Color_BorderLight);
            popup.setBackground(appdesign.Color_BackgroundContainer);
        }
    }
    
    /**
     * Custome PopupMenuListener Class
     * Extens the implemented one by customes Eventlistener
     * Primarly used for UI update stuff I guess
     */
    private class HeavyWeightContainerListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            EventQueue.invokeLater(() -> {
                JComboBox<APU_Enum> combo = (JComboBox<APU_Enum>) e.getSource();
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
    
    /**
     * Custome AbstractBorder Class 
     * Only used for UI decoration
     */
    private static class RoundedCornerBorder extends AbstractBorder {
        AppDesign appdesign = App.getAppDesign();
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = appdesign.BorderRadiusComponents;
            int w = width - 1;
            int h = height - 1;
    
            Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, r, r));
            if (c instanceof JPopupMenu) {
                g2.setPaint(c.getBackground()); // Background of the dropdown list
                g2.fill(round);
            } else {
                Container parent = c.getParent();
                if (Objects.nonNull(parent)) {
                    g2.setPaint(appdesign.Color_BackgroundMain); // Color of the "corners" from the main combobox
                    Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                    corner.subtract(round);
                    g2.fill(corner);
                }
            }
            g2.setPaint(appdesign.Color_BorderLight);
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
   
    /**
     * Oh what a supprise. A second custome PopupMenuLister Class apeared. I wonder why I've roud two of them. IDK, IDC 
     */
    private class CustomPopupMenuListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            CustomeComboBoxButtonUI ui = (CustomeComboBoxButtonUI) getUI();
            ui.rotateIcon(true);
            customizeComboBoxScrollBar((JComboBox<?>) e.getSource());
        }
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            CustomeComboBoxButtonUI ui = (CustomeComboBoxButtonUI) getUI();
            ui.rotateIcon(false);
        }
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {}
    }
    /**
     * Edits the comboBox parameters ScrollBar UI appereance
     * @param comboBox JComboBox<?>
     */
    private void customizeComboBoxScrollBar(JComboBox<?> comboBox) {
        SwingUtilities.invokeLater(() -> {
            BasicComboPopup popup = (BasicComboPopup) comboBox.getAccessibleContext().getAccessibleChild(0);
            JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

            JScrollBar customScrollBar = new JScrollBar(JScrollBar.VERTICAL);
            customScrollBar.setPreferredSize(new Dimension(8, customScrollBar.getHeight()));
            // customScrollBar.setBackground(Color.LIGHT_GRAY);
            customScrollBar.setUI(new ScrollBarUI(true)); 

            scrollPane.setVerticalScrollBar(customScrollBar);
        });
    }
}

/**
 * Custome BasicComboBoxUI class
 * Only used for UI decorations (Includes custome Arrow Button and other stuff I don't know any more)
 */
class CustomeComboBoxButtonUI extends BasicComboBoxUI {
    private final ImageIcon icon;
    private JButton arrowButton;

    public CustomeComboBoxButtonUI(ImageIcon icon, AppDesign appdesign) {
        this.icon = scaleIcon(icon, 14, 11); // Skaliert das Icon auf eine Größe von 24x24 Pixel
    }

    @Override
    protected JButton createArrowButton() {
        arrowButton = new JButton(icon);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setBorder(null);

        return arrowButton;
    }

    public void rotateIcon(boolean rotate) {
        arrowButton.setIcon(getRotatedIcon(icon, rotate ? 180 : 0));
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