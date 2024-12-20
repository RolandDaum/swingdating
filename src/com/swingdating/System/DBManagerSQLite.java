package  com.swingdating.System;
import java.sql.*;
import java.io.PrintWriter;

public class DBManagerSQLite {
  
  /** der Name der Datenbank */
  private String DBConnectionURL = "";
  
  /** ein Objekt für die Verbindung zur Datenbank*/
  private Connection verbindung;
  
  /** ein Objekt zum Absenden von SQL-Anfragen*/
  private Statement statement;

      
  /** Erzeugt ein DBManagerSQLite-Objekt für die angegebene Datenbank.
  * Es wird eine Verbindung zu der angegebenen Datenbank hergestellt, so dass Methoden 
  * zum Ausführen der SQL-Anweisungen genutzt werden können.
  * @param dbName der Name der Datenbank
  */
  public DBManagerSQLite(String DBConnectionURL) {
    this.DBConnectionURL = DBConnectionURL;
    //Status Informationen des DriverManagers ausgeben
    DriverManager.setLogWriter(new PrintWriter(System.out));
    
     //Treiber laden
    /*try{
      Class.forName("org.sqlite.JDBC");
    }catch(ClassNotFoundException e )
    {
      System.err.println( "Keine Treiber-Klasse!" );
      return;
    } */
    
    //Connection- und Statement-Objekt erzeugen
    try{
      verbindung = DriverManager.getConnection(DBConnectionURL);
      statement = verbindung.createStatement();
    }
    catch(SQLException e){
       System.out.println(e.toString());
       e.printStackTrace();
    }
  }
  
  /**
  * Führt die übergebene SQL-Anfrage aus.
  * Die gefundenen Datensätze werden in einer zweidimensionalen Reihung vom Typ Zeichenkette zur Verfügung gestellt.
  * @param sqlAnfrage Die SQL-Anfrage, die ausgeführt werden soll, als Zeichenkette.
  * @return Das Ergebnis der SQL-Anfrage als zweidimensionale Reihung vom Typ Zeichenkette. Interpretiert man den ersten Index als Zeilen- und den zweiten als Spatennummer, enthält die erste Zeile der Reihung die Überschriften der Spalten. Danach folgt pro Datensatz eine Zeile mit den entsprechenden Werten. 
   * Diese werden unabhängig von den Datentypen der Datenbank als Zeichenkette gespeichert. Enthält eine Zelle in der Datenbank den Wert null, wird die Zeichenkette "null" in das entsprechende Feld der zweidimensionalen Reihung geschrieben.
   * Schlägt der Versuch die SQL-Anfrage zu stellen fehl, enthält die zweidimensionale Reihung nur ein Feld mit dem Inhalt "Fehler".
   * <pre>
   * Beispiel:
   *____________[][0]____[][1]_____[][2]
   *  [0][]  |  name  |  alter  |  jahr
   *  [1][]  |  Tom   |  54     |  1956
   *  [2][]  |  Nina  |  76     |  1955
   * </pre>
   */    
  public String[][] get(String sqlAnfrage){
    //Rückgabe vorbereiten
    String[][] ergebnis = new String[1][1];
    ergebnis[0][0] = "Fehler";
    try{
      //falls die Verbindung schon wieder geschlossen wurde, muss sie neu aufgebaut werden und ein neues Statement-Objekt muss erzeugt werden.
      if (verbindung == null || verbindung.isClosed()) {
        System.out.println("create new connection");
        verbindung = DriverManager.getConnection(this.DBConnectionURL);
        statement = verbindung.createStatement();
      }
      
      //Das Ergebnis kommt als Java-Objekt vom Typ ResultSet zurück. Der Inhalt wird in die zweidimensionale Reihung übertragen und dies dann zurückgegeben.
      ResultSet myResultSet = statement.executeQuery(sqlAnfrage);
      ResultSetMetaData myRSMetaData = myResultSet.getMetaData();
      
      //zunächst muss die Größe der benötigten Zeilen und Spalten in der Reihung bestimmt werden.  
      int spalten  = myRSMetaData.getColumnCount();
      int zeilen = 1;
      while(myResultSet.next()){
        zeilen++;
      }
      ergebnis = new String[zeilen][spalten];
      myResultSet.close();
          
      //Da das Resultset in Verbindung mit SQLite nur einmal durchlaufen werden kann, muss
      // die Anfrage hier nocheinmal gestellt werden, um ein neues Resultset zu erhalten, 
      // das nun in die Reihung übertragen werden kann.
      myResultSet = statement.executeQuery(sqlAnfrage);
      myRSMetaData = myResultSet.getMetaData();
      
            
      //die erste Zeile mit den Spaltennamen füllen  
      int zeilenzaehler = 0;  
      for (int i = 1; i <= spalten; i++) {
        ergebnis[0][i-1] = myRSMetaData.getColumnName(i);
      } // end of for
      zeilenzaehler++;
      
      //die Datensätze uebertragen
      while (myResultSet.next()) {
        for(int i = 1; i <= spalten; i++){
          ergebnis[zeilenzaehler][i-1] = myResultSet.getString(i);
          
          //enthält eine Zelle in der Datenbank den Wert null, wird die Zeichenkette "null" in das entsprechende Felde der zweidimensionalen Reihung geschrieben.
          if(myResultSet.wasNull()) ergebnis[zeilenzaehler][i-1] = "null";
        }
        zeilenzaehler++; 
      } // end of while
      myResultSet.close();
      
    }catch(SQLException e){
      e.printStackTrace();
    }
    return ergebnis; 
  }
  
