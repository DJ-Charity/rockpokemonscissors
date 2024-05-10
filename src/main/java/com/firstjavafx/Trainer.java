package com.firstjavafx;

public class Trainer {
    String username;
    String password;
    int level;
    int tid;
    
    Trainer() {
        username = "None";
        password = "None";
        level = 0;
        tid = 0;
    }
    
    Trainer(int tid, String username, String password, int level) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.tid = tid;
    }
    
    String getName() {
        return this.username;
    }

    String getPsswd() {
        return this.password;
    }
    
    int getLevel() {
        return this.level;
    }

    int getTid() {
        return this.tid;
    }

    void setName(String username) {
        this.username = username;
    }

    void setPsswd(String password) {
        this.password = password;
    }
    
    void levelUp() {
        ++this.level;
    }

}
