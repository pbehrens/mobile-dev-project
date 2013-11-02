package com.example.remotelight;

/**
 * Created by thebeagle on 11/2/13.
 */
public class Command {
    private String name;
    private String command;
    private String type;
    private int requestTimeout;
    private int killTimeout;
    private int pid;

    public Command(String name, String command, String type){
        this.name = name;
        this.command = command;
        this.type = type;
        this.requestTimeout = 20;
        this.killTimeout = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getKillTimeout() {
        return killTimeout;
    }

    public void setKillTimeout(int killTimeout) {
        this.killTimeout = killTimeout;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


}
