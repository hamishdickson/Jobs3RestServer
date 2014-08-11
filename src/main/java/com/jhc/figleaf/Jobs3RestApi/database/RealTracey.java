package com.jhc.figleaf.Jobs3RestApi.database;

import com.ibm.as400.access.*;
import com.jhc.figleaf.Jobs3RestApi.models.*;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.jhc.figleaf.Jobs3RestApi.utils.ConfigManager;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamish dickson on 12/03/2014.
 *
 * Go and get the data from tracey
 */
public class RealTracey {

    private static final String DB_CONNECTION = "jdbc:as400://" + ConfigManager.getSetting("server.address") + ";naming=system;prompt=false";
    private static final String DB_USER = ConfigManager.getSetting("username");
    private static final String DB_PASSWORD = ConfigManager.getSetting("password");
    private static final BasicDataSource dataSource = new BasicDataSource();
    private static final AS400 as400;

    private static final int CLOSE_DATE = 20391231; // this is hard coded in jobs3
    private static final String JOB_FIELDS = "CODEX, DESCRQ, WHODO, STATUS, CLIENT, IMPORT, WHOPAY, CONTAC, BCODEX, JTYPE, EXTRA4, EXTRA1, SYSTEM, INVTXT, REQUES, TIMEIN, JBUG, LIVUAT, RLSVER, PROJ, DESCRP";

    private static Statement statement;
    private static ResultSet resultSet;

    private static Connection connection;

    /**
     * specify the library
     */
    private static final String LIBRARY = ConfigManager.getSetting("library");

