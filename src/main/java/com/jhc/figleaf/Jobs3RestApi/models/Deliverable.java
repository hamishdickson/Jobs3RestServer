package com.jhc.figleaf.Jobs3RestApi.models;

/**
 * Created by DicksonH on 10/07/2014.
 *
 */
public class Deliverable {
    private int jobNumber;
    private int id;
    private int promisedDate;
    private String type;
    private String description;
    private int deliveredDate;
    private String whoDo;
    private String deleted;
    private String app;
    private String internal;

    public Deliverable(int jobNumber, int id, int promisedDate, String type, String description, int deliveredDate, String whoDo, String deleted, String app, String internal) {
        this.jobNumber = jobNumber;
        this.id = id;
        this.promisedDate = promisedDate;
        this.type = type;
        this.description = description;
        this.deliveredDate = deliveredDate;
        this.whoDo = whoDo;
        this.deleted = deleted;
        this.app = app;
        this.internal = internal;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(int deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getWhoDo() {
        return whoDo;
    }

    public void setWhoDo(String whoDo) {
        this.whoDo = whoDo;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getInternal() {
        return internal;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }
}
