package com.swingdating.Pages;
import java.awt.BasicStroke;
import java.awt.Component;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import com.swingdating.App;
import com.swingdating.Components.Button;
import com.swingdating.Components.CheckBoxFHP;
import com.swingdating.Components.InputField;
import com.swingdating.Components.InputLabel;
import com.swingdating.Components.ProfileCardFHP;
import com.swingdating.Components.ScrollBarUI;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;
import com.swingdating.System.AppUserEnums.APU_Gender;
import com.swingdating.System.AppUserEnums.APU_Sexuality;

public class PageHome extends JSplitPane {
    public static String pagename = "SWINGDATING - HOME";

    private AppDesign appdesign;
    private AppUser appuser = App.getAppUser();

    private JPanel filterPanel;
    private JPanel matchesPannel;

    private CheckBoxFHP religionFCB;
    private InputField ageFIF;
    private CheckBoxFHP sexualityFCB;
    private CheckBoxFHP nationalityFCB;
    private CheckBoxFHP nearbyFCB;
    private CheckBoxFHP musicPrefFCB;
    private CheckBoxFHP subjectPrefFCB;


    public PageHome(AppDesign appdesign) {
        this.appdesign = appdesign;
        setName(pagename);
        // setDividerLocation(150);
        setResizeWeight(0.1);
        setBackground(appdesign.Color_BackgroundMain);
        setOpaque(true);

        setUI(new CustomSplitPaneUI(appdesign));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        filterPanel = new JPanel();
        filterPanel.setBackground(appdesign.Color_BackgroundMain);
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane leftScrollPane = createCustomScrollPane(filterPanel, appdesign);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScrollPane.setBorder(BorderFactory.createEmptyBorder());

        matchesPannel = new JPanel();
        matchesPannel.setBackground(appdesign.Color_BackgroundMain);
        matchesPannel.setBorder(BorderFactory.createEmptyBorder());
        matchesPannel.setLayout(new BoxLayout(matchesPannel, BoxLayout.Y_AXIS));
        JScrollPane rightScrollPane = createCustomScrollPane(matchesPannel, appdesign);
        rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightScrollPane.setBorder(BorderFactory.createEmptyBorder());

        addFilteItems();
        getMatches();

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

    private void addFilteItems() {
        float xalignment = Component.LEFT_ALIGNMENT;

        Button logoutbutton = new Button("LOGOUT", appdesign, new Dimension(175, 50), () -> App.switchToPage(PageLogin.pagename));
        logoutbutton.setAlignmentX(xalignment);
        filterPanel.add(logoutbutton);

        filterPanel.add(Box.createVerticalStrut(25));
    
        JLabel ageLabel = new InputLabel("   Max Age Difference", appdesign);
        ageLabel.setAlignmentX(xalignment);
        filterPanel.add(ageLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        ageFIF = new InputField(appdesign, "Input max age difference", true, new Dimension(175, 50));
        ageFIF.setAlignmentX(xalignment);
        ageFIF.onType((value) -> getMatches());
        filterPanel.add(ageFIF);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel sexLabel = new InputLabel("   Matching sexuality", appdesign);
        sexLabel.setAlignmentX(xalignment);
        filterPanel.add(sexLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        sexualityFCB = new CheckBoxFHP(appdesign, "Sexuality", new Insets(5, 10, 5, 10));
        sexualityFCB.setAlignmentX(xalignment);
        sexualityFCB.setOnChange(() -> getMatches());
        filterPanel.add(sexualityFCB);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel religionLabel = new InputLabel("   Matching religion", appdesign);
        religionLabel.setAlignmentX(xalignment);
        filterPanel.add(religionLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        religionFCB = new CheckBoxFHP(appdesign, "Religion", new Insets(5, 10, 5, 10));
        religionFCB.setAlignmentX(xalignment);
        religionFCB.setOnChange(() -> getMatches());
        filterPanel.add(religionFCB);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel nationalityLabel = new InputLabel("   Matching nationality", appdesign);
        nationalityLabel.setAlignmentX(xalignment);
        filterPanel.add(nationalityLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        nationalityFCB = new CheckBoxFHP(appdesign, "Nationality", new Insets(5, 10, 5, 10));
        nationalityFCB.setAlignmentX(xalignment);
        nationalityFCB.setOnChange(() -> getMatches());
        filterPanel.add(nationalityFCB);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel nearbyLabel = new InputLabel("   Match nearby", appdesign);
        nearbyLabel.setAlignmentX(xalignment);
        filterPanel.add(nearbyLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        nearbyFCB = new CheckBoxFHP(appdesign, "Nearby", new Insets(5, 10, 5, 10));
        nearbyFCB.setAlignmentX(xalignment);
        nearbyFCB.setOnChange(() -> getMatches());
        filterPanel.add(nearbyFCB);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel subjectLabel = new InputLabel("   Matching subject preferences", appdesign);
        subjectLabel.setAlignmentX(xalignment);
        filterPanel.add(subjectLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        subjectPrefFCB = new CheckBoxFHP(appdesign, "Subject", new Insets(5, 10, 5, 10));
        subjectPrefFCB.setAlignmentX(xalignment);
        subjectPrefFCB.setOnChange(() -> getMatches());
        filterPanel.add(subjectPrefFCB);

        filterPanel.add(Box.createVerticalStrut(25));

        JLabel musicLabel = new InputLabel("   Matching music preferences", appdesign);
        musicLabel.setAlignmentX(xalignment);
        filterPanel.add(musicLabel);
        filterPanel.add(Box.createVerticalStrut(10));
        musicPrefFCB = new CheckBoxFHP(appdesign, "Music", new Insets(5, 10, 5, 10));
        musicPrefFCB.setAlignmentX(xalignment);
        musicPrefFCB.setOnChange(() -> getMatches());
        filterPanel.add(musicPrefFCB);
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

    private void getMatches() {
        String sqlString = "SELECT UUID FROM appusers WHERE ";
        sqlString += "UUID != '" + appuser.getUUID() + "' ";
        if (!ageFIF.getValue().isEmpty())  {
            sqlString += "AND ABS(CAST(strftime('%Y', '" + appuser.getBirthDate() + "') AS INTEGER) - CAST(substr(birth_date, 1, 4) AS INTEGER)) <= " + Integer.parseInt(ageFIF.getValue()) + " ";
        }
        if (religionFCB.isSelected()) {
            sqlString += "AND religion = '" + appuser.getReligion().getCode() + "' ";
        }
        if (sexualityFCB.isSelected()) {
            sqlString += "AND sexuality = '" + appuser.getSexuality().getCode() + "' ";
            APU_Gender ownGender = appuser.getGender();
            APU_Gender oppositGender;
            if (ownGender.equals(APU_Gender.MALE)) {
                oppositGender = APU_Gender.FEMALE;
            } else {
                oppositGender = APU_Gender.MALE;
            }
    
            switch (appuser.getSexuality()) {
                case APU_Sexuality.NORMAL:
                    sqlString += "AND gender = '" + oppositGender.getCode() + "' ";
                    break;
                case APU_Sexuality.BOTH:
                    break;
                case APU_Sexuality.SAME:
                    sqlString += "AND gender = '" + ownGender.getCode() + "' ";
                    break;
                default:
                    return;
            }
        }
        if (nationalityFCB.isSelected()) {
            sqlString += "AND nationality = '" + appuser.getNationality().getCode() + "' ";
        }
        if (nearbyFCB.isSelected()) {
            sqlString += "AND postal_code = '" + appuser.getPostalCode() + "' ";
        }
        if (musicPrefFCB.isSelected()) {
            sqlString += "AND music_preference = '" + appuser.getMusicPreference().getCode() + "' ";
        }
        if (subjectPrefFCB.isSelected()) {
            sqlString += "AND favorite_subject = '" + appuser.getFavoriteSubject().getCode() + "' ";
        }
        String[][] matchesUUIDs = App.db.get(sqlString);

        matchesPannel.removeAll();
        if (matchesUUIDs.length <= 1) {
            InputLabel ipl = new InputLabel("NO MATCHES FOUND", appdesign);
            ipl.setAlignmentX(Component.CENTER_ALIGNMENT);
            matchesPannel.add(ipl);
            return;
        }
        App frame = App.getAppInstance();
        InputLabel ipl = new InputLabel(matchesUUIDs.length-1 + " - MATCHES", appdesign);
        ipl.setAlignmentX(Component.CENTER_ALIGNMENT);
        matchesPannel.add(ipl);
        for (int i = 1; i < matchesUUIDs.length; i++) {
            matchesPannel.add(new ProfileCardFHP(AppUser.getAppUserByUUID(matchesUUIDs[i][0]), frame));
            matchesPannel.add(Box.createVerticalStrut(20));
        }
        matchesPannel.updateUI();
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