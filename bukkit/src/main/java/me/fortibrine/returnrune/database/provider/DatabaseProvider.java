package me.fortibrine.returnrune.database.provider;

import com.j256.ormlite.support.ConnectionSource;

import java.io.Closeable;

public interface DatabaseProvider extends Closeable {
    ConnectionSource getConnectionSource();
}
