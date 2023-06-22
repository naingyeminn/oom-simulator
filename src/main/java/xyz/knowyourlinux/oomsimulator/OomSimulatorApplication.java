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

    List<byte[]> memoryList = new ArrayList<>();
    int arraySize = 100 * 1024 * 1024; // 100MB

    try {
      while (true) {
        byte[] memory = new byte[arraySize];
        memoryList.add(memory);
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
