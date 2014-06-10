package com.jhc.figleaf.Jobs3RestApi.models;

/**
 * Created by hamish dickson on 09/03/2014.
 *
 * Bean for particle
 */
public class Job {
    private int jobNumber;
    private String description;
    private String whoDo;
    private String status;
    private String client;
    private int importance;
    private String whoPay;
    private String contact;
    private int workorder;
    private String jobType;
    private String enteredBy;
    private String functionalArea;
    private String system;
    private String invoiceText;
    private int enteredDate;
    private int enteredTime;
    private String defect;
    private String liveUat;
    private String releaseVersion;
    private String project;
    private String urgent;

    public Job(int jobNumber, String description, String whoDo, String status, String client, int importance, String whoPay, String contact, int workorder, String jobType, String enteredBy, String functionalArea, String system, String invoiceText, int enteredDate, int enteredTime, String defect, String liveUat, String releaseVersion, String project, String urgent) {
        this.jobNumber = jobNumber;
        this.description = description;
        this.whoDo = whoDo;
        this.status = status;
        this.client = client;
        this.importance = importance;
        this.whoPay = whoPay;
        this.contact = contact;
        this.workorder = workorder;
        this.jobType = jobType;
        this.enteredBy = enteredBy;
        this.functionalArea = functionalArea;
        this.system = system;
        this.invoiceText = invoiceText;
        this.enteredDate = enteredDate;
        this.enteredTime = enteredTime;
        this.defect = defect;
        this.liveUat = liveUat;
        this.releaseVersion = releaseVersion;
        this.project = project;
        this.urgent = urgent;
    }

    public Job() {
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFunctionalArea() {
        return functionalArea;
    }

    public void setFunctionalArea(String functionalArea) {
        this.functionalArea = functionalArea;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getInvoiceText() {
        return invoiceText;
    }

    public void setInvoiceText(String invoiceText) {
        this.invoiceText = invoiceText;
    }

    public int getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(int enteredDate) {
        this.enteredDate = enteredDate;
    }

    public int getEnteredTime() {
        return enteredTime;
    }

    public void setEnteredTime(int enteredTime) {
        this.enteredTime = enteredTime;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getLiveUat() {
        return liveUat;
    }

    public void setLiveUat(String liveUat) {
        this.liveUat = liveUat;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(String whoPay) {
        this.whoPay = whoPay;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getWorkorder() {
        return workorder;
    }

    public void setWorkorder(int workorder) {
        this.workorder = workorder;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhoDo() {
        return whoDo;
    }

    public void setWhoDo(String whoDo) {
        this.whoDo = whoDo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        return jobNumber == job.jobNumber;

    }

    @Override
    public int hashCode() {
        return jobNumber;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobNumber=" + jobNumber +
                ", description='" + description + '\'' +
                ", whoDo='" + whoDo + '\'' +
                ", status='" + status + '\'' +
                ", client='" + client + '\'' +
                ", importance=" + importance +
                ", whoPay='" + whoPay + '\'' +
                ", contact='" + contact + '\'' +
                ", workorder=" + workorder +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
