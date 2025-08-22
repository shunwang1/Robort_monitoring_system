package cn.iua.caas.robotdatahub.context;


public class UserContext {
    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void setUser(String username) {
        userThreadLocal.set(username);
    }

    public static String getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
