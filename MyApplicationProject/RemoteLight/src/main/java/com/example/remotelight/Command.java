package com.example.remotelight;

/**
 * Created by thebeagle on 11/2/13.
 */
public class Command {
    private String name;
    private String command;
    private String type;
    private String directory;

    private int requestTimeout;
    private int pid;
    private String flags;

    public Command(String name, String command, String type, String flags, String directory, int timeoutMillis){
        this.name = name;
        this.command = command;
        this.type = type;
        this.directory = directory;
        this.flags = flags;

        this.requestTimeout = timeoutMillis;
    }

    public String toString(){
        return name + "\n" + command + "\n" + type + "\n" + requestTimeout;
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


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }


}
