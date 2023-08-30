import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

     //Setting total Memory Size and partition Size
    static int memorySize = 256;
    static int partitionsize = 32;

    static int numberOfPartitions = memorySize / partitionsize;

    //Making a memory
    static int[][] memory = new int[numberOfPartitions][partitionsize];

    static ArrayList<Process> processList = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    static int processId = 0;

    public static void main(String[] args) {

       
        //initializing memory
        for (int i = 0; i < numberOfPartitions; i++) {
            for (int j = 0; j < partitionsize; j++) {
                memory[i][j] = 0;
            }
        }
        

        while(true) {
            
            printmemory();

            printdashedline();

            printcommandinfo();

            printdashedline();

            int userInput = scanner.nextInt();
            
            switch (userInput) {
                
                case 0:
                    System.out.println("Exiting program");
                    scanner.close();
                    return;

                case 1:
                    Process newProcess = new Process(processId, "Process " + processId);
                    processList.add(newProcess);
                    processId++;

                    System.out.println("New process created with id " + newProcess.getId()); 

                    newProcess.setArrivalTime(new Time(System.currentTimeMillis()));
                    
                    System.out.println("Enter the size of Required memory : ");
                    int requiredMemory = scanner.nextInt();
                    newProcess.setRequiredMemory(requiredMemory);

                    allocateMemory(newProcess , requiredMemory);
                    
                    printdashedline();
                    break;
                
                case 2:
                    printprocessinfo();
                    printdashedline();
                    break;
                    
                case 3:
                    printmemoryinfo();
                    printdashedline();
                    break;
                    
                case 4:
                    terminateprocess();
                    printdashedline();
                    break;
                
                case 5:
                    terminateprocesses();
                    printdashedline();
                    break;
                
                case 6:
                    allocate_waiting_processes();
                    printdashedline();
                    break;
                
                case 7:
                    contextswitch();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void contextswitch() {
        for(Process process : processList){
            if(process.isAllocated()){
                System.out.println("Process " + process.getId() + " is being context switched");
               
                if(process.getNo_of_partitions() == 1){
                    int partition = process.getpartitionallocated();
                    for(int i=0 ; i<partitionsize ; i++){
                        memory[partition][i] = 0;
                    }
                    
                }else if(process.getNo_of_partitions() > 1){
                    int partitionstart = process.getPartitionStart();
                    int partitionend   = process.getPartitionEnd();
                    
                    for(int i=partitionstart ; i<=partitionend ; i++){
                        for(int j=0 ; j<partitionsize ; j++){
                            memory[i][j] = 0;
                        }
                    }
                }
                
                process.setAllocated(false);

                processList.remove(process);
                System.out.println("Process " + process.getId() + " context switched");
                
                allocate_waiting_processes();
                
                processList.add(process);
                break;
            }
        }
    
    }

    private static void allocate_waiting_processes() {
        for (int i = 0; i < processList.size(); i++) {
            if(!processList.get(i).isAllocated()){
                allocateMemory(processList.get(i), processList.get(i).getRequiredMemory());
            }
        }
    }

    private static void terminateprocesses() {

        for(int i=0 ; i<processList.size() ; i++){
            processList.clear();
        }
        
        for (int i = 0; i < numberOfPartitions; i++) {
                for (int j = 0; j < partitionsize; j++) {
                    memory[i][j] = 0;
                }
            }
        System.out.println("All processes terminated");
        System.out.println("Memory Cleared");
    }
    
    private static void printdashedline() {
        System.out.println("\n-------------------------------------------------------------\n");
    }

    private static void allocateMemory(Process newProcess, int requiredMemory) {

        System.out.println("Allocating memory to process " + newProcess.getId());

        if(requiredMemory > memorySize){
            System.out.println("Process to big for System");
            System.out.println("Process removed from System");
            processList.remove(newProcess);
        }
        else if(requiredMemory <=0 ){
            System.out.println("No Memory Required");
            System.out.println("Process Terminated");
            processList.remove(newProcess);
        }

        else if(requiredMemory <= partitionsize){
             Boolean allocated = false;
              for (int i = 0; i < numberOfPartitions; i++) {
                if(memory[i][0] == 0){
                     System.out.println("Process " + newProcess.getId() + " is allocated to partition " + i);
                     newProcess.setpartitionallocated(i);
                     newProcess.setAllocated(true);
                     newProcess.setNo_of_partitions(1);

                     for(int j=0 ; j<requiredMemory ; j++){
                            memory[i][j] = 1;
                     }
                     allocated = true;
                     break;
                }
              }
                if(!allocated){
                    newProcess.setAllocated(false);
                    System.out.println("Not enough Memory");
                    System.out.println("Process Added to wait state");
                }

        }else if(requiredMemory > partitionsize){
            int numberOfPartitionsRequired = requiredMemory / partitionsize;
            if(requiredMemory % partitionsize != 0){
                numberOfPartitionsRequired++;
            }
            int count = 0;
            Boolean allocated = false;
            for (int i = 0; i < numberOfPartitions; i++) {
                if(memory[i][0] == 0){
                    count++;
                    if(count == numberOfPartitionsRequired){
                        System.out.println("Process " + newProcess.getId() + " is allocated to partition " + (i-numberOfPartitionsRequired+1) + " to " + i);
                        newProcess.setAllocated(true);
                        newProcess.setPartitionStart(i-numberOfPartitionsRequired+1);
                        newProcess.setPartitionEnd(i);
                        newProcess.setNo_of_partitions(numberOfPartitionsRequired);

                        for(int j=i-numberOfPartitionsRequired+1 ; j<=i ; j++){
                            for(int k=0 ; k<partitionsize ; k++){
                                requiredMemory--;
                                memory[j][k] = 1;
                                if(requiredMemory == 0){
                                    break;
                                }
                            }
                        }
                        
                        allocated = true;
                        break;
                    }
                }else{
                    count = 0;
                }
            }
            if(!allocated){
                newProcess.setAllocated(false);
                System.out.println("Not enough Memory");
                System.out.println("Process Added to wait state");
            }
        }
    }

    private static void terminateprocess() {
        System.out.println("Enter the id of the process to terminate:");
                int processToTerminate = scanner.nextInt();
                boolean found = false;
                boolean freed = false;

                for (Process process : processList) {
                    if (process.getId() == processToTerminate) {
                        if (process.isAllocated()) {
                            if(process.getNo_of_partitions() == 1 ){
                                int partition = process.getpartitionallocated();
                                for (int i = 0; i < partitionsize; i++) {
                                    memory[partition][i] = 0;
                                }
                            }else if (process.getNo_of_partitions() > 1){
                                int to = process.getPartitionStart() ;
                                int from = process.getPartitionEnd() ; 
                                for(int i=to ; i<=from ; i++){
                                    for (int j = 0; j < partitionsize; j++) {
                                        memory[i][j] = 0;
                                    }
                                }
                            }
                            
                            freed = true ; 
                        } 
                        
                        processList.remove(process);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("No process found with id " + processToTerminate);
                } else {
                    System.out.println("Process with id " + processToTerminate + " terminated successfully");
                    if(freed){
                        System.out.println("Memory Freed Succesfully");
                    }else{
                        System.out.println("No Memory Freed");
                    }
                }
    }

    private static void printmemoryinfo() {
                    System.out.println("Current Memory Status : ");
                    for (int i = 0; i < numberOfPartitions; i++) {
                        System.out.print("Partition " + i + ": ");
                        System.out.print("Size : " + partitionsize + " | ");
                        int filled = 0 ; 
                        for (int j = 0; j < partitionsize; j++) {
                            if(memory[i][j] == 1){
                                filled ++ ; 
                            }
                        }
                        System.out.print("Used : " + filled + " | ");
                        System.out.print("Waste : " + (partitionsize-filled) + " | ");

                        System.out.println();
                    }
    }

    private static void printprocessinfo() {
        for (Process process : processList) {
            System.out.print("Process ID : " + process.getId() + " | ");
            if(process.isAllocated() && process.getNo_of_partitions() == 1 ){
                System.out.print("Memory Allocated at partition : " + process.getpartitionallocated() + " | ");
            }else if(!process.isAllocated()){
                System.out.print("Process is in wait State" + " | ");
            }else if(process.isAllocated() && process.getNo_of_partitions() > 1){
                System.out.print("Process is allocated to multiple partitions ");
                System.out.print(" From " + process.getPartitionStart() + " to " + process.getPartitionEnd() + " | ");
            }
            System.out.println();
        }
    }

    private static void printcommandinfo() {
        System.out.println("Enter : \n"+
                                "1 : To create a new process \n" + 
                                "2 : To print all processes Data    | " + " 3 : To print memory status \n"+
                                "4 : To remove a process            | " + " 5 : To remove all processes \n"+
                                "6 : To Allocate Memory to Waiting Process \n"+ 
                                "7 : To Context Switch \n" +
                                "0 : To exit program");
    }

    public static void  printmemory(){

        System.out.println("Current Memory Status");
            for (int i = 0; i < numberOfPartitions; i++) {
                System.out.print("Partition " + i + ": ");
                for (int j = 0; j < partitionsize; j++) {
                    System.out.print(memory[i][j] + " ");
                }
                System.out.println();
            }
    }


}
