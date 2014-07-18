package com.jhc.figleaf.Jobs3RestApi.models;

import java.util.List;

/**
 * Created by hamishdickson on 17/06/2014.
 *
 */
public class JobNotes {
    private int jobNumber;
    private String notes;
    private int softwarePackage;

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSoftwarePackage() {
        return softwarePackage;
    }

    public void setSoftwarePackage(int softwarePackage) {
        this.softwarePackage = softwarePackage;
    }

    public JobNotes(int jobNumber, String notes, int softwarePackage) {
        this.jobNumber = jobNumber;
        this.notes = notes;
        this.softwarePackage = softwarePackage;
    }

    public JobNotes(int jobNumber, List<String> notesList, int softwarePackage) {
        this.jobNumber = jobNumber;
        this.softwarePackage = softwarePackage;

        StringBuilder sb = new StringBuilder();

        for (String s: notesList) {
            sb.append(s);
        }

        // remove the last space
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        this.notes = sb.toString();
    }

    @Override
    public String toString() {
        return "JobNotes{" +
                "jobNumber=" + jobNumber +
                ", notes='" + notes + '\'' +
                ", softwarePackage=" + softwarePackage +
                '}';
    }
}
