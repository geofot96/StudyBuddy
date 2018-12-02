package ch.epfl.sweng.studdybuddy.util;

public enum RequestCodes {
    MAPS(1),
    CREATEMEETING(2);

    private int requestCode;

    RequestCodes(int i){
        requestCode = i;
    }

    public int getRequestCode(){
        return requestCode;
    }
}
