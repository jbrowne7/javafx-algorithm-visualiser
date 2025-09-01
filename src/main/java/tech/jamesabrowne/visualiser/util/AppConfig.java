package tech.jamesabrowne.visualiser.util;

public record AppConfig(
        String algorithm
) {
    public static AppConfig defaults() {
        return new AppConfig("dijkstra");
    }
}