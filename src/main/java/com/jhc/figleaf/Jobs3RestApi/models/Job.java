package com.jhc.figleaf.Jobs3RestApi.models;

import java.util.List;

/**
 * Created by hamish dickson on 09/03/2014.
 *
 * Bean for job
 */
public class Job {
    private int jobNumber;
    private String description;
    private String response;
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
    private List<Deliverable> deliverables;

/*    public Job(int jobNumber, String description, String whoDo, String status, String client, int importance, String whoPay, String contact, int workorder, String jobType, String enteredBy, String functionalArea, String system, String invoiceText, int enteredDate, int enteredTime, String defect, String liveUat, String releaseVersion, String project, String urgent) {
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
    }*/

    public Job(int jobNumber, String description, String whoDo, String status, String client, int importance, String whoPay, String contact, int workorder, String jobType, String enteredBy, String functionalArea, String system, String invoiceText, int enteredDate, int enteredTime, String defect, String liveUat, String releaseVersion, String project, String urgent, List<Deliverable> deliverables, String response) {
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
        this.deliverables = deliverables;
        this.response = response;
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

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (enteredDate != job.enteredDate) return false;
        if (enteredTime != job.enteredTime) return false;
        if (importance != job.importance) return false;
        if (jobNumber != job.jobNumber) return false;
        if (workorder != job.workorder) return false;
        if (client != null ? !client.equals(job.client) : job.client != null) return false;
        if (contact != null ? !contact.equals(job.contact) : job.contact != null) return false;
        if (defect != null ? !defect.equals(job.defect) : job.defect != null) return false;
        if (deliverables != null ? !deliverables.equals(job.deliverables) : job.deliverables != null) return false;
        if (description != null ? !description.equals(job.description) : job.description != null) return false;
        if (enteredBy != null ? !enteredBy.equals(job.enteredBy) : job.enteredBy != null) return false;
        if (functionalArea != null ? !functionalArea.equals(job.functionalArea) : job.functionalArea != null)
            return false;
        if (invoiceText != null ? !invoiceText.equals(job.invoiceText) : job.invoiceText != null) return false;
        if (jobType != null ? !jobType.equals(job.jobType) : job.jobType != null) return false;
        if (liveUat != null ? !liveUat.equals(job.liveUat) : job.liveUat != null) return false;
        if (project != null ? !project.equals(job.project) : job.project != null) return false;
        if (releaseVersion != null ? !releaseVersion.equals(job.releaseVersion) : job.releaseVersion != null)
            return false;
        if (status != null ? !status.equals(job.status) : job.status != null) return false;
        if (system != null ? !system.equals(job.system) : job.system != null) return false;
        if (urgent != null ? !urgent.equals(job.urgent) : job.urgent != null) return false;
        if (whoDo != null ? !whoDo.equals(job.whoDo) : job.whoDo != null) return false;
        if (whoPay != null ? !whoPay.equals(job.whoPay) : job.whoPay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobNumber;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (whoDo != null ? whoDo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + importance;
        result = 31 * result + (whoPay != null ? whoPay.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + workorder;
        result = 31 * result + (jobType != null ? jobType.hashCode() : 0);
        result = 31 * result + (enteredBy != null ? enteredBy.hashCode() : 0);
        result = 31 * result + (functionalArea != null ? functionalArea.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (invoiceText != null ? invoiceText.hashCode() : 0);
        result = 31 * result + enteredDate;
        result = 31 * result + enteredTime;
        result = 31 * result + (defect != null ? defect.hashCode() : 0);
        result = 31 * result + (liveUat != null ? liveUat.hashCode() : 0);
        result = 31 * result + (releaseVersion != null ? releaseVersion.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (urgent != null ? urgent.hashCode() : 0);
        result = 31 * result + (deliverables != null ? deliverables.hashCode() : 0);
        return result;
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
                ", enteredBy='" + enteredBy + '\'' +
                ", functionalArea='" + functionalArea + '\'' +
                ", system='" + system + '\'' +
                ", invoiceText='" + invoiceText + '\'' +
                ", enteredDate=" + enteredDate +
                ", enteredTime=" + enteredTime +
                ", defect='" + defect + '\'' +
                ", liveUat='" + liveUat + '\'' +
                ", releaseVersion='" + releaseVersion + '\'' +
                ", project='" + project + '\'' +
                ", urgent='" + urgent + '\'' +
                ", deliverables=" + deliverables +
                '}';
    }
}
