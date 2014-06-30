package com.jhc.figleaf.Jobs3RestApi.database;

import com.ibm.as400.access.*;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.jhc.figleaf.Jobs3RestApi.models.JobNotes;
import com.jhc.figleaf.Jobs3RestApi.models.Person;
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
    private static final String JOB_FIELDS = "CODEX, DESCRQ, WHODO, STATUS, CLIENT, IMPORT, WHOPAY, CONTAC, BCODEX, JTYPE, EXTRA4, EXTRA1, SYSTEM, INVTXT, REQUES, TIMEIN, JBUG, LIVUAT, RLSVER, PROJ";

    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * specify the library
     */
    private static final String LIBRARY = ConfigManager.getSetting("library");

    static {
        dataSource.setDriverClassName("com.ibm.as400.access.AS400JDBCDriver");
        dataSource.setMaxActive(5);
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
        Connection connection = dataSource.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);

        return resultSet;
    }

    private static void closeDownQuery() throws SQLException {
        statement.close();
        resultSet.close();
    }

    public static Job getJob(int jobNumber) throws SQLException {

        Job job = null;

        try {
            String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE CODEX = " + jobNumber + " FETCH FIRST 1 ROWS ONLY";
            getResultSet(selectSQL);

            while (resultSet.next()) {
                job = new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N");
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
        String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE WHODO = '" + user.toUpperCase() + "'" ;
        return doSqlForGetJobsForUserAndStatus(selectSQL);
    }

    private static List<Job> doSqlForGetJobsForUserAndStatus(String sqlStatement) throws SQLException {
        List<Job> jobs = new ArrayList<Job>();

        try {
            getResultSet(sqlStatement);

            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownQuery();
        }
        return jobs;
    }

    public static JobNotes getJobNotes(int jobNumber) throws SQLException {

        List<String> notes = new ArrayList<String>();

        try {
            String selectSQL = "SELECT TEXT79 FROM " + LIBRARY + "/JOBSCRAT WHERE CODEX = " + jobNumber + " ORDER BY PAGNUM, LINNUM" ;
            getResultSet(selectSQL);

            while (resultSet.next()) {
                notes.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownQuery();
        }
        return new JobNotes(jobNumber, notes, 0);
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
