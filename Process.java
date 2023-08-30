import java.sql.Time;

class Process {

    int id;
    String name;

    int requiredMemory;
    Time arrivalTime;
   
    boolean isAllocated;

    int no_of_partitions;
    int partitionallocated;
    int PartitionStart;
    int PartitionEnd;
    

    //constructors
    public Process(){
        this.id = 0;
        this.name = "";
    }
    public Process(int id, String name){
        this.id = id;
        this.name = name;
        this.isAllocated = false;
    }

    //Getters and Setters

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public boolean isAllocated() {
        return isAllocated;
    }
    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }
    
    public int getpartitionallocated() {
        return this.partitionallocated;
    }
    public void setpartitionallocated(int partitionallocated) {
        this.partitionallocated = partitionallocated;
    }

    public int getPartitionStart() {
        return PartitionStart;
    }

    public void setPartitionStart(int partitionStart) {
        PartitionStart = partitionStart;
    }

    public int getPartitionEnd() {
        return PartitionEnd;
    }

    public void setPartitionEnd(int partitionEnd) {
        PartitionEnd = partitionEnd;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNo_of_partitions() {
        return no_of_partitions;
    }

    public void setNo_of_partitions(int no_of_partitions) {
        this.no_of_partitions = no_of_partitions;
    }

}
