package com.swingdating.System;

import com.swingdating.App;
import com.swingdating.System.LRResponse.*;

// if user is not in appusers table -> go to register poge -> ask for name name and birthday, look if in students table if yes take over the existing information, else create new student entry for new appuser
// RULE: Unique Usernames only
public class LRManager {
    public static LRResponse credentialCheck(String username, String password) {
        DBManagerSQLite db = new DBManagerSQLite("jdbc:sqlite:src/com/swingdating/database.db");
        String[][] data = db
                .get("SELECT password_hash, password_salt FROM appusers where username = '" + username + "'");
        if (data.length <= 1) {
            register();
            return new LRResponse(ResponseType.USERNAME_NOTFOUND);
        } else {
            return login(data[1][0], new PasswordHash(password, data[1][1]));
        }
    }

    private static LRResponse login(String passwordHash, PasswordHash pwhash) {
        boolean valid = PasswordHash.verifyPassword(passwordHash, pwhash);
        if (valid) {
            App.switchToPage("SWINGDATING - HOME");
            return new LRResponse(ResponseType.VALID_PASSWORD);
        } else {
            return new LRResponse(ResponseType.INVALID_PASSWORD);
        }
        
    }

    private static void register() {
        App.switchToPage("SWINGDATING - REGISTER");
    }
}
