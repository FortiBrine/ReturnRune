package me.fortibrine.returnrune.database.provider;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class SqliteDatabaseProvider implements DatabaseProvider {

    private final ConnectionSource connectionSource;

    public SqliteDatabaseProvider(String path) throws SQLException {
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + path);
    }

    @Override
    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    @Override
    public void close() {
        try {
            this.connectionSource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
