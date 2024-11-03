package com.swingdating;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.swingdating.Components.Titlebar;
import com.swingdating.System.AppDesign;
import com.swingdating.System.DBManagerSQLite;
import com.swingdating.pages.PageHome;
import com.swingdating.pages.PageLogin;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class App extends JFrame {

    static DBManagerSQLite dbmanager = new DBManagerSQLite("jdbc:sqlite:src/schule_erweitert.db");
    private boolean darkmodeenabled = true;
    public AppDesign appdesign = new AppDesign(darkmodeenabled);
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    // Layout setup
    private Titlebar titlebar;    
    private static JPanel mainPanel;
    private static CardLayout mainPanelCardLayout;
    private List<JPanel> mainPanelPages = new ArrayList<>();

    // Resizeable Frame stuff
    private int prevMX;
    private int prevMY;
    private boolean resizing_horz = false;
    private boolean resizing_vert = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

    public App() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 500);
        setMinimumSize(new Dimension(750, 500));
        
        Dimension screenSize = toolkit.getScreenSize();
        setDefaultWindowShape();

        setLocation((int) (screenSize.getWidth()/2)-(getWidth()/2), (int) (screenSize.getHeight()/2)-(getHeight()/2));

        mainPanelCardLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelCardLayout);
        createPages(); // Muss nach der Layout- und Panel-Erstellung ausgeführt werden

        // Add All Panel Pages to main CaryLayoutPanel
        for (JPanel jPanelPage : mainPanelPages) {
            mainPanel.add(jPanelPage, jPanelPage.getName());
        }

        // Load first Panlepage
        mainPanelCardLayout.show(mainPanel, mainPanelPages.get(0).getName());

        add(mainPanel, BorderLayout.CENTER); // Hauptpanel im Zentrum hinzufügen

        // Create Titlebar
        titlebar = new Titlebar(this, appdesign, mainPanelPages.get(0).getName());

        add(titlebar, BorderLayout.NORTH);

        setBackground(appdesign.Color_BackgroundMain);






        // Resize Eventlisteners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize && Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize) {
                    resizing_horz = true;
                    resizing_vert = true;
                } else if (Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize) {
                    resizing_horz = true;
                    resizing_vert = false;
                } else if (Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize) {
                    resizing_horz = false;
                    resizing_vert = true;
                }
                prevMX = e.getX();
                prevMY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing_horz = false;
                resizing_vert = false;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.DEFAULT_CURSOR);
                super.mouseExited(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize && Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize) {
                    setCursor(Cursor.SE_RESIZE_CURSOR);
                } else if (Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize) {
                    setCursor(Cursor.E_RESIZE_CURSOR);
                } else if (Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize) {
                    setCursor(Cursor.S_RESIZE_CURSOR);
                } else {
                    setCursor(Cursor.DEFAULT_CURSOR);
                }
                super.mouseMoved(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing_horz && resizing_vert) {
                    int deltaX = e.getX() - prevMX;
                    int deltaY = e.getY() - prevMY;

                    int newWidth = getWidth() + deltaX;
                    int newHeight = getHeight() + deltaY;

                    setSize(newWidth, newHeight);
                    updateDefaultWindowShape();                        

                    prevMX = e.getX();
                    prevMY = e.getY();
                } else if (resizing_horz) {
                    int deltaX = e.getX() - prevMX;
                    int newWidth = getWidth() + deltaX;
                    if (newWidth > 100) {
                        setSize(newWidth, getHeight());
                        updateDefaultWindowShape();
                    }
                    prevMX = e.getX();
                } else if (resizing_vert) {
                    int deltaY = e.getY() - prevMY;
                    int newHeight = getHeight() + deltaY;
                    if (newHeight > 100) {
                        setSize(getWidth(), newHeight);
                        updateDefaultWindowShape();
                    }
                    prevMY = e.getY();
                }
            }
        });
        
        // setOpacity(0.5f);
        setVisible(true);
    }

    private void createPages() {
        mainPanelPages.add(new PageLogin(appdesign));
        mainPanelPages.add(new PageHome(appdesign));
    }

    private void setDefaultWindowShape() {
        setShape(appdesign.getDefaultWindowsShape(getWidth(), getHeight()));
    }
    public static void setWindowShape(Shape shape) {
        App appInstance = (App) SwingUtilities.getWindowAncestor(mainPanel);
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.setShape(shape);
        }
    }
    public static void updateDefaultWindowShape() {
        App appInstance = (App) SwingUtilities.getWindowAncestor(mainPanel);
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.setShape(appInstance.appdesign.getDefaultWindowsShape(appInstance.getWidth(), appInstance.getHeight()));
        }
    }

    public static void switchToPage(String pageName) {
        mainPanelCardLayout.show(mainPanel, pageName);
        setWindowsTitle(pageName);
    }

    public static void setWindowsTitle(String title) {
        App appInstance = (App) SwingUtilities.getWindowAncestor(mainPanel);
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.titlebar.setTitle(title);
        }
    }

    public static App getAppInstance() {
        return (App) SwingUtilities.getWindowAncestor(mainPanel);
    }

}
