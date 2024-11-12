package com.swingdating.System;

import com.swingdating.App;

public class AppUser {
    // UUID CHAR(20) PRIMARY KEY NOT NULL,
    // username CHAR(64) NOT NULL,
    // password_hash CHAR(44) NOT NULL,
    // password_salt CHAR(24) NOT NULL,

    // first_name CHAR(20) NOT NULL,
    // last_name CHAR(20) NOT NULL,
    // birth_date CHAR(10) NOT NULL,
    // birth_place CHAR(30) NOT NULL,
    // nationality CHAR(20) NOT NULL,
    // gender CHAR(1) NOT NULL,
    // sexuality VARCHAR(45) NOT NULL,

    // postal_code INT(11) NOT NULL,
    // city CHAR(25) NOT NULL,
    // district CHAR(25) NULL,

    // height INT(3) NOT NULL,
    // weight INT(10) NOT NULL,
    // hair_color VARCHAR(45) NOT NULL,
    // eye_color VARCHAR(45) NOT NULL,

    // religion CHAR(12) NOT NULL,
    // favorite_subject VARCHAR(60) NOT NULL,
    // music_preference VARCHAR(45) NOT NULL
    private String UUID;
    private String username;
    private PasswordHash passwordHash;

    private String first_name;
    private String last_name;
    private String birth_date;
    private String birth_place;
    private String nationality;
    private String gender;
    private String sexuality;

    private int postal_code;
    private String city;
    private String district;

    private int height;
    private int weight;
    private String hair_color;
    private String eye_color;

    private String religion;
    private String favorite_subject;
    private String music_preference;

    private boolean UUIDexistsinDB = false;

    public AppUser(
        String UUID, String username, PasswordHash passwordHash, 
        String first_name, String last_name, String birth_date, String nationality, String gender, String sexuality, 
        int postal_code, String city, String district,
        int height, int weight, String hair_color, String eye_color,
        String religion, String favorite_subject, String music_preference
        ) {
        this.UUID = UUID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.nationality = nationality;
        this.gender = gender;
        this.sexuality = sexuality;
        this.postal_code = postal_code;
        this.city = city;
        this.district = district;
        this.height = height;
        this.weight = weight;
        this.hair_color = hair_color;
        this.eye_color = eye_color;
        this.religion = religion;
        this.favorite_subject = favorite_subject;
        this.music_preference = music_preference;
    }

    public static AppUser getAppUserByUsername(String username) {
        DBManagerSQLite db = App.db;
    
        String[][] data = db.get("SELECT * FROM appusers WHERE username = '" + username + "'");
        if (data.length <= 1) {
            return null;
        }
    
        AppUser appuser = new AppUser(
            data[1][0], data[1][1], new PasswordHash(data[1][2], data[1][3], true), 
            data[1][4], data[1][5], data[1][6], data[1][7], data[1][8], data[1][9], 
            Integer.parseInt(data[1][10]), data[1][11], data[1][12], 
            Integer.parseInt(data[1][13]), 
            Integer.parseInt(data[1][14]), 
            data[1][15], data[1][16], 
            data[1][17], data[1][18], 
            data[1][19]);

        appuser.UUIDexistsinDB = true;
        
        return appuser;
    }
    
    public static AppUser createDefaultAppUser() {
        return null;
    }

    public int getAge() {
        return 0;
    }

    public void savetoDB() {
        DBManagerSQLite db = App.db;
        if (!validateData()) {
            return;
        }
    
        if (UUIDexistsinDB) {
            db.update(
                String.format(
                    "UPDATE appusers " + 
                    "SET username = '%s', password_hash = '%s', password_salt = '%s', first_name = '%s', last_name = '%s', birth_date = '%s', birth_place = '%s', nationality = '%s', gender = '%s', sexuality = '%s', postal_code = '%s', city = '%s', district = '%s', height = '%s', weight = '%s', hair_color = '%s', eye_color = '%s', religion = '%s', favorite_subject = '%s', music_preference = '%s' " + 
                    "WHERE UUID = '%s'",
                    username, passwordHash.getPasswordHash(), passwordHash.getPasswordSalt(), first_name, last_name, birth_date, birth_place, nationality, gender, sexuality, postal_code, city, district, height, weight, hair_color, eye_color, religion, favorite_subject, music_preference, 
                    UUID
                )
            );
        } else {
            db.add(
                String.format(
                    "INSERT INTO appusers" +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    UUID, username, passwordHash.getPasswordHash(), passwordHash.getPasswordSalt(), first_name, last_name, birth_date, birth_place, nationality, gender, sexuality, postal_code, city, district, height, weight, hair_color, eye_color, religion, favorite_subject, music_preference
                )
            );
        }
    }

    public boolean validateData() {
        return UUID != null && !UUID.isEmpty() &&
               username != null && !username.isEmpty() &&
               passwordHash != null &&
               first_name != null && !first_name.isEmpty() &&
               last_name != null && !last_name.isEmpty() &&
               gender != null && !gender.isEmpty() &&
               birth_date != null && !birth_date.isEmpty() &&
               sexuality != null && !sexuality.isEmpty() &&
               nationality != null && !nationality.isEmpty() &&
               city != null && !city.isEmpty() &&
            //    district != null && !district.isEmpty() &&
               favorite_subject != null && !favorite_subject.isEmpty() &&
               music_preference != null && !music_preference.isEmpty() &&
               birth_place != null && !birth_place.isEmpty() &&
               religion != null && !religion.isEmpty() &&
               height > 0 && weight > 0;
    }
}
