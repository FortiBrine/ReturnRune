package me.fortibrine.returnrune.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.execute.ExecuteDefault;
import dev.rollczi.litecommands.annotations.permission.Permission;
import me.fortibrine.returnrune.ReturnRunePlugin;
import me.fortibrine.returnrune.command.argument.HomeArgument;
import me.fortibrine.returnrune.database.entity.PlayerHome;
import me.fortibrine.returnrune.home.HomeManager;
import me.fortibrine.returnrune.message.Message;
import me.fortibrine.returnrune.message.MessageManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

@Command(name = "home", aliases = {"h"})
@Permission("returnrune.home")
public class HomeCommand {

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final HomeManager homeManager = plugin.getHomeManager();
    private final MessageManager messageManager = plugin.getMessageManager();

    @ExecuteDefault
    @Async
    public void getAllHomes(
            @Context Player sender
    ) {
        List<String> homes = homeManager.getPlayerHomes(sender);

        String homeList = String.join(", ", homes);

        messageManager.sendMessage(sender, Message.HOME_LIST,
                new HashMap<String, String>() {{
                    put("homes", homeList);
                }});
    }

    @Execute
    @Async
    public void teleport(
            @Context Player sender,

            @Arg(HomeArgument.KEY) String home
    ) {
        PlayerHome playerHome = homeManager.getPlayerHome(sender, home);

        if (playerHome == null) {
            List<String> homes = homeManager.getPlayerHomes(sender);

            String homeList = String.join(", ", homes);

            messageManager.sendMessage(sender, Message.HOME_LIST,
                    new HashMap<String, String>() {{
                        put("homes", homeList);
                    }});
            return;
        }

        World world = plugin.getServer().getWorld(playerHome.getWorld());
        Location location = new Location(world, playerHome.getX(), playerHome.getY(), playerHome.getZ());

        plugin.getServer().getScheduler().runTask(plugin, () -> {
            sender.teleport(location);
        });

        messageManager.sendMessage(sender, Message.SUCCESS_TELEPORT_HOME,
                new HashMap<String, String>() {{
                    put("home", home);
                }});

    }

}
