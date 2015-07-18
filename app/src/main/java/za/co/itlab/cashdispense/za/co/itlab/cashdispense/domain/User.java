package za.co.itlab.cashdispense.za.co.itlab.cashdispense.domain;

import java.io.Serializable;

/**
 * Created by poovanpillay on 2015/07/14.
 */
public class User implements Serializable{

    private String username;

    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
