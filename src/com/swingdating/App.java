package com.swingdating;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.swingdating.Components.Titlebar;
import com.swingdating.Pages.PageHome;
import com.swingdating.Pages.PageLogin;
import com.swingdating.Pages.PageRegister;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;
import com.swingdating.System.DBManagerSQLite;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class App extends JFrame {
    public static DBManagerSQLite db = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/appdb.db");
    public AppUser appuser;

    private boolean darkmodeenabled = true;
    public AppDesign appdesign = new AppDesign(darkmodeenabled);
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    // Layout setup
    private Titlebar titlebar;    
    private JPanel mainPanel;
    private CardLayout mainPanelCardLayout;
    private static JPanel rootpanel;

    // Resizeable Frame stuff
    private int prevMX;
    private int prevMY;
    private boolean resizing_horzL = false;
    private boolean resizing_horzR = false;
    private boolean resizing_vertB = false;
    private boolean resizing_vertT = false; // Vetical Top resize is not implemented

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

    public App() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setMinimumSize(new Dimension(850, 500));
        setTitle("SwingDating");
        setBackground(appdesign.Color_BackgroundMain);

        try {
            setIconImage(ImageIO.read(new File("src/com/swingdating/assets/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Place in center of screen at start
        Dimension screenSize = toolkit.getScreenSize();
        setLocation((int) (screenSize.getWidth()/2)-(getWidth()/2), (int) (screenSize.getHeight()/2)-(getHeight()/2));

        // Create Rootpanel
        rootpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                // super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), appdesign.titlebarHeight, appdesign.titlebarHeight));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusWindow-appdesign.BorderThicknessWindow, appdesign.BorderRadiusWindow-appdesign.BorderThicknessWindow);
            }
        };
        rootpanel.setOpaque(true);
        rootpanel.setLayout(new BorderLayout());

        // Create Main Pages Stuff
        mainPanelCardLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelCardLayout);
        PageLogin pagelogin = new PageLogin(appdesign);
        mainPanel.add(pagelogin, pagelogin.getName());

        // Create Titlebar
        titlebar = new Titlebar(this, appdesign, pagelogin.getName());

        //  Add stuff together
        rootpanel.add(titlebar, BorderLayout.NORTH);
        rootpanel.add(mainPanel, BorderLayout.CENTER);

        add(rootpanel);

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
                resizing_vertB = false;
                resizing_vertT = false;
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @SuppressWarnings("deprecation")
            @Override
            public void mouseMoved(MouseEvent e) {
                if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    return;
                }
                resizing_horzL = e.getX() < appdesign.windowDragableBorderSize ? true : false;
                resizing_horzR = Math.abs(e.getX()-getWidth()) < appdesign.windowDragableBorderSize ? true : false;
                resizing_vertB = Math.abs(e.getY()-getHeight()) < appdesign.windowDragableBorderSize ? true : false;
                resizing_vertT = e.getY() < appdesign.windowDragableBorderSize ? true : false;
                
                if (!resizing_horzL && resizing_horzR && resizing_vertB) {
                    setCursor(Cursor.SE_RESIZE_CURSOR);
                } else if (resizing_horzL && !resizing_horzR && resizing_vertB) {
                    setCursor(Cursor.SW_RESIZE_CURSOR);
                } else if (!resizing_horzL && resizing_horzR && !resizing_vertB) {
                    setCursor(Cursor.E_RESIZE_CURSOR);
                } else if (!resizing_horzL && !resizing_horzR && resizing_vertB) {
                    setCursor(Cursor.S_RESIZE_CURSOR);
                } else if (resizing_horzL && !resizing_horzR && !resizing_vertB) {
                    setCursor(Cursor.W_RESIZE_CURSOR);
                } else {
                    setCursor(Cursor.DEFAULT_CURSOR);
                }
                super.mouseMoved(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                // Sout-East
                if (!resizing_horzL && resizing_horzR && resizing_vertB) {
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
                else if (resizing_horzL && !resizing_horzR && resizing_vertB) {
                    int deltaX = e.getX() - prevMX;
                    int deltaY = e.getY() - prevMY;

                    int newWidth = getWidth() - deltaX;
                    int newHeight = getHeight() + deltaY;

                    if (newWidth > getMinimumSize().getWidth() && newHeight > getMinimumSize().getHeight() ) {
                        if (Math.abs(newWidth-getWidth()) >= 1) {
                            setLocation(getX() + deltaX, getY());
                        }
                        setSize(newWidth, newHeight);
                    }

                    prevMX = e.getX();
                    prevMY = e.getY();
                }
                // East
                else if (!resizing_horzL && resizing_horzR && !resizing_vertB) {
                    int deltaX = e.getX() - prevMX;

                    int newWidth = getWidth() + deltaX;
                    if (newWidth > getMinimumSize().getWidth()) {
                        setSize(newWidth, getHeight());
                    }
                    prevMX = e.getX();
                } 
                // West
                else if (resizing_horzL && !resizing_horzR && !resizing_vertB) {
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
                else if (!resizing_horzL && !resizing_horzR && resizing_vertB) {
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
                updateWindow();
            }
        });
        updateWindow();

        // setOpacity(0.5f);
        setVisible(true);
        // switchToPage(PageHome.pagename);
    }

    // Methods used to updated the border and size of the Main JFrame border and Background
    private void setDefaultWindowShape() {
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusWindow, appdesign.BorderRadiusWindow));
        getContentPane().setBackground(appdesign.Color_BorderLight);
        rootpanel.setBackground(appdesign.Color_BorderLight);
        rootpanel.setBounds(appdesign.BorderThicknessWindow, appdesign.BorderThicknessWindow, getWidth() - (appdesign.BorderThicknessWindow*2), getHeight() - (appdesign.BorderThicknessWindow*2));
        revalidate();
        repaint();  
    }
    public void setFullscreenWindowShape() {
        setShape(appdesign.getFullscreenWindowsShape(getWidth(), getHeight()));
        // getContentPane().setBackground(appdesign.Color_BorderLight);
        rootpanel.setBackground(appdesign.Color_BackgroundMain);
        rootpanel.setBounds(0, 0, getWidth(), getHeight());
        revalidate();
        repaint();

    }
    /**
     * Call to update the window if somehow visual bugs appearing
     */
    public static void updateWindow() {
        App appInstance = getAppInstance();
        if (appInstance == null) {return;}
        if (appInstance.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            appInstance.setFullscreenWindowShape();
        } else {
            appInstance.setDefaultWindowShape();
        }
    }
    
    /**
     * @param pageName String which is equivalent to the 'Page[NAME].pagename'
     */
    public static void switchToPage(String pageName) {
        App appinstance = App.getAppInstance();
        try {   appinstance.mainPanel.remove(1);    } catch (Exception e) {}
        if (pageName.equals(PageHome.pagename)) {
            App.setWindowTitle(PageHome.pagename);
            appinstance.mainPanel.add(new PageHome(appinstance.appdesign), PageHome.pagename);
            appinstance.mainPanelCardLayout.show(appinstance.mainPanel, PageHome.pagename);
        } else if (pageName.equals(PageRegister.pagename)) {
            App.setWindowTitle(PageRegister.pagename);
            appinstance.mainPanel.add(new PageRegister(appinstance.appdesign), PageRegister.pagename);
            appinstance.mainPanelCardLayout.show(appinstance.mainPanel, PageRegister.pagename);;
        } else if (pageName.equals(PageLogin.pagename)) {
            App.setWindowTitle(PageLogin.pagename);
            App.removeGlobalUser();
            appinstance.mainPanelCardLayout.show(appinstance.mainPanel, PageLogin.pagename);;
        }
        updateWindow();
    }

    /**
     * @return current window title as String
     */
    public static String getWindowTitle() {
        App appInstance = getAppInstance();
        if (appInstance != null && appInstance.titlebar != null) {
            return appInstance.titlebar.getTitle();
        }
        return "";
    }
    /**
     * @param title as String to be set as the new window title
     */
    public static void setWindowTitle(String title) {
        App appInstance = getAppInstance();
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.titlebar.setTitle(title);
        }
        updateWindow();
    }
    /**
     * Global method to get the AppUser
     * @return the current AppUser Object stored in the main App instance
     */
    public static AppUser getAppUser() {
        App appinstance = App.getAppInstance();

        return (appinstance == null) ? null : appinstance.appuser;
    }
    /**
     * Global method to update the AppUser
     * @param appuser overrides the currently stored AppUser Object in the main App instance
     */
    public static void setAppUser(AppUser appuser) {
        removeGlobalUser();
        App.getAppInstance().appuser = appuser;
    }
    /**
     * Removes the stored AppUser and replace it with null.
     */
    public static void removeGlobalUser() {
        App.getAppInstance().appuser = null;
    }
    /**
     * Get the global AppDesign object if a page or component did not get it via a parameter
     * @return the globaly used AppDesign Object.
     */
    public static AppDesign getAppDesign() {
        App appinstance = App.getAppInstance();
        return appinstance.appdesign;
    }
    /**
     * Get the main AppInstance Object (JFrame) to edit attributes used globaly, e.g. AppUser
     * @return App main App instance
     */
    public static App getAppInstance() {
        return (App) SwingUtilities.getWindowAncestor(rootpanel);
    }    
}