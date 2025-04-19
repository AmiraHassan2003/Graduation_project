package com.shop.shop.Holder;

public class TokenHolder {
private static String Token;
private static String State;


public TokenHolder(){

}

public static void clearToken(){
    Token = null;
}

public static String getToken() {
    return Token;
}

public static void setToken(String jwtToken) {
    Token = jwtToken;
}

public static String getState() {
    return State;
}

public static void setState(String stste) {
    State = stste;
}
public static void clearState(){
    State = null;
}

}


