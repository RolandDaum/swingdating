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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    private boolean resizing_horzL = false;
    private boolean resizing_horzR = false;
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
                prevMX = e.getX();
                prevMY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing_horzL = false;
                resizing_horzR = false;
                resizing_vert = false;
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    return;
                }
                resizing_horzL = e.getX() < appdesign.windowDragableBorderSize ? true : false;
                resizing_horzR = Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize ? true : false;
                resizing_vert = Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize ? true : false;
                if (!resizing_horzL && resizing_horzR && resizing_vert) {
                    setCursor(Cursor.SE_RESIZE_CURSOR);
                } else if (resizing_horzL && !resizing_horzR && resizing_vert) {
                    setCursor(Cursor.SW_RESIZE_CURSOR);
                } else if (!resizing_horzL && resizing_horzR && !resizing_vert) {
                    setCursor(Cursor.E_RESIZE_CURSOR);
                } else if (!resizing_horzL && !resizing_horzR && resizing_vert) {
                    setCursor(Cursor.S_RESIZE_CURSOR);
                } else if (resizing_horzL && !resizing_horzR && !resizing_vert) {
                    setCursor(Cursor.W_RESIZE_CURSOR);
                } else {
                    setCursor(Cursor.DEFAULT_CURSOR);
                }
                super.mouseMoved(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                // Sout-East
                if (!resizing_horzL && resizing_horzR && resizing_vert) {
                    int deltaY = e.getY() - prevMY;
                    int deltaX = e.getX() - prevMX;

                    int newWidth = getWidth() + deltaX;
                    int newHeight = getHeight() + deltaY;

                    if (newWidth > getMinimumSize().getWidth() && newHeight > getMinimumSize().getHeight()) {
                        setSize(newWidth, newHeight);
                    }

                    prevMX = e.getX();
                    prevMY = e.getY();
                } 
                // South West
                else if (resizing_horzL && !resizing_horzR && resizing_vert) {
                    int deltaX = e.getX() - prevMX;
                    int deltaY = e.getY() - prevMY;

                    int newWidth = getWidth() - deltaX;
                    int newHeight = getHeight() + deltaY;

                    if (newWidth > getMinimumSize().getWidth() && newHeight > getMinimumSize().getHeight() ) {
                        System.out.println("now: " + Math.abs(newWidth-getWidth()));
                        if (Math.abs(newWidth-getWidth()) >= 1) {
                            setLocation(getX() + deltaX, getY());
                        }
                        setSize(newWidth, newHeight);
                    }

                    prevMX = e.getX();
                    prevMY = e.getY();
                }
                // East
                else if (!resizing_horzL && resizing_horzR && !resizing_vert) {
                    int deltaX = e.getX() - prevMX;

                    int newWidth = getWidth() + deltaX;
                    if (newWidth > getMinimumSize().getWidth()) {
                        setSize(newWidth, getHeight());
                    }
                    prevMX = e.getX();
                } 
                // West
                else if (resizing_horzL && !resizing_horzR && !resizing_vert) {
                    int deltaX = e.getX() - prevMX;
                    int newWidth = getWidth() - deltaX;
                    if (newWidth > getMinimumSize().getWidth()) {
                        if (Math.abs(newWidth-getWidth()) >= 1) {
                            setLocation(getX() + deltaX, getY());
                        }
                        setSize(newWidth, getHeight());
                    }
                    prevMX = e.getX();
                }
                // South
                else if (!resizing_horzL && !resizing_horzR && resizing_vert) {
                    int deltaY = e.getY() - prevMY;
                    int newHeight = getHeight() + deltaY;
                    if (newHeight > getMinimumSize().getHeight()) {
                        setSize(getWidth(), newHeight);
                    }
                    prevMY = e.getY();
                
            
                }
        }});
        
        // Window size change listener (Rounden window corners)
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                    setFullscreenWindowShape();
                } else {
                    setDefaultWindowShape();
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
    private void setFullscreenWindowShape() {
        setWindowShape(appdesign.getFullscreenWindowsShape(getWidth(), getHeight()));  
    }
    public static void setWindowShape(Shape shape) {
        App appInstance = (App) SwingUtilities.getWindowAncestor(mainPanel);
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.setShape(shape);
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