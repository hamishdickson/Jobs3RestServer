package com.jhc.figleaf.Jobs3RestApi.models;

/**
 * Created by hamishdickson on 26/06/2014.
 *
 */
public class Person {
    private String name;
    private String initials;
    private String teamLeader;
    private String windowsProfileName;

    public Person(String name, String initials, String teamLeader, String windowsProfileName) {
        this.name = name;
        this.initials = initials;
        this.teamLeader = teamLeader;
        this.windowsProfileName = windowsProfileName;
    }

    public String getWindowsProfileName() {
        return windowsProfileName;
    }

    public void setWindowsProfileName(String windowsProfileName) {
        this.windowsProfileName = windowsProfileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
}
