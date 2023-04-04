package com.laudwilliam.keyvaluedatabase.communication;

public class LoginResponse {
    private final Session session;
    private final Response response;

    private final char[] data;


    private LoginResponse(Session session, Response response, char[] data) {
        this.session = session;
        this.response = response;
        this.data = data;
    }

    public LoginResponse createResponse(Session session, Response response, char[] data) {
        return new LoginResponse(session, response, data);
    }
}
