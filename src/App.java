import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class App extends JFrame {

    DBManagerSQLite dbmanager = new DBManagerSQLite("jdbc:sqlite:src/schule_erweitert.db");
    boolean darkmodeenabled = true;
    AppColors appcolors = new AppColors(darkmodeenabled);
    public void main(String[] args) {
        String[][] value = dbmanager.get("select kursnummer from hatkurs where note = 15");
        for (int i = 0; i < value.length; i++) {
            for (int u = 0; u < value[i].length; u++) {
                System.out.println(value[i][u]);
            }
        }
    }

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(500,500);

        setAppColor();


        JButton button = new JButton("x");
        button.setBounds(0, 0, 30, 30);
        
        // Füge eine Aktion hinzu
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                darkmodeenabled = !darkmodeenabled;
                appcolors = new AppColors(darkmodeenabled);
                setAppColor();
            }
            
        });

        
        add(button);



        setVisible(true);
    }

    private void setAppColor() {
        getContentPane().setBackground(appcolors.primaryBackground);
    }
}
