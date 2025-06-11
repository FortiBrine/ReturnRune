package me.fortibrine.returnrune.database;

import lombok.Getter;
import me.fortibrine.returnrune.database.provider.DatabaseProvider;
import me.fortibrine.returnrune.database.provider.SqliteDatabaseProvider;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.SQLException;
import java.util.Optional;

@Getter
public class DatabaseManager {

    private final DatabaseProvider databaseProvider;

    public DatabaseManager(ConfigurationSection section) {
        if (section == null) {
            this.databaseProvider = null;
            return;
        }

        if (Optional.ofNullable(section.getString("type"))
                .orElse("")
                .equals("sqlite")) {
            try {
                this.databaseProvider = new SqliteDatabaseProvider(section.getString("path"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.databaseProvider = null;
        }
    }

}
