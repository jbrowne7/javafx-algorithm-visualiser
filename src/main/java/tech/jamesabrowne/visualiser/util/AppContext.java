package tech.jamesabrowne.visualiser.util;

public final class AppContext {
    private static volatile AppConfig config = AppConfig.defaults();
    private AppContext() {}
    public static void setConfig(AppConfig c) { config = c; }
    public static AppConfig getConfig() { return config; }
}
