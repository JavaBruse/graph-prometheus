package ru.javabruse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @GetMapping("/")
    public String mainPage() {
        return "Главная страница Спринг графана прометеус";
    }

    private static final List<byte[]> MEMORY_LEAK = new ArrayList<>();
    //http://localhost:80/load?gigabytes=5 - 5 GB
    @GetMapping("/load")
    public String heavyLoad(@RequestParam(defaultValue = "1") int gigabytes) {
        if (gigabytes > 10) {
            gigabytes = 10; // ограничение для безопасности
        }

        int chunks = gigabytes * 100; // 100 chunks по 10 MB = 1 GB
        for (int i = 0; i < chunks; i++) {
            MEMORY_LEAK.add(new byte[10 * 1024 * 1024]); // 10 MB
            try {
                Thread.sleep(10); // меньше задержка для быстрого выделения
            } catch (InterruptedException ignored) {}
        }

        return String.format("Allocated %d GB of non-collectable memory! Total: %d MB",
                gigabytes, MEMORY_LEAK.size() * 10);
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
