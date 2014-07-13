package com.jhc.figleaf.Jobs3RestApi.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamish dickson on 09/03/2014.
 *
 * Bit of dummy data
 */
public class Jobs {
    private static List<Job> jobList = new ArrayList<Job>();

    private static List<Deliverable> deliverables = new ArrayList<Deliverable>();
    private static List<Deliverable> deliverables1 = new ArrayList<Deliverable>();
    private static List<Deliverable> deliverables2 = new ArrayList<Deliverable>();

    static {
        /*jobList.add(new Job(1, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));
        jobList.add(new Job(2, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));
        jobList.add(new Job(3, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));*/
        deliverables.add(new Deliverable(123456, 1, 20140801, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables1.add(new Deliverable(123456, 1, 20140701, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables1.add(new Deliverable(123456, 1, 20140601, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables2.add(new Deliverable(123456, 1, 20140701, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables2.add(new Deliverable(123456, 1, 20140501, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));

        jobList.add(new Job(123456, "first description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables));
        jobList.add(new Job(223456, "2 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables1));
        jobList.add(new Job(323456, "3 description", "HD", "W", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables1));
        jobList.add(new Job(423456, "4 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2));
        jobList.add(new Job(523456, "5 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2));
        jobList.add(new Job(623456, "6 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2));
        jobList.add(new Job(723456, "7 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2));
        jobList.add(new Job(823456, "8 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables));
        jobList.add(new Job(923456, "9 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables));
    }

    public static void addJob(Job job) {
        jobList.add(job);
    }

    public static boolean deleteJob(int jobNumber) {
        for (Job job : jobList) {
            if (job.getJobNumber() == jobNumber) {
                jobList.remove(job);
                return true;
            }
        }
        return false;
    }

    public static void setJob(Job job) {
        if (jobList.contains(job)) {
            jobList.set(jobList.indexOf(job), job);
        } else {
            jobList.add(job);
        }
    }

    public static String getJobJson(int jobNumber) {
        for (Job job : jobList) {
            if (job.getJobNumber() == jobNumber) {
                return new Gson().toJson(job);
            }
        }
        //return new Gson().toJson(new Job(0, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));
        return new Gson().toJson(new Job());
    }

    public static String getJobsList() {
        return new Gson().toJson(jobList);
    }

    public static String toJsonString() {
        return new Gson().toJson(jobList);
    }

    public static boolean isInKnownJob(int jobNumber) {
        for(Job job : jobList) {
            if (job.getJobNumber() == jobNumber) {
                return true;
            }
        }
        return false;
    }
}
