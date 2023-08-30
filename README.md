### Fixed-Partition-Memory-Simulation-java
An Java program simulating fixed-size partition memory allocation, enabling users to create processes and allocate memory using a first-fit algorithm. It provides basic memory allocation and process management functionalities.

### Memory and Partitions Design
The memory is represented as a two-dimensional array with the number of partitions determined by the total memory size divided by the partition size. Since the alogirithim is fixed size partition so we have used array. Each element of the array represents a partition, and its value (0 or 1) indicates the allocation status.

#### Example : 

Let's take an example to understand how the memory and partitions are configured:

- **Total Memory Size:** 256
- **Partition Size:** 32

With the provided configuration:
- The number of partitions can be calculated by dividing the total memory size by the partition size:
  Total Partitions = Total Memory Size / Partition Size
  Total Partitions = 256 / 32
  Total Partitions = 8

- Each partition is represented as a row in a 2D array. In this case, the memory configuration will be [8] [32].
  - 8 rows (partitions) and 32 columns (partition size).

This setup results in a total memory size of 256 units, divided into 8 partitions, each with a size of 32 units.

Initially, the memory array is initialized with all elements set to 0. This signifies that each partition is empty and available for memory allocation. As processes are allocated memory, the corresponding partition elements will be marked as 1 to indicate allocation.


 ### User Inputs and Actions
The program provides a menu-driven interface for users to interact with. Users can:
- Create new processes and allocate memory to them.
- Print process information and memory status.
- Terminate specific processes and deallocate memory.
- Terminate all processes and clear memory.
- Allocate memory to waiting processes.
- Simulate context switching.



**Note:** This code is a simplified simulation and might not cover all complexities of real-world memory management systems.

