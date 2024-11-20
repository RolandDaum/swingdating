package com.swingdating.Pages.RegisterSubTiles;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.swingdating.App;
import com.swingdating.Components.ScrollBarUI;
import com.swingdating.System.AppDesign;
import com.swingdating.System.AppUser;

public class RST_Layout extends JScrollPane {
    public JPanel rootpanel;
    public AppUser appuser = App.getAppUser();
    public Runnable onSubmit;
    public RST_Layout(AppDesign appdesign) {
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(null);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        rootpanel = new JPanel();
        rootpanel.setLayout(new BoxLayout(rootpanel, BoxLayout.Y_AXIS));

        rootpanel.setOpaque(false);

        setViewportView(rootpanel);

        getVerticalScrollBar().setUI(new ScrollBarUI());
    }

    /**
     * Returns true if the inserted data is valid and save to use. Only then continue.
     * @return Default return value is false, to prevent currupted data from being inserted into AppUser obj and later into the database
     */
    public boolean valid() {
        return false;
    }
    
    public void rootAdd(Component comp) {
        rootpanel.add(comp);
    }

    public void onSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }
}
