package com.laudwilliam.keyvaluedatabase.communication;

public class LoginRequest {
    private final String database;
    private final String username;
    private final String password;

    private LoginRequest(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public static LoginRequest createRequest(String database, String username, String password)
    {
        return new LoginRequest(database, username, password);
    }
}
