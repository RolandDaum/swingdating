import com.swingdating.System.DBManagerSQLite;

public class DBRebuild {
    public static void neverrunthisunlessyouknowwhatyourdoingwhatyouprobalbydonot(String[] args) {
        // DBManagerSQLite dbOLD = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/OLD.db");
        DBManagerSQLite db = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/appdb.db");

        // db.update("UPDATE appusers SET nationality = 'DEU' WHERE nationality = 'deutsch'");
        // db.update("UPDATE appusers SET nationality = 'IRQ' WHERE nationality = 'irakisch'");
        // db.update("UPDATE appusers SET nationality = 'IRN' WHERE nationality = 'iranisch'");
        // db.update("UPDATE appusers SET nationality = 'RUS' WHERE nationality = 'russisch'");
        // db.update("UPDATE appusers SET nationality = 'CAF' WHERE nationality = 'tuerkisch'");
        // db.update("UPDATE appusers SET nationality = 'TUR' WHERE nationality = 'afrikanisch'");
        db.update("UPDATE appusers SET sexuality = 'hetrosexual' WHERE sexuality = 'heterosexual'");
        // db.update("UPDATE appusers SET sexuality = 'bisexual' WHERE sexuality = 'bisexuell'");
        // db.update("UPDATE appusers SET sexuality = 'homosexual' WHERE sexuality = 'homosexuell'");
        // db.update("UPDATE appusers SET religion = 'EV' WHERE religion = 'evangelisch'");
        // db.update("UPDATE appusers SET religion = 'ISL' WHERE religion = 'islamisch'");
        // db.update("UPDATE appusers SET religion = 'CAT' WHERE religion = 'katholisch'");
        // db.update("UPDATE appusers SET religion = '' WHERE religion = 'sonstige'");
        // String[][] data = db.get("SELECT religion FROM appusers GROUP BY religion");
        // for (int i = 0; i < data.length; i++) {
        //     System.out.println(data[i][0]);
        //     if (data[i][0].equals("")) {
        //         System.out.println("Yes it is an empty String with length of 0");
        //     }
        // }
        // String[][] dataOLD = dbOLD.get("SELECT ID_nummer, Name, Vorname, Geburtstag, Geburtsort, Staatsang, Geschlecht, Sexualitaet, PLZ, Ort, Ortsteil, Groesse, Gewicht, Haarfarbe, Augenfarbe, Konfession, Lieblingsfach, Musik FROM schueler");
        // db.update("UPDATE appusers SET favorite_subject = 'de' WHERE favorite_subject = 'Deutsch'");
        // db.update("UPDATE appusers SET favorite_subject = 'eng' WHERE favorite_subject = 'Englisch'");
        // db.update("UPDATE appusers SET favorite_subject = 'fr' WHERE favorite_subject = 'Franzoesisch'");
        // db.update("UPDATE appusers SET favorite_subject = 'hi' WHERE favorite_subject = 'Geschichte'");
        // db.update("UPDATE appusers SET favorite_subject = 'art' WHERE favorite_subject = 'Kunst'");
        // db.update("UPDATE appusers SET favorite_subject = 'la' WHERE favorite_subject = 'Latein'");
        // db.update("UPDATE appusers SET favorite_subject = 'math' WHERE favorite_subject = 'Mathematik'");
        // db.update("UPDATE appusers SET favorite_subject = 'music' WHERE favorite_subject = 'Musik'");
        // db.update("UPDATE appusers SET favorite_subject = 'phy' WHERE favorite_subject = 'Physik'");
        // db.update("UPDATE appusers SET favorite_subject = 'pol' WHERE favorite_subject = 'Politik'");
        // db.update("UPDATE appusers SET favorite_subject = 'rel' WHERE favorite_subject = 'Religion'");
        // db.update("UPDATE appusers SET favorite_subject = 'spa' WHERE favorite_subject = 'Spanisch'");
        // db.update("UPDATE appusers SET favorite_subject = 'pe' WHERE favorite_subject = 'Sport'");
        // db.update("UPDATE appusers SET favorite_subject = 'eth' WHERE favorite_subject = 'Werte und Normen'");
        
        // db.update("UPDATE appusers SET music_preference = 'house' WHERE music_preference = 'House'");
        // db.update("UPDATE appusers SET music_preference = 'classical' WHERE music_preference = 'Klassik'");
        // db.update("UPDATE appusers SET music_preference = 'metal' WHERE music_preference = 'Metal'");
        // db.update("UPDATE appusers SET music_preference = 'pop' WHERE music_preference = 'Pop'");
        // db.update("UPDATE appusers SET music_preference = 'rap' WHERE music_preference = 'Rap'");
        // db.update("UPDATE appusers SET music_preference = 'rock' WHERE music_preference = 'Rock'");
        // db.update("UPDATE appusers SET music_preference = 'schlager' WHERE music_preference = 'Schlager'");
        // db.update("UPDATE appusers SET music_preference = 'techno' WHERE music_preference = 'Techno'");
        
        // db.update("UPDATE appusers SET hair_color = 'blond' WHERE hair_color = 'blond'");
        // db.update("UPDATE appusers SET hair_color = 'brown' WHERE hair_color = 'braun'");
        // db.update("UPDATE appusers SET hair_color = 'red' WHERE hair_color = 'rot'");

        // db.update("UPDATE appusers SET eye_color = 'blue' WHERE eye_color = 'blau'");
        // db.update("UPDATE appusers SET eye_color = 'brown' WHERE eye_color = 'braun'");
        // db.update("UPDATE appusers SET eye_color = 'green' WHERE eye_color = 'gruen'");


        // for (int i = 1; i < dataOLD.length; i++) {
        //     CredentialHash pwHash = new CredentialHash(dataOLD[i][2].replaceAll("\\s","").toLowerCase() + "." + dataOLD[i][1].replaceAll("\\s","").toLowerCase());
        //     String sql = "INSERT INTO appusers VALUES (" +
        //             // "'" + dataOLD[i][0] + "', " +  // ID_number
        //             "'" + UUID.randomUUID() + "', " +  // ID_number
        //             "'" + dataOLD[i][2].replaceAll("\\s","").toLowerCase() + "." + dataOLD[i][1].replaceAll("\\s","").toLowerCase() + "', " +  // username
        //             "'" + pwHash.getCredentialHash() + "', " +  // password_hash
        //             "'" + pwHash.getPasswordSalt() + "', " +  // password_salt
        //             "'" + dataOLD[i][2] + "', " +  // first_name
        //             "'" + dataOLD[i][1] + "', " +  // last_name
        //             "'" + dataOLD[i][3] + "', " +  // birth_date
        //             "'" + dataOLD[i][4] + "', " +  // birth_place
        //             "'" + dataOLD[i][5] + "', " +  // nationality
        //             "'" + dataOLD[i][6] + "', " +  // gender
        //             "'" + dataOLD[i][7] + "', " +  // sexuality
        //             dataOLD[i][8] + ", " +        // postal_code (no quotes for INT)
        //             "'" + dataOLD[i][9] + "', " +  // city
        //             (dataOLD[i][10].equals("null") ? "NULL" : "'" + dataOLD[i][10] + "'") + ", " + // district
        //             (int) (Double.parseDouble(dataOLD[i][11])*100) + ", " +        // height (no quotes for INT)
        //             dataOLD[i][12] + ", " +        // weight (no quotes for INT)
        //             "'" + dataOLD[i][13] + "', " + // hair_color
        //             "'" + dataOLD[i][14] + "', " + // eye_color
        //             "'" + dataOLD[i][15] + "', " + // religion
        //             "'" + dataOLD[i][16] + "', " + // favorite_subject
        //             "'" + dataOLD[i][17] + "'" +   // music_preference
        //             ");";
        //     db.add(sql);
        // }
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