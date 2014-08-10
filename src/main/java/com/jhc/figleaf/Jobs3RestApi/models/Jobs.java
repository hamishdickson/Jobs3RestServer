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

    private static JobNotes jobNotes;

    static {
        deliverables.add(new Deliverable(123456, 1, 20140901, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables1.add(new Deliverable(123456, 1, 20140723, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables1.add(new Deliverable(123456, 1, 20141001, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables2.add(new Deliverable(123456, 1, 20140701, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));
        deliverables2.add(new Deliverable(123456, 1, 20140501, "I", "First deliverable", 0, "HD", "N", "F63", "Y"));

        jobNotes = new JobNotes(123456, "This is the job notes for job number 123456", 0);

        List<String> note = new ArrayList<String>();

        note.add("This is the job notes for job number 123456");

        jobList.add(new Job(123456, "first description 123456", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables, "Response 1", note));
        jobList.add(new Job(223456, "2 description (with no response)", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables1, "Repsonse", note));
        jobList.add(new Job(323456, "3 description", "HD", "W", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables1, "Response 3", note));
        jobList.add(new Job(423456, "4 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2, "Response 4", note));
        jobList.add(new Job(523456, "5 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2, "Response 5", note));
        jobList.add(new Job(623456, "6 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2, "Response 6", note));
        jobList.add(new Job(723456, "7 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables2, "Response 7", note));
        jobList.add(new Job(823456, "8 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables, "Response 8", note));
        jobList.add(new Job(923456, "9 description", "HD", "A", "JHC", 3, "JHC", "Hamish", 2, "J", "HD", "WEBWEB", "TRACEY", "Test Job 1", 20140624, 900, "N", "L", "F63", "JOBS", "Y", deliverables, "Response 9", note));
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

    public static void updateNote(JobNotes jobNotes) {
        Jobs.jobNotes.setNotes(jobNotes.getNotes());
    }
}
