package me.fortibrine.returnrune.home;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.fortibrine.returnrune.ReturnRunePlugin;
import me.fortibrine.returnrune.database.DatabaseManager;
import me.fortibrine.returnrune.database.entity.PlayerHome;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class HomeManager {

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final DatabaseManager databaseManager = plugin.getDatabaseManager();

    private final Dao<PlayerHome, String> playerHomeDao;

    public HomeManager() {
        try {
            ConnectionSource connectionSource = databaseManager.getDatabaseProvider().getConnectionSource();
            TableUtils.createTableIfNotExists(connectionSource, PlayerHome.class);
            this.playerHomeDao = DaoManager.createDao(connectionSource, PlayerHome.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getPlayerHomes(String username) {
        try {
            List<PlayerHome> homes = playerHomeDao.queryForEq("username", username);

            return homes.stream()
                    .map(PlayerHome::getName)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getPlayerHomes(Player player) {
        return getPlayerHomes(player.getName());
    }

    public PlayerHome getPlayerHome(Player player, String homeName) {
        try {
            return playerHomeDao.queryBuilder()
                    .where()
                    .eq("username", player.getName())
                    .and()
                    .eq("name", homeName)
                    .queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHome(Player player, String homeName, Location location) {
        try {
            if (playerHomeDao.queryBuilder()
                    .where()
                    .eq("username", player.getName())
                    .and()
                    .eq("name", homeName)
                    .queryForFirst() == null) {
                playerHomeDao.create(new PlayerHome(
                        player.getName(),
                        homeName,
                        location.getWorld().getName(),
                        location.getX(),
                        location.getY(),
                        location.getZ()
                ));

                return;
            }


            UpdateBuilder<PlayerHome, String> updateBuilder = playerHomeDao.updateBuilder();
            updateBuilder
                    .updateColumnValue("world", location.getWorld().getName())
                    .updateColumnValue("x", location.getX())
                    .updateColumnValue("y", location.getY())
                    .updateColumnValue("z", location.getZ())
                    .where()
                    .eq("username", player.getName())
                    .and()
                    .eq("name", homeName);

            playerHomeDao.update(updateBuilder.prepare());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteHome(Player player, String homeName) {
        try {
            DeleteBuilder<PlayerHome, String> deleteBuilder = playerHomeDao.deleteBuilder();
            deleteBuilder.where()
                    .eq("username", player.getName())
                    .and()
                    .eq("name", homeName);
            PreparedDelete<PlayerHome> preparedDelete = deleteBuilder.prepare();
            return playerHomeDao.delete(preparedDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
