package com.jhc.figleaf.Jobs3RestApi.database;

import com.ibm.as400.access.*;
import com.jhc.figleaf.Jobs3RestApi.models.Job;
import com.jhc.figleaf.Jobs3RestApi.utils.ConfigManager;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hamish dickson on 12/03/2014.
 *
 * Go and get the data from tracey
 */
public class RealTracey {

    private static final String DB_DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
    private static final String DB_CONNECTION = "jdbc:as400://" + ConfigManager.getSetting("server.address") + ";naming=system;prompt=false";
    private static final String DB_USER = ConfigManager.getSetting("username");
    private static final String DB_PASSWORD = ConfigManager.getSetting("password");

    private static final AtomicInteger uniqueInvocationNumber = new AtomicInteger();
    private static final BasicDataSource dataSource = new BasicDataSource();
    private static final AS400 as400;

    /**
     * specify the library
     */
    private static final String LIBRARY = ConfigManager.getSetting("library");

    static {
        dataSource.setDriverClassName("com.ibm.as400.access.AS400JDBCDriver");
        dataSource.setMaxActive(Integer.valueOf(5).intValue());
        dataSource.setMaxIdle(Integer.valueOf(2).intValue());
        dataSource.setValidationQuery("SELECT * FROM " + LIBRARY + "/JOBS3 WHERE CODEX = 170395");
        dataSource.setTestOnBorrow(true);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_CONNECTION);
        as400 = new AS400("TRACEY", DB_USER, DB_PASSWORD);
    }

    private static final int CLOSE_DATE = 20391231; // not my fault - blame JMM
    private static final String JOB_FIELDS = "CODEX, DESCRQ, WHODO, STATUS, CLIENT, IMPORT, WHOPAY, CONTAC, BCODEX, JTYPE, EXTRA4, EXTRA1, SYSTEM, INVTXT, REQUES, TIMEIN, JBUG, LIVUAT, RLSVER, PROJ";

    public static Job getJob(int jobNumber) throws SQLException {

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE CODEX = " + jobNumber + " FETCH FIRST 1 ROWS ONLY";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            Job job = null;

            while (resultSet.next()) {
                job = new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N");
            }

            statement.close();
            resultSet.close();

            return job;
        } catch (SQLException e) {
            return new Job();
        }

    }

    public static List<Job> getJobsForUser(String user) throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String selectSQL = "SELECT " + JOB_FIELDS + " FROM " + LIBRARY + "/JOBS3 WHERE WHODO = '" + user + "'" ;
            ResultSet resultSet = statement.executeQuery(selectSQL);

            List<Job> jobs = new ArrayList<Job>();

            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getInt(15), resultSet.getInt(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19), resultSet.getString(20), "N"));
            }

            statement.close();
            resultSet.close();

            return jobs;
        } catch (SQLException e) {
            return null;
        }
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
            //Statement statement = connection.createStatement();
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

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            return job;
        }
    }

    private static int getNextJobNumber() throws SQLException, InterruptedException, IOException, IllegalObjectTypeException, ObjectDoesNotExistException, ErrorCompletingRequestException, AS400SecurityException {
        QSYSObjectPathName path = new QSYSObjectPathName("/QSYS.LIB/" + LIBRARY + ".LIB/JOBSARA.DTAARA");
        DecimalDataArea dataArea = new DecimalDataArea(as400, path.getPath());

        BigDecimal jobNumber = dataArea.read();

        dataArea.write(jobNumber.add(new BigDecimal(1)));

        return jobNumber.intValueExact();
    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;
    }
}
