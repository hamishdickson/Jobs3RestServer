package com.jhc.figleaf.Jobs3RestApi.models;

/**
 * Created by DicksonH on 10/07/2014.
 *
 */
public class DeliverableKey {
    private int jobNumber;
    private int id;
    private int promisedDate;

    public DeliverableKey(int jobNumber, int id, int promisedDate) {
        this.jobNumber = jobNumber;
        this.id = id;
        this.promisedDate = promisedDate;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromisedDate() {
        return promisedDate;
    }

    public void setPromisedDate(int promisedDate) {
        this.promisedDate = promisedDate;
    }
}
