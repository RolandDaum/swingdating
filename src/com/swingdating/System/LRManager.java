package com.swingdating.System;

import com.swingdating.App;
import com.swingdating.System.LRResponse.*;

// if user is not in appusers table -> go to register poge -> ask for name name and birthday, look if in students table if yes take over the existing information, else create new student entry for new appuser
// RULE: Unique Usernames only
public class LRManager {
    public static LRResponse credentialCheck(String username, String password) {
        DBManagerSQLite db = App.db;
        String[][] data = db
                .get("SELECT password_hash, password_salt FROM appusers WHERE username = '" + username + "'");
        // for (int i = 0; i < data.length; i++) {
        //     for (int j = 0; j < data.length; j++) {
        //         System.out.print(data[i][j] +" | ");
        //     }
        //     System.out.println();
        // }
        if (data.length <= 1) {
            register(username, password);
            return new LRResponse(ResponseType.USERNAME_NOTFOUND);
        } else {
            return login(new PasswordHash(password, data[1][1]), new PasswordHash(data[1][0], data[1][1], true));
        }
    }

    private static LRResponse login(PasswordHash userPasswordHash, PasswordHash storedPasswordHash) {
        boolean valid = PasswordHash.verifyPassword(userPasswordHash, storedPasswordHash);
        if (valid) {
            App.switchToPage("SWINGDATING - HOME");
            return new LRResponse(ResponseType.VALID_PASSWORD);
        } else {
            return new LRResponse(ResponseType.INVALID_PASSWORD);
        }
        
    }

    private static void register(String username, String password) {
        DBManagerSQLite db = App.db;
        PasswordHash pwHash = new PasswordHash(password);
        db.add("INSERT INTO appusers VALUES ('ID COMMING SOON', '" + username + "','" + pwHash.getPasswordHash() + "','" + pwHash.getPasswordSalt() + "')");
        App.switchToPage("SWINGDATING - REGISTER");
    }
}
