import java.util.UUID;

import com.swingdating.System.DBManagerSQLite;
import com.swingdating.System.PasswordHash;

public class DBRebuild {
    public static void neverrunthisunlessyouknowwhatyourdoingwhatyouprobalbydonot(String[] args) {
        DBManagerSQLite dbOLD = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/OLD.db");
        DBManagerSQLite db = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/appdb.db");

        String[][] dataOLD = dbOLD.get("SELECT ID_nummer, Name, Vorname, Geburtstag, Geburtsort, Staatsang, Geschlecht, Sexualitaet, PLZ, Ort, Ortsteil, Groesse, Gewicht, Haarfarbe, Augenfarbe, Konfession, Lieblingsfach, Musik FROM schueler");


        for (int i = 1; i < dataOLD.length; i++) {
            PasswordHash pwHash = new PasswordHash(dataOLD[i][2].replaceAll("\\s","").toLowerCase() + "." + dataOLD[i][1].replaceAll("\\s","").toLowerCase());
            String sql = "INSERT INTO appusers VALUES (" +
                    // "'" + dataOLD[i][0] + "', " +  // ID_number
                    "'" + UUID.randomUUID() + "', " +  // ID_number
                    "'" + dataOLD[i][2].replaceAll("\\s","").toLowerCase() + "." + dataOLD[i][1].replaceAll("\\s","").toLowerCase() + "', " +  // username
                    "'" + pwHash.getPasswordHash() + "', " +  // password_hash
                    "'" + pwHash.getPasswordSalt() + "', " +  // password_salt
                    "'" + dataOLD[i][2] + "', " +  // first_name
                    "'" + dataOLD[i][1] + "', " +  // last_name
                    "'" + dataOLD[i][3] + "', " +  // birth_date
                    "'" + dataOLD[i][4] + "', " +  // birth_place
                    "'" + dataOLD[i][5] + "', " +  // nationality
                    "'" + dataOLD[i][6] + "', " +  // gender
                    "'" + dataOLD[i][7] + "', " +  // sexuality
                    dataOLD[i][8] + ", " +        // postal_code (no quotes for INT)
                    "'" + dataOLD[i][9] + "', " +  // city
                    (dataOLD[i][10].equals("null") ? "NULL" : "'" + dataOLD[i][10] + "'") + ", " + // district
                    (int) (Double.parseDouble(dataOLD[i][11])*100) + ", " +        // height (no quotes for INT)
                    dataOLD[i][12] + ", " +        // weight (no quotes for INT)
                    "'" + dataOLD[i][13] + "', " + // hair_color
                    "'" + dataOLD[i][14] + "', " + // eye_color
                    "'" + dataOLD[i][15] + "', " + // religion
                    "'" + dataOLD[i][16] + "', " + // favorite_subject
                    "'" + dataOLD[i][17] + "'" +   // music_preference
                    ");";
            db.add(sql);
        }
    }
}
// CHECK FOR DOBBLE USERNAMES:
// SELECT username, COUNT(*) FROM appusers GROUP BY username HAVING COUNT(*) > 1;

/**
CREATE TABLE appusers (
    UUID CHAR(20) PRIMARY KEY NOT NULL,
    username CHAR(64) NOT NULL,
    password_hash CHAR(44) NOT NULL,
    password_salt CHAR(24) NOT NULL,

    first_name CHAR(20) NOT NULL,
    last_name CHAR(20) NOT NULL,
    birth_date CHAR(10) NOT NULL,
    birth_place CHAR(30) NOT NULL,
    nationality CHAR(20) NOT NULL,
    gender CHAR(1) NOT NULL,
    sexuality VARCHAR(45) NOT NULL,

    postal_code INT(11) NOT NULL,
    city CHAR(25) NOT NULL,
    district CHAR(25) NULL,

    height INT(3) NOT NULL,
    weight INT(10) NOT NULL,
    hair_color VARCHAR(45) NOT NULL,
    eye_color VARCHAR(45) NOT NULL,

    religion CHAR(12) NOT NULL,
    favorite_subject VARCHAR(60) NOT NULL,
    music_preference VARCHAR(45) NOT NULL
);
 */