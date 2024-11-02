package FirstTestVonDanisch;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame {
    private JLabel lName = new JLabel();
    private JLabel lVorname = new JLabel();
    private JTextField tfName = new JTextField();
    private JTextField tfVorname = new JTextField();
    private JButton bSucheTutoren = new JButton();
    private JLabel lTutor = new JLabel();
    private JLabel lAusgabe = new JLabel();

    private DBManagerSQLite myDBManager = new DBManagerSQLite("jdbc:sqlite:/Users/rlddm/Development/Projects/Schulprojekt/project/src/FirstTestVonDanisch/schule.db");

    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        // JFrame mainFrame = new JFrame();
        // mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // mainFrame.setSize(200,200);
        // mainFrame.setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 757; 
        int frameHeight = 300;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("SucheTutoren");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        lAusgabe.setBounds(88, 176, 609, 25);
        lAusgabe.setText("");
        lAusgabe.setBackground(Color.WHITE);
        lAusgabe.setOpaque(true);
        lAusgabe.setFont(new Font("Dialog", Font.BOLD, 12));
        cp.add(lAusgabe);
        lTutor.setBounds(8, 176, 86, 20);
        lTutor.setText("Tutor:");
        lTutor.setFont(new Font("Dialog", Font.BOLD, 12));
        cp.add(lTutor);
        bSucheTutoren.setBounds(88, 120, 193, 33);
        bSucheTutoren.setText("suche Tutoren");
        bSucheTutoren.setMargin(new Insets(2, 2, 2, 2));
        bSucheTutoren.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent evt) { 
            bSucheTutoren_ActionPerformed(evt);
            
          }
        });
        bSucheTutoren.setFont(new Font("Dialog", Font.BOLD, 12));
        cp.add(bSucheTutoren);
        tfVorname.setBounds(88, 72, 193, 25);
        tfVorname.setFont(new Font("Dialog", Font.PLAIN, 12));
        tfVorname.setEnabled(true);
        cp.add(tfVorname);
        tfName.setBounds(88, 32, 193, 25);
        tfName.setFont(new Font("Dialog", Font.PLAIN, 12));
        cp.add(tfName);
        lVorname.setBounds(8, 72, 78, 20);
        lVorname.setText("Vorname:");
        lVorname.setFont(new Font("Dialog", Font.BOLD, 12));
        cp.add(lVorname);
        lName.setBounds(8, 32, 78, 20);
        lName.setText("Name:");
        lName.setFont(new Font("Dialog", Font.BOLD, 12));
        cp.add(lName);
        
        // Ende Komponenten
        
        setVisible(true);
    }
  //Diese Methode wird ausgeführt, wenn der Button angeklickt wird.
  public void bSucheTutoren_ActionPerformed(ActionEvent evt) {
    String name = tfName.getText();
    String vorname = tfVorname.getText();
            
    String sql = "select Tutor_in_11, Tutor_in_12_13 from schueler where Name = '" + name +
            "' and Vorname = '" + vorname + "';";
    
    String[][] ergebnis = myDBManager.get(sql);
    
    //Wenn das Ergebnis zwei Zeilen enthaelt, stehen in der zweiten Zeile die Tutoren und werden ausgegeben.
    if(ergebnis.length == 2)
        lAusgabe.setText("Die Tutoren von " + vorname + " " + name + " sind " + ergebnis[1][0] + " in Jahrgang 11 und " + ergebnis[1][1] + " in Jahrgang 12 und 13.");
    else{ 
        //Wenn das Ergebnis nur eine Zeile enthaelt, ist entweder ein Fehler aufgetreten oder der Name wurde nicht gefunden.
        if(ergebnis.length == 1){
            if(ergebnis[0][0].equals("Fehler")) lAusgabe.setText("Es ist ein Fehler aufgetreten.");
            else{
               lAusgabe.setText("Diese Person gibt es in der Datenbank nicht!");
      }
        }          
    }

} // end of bSucheTutoren_ActionPerformed

}
