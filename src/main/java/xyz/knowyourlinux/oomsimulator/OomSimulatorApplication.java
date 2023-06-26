package xyz.knowyourlinux.oomsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SpringBootApplication
public class OomSimulatorApplication {

	public static void main(String[] args) {

		SpringApplication.run(OomSimulatorApplication.class, args);

    printHeapSize();
    consumeMemory();
	}

  private static void consumeMemory() {

    boolean runRandomMemoryAllocation = Boolean.parseBoolean(System.getenv().getOrDefault("RANDOM_MEMORY_ALLOCATION", "True"));
    long memoryLimit = Long.parseLong(System.getenv().getOrDefault("MEMORY_LIMIT", "100")); // in megabytes
    int memoryIncrement = Integer.parseInt(System.getenv().getOrDefault("MEMORY_INCREMENT", "10")); // in megabytes
    long memoryLimitBytes = memoryLimit * 1024 * 1024;
    byte[] memoryBlock;

    List<byte[]> memoryList = new ArrayList<>();
    long allocatedMemory = 0;

    Random random = new Random();

    try {
      while (true) {
        if (allocatedMemory >= memoryLimitBytes) {
          System.out.println("Memory limit reached.");
          Thread.sleep(1000);
          continue;
        }
        if (runRandomMemoryAllocation) {
          int randomIncrement = random.nextInt(memoryIncrement);
          memoryBlock = new byte[randomIncrement * 1024 * 1024]; // Allocate random memory block
        } else {
          memoryBlock = new byte[memoryIncrement * 1024 * 1024]; // Allocate memory block by MEMORY_INCREMENT
        }

        memoryList.add(memoryBlock);
        allocatedMemory += memoryBlock.length;
        Thread.sleep(1000);

        if (!memoryList.isEmpty() && runRandomMemoryAllocation) {
          int index = random.nextInt(memoryList.size());
          byte[] memoryAllocation = memoryList.remove(index);
        }

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

