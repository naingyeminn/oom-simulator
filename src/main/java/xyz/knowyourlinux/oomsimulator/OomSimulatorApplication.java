package xyz.knowyourlinux.oomsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OomSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OomSimulatorApplication.class, args);

    printHeapSize();
    consumeMemory();
	}

  private static void consumeMemory() {

    long memoryLimit = Long.parseLong(System.getenv().getOrDefault("MEMORY_LIMIT", "100")); // in megabytes
    long memoryLimitBytes = memoryLimit * 1024 * 1024;


    List<byte[]> memoryList = new ArrayList<>();
    long allocatedMemory = 0;

    try {
      while (true) {
        if (allocatedMemory >= memoryLimitBytes) {
          System.out.println("Memory limit reached.");
          Thread.sleep(1000);
          continue;
        }

        byte[] memoryBlock = new byte[100 * 1024 * 1024]; // Allocate 100MB memory block
        memoryList.add(memoryBlock);
        allocatedMemory += memoryBlock.length;
        System.out.println("Allocated " + memoryList.size() * 100 + " MB");
        Thread.sleep(1000);
        printHeapSize();
      }
    } catch (InterruptedException e) {
      System.err.println("Thread interrupted!");
      e.printStackTrace();
    } catch (OutOfMemoryError e) {
      System.err.println("Out-of-Memory error occurred!");
      e.printStackTrace();
    }
  }
  private static void printHeapSize() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory() / 1024 / 1024; // Convert to megabytes
    long allocatedMemory = runtime.totalMemory() / 1024 / 1024; // Convert to megabytes
    long freeMemory = runtime.freeMemory() / 1024 / 1024; // Convert to megabytes

    System.out.println("Heap Size - Allocated: " + allocatedMemory + "MB" +
            ", Free: " + freeMemory + "MB" +
            ", Max: " + maxMemory + "MB");
  }
}
