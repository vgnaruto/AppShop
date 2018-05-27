package com.example.windows10.dapurukm;

public class UserManager {
    private User user;
    private boolean isLogin;

    public UserManager() {
        this.user = new User();
        this.isLogin = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
