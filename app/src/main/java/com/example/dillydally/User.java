
package com.example.dillydally;

public class User {
    public String uid;
    public String email;
    public String displayName;
    public String photoUrl;
    public boolean isOnline;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String email, String displayName, String photoUrl) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.isOnline = false;
    }
}
