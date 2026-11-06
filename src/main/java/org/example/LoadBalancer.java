package org.example;

import java.util.*;

class Resource{
    String name;
    int totalCpu;
    int totalMem;
    int usedCpu;
    int usedMem;
    public Resource(String name, int cpu, int mem) {
        this.name = name;
        this.totalCpu = cpu;
        this.totalMem = mem;
        this.usedCpu = 0;
        this.usedMem = 0;
    }
    public boolean canAllocate(int cpu, int mem) {
        if ( cpu + usedCpu <= totalCpu && mem +usedMem <= totalMem) {
            return true;

        }
        return false;
    }
    public String Allocate(int cpu, int mem) {
        usedCpu += cpu;
        usedMem += mem;
        return "allocated";

    }
    public String toString() {
        return "name: " + name + " cpu: " + usedCpu + " mem: " + usedMem;
    }

}
class Job {
    String id;
    int cpu;
    int mem;

    public Job(String id, int cpu, int mem) {
        this.id = id;
        this.cpu = cpu;
        this.mem = mem;
    }
}
class Main {
    public static void main(String[] args){
        Map<String, Resource> resources = new LinkedHashMap<>();
        resources.put("R1", new Resource("R1", 8, 16));
        resources.put("R2", new Resource("R2", 6, 12));
        resources.put("R3", new Resource("R3", 10, 20));






        List<Job> jobs = Arrays.asList(
                new Job("J1", 2, 4),
                new Job("J2", 4, 8),
                new Job("J1", 8, 16),
                new Job("J2", 16, 24)
                );

        List<String> resourceKeys = new ArrayList<>(resources.keySet());
        int currentIdx = 0;


        for (Job job : jobs) {
            boolean assigned = false;
            int attempts = 0;

            while (attempts < resourceKeys.size()) {
                String resourceKey = resourceKeys.get(currentIdx);
                Resource resource = resources.get(resourceKey);
                if (resource.canAllocate(job.cpu, job.mem)) {
                    resource.Allocate(job.cpu, job.mem);
                    System.out.println("Job " + job.id + " (CPU:" + job.cpu + ", Mem:" + job.mem + ") assigned to " + resource.name);
                    currentIdx = (currentIdx + 1) % resourceKeys.size();
                    assigned = true;
                    System.out.println("Trying next resource...");
                    System.out.println("Current resource: " + resource.name);
                    System.out.println("Current attempts: " + attempts);
                    System.out.println("Current index: " + currentIdx);
                    break;
                }
                else {
                    currentIdx = (currentIdx + 1) % resourceKeys.size();
                    attempts++;

                }

            }


            if (!assigned) {
                System.out.println("Job " + job.id + " could not be assigned: Insufficient resources cluster-wide.");
            }

            }
        System.out.println("\nFinal Resource States:");
        for (Resource r : resources.values()) {
            System.out.println(r);
        }

        }

    }
