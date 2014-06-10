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

    static {
        /*jobList.add(new Job(1, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));
        jobList.add(new Job(2, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));
        jobList.add(new Job(3, "this is a test job", "me do", "A", "bob", 1, "something", "string", 1, "another"));*/
        jobList.add(new Job());
        jobList.add(new Job());
        jobList.add(new Job());
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
