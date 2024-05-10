package com.firstjavafx;

public class Professor {
    String username;
    String password;
    int pid;
    
    Professor() {
        username = "None";
        password = "None";
        pid = 0;
    }
    
    Professor(String username, String password, int pid) {
        this.username = username;
        this.password = password;
        this.pid = pid;
    }
    
    String getName() {
        return username;
    }

    String getPsswd() {
        return password;
    }
    
    int getPid() {
        return pid;
    }

    void setName(String username) {
        this.username = username;
    }

    void setPsswd(String password) {
        this.password = password;
    }
    
}
