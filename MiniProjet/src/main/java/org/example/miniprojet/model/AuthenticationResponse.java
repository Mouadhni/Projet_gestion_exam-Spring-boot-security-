package org.example.miniprojet.model;

public class AuthenticationResponse {
    private  String token;
    private  Type type;
        private  User user;



    public AuthenticationResponse(String token,Type type) {
        this.token = token;
        this.type=type;
    }
    public AuthenticationResponse(String token,Type type,User user) {
        this.token = token;
        this.type=type;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
