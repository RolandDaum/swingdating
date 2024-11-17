package com.swingdating.Pages;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import com.swingdating.App;
import com.swingdating.Components.CheckBoxFHP;
import com.swingdating.Components.ProfileCardFHP;
import com.swingdating.Components.ScrollBarUI;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;

public class PageHome extends JSplitPane {
    public static String pagename = "SWINGDATING - HOME";

    private AppDesign appdesign;
    private AppUser appuser = App.getAppUser();

    public PageHome(AppDesign appdesign) {
        this.appdesign = appdesign;
        setName(pagename);
        // setDividerLocation(150);
        setResizeWeight(0.2);
        setBackground(appdesign.Color_BackgroundMain);
        setOpaque(true);

        setUI(new CustomSplitPaneUI(appdesign));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(appdesign.Color_BackgroundMain);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane leftScrollPane = createCustomScrollPane(leftPanel, appdesign);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScrollPane.setBorder(BorderFactory.createEmptyBorder());

        for (int i = 1; i <= 20; i++) {
            CheckBoxFHP checkBox = new CheckBoxFHP(appdesign, "   Item " + i, new Insets(5, 10, 5,10));
            leftPanel.add(checkBox);
            leftPanel.add(Box.createVerticalStrut(20));
        }

        
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(appdesign.Color_BackgroundMain);
        rightPanel.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JScrollPane rightScrollPane = createCustomScrollPane(rightPanel, appdesign);
        rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightScrollPane.setBorder(BorderFactory.createEmptyBorder());

        App frame = App.getAppInstance();
        for (int i = 1; i <= 10; i++) {
            rightPanel.add(new ProfileCardFHP(appuser, frame));
            rightPanel.add(Box.createVerticalStrut(20));
        }

        setLeftComponent(leftScrollPane);
        setRightComponent(rightScrollPane);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
        g2.setColor(appdesign.Color_BorderLight);
        g2.setStroke(new BasicStroke(appdesign.BorderThickness));
        g2.drawRoundRect(appdesign.BorderThickness/2, appdesign.BorderThickness/2, getWidth()- appdesign.BorderThickness, getHeight() - appdesign.BorderThickness, appdesign.BorderRadiusWindow, appdesign.BorderRadiusWindow);
        super.paintComponent(g2);
        g2.dispose();
    }

    private JScrollPane createCustomScrollPane(JPanel panel, AppDesign appdesign) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Vertikale Scrollbar anpassen
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        verticalBar.setUI(new ScrollBarUI());
        horizontalBar.setUI(new ScrollBarUI());
        verticalBar.setPreferredSize(new Dimension(12, Integer.MAX_VALUE));
        return scrollPane;
    }
}

class CustomSplitPaneUI extends BasicSplitPaneUI {
    private boolean isHovered = false;
    private boolean isMoving = false;
    private AppDesign appdesign;
    
    public CustomSplitPaneUI(AppDesign appdesign) {
        this.appdesign = appdesign;
    }

    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isMoving ? appdesign.Color_BackgroundMain : appdesign.Color_BorderLight);

                Float roundedRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getWidth(), getWidth());
                g2.fill(roundedRect);
                
                // g2.setColor(isMoving ? appdesign.Color_BackgroundMain : appdesign.Color_BorderLight); // Border-Farbe
                // g2.setStroke(new BasicStroke(appdesign.BorderThickness*2));
                g2.draw(roundedRect); 
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(2, 100);
            }
        };
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        BasicSplitPaneDivider divider = getDivider();
        divider.addMouseMotionListener(new MouseMotionAdapter() {
 
            @Override
            public void mouseDragged(MouseEvent e) {
                App.updateWindow();
                isMoving = true; 
                divider.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                App.updateWindow();
                isMoving = false; 
                divider.repaint();
            }
        });
        divider.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                App.updateWindow();
                divider.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                divider.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                App.updateWindow();
                isHovered = false;
                divider.repaint();
            }
        });
    }
}