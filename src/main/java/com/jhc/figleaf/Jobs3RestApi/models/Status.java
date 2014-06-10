package com.jhc.figleaf.Jobs3RestApi.models;

/**
 * Created by hamish dickson on 12/03/2014.
 *
 * We will probably get rid of this when we move completely to jira
 */
public enum Status {
    NOT_STARTED("A"),
    STARTED("B"),
    CLOSED("C"),
    ON_HOLD("H"),
    ON_WAIT("W");

    private String statusCode;

    private Status(String s) {
        statusCode = s;
    }

    private String getStatusCode() {
        return statusCode;
    }
}
