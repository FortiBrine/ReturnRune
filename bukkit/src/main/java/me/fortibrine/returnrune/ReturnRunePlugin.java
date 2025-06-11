package me.fortibrine.returnrune;

import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.message.LiteMessages;
import lombok.Getter;
import me.fortibrine.returnrune.command.DeleteHomeCommand;
import me.fortibrine.returnrune.command.HomeCommand;
import me.fortibrine.returnrune.command.argument.HomeArgument;
import me.fortibrine.returnrune.command.handler.InvalidCommandUsageHandler;
import me.fortibrine.returnrune.command.SetHomeCommand;
import me.fortibrine.returnrune.database.DatabaseManager;
import me.fortibrine.returnrune.home.HomeManager;
import me.fortibrine.returnrune.message.Message;
import me.fortibrine.returnrune.message.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ReturnRunePlugin extends JavaPlugin {

    @Getter
    private static ReturnRunePlugin instance;
    private LiteCommands<CommandSender> liteCommands;

    private MessageManager messageManager;
    private DatabaseManager databaseManager;

    private HomeManager homeManager;

    @Override
    public void onEnable() {
        instance = this;
        Logger.setGlobalLogLevel(Level.OFF);

        saveDefaultConfig();
        this.messageManager = new MessageManager();
        this.databaseManager = new DatabaseManager(getConfig().getConfigurationSection("database"));

        this.homeManager = new HomeManager();

        this.liteCommands = LiteBukkitFactory.builder("returnrune", this)
                .commands(
                        new HomeCommand(),
                        new SetHomeCommand(),
                        new DeleteHomeCommand()
                )
                .argument(String.class, ArgumentKey.of(HomeArgument.KEY), new HomeArgument())
                .message(LiteMessages.MISSING_PERMISSIONS, permissions ->
                        this.messageManager.parseMessage(Message.NO_PERMISSION))
                .message(LiteBukkitMessages.PLAYER_ONLY, unused ->
                        this.messageManager.parseMessage(Message.NOT_PLAYER))
                .invalidUsage(new InvalidCommandUsageHandler())
                .build();
    }

    @Override
    public void onDisable() {
        instance = null;

        this.messageManager = null;
        this.databaseManager = null;
        this.homeManager = null;

        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }

    }

}
