package  com.swingdating.System;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AppDesign {
    // COLORS
    public Color Color_BackgroundMain;
    public Color Color_BackgroundContainer;
    public Color Color_BackgroundOnContainer;
    public Color Color_FontPrimary;
    public Color Color_FontSecondary;
    public Color Color_AccentPrimary;
    public Color Color_AccentSecondary;
    public Color Color_AccentTertiary;
    public Color Color_BorderDark;
    public Color Color_BorderLight;

    // SIZES
    public int titlebarHeight = 35;
    public int windowDragableBorderSize = 10;
    public int inputFieldHeight = 45;
    public int inputFieldWidth = 225;
    
    // ASSETS
    public String AssetName_Icon_Titlebar_Exitmaximize = "Icon_Titlebar_ExitMaximize.png";
    public String AssetName_Icon_Titlebar_Close = "Icon_Titlebar_Close.png";
    public String AssetName_Icon_Titlebar_Maximize = "Icon_Titlebar_Maximize.png";
    public String AssetName_Icon_Titlebar_Minimize = "Icon_Titlebar_Minimize.png";
    public Character PasswordFiledChar = '*';

    public Boolean darkmodeenabled;

    private List<String> fontlist = new ArrayList<>(Arrays.asList(
        "Orbitron-Black.ttf",
        "Orbitron-Bold.ttf",
        "Orbitron-ExtraBold.ttf",
        "Orbitron-Medium.ttf",
        "Orbitron-Regular.ttf",
        "Orbitron-SemiBold.ttf",
        "Orbitron.ttf",
        "RobotoBlack.ttf",
        "RobotoBlackItalic.ttf",
        "RobotoBold.ttf",
        "RobotoBoldItalic.ttf",
        "RobotoItalic.ttf",
        "RobotoLight.ttf",
        "RobotoLightItalic.ttf",
        "RobotoMedium.ttf",
        "RobotoMediumItalic.ttf",
        "RobotoRegular.ttf",
        "RobotoThin.ttf",
        "RobotoThinItalic.ttf"
    ));
    public Map<String,Font> fonts = new HashMap<>();

    public AppDesign(boolean darkmodeenabled) {
        this.darkmodeenabled = darkmodeenabled;

        // Load all fonts
        for (String fontname : fontlist) {
            try {
                Font tmpfont = Font.createFont(Font.TRUETYPE_FONT, new File("src/com/swingdating/assets/font/" + fontname));
                System.out.println(tmpfont.getFontName());
                fonts.put(tmpfont.getFontName(), tmpfont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }

        // set colors
        if (darkmodeenabled) {
            this.Color_BackgroundMain = new Color(8,8,8);
            this.Color_BackgroundContainer = new Color(21,21,21);
            this.Color_BackgroundOnContainer = new Color(32,32,32);
            this.Color_FontPrimary =  new Color(177,177,176);
            this.Color_FontSecondary =  new Color(255,255,255);
            this.Color_AccentPrimary =  new Color(242,235,210);
            this.Color_AccentSecondary =  new Color(249,4,6);
            this.Color_AccentTertiary =  new Color(246,182,83);
            this.Color_BorderDark =  new Color(7,7,7);
            this.Color_BorderLight =  new Color(34,34,34);
        }
        else {
            this.Color_BackgroundMain = new Color(248, 248, 248);
            this.Color_BackgroundContainer = new Color(235, 235, 235);
            this.Color_BackgroundOnContainer = new Color(224, 224, 224);
            this.Color_FontPrimary = new Color(77, 77, 76);
            this.Color_FontSecondary = new Color(0, 0, 0);
            this.Color_AccentPrimary = new Color(92, 85, 60);
            this.Color_AccentSecondary = new Color(200, 24, 6);
            this.Color_AccentTertiary = new Color(196, 132, 43);
            this.Color_BorderDark = new Color(180, 180, 180);
            this.Color_BorderLight = new Color(220, 220, 220);
        }
    }



    // x: 0, y: 0 - Start/Location Coordinates
    public Shape getFullscreenWindowsShape(int width, int height) {
        return new RoundRectangle2D.Double(0, 0, width, height, 0,0);
    }
    public Shape getDefaultWindowsShape(int width, int height) {
        return new RoundRectangle2D.Double(0, 0, width, height, this.titlebarHeight, this.titlebarHeight);
    }
    public Shape getDefaultWindowsShape(int x, int y, int width, int height) {
        return new RoundRectangle2D.Double(x, y, width, height, this.titlebarHeight, this.titlebarHeight);
    }

}
