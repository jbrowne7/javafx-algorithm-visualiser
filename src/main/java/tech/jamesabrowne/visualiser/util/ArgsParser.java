package tech.jamesabrowne.visualiser.util;

import java.util.HashMap;
import java.util.Map;

public class ArgsParser {
    private ArgsParser() {}

    public static AppConfig parse(String[] args) {

        Map<String, String> m = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a.startsWith("--") && a.contains("=")) {
                String[] kv = a.substring(2).split("=", 2);
                m.put(kv[0], kv[1]);
            } else if (a.startsWith("--") && i + 1 < args.length && !args[i + 1].startsWith("--")) {
                m.put(a.substring(2), args[++i]);
            }
        }

        AppConfig d = AppConfig.defaults();
        String alg = m.getOrDefault("algorithm", d.algorithm());
        return new AppConfig(alg);
    }
}