    static {
        dataSource.setDriverClassName("com.ibm.as400.access.AS400JDBCDriver");
        dataSource.setMaxActive(2);
        dataSource.setMaxIdle(2);
        dataSource.setValidationQuery("SELECT * FROM " + LIBRARY + "/JOBS3 WHERE CODEX = 170396");
        dataSource.setTestOnBorrow(true);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_CONNECTION);
        as400 = new AS400("TRACEY", DB_USER, DB_PASSWORD);
        System.out.println("Got configuration data: " + DB_CONNECTION);
    }

    private static ResultSet getResultSet(String sqlStatement) throws SQLException {
        connection = dataSource.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);

        return resultSet;
    }

    private static void closeDownQuery() throws SQLException {
        statement.close();
        resultSet.close();
        connection.close();
    }

    public static Job getJob(int jobNumber) throws SQLException {

        Job job = null;
        List<Deliverable> deliverables = getOpenDeliverablesForJob(jobNumber);

        List<String> notes = new ArrayList<String>();

        notes.add(getJobNotes(jobNumber).getNotes());

        try {
            String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE CODEX = " + jobNumber + " FETCH FIRST 1 ROWS ONLY";
            getResultSet(selectSQL);

            while (resultSet.next()) {
                job = new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N", deliverables, resultSet.getString(21), notes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownQuery();
        }
        return job;

    }

    public static Person getPersonByWhoDo(String whoDo) throws SQLException {
        Person person = null;

        try {
            String selectSql = "SELECT TENAME, TEEAR, TETEAM, TEUSER FROM TEARNER WHERE TEEAR = '" + whoDo.toUpperCase() + "' FETCH FIRST 1 ROWS ONLY";
            getResultSet(selectSql);

            while (resultSet.next()) {
                person = new Person(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownQuery();
        }
        return person;
    }

    public static List<Job> getJobsForUserAndStatus(String user, String status) throws SQLException {
        String selectSQL = "";
        if (status.toUpperCase().equals("Z")) {
            selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE WHODO = '" + user.toUpperCase() + "' AND (STATUS = 'A' OR STATUS = 'B')";
        } else {
            selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE WHODO = '" + user.toUpperCase() + "' AND STATUS = '" + status.toUpperCase() + "'";
        }
        return doSqlForGetJobsForUserAndStatus(selectSQL);
    }

    public static List<Job> getJobsForUserAndStatus(String user) throws SQLException {
        String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE WHODO = '" + user.toUpperCase() + "' AND STATUS <> 'C'" ;
        return doSqlForGetJobsForUserAndStatus(selectSQL);
    }

    // TODO is there a better way to get the deliverables?
    private static List<Job> doSqlForGetJobsForUserAndStatus(String sqlStatement) throws SQLException {
        List<Job> jobs = new ArrayList<Job>();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N", null, resultSet.getString(21), null));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
            connection.close();
        }

        List<String> notes = new ArrayList<String>();

        // ok at this point, we have the jobs, but without any deliverables information in there - sort that out
        for (Job job : jobs) {
            job.setDeliverables(getOpenDeliverablesForJob(job.getJobNumber()));

            notes.add(getJobNotes(job.getJobNumber()).getNotes());
            job.setNotes(notes);

            notes = new ArrayList<String>();
        }
        return jobs;
    }

    public static JobNotes getJobNotes(int jobNumber) throws SQLException {

        List<String> notes = new ArrayList<String>();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        try {
            String selectSQL = "SELECT TEXT79 FROM " + LIBRARY + "/JOBSCRAT WHERE CODEX = " + jobNumber + " ORDER BY PAGNUM, LINNUM" ;
            resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                notes.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
            connection.close();
        }
        return new JobNotes(jobNumber, notes, 0);
    }

    public static List<Deliverable> getDeliverablesForUser(String whodo) throws SQLException {
        List<Deliverable> deliverables = new ArrayList<Deliverable>();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        String sqlStatement = "SELECT CODEX, UNQREF, PROMD8, TYPE, DESC, DELD8, DELTED, APP, INTRNL FROM DELVRB WHERE WHODO='" + whodo.toUpperCase() + "' AND DELTED = ' ' AND DELD8 = 0 ORDER BY CODEX, PROMD8, UNQREF";
        try {
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                deliverables.add(new Deliverable(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), whodo, resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
            connection.close();
        }
        return deliverables;

    }

    public static List<Deliverable> getOpenDeliverablesForJob(int jobNumber) throws SQLException {
        List<Deliverable> deliverables = new ArrayList<Deliverable>();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        String sqlStatement = "SELECT UNQREF, PROMD8, TYPE, DESC, DELD8, WHODO, DELTED, APP, INTRNL FROM DELVRB WHERE CODEX='" + jobNumber + "' AND DELTED = ' ' AND DELD8 = 0 ORDER BY PROMD8, UNQREF";
        try {
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                deliverables.add(new Deliverable(jobNumber, resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
            connection.close();
        }
        return deliverables;

    }

    public static Deliverable getDeliverable(int jobNumber, int id) throws SQLException {
        Deliverable deliverable = null;

        try {
            String selectSQL = "SELECT PROMD8, TYPE, DESC, DELD8, WHODO, DELTED, APP, INTRNL FROM " + LIBRARY + "/DELVRB WHERE CODEX=" + jobNumber + " AND UNQREF=" + id + " FETCH FIRST 1 ROWS ONLY";
            getResultSet(selectSQL);

            while (resultSet.next()) {
                deliverable = new Deliverable(jobNumber, id, resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownQuery();
        }

        return deliverable;
    }

    /**
     * This returns a job and a work order
     */
    public static Job addJob(Job job) throws InterruptedException, IOException, SQLException, IllegalObjectTypeException, ObjectDoesNotExistException, ErrorCompletingRequestException, AS400SecurityException {

        // first get the job number
        job.setJobNumber(getNextJobNumber());

        PreparedStatement preparedStatement = null;

        try {
            Connection connection = dataSource.getConnection();
            String insertSQL = "INSERT INTO " + LIBRARY + "/JOBS3 "
                    + "(" + JOB_FIELDS + ", APPROV, COMPLE) VALUES"
                    + "(?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setInt(1, job.getJobNumber());
            preparedStatement.setString(2, job.getDescription());
            preparedStatement.setString(3, job.getWhoDo());
            preparedStatement.setString(4, job.getStatus());
            preparedStatement.setString(5, job.getClient());
            preparedStatement.setInt(6, job.getImportance());
            preparedStatement.setString(7, job.getWhoPay());
            preparedStatement.setString(8, job.getContact());
            preparedStatement.setInt(9, job.getWorkorder());
            preparedStatement.setString(10, job.getJobType());
            preparedStatement.setString(11, job.getEnteredBy());
            preparedStatement.setString(12, job.getFunctionalArea());
            preparedStatement.setString(13, job.getSystem());
            preparedStatement.setString(14, job.getInvoiceText());
            preparedStatement.setInt(15, job.getEnteredDate());
            preparedStatement.setInt(16, job.getEnteredTime());
            preparedStatement.setString(17, job.getDefect());
            preparedStatement.setString(18, job.getLiveUat());
            preparedStatement.setString(19, job.getReleaseVersion());
            preparedStatement.setString(20, job.getProject());
            preparedStatement.setString(21, job.getUrgent());
            preparedStatement.setInt(22, CLOSE_DATE);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return job;
    }

    private static int getNextJobNumber() throws SQLException, InterruptedException, IOException, IllegalObjectTypeException, ObjectDoesNotExistException, ErrorCompletingRequestException, AS400SecurityException {
        QSYSObjectPathName path = new QSYSObjectPathName("/QSYS.LIB/" + LIBRARY + ".LIB/JOBSARA.DTAARA");
        DecimalDataArea dataArea = new DecimalDataArea(as400, path.getPath());

        BigDecimal jobNumber = dataArea.read();

        dataArea.write(jobNumber.add(new BigDecimal(1)));

        return jobNumber.intValueExact();
    }
}