  /**
  * Führt die übergebene SQL-Einfüge-Anweisung aus.
  * @param sql Die SQL-Anweisung, die ausgeführt werden soll, als Zeichenkette.
  * @return Die Anzahl der betroffenen Datensätze. Der Rückgabewert ist 0, wenn die Anweisung keine Änderung in der Datenbank bewirkt hat. 
  * Der Rückgabewert -1 zeigt an, dass ein Fehler aufgetreten ist.
  */   
  public int add(String sql){
    int ergebnis = -1;
    try{
      //falls die Verbindung schon wieder geschlossen wurde, muss sie neu aufgebaut werden und ein neues Statement-Objekt erzeugt werden.
      if (verbindung == null || verbindung.isClosed()) {
        verbindung = DriverManager.getConnection(this.DBConnectionURL);
        statement = verbindung.createStatement();
      }
    //Die SQL-Anweisung zum Einfügen wird ausgeführt und das Ergebnis durchgereicht.    
    ergebnis = statement.executeUpdate(sql);
    }catch(SQLException e){
       e.printStackTrace();
    }  
    return ergebnis;  
    } 
  
     /**
  * Führt die übergebene SQL-Update-Anweisung aus.
  * @param sql Die SQL-Anweisung, die ausgeführt werden soll, als Zeichenkette
  * @return Die Anzahl der betroffenen Datensätze. Der Rückgabewert ist 0, wenn die Anweisung keine Änderung in der Datenbank bewirkt hat. 
  * Der Rückgabewert -1 zeigt an, dass ein Fehler aufgetreten ist.
  */  
    public int update(String sql){
    int ergebnis = -1;
    try{
      //falls die Verbindung schon wieder geschlossen wurde, muss sie neu aufgebaut werden und ein neues Statement-Objekt erzeugt werden.
      if (verbindung == null || verbindung.isClosed()) {
        verbindung = DriverManager.getConnection(this.DBConnectionURL);
        statement = verbindung.createStatement();
      }
      
    //Die SQL-Anweisung zum Ändern wird ausgeführt und das Ergebnis durchgereicht.
    ergebnis = statement.executeUpdate(sql);
    }catch(Exception e){
      e.printStackTrace();
    }  
    return ergebnis;  
    }
  
   /**
  * Führt die übergebene SQL-Lösch-Anweisung aus.
  * @param sql Die SQL-Anweisung, die ausgeführt werden soll, als Zeichenkette
  * @return Die Anzahl der betroffenen Datensätze. Der Rückgabewert ist 0, wenn die Anweisung keine Änderung in der Datenbank bewirkt hat. 
  * Der Rückgabewert -1 zeigt an, dass ein Fehler aufgetreten ist.
  */  
   public int delete(String sql){
    int ergebnis = -1;
    try{
      //falls die Verbindung schon wieder geschlossen wurde, muss sie neu aufgebaut und ein neues Statement-Objekt erzeugt werden.
      if (verbindung == null || verbindung.isClosed()) {
        verbindung = DriverManager.getConnection(this.DBConnectionURL);
        statement = verbindung.createStatement();
      }
      
    //Die SQL-Anweisung zum Löschen wird ausgeführt und das Ergebnis durchgereicht.  
    ergebnis = statement.executeUpdate(sql);
    }catch(Exception e){
      e.printStackTrace();
    }  
    return ergebnis;  
    }
}