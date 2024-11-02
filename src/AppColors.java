import java.awt.Color;

public class AppColors {
    Color primaryBackground;
    public AppColors(boolean darkmodeenabled) {
        if (darkmodeenabled) {
            this.primaryBackground = new Color(32,32,32);
        }
        else {
            this.primaryBackground = new Color(232,232,232);
        }
    }
}
