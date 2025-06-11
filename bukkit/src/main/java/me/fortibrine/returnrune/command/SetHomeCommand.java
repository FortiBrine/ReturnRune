package me.fortibrine.returnrune.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import me.fortibrine.returnrune.ReturnRunePlugin;
import me.fortibrine.returnrune.command.argument.HomeArgument;
import me.fortibrine.returnrune.home.HomeManager;
import me.fortibrine.returnrune.message.Message;
import me.fortibrine.returnrune.message.MessageManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Command(name = "sethome")
@Permission("returnrune.sethome")
public class SetHomeCommand {

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final HomeManager homeManager = plugin.getHomeManager();
    private final MessageManager messageManager = plugin.getMessageManager();

    @Execute
    @Async
    public void execute(
            @Context Player sender,
            @Context Location location,

            @Arg(HomeArgument.KEY) String homeName
    ) {

        homeManager.setHome(
                sender,
                homeName,
                location
        );

        messageManager.sendMessage(sender, Message.SUCCESS_SET_HOME,
                new HashMap<String, String>() {{
                    put("home", homeName);
                }});
    }

}
