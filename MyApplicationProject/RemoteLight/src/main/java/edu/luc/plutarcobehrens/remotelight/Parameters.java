package edu.luc.plutarcobehrens.remotelight;

import java.io.Serializable;


/**
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
 Serializable paramter object thjat allows for data to be transferred between activities
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class Parameters implements Serializable {

    public Parameters(String user, String password, String host){
        this.user = user;
        this.password = password;
        this.host = host;

    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String host;

}
