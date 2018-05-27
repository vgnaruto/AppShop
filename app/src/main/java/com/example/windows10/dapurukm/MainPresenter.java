package com.example.windows10.dapurukm;

public class MainPresenter {
    private UserManager userManager;
    private MainActivity activity;

    public MainPresenter(MainActivity act){
        activity = act;
        userManager = new UserManager();
    }

    public void setUser(User user){
        userManager.setUser(user);
        userManager.setLogin(true);
        notifyUserChanged();
    }
    public User getUser(){
        return userManager.getUser();
    }

    public String formatRupiah(int angka){
        String result = "";
        int count = 1;
        while(angka > 0){
            result = (angka%10) + result;
            angka/=10;
            count++;
            if(angka > 0) {
                if (count == 4) {
                    result = "." + result;
                    count = 1;
                }
            }
        }
        return "Rp "+result;
    }

    public void notifyUserChanged(){
        activity.notifyUserChanged();
    }
}
