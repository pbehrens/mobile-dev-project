package edu.luc.plutarcobehrens.remotelight;

import java.io.Serializable;


/**
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
 Serializable paramter object thjat allows for data to be transferred between activities
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class Parameters implements Serializable {
    private String user;
    private String password;
    private String host;

    public Parameters(String user, String password, String host){
        this.user = user;
        this.password = password;
        this.host = host;
    }

    /**
     * @return the user string
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user user string
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password string
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the host string
     */
    public String getHost() {
        return host;
    }

    /**
     *  Sets the host string
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }


}
