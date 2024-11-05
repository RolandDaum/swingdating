package com.swingdating;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
import java.io.File;
import java.io.IOException;

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
    private JPanel rootpanel;

    // Resizeable Frame stuff
    private int prevMX;
    private int prevMY;
    private boolean resizing_horzL = false;
    private boolean resizing_horzR = false;
    private boolean resizing_vertB = false;
    private boolean resizing_vertT = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }

    public App() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(500, 500);
        setMinimumSize(new Dimension(750, 500));
        setTitle("SwingDating");
        try {
            setIconImage(ImageIO.read(new File("src/com/swingdating/assets/icon.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Place in center of screen at start
        Dimension screenSize = toolkit.getScreenSize();
        setLocation((int) (screenSize.getWidth()/2)-(getWidth()/2), (int) (screenSize.getHeight()/2)-(getHeight()/2));

        // Create Rootpanel
        rootpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), appdesign.titlebarHeight, appdesign.titlebarHeight));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.inputFieldHeight, appdesign.inputFieldHeight);
            }
        };
        rootpanel.setOpaque(true);
        rootpanel.setBounds(2, 2, getWidth() - 4, getHeight() - 4);
        rootpanel.setLayout(new BorderLayout());

        // Create Main Pages Stuff
        mainPanelCardLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelCardLayout);
        createPages(); // Muss nach der Layout- und Panel-Erstellung ausgeführt werden

        // Add All Panel Pages to main CaryLayoutPanel
        for (JPanel jPanelPage : mainPanelPages) {
            mainPanel.add(jPanelPage, jPanelPage.getName());
        }

        // Load first Page from Panel list
        mainPanelCardLayout.show(mainPanel, mainPanelPages.get(0).getName());

        // Create Titlebar
        titlebar = new Titlebar(this, appdesign, mainPanelPages.get(0).getName());

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
                // TODO: Add top vertical rezisement
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
                if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    setFullscreenWindowShape();
                } else {
                    setDefaultWindowShape();
                }
            }
        });
        setDefaultWindowShape();

        // setOpacity(0.5f);
        setVisible(true);
    }


    private void setDefaultWindowShape() {
        setShape(appdesign.getDefaultWindowsShape(getWidth(), getHeight()));
        getContentPane().setBackground(appdesign.Color_BorderLight);
        rootpanel.setBackground(appdesign.Color_BorderLight);
        rootpanel.setBounds(2, 2, getWidth() - 4, getHeight() - 4);
        rootpanel.revalidate();
        rootpanel.repaint();

    }
    private void setFullscreenWindowShape() {
        setShape(appdesign.getFullscreenWindowsShape(getWidth(), getHeight()));
        rootpanel.setBackground(appdesign.Color_BackgroundMain);
        rootpanel.setBounds(0, 0, getWidth(), getHeight());
        rootpanel.revalidate();
        rootpanel.repaint();
    }
    public static void setWindowShape(Shape shape) {
        App appInstance = (App) SwingUtilities.getWindowAncestor(mainPanel);
        if (appInstance != null && appInstance.titlebar != null) {
            appInstance.setShape(shape);
        }
    }
    
    private void createPages() {
        mainPanelPages.add(new PageLogin(appdesign));
        mainPanelPages.add(new PageHome(appdesign));
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