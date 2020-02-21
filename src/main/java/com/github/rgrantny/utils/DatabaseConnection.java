package com.github.rgrantny.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum DatabaseConnection implements DatabaseConnectionInterface {
    MANAGEMENT_SYSTEM,
    UAT;
    private static final IOConsole console = new IOConsole(IOConsole.AnsiColor.BLUE);
    private final ConnectionBuilder connectionBuilder;
    DatabaseConnection(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }
    DatabaseConnection() {
        this(new ConnectionBuilder()
                .setUser("root")
                .setPassword("Rm1#(l8")
                .setPort(3306)
                .setDatabaseVendor("mysql")
                .setHost("127.0.0.1"));
    }
    @Override
    public String getDatabaseName() {
        return name().toLowerCase();
    }
    @Override
    public Connection getDatabaseConnection() {
        return connectionBuilder
                .setDatabaseName(getDatabaseName())
                .build();
    }
    @Override
    public Connection getDatabaseEngineConnection() {
        return connectionBuilder.build();
    }
    @Override
    public void create() {
        String sqlStatement = "CREATE DATABASE " + getDatabaseName(); // TODO - define statement
        try {
            getDatabaseEngineConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            String info = "Successfully executed statement \n\t`%s`.";
            console.println(info, sqlStatement);
        } catch (Exception sqlException) {
            throw new Error(sqlException);
        }
    }
    @Override
    public void drop() {
        String sqlStatement = "DROP DATABASE IF EXISTS " + getDatabaseName(); // TODO - define statement
        try {
            getDatabaseEngineConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            String info = "Successfully executed statement \n\t`%s`.";
            console.println(info, sqlStatement);
        } catch (Exception sqlException) {
            throw new Error(sqlException);
        }
    }
    @Override
    public void use() {
        String sqlStatement = "USE " + getDatabaseName(); // TODO - define statement
        try {
            getDatabaseEngineConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            String info = "Successfully executed statement \n\t`%s`.";
            console.println(info, sqlStatement);
        } catch (Exception sqlException) {
            throw new Error(sqlException);
        }
    }
    @Override
    public void executeStatement(String sqlStatement) {
        try {
            sqlStatement = sqlStatement.trim();
            getDatabaseConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            String info = "Successfully executed statement \n\t`%s`.";
            console.println(info, sqlStatement);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    @Override
    public ResultSet executeQuery(String sqlQuery) {
        try {
            sqlQuery = sqlQuery.trim();
            ResultSet result = getDatabaseConnection()
                    .prepareStatement(sqlQuery)
                    .executeQuery();
            String info = "Successfully executed statement \n\t`%s`.";
            console.println(info, sqlQuery);
            return result;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}