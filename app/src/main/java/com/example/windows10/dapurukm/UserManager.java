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
        if(user != null) {
            this.user = user;
            if (user.getKabupaten() == null) {
                isLogin = false;
            } else {
                isLogin = true;
            }
        }
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
