package com.swingdating.System;

import java.time.LocalDate;
import java.time.Period;

import com.swingdating.App;
import com.swingdating.System.AppUserEnums.APU_EyeColor;
import com.swingdating.System.AppUserEnums.APU_FavoriteSubject;
import com.swingdating.System.AppUserEnums.APU_Gender;
import com.swingdating.System.AppUserEnums.APU_HairColor;
import com.swingdating.System.AppUserEnums.APU_MusicPreference;
import com.swingdating.System.AppUserEnums.APU_Nationality;
import com.swingdating.System.AppUserEnums.APU_Religion;
import com.swingdating.System.AppUserEnums.APU_Sexuality;

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
    private CredentialHash CredentialHash;

    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private String birth_place;
    private APU_Nationality nationality;  // Changed to enum
    private APU_Gender gender;            // Changed to enum
    private APU_Sexuality sexuality;

    private Integer postal_code;
    private String city;
    private String district;

    private Integer height;
    private Integer weight;
    private APU_HairColor hair_color;  
    private APU_EyeColor eye_color;    

    private APU_Religion religion;    
    private APU_FavoriteSubject favorite_subject;  
    private APU_MusicPreference music_preference;  

    private Boolean UUIDexistsinDB = false;

    public AppUser(
        String UUID, String username, CredentialHash CredentialHash, 
        String first_name, String last_name, LocalDate birth_date, String birth_place, 
        APU_Nationality nationality, APU_Gender gender, APU_Sexuality sexuality, 
        Integer postal_code, String city, String district,
        Integer height, Integer weight, APU_HairColor hair_color, APU_EyeColor eye_color,
        APU_Religion religion, APU_FavoriteSubject favorite_subject, APU_MusicPreference music_preference
    ) {
        this.UUID = UUID;
        this.username = username;
        this.CredentialHash = CredentialHash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.birth_place = birth_place;
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
    public static AppUser getAppUserByUUID(String UUID) {
        DBManagerSQLite db = App.db;

        String[][] data = db.get("SELECT * FROM appusers WHERE UUID = '" + UUID + "'");
        if (data.length <= 1) {return null;}

        AppUser appuser = new AppUser(
            data[1][0], data[1][1], new CredentialHash(data[1][2], data[1][3], true), 
            data[1][4], data[1][5], LocalDate.parse(data[1][6]), data[1][7], 
            APU_Nationality.fromCode(data[1][8]),
            APU_Gender.fromCode(data[1][9]),      
            APU_Sexuality.fromCode(data[1][10]), 
            Integer.parseInt(data[1][11]), data[1][12], data[1][13], 
            Integer.parseInt(data[1][14]), Integer.parseInt(data[1][15]), 
            APU_HairColor.fromCode(data[1][16]), 
            APU_EyeColor.fromCode(data[1][17]),  
            APU_Religion.fromCode(data[1][18]),  
            APU_FavoriteSubject.fromCode(data[1][19]), 
            APU_MusicPreference.fromCode(data[1][20]) 
        );

        appuser.UUIDexistsinDB = true;

        return appuser;
    }
    public static AppUser getAppUserByUsername(String username) {
        DBManagerSQLite db = App.db;

        String[][] data = db.get("SELECT * FROM appusers WHERE username = '" + username + "'");
        if (data.length <= 1) {return null;}

        AppUser appuser = new AppUser(
            data[1][0], data[1][1], new CredentialHash(data[1][2], data[1][3], true), 
            data[1][4], data[1][5], LocalDate.parse(data[1][6]), data[1][7], 
            APU_Nationality.fromCode(data[1][8]),  // Use fromCode() for enum
            APU_Gender.fromCode(data[1][9]),      // Use fromCode() for enum
            APU_Sexuality.fromCode(data[1][10]), // sexuality remains a String
            Integer.parseInt(data[1][11]), data[1][12], data[1][13], 
            Integer.parseInt(data[1][14]), Integer.parseInt(data[1][15]), 
            APU_HairColor.fromCode(data[1][16]),  // Use fromCode() for enum
            APU_EyeColor.fromCode(data[1][17]),   // Use fromCode() for enum
            APU_Religion.fromCode(data[1][18]),   // Use fromCode() for enum
            APU_FavoriteSubject.fromCode(data[1][19]), // Use fromCode() for enum
            APU_MusicPreference.fromCode(data[1][20]) // Use fromCode() for enum
        );

        appuser.UUIDexistsinDB = true;

        return appuser;
    }

    public boolean savetoDB() {
        createUUID();
        DBManagerSQLite db = App.db;
        if (!validateData()) {
            return false;
        }

        if (UUIDexistsinDB) {
            db.update(
                String.format(
                    "UPDATE appusers " +
                    "SET username = '%s', password_hash = '%s', password_salt = '%s', first_name = '%s', last_name = '%s', birth_date = '%s', birth_place = '%s', nationality = '%s', gender = '%s', sexuality = '%s', postal_code = '%s', city = '%s', district = '%s', height = '%s', weight = '%s', hair_color = '%s', eye_color = '%s', religion = '%s', favorite_subject = '%s', music_preference = '%s' " +
                    "WHERE UUID = '%s'",
                    username, CredentialHash.getCredentialHash(), CredentialHash.getPasswordSalt(), first_name, last_name, birth_date, birth_place, 
                    nationality.getCode(), gender.getCode(), sexuality.getCode(), postal_code, city, district, height, weight, 
                    hair_color.getCode(), eye_color.getCode(), religion.getCode(), favorite_subject.getCode(), music_preference.getCode(), UUID
                )
            );
        } else {
            db.add(
                String.format(
                    "INSERT INTO appusers " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    UUID, username, CredentialHash.getCredentialHash(), CredentialHash.getPasswordSalt(), first_name, last_name, birth_date, birth_place, 
                    nationality.getCode(), gender.getCode(), sexuality.getCode(), postal_code, city, district, height, weight, 
                    hair_color.getCode(), eye_color.getCode(), religion.getCode(), favorite_subject.getCode(), music_preference.getCode()
                )
            );
        }
        return true;
    }
    /**
     * Method is not finished yet
     * @return true if data is valid
     */
    public boolean validateData() {
        return UUID != null && !UUID.isEmpty() &&
               username != null && !username.isEmpty() &&
               CredentialHash != null &&
               first_name != null && !first_name.isEmpty() &&
               last_name != null && !last_name.isEmpty() &&
               gender != null && 
               birth_date != null &&
               sexuality != null &&
               nationality != null &&
               city != null && !city.isEmpty() &&
               favorite_subject != null &&
               music_preference != null &&
               birth_place != null &&
               religion != null &&
               height > 0 && weight > 0;
    }
    // Get and Set Methods
    private void createUUID() {
        if (!UUIDexistsinDB) {
            this.UUID = java.util.UUID.randomUUID().toString();
        }
    }
    public String getUUID() {
        return this.UUID;
    }
    public CredentialHash getCDHash() {
        return this.CredentialHash;
    }
    public boolean setCDHash(CredentialHash pwHash) {
        this.CredentialHash = pwHash;
        return true;
    }
    public String getUsername() {
        return this.username;
    }
    public boolean setUsername(String username) {
        if (AppUser.getAppUserByUsername(username) == null && !username.isEmpty() && !username.trim().isEmpty()) {
            this.username = username;
            return true;
        } else if (this.username.equals(username)) {
            return true;
        } else {
            return false;
        }
    }
    public String getFirstName() {
        return this.first_name;
    }
    public String getLastName() {
        return this.last_name;
    }
    public boolean setName(String first_name, String last_name) {
        if (!first_name.equals(last_name) && !first_name.trim().isEmpty() && !last_name.trim().isEmpty()) {
            this.first_name = first_name;
            this.last_name = last_name;
            return true;
        } else {
            return false;
        }
    }
    public LocalDate getBirthDate() {
        return this.birth_date;
    }
    public boolean setBirthDate(LocalDate birhtDate) {
        this.birth_date = birhtDate;
        return true;
    }
    public int getHeight() {
        return this.height;
    }
    public boolean setHeight(int height) {
        if (height >= 100 && height <= 300) {
            this.height = height;
            return true;
        }
        return false;
    }
    public int getWeight() {
        return this.weight;
    }
    public boolean setWeight(int weight) {
        if (weight >= 10 && weight <= 200) {
            this.weight = weight;
            return true;
        }
        return false;
    }
    public int getPostalCode() {
        return this.postal_code;
    }
    public boolean setPostalCode(int postalCode) {
        if (String.valueOf(postalCode).length() == 5) {
            this.postal_code = postalCode;
            return true;
        }
        return false;
    }
    public String getCity() {
        return this.city;
    }
    public boolean setCity(String city) {
        if (!city.isEmpty()) {
            this.city = city;
            return true;
        }
        return false;
    }
    public String getDistrict() {
        return this.district;
    }
    public boolean setDistrcit(String district) {
        this.district = district;
        return true;
    }
    public String getBirthplace() {
        return this.birth_place;
    }
    public boolean setBirthPlace(String birthPlace) {
        if (!birthPlace.isEmpty()) {
            this.birth_place = birthPlace;
            return true;
        }
        return false;
    }
    public int getAge() {
        return Period.between(this.birth_date, LocalDate.now()).getYears();
    }

    public APU_EyeColor getEyeColor() {
        return this.eye_color;
    }
    public boolean setEyeColor(APU_EyeColor eyeColor) {
        this.eye_color = eyeColor;
        return true;
    }
    public APU_FavoriteSubject getFavoriteSubject() {
        return this.favorite_subject;
    }
    public boolean setFavoriteSubject(APU_FavoriteSubject favoriteSubject) {
        this.favorite_subject = favoriteSubject;
        return true;
    } 
    public APU_Gender getGender() {
        return this.gender;
    }
    public boolean setGender(APU_Gender gender) {
        this.gender = gender;
        return true;
    }
    public APU_HairColor getHairColor() {
        return this.hair_color;
    }
    public boolean setHairColor(APU_HairColor hairColor) {
        this.hair_color = hairColor;
        return true;
    }
    public APU_MusicPreference getMusicPreference() {
        return this.music_preference;
    }
    public boolean setMusicPreference(APU_MusicPreference musicPreference) {
        this.music_preference = musicPreference;
        return true;
    }
    public APU_Nationality getNationality() {
        return this.nationality;
    }
    public boolean setNationality(APU_Nationality nationality) {
        this.nationality = nationality;
        return true;
    }
    public APU_Religion getReligion() {
        return this.religion;
    }
    public boolean setReligion(APU_Religion religion) {
        this.religion = religion;
        return true;
    }    
    public APU_Sexuality getSexuality() {
        return this.sexuality;
    }
    public boolean setSexuality(APU_Sexuality sexuality) {
        this.sexuality = sexuality;
        return true;
    }
}