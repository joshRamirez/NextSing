package model;

public class Session {
    private int authId;
    private int userId;
    private String sessionId;
    private Boolean isLoggedIn;

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
