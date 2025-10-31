package ru.javabruse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {
    @GetMapping("/")
    public String mainPage() {
        return "Главная страница Спринг графана прометеус";
    }

    private static final List<String> MEMORY_LEAK = new ArrayList<>();

    @GetMapping("/load")
    public String heavyLoad() {

        for (int i = 0; i < 200000; i++) {
            char[] chars = new char[500];
            Arrays.fill(chars, (char) ('A' + (i % 26)));
            MEMORY_LEAK.add(new String(chars));
        }

        return "Loaded 200MB! Total: " + MEMORY_LEAK.size();
    }

    @GetMapping("/clear")
    public String clearMemory() {
        MEMORY_LEAK.clear();
        System.gc();
        return "Memory cleared!";
    }

    @GetMapping("/memory-status")
    public String memoryStatus() {
        Runtime runtime = Runtime.getRuntime();
        return String.format(
                "Memory Status:\n" +
                        "Used: %d MB\n" +
                        "Free: %d MB\n" +
                        "Total: %d MB\n" +
                        "Max: %d MB\n" +
                        "Static memory chunks: %d",
                (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024),
                runtime.freeMemory() / (1024 * 1024),
                runtime.totalMemory() / (1024 * 1024),
                runtime.maxMemory() / (1024 * 1024),
                MEMORY_LEAK.size()
        );
    }

}
