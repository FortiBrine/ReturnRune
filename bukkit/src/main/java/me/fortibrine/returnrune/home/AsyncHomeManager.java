package me.fortibrine.returnrune.home;

import me.fortibrine.returnrune.database.entity.PlayerHome;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncHomeManager extends HomeManager {

    public CompletableFuture<List<String>> getPlayerHomesAsync(String username) {
        return CompletableFuture.supplyAsync(() -> super.getPlayerHomes(username));
    }

    public CompletableFuture<List<String>> getPlayerHomesAsync(Player player) {
        return CompletableFuture.supplyAsync(() -> super.getPlayerHomes(player));
    }

    public CompletableFuture<Integer> deleteHomeAsync(Player player, String homeName) {
        return CompletableFuture.supplyAsync(() -> super.deleteHome(player, homeName));
    }

    public CompletableFuture<PlayerHome> getPlayerHomeAsync(Player player, String homeName) {
        return CompletableFuture.supplyAsync(() -> super.getPlayerHome(player, homeName));
    }

    public CompletableFuture<Void> setPlayerHomeAsync(Player player, String homeName, Location location) {
        return CompletableFuture.runAsync(() -> super.setHome(player, homeName, location));
    }

}
