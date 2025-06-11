package me.fortibrine.returnrune.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import me.fortibrine.returnrune.ReturnRunePlugin;
import me.fortibrine.returnrune.message.Message;
import me.fortibrine.returnrune.message.MessageManager;
import org.bukkit.command.CommandSender;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final MessageManager messageManager = plugin.getMessageManager();

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        if (invocation.name().equals("sethome")) {
            messageManager.sendMessage(
                    invocation.sender(),
                    Message.SETHOME_USAGE
            );
            return;
        } else if (invocation.name().equals("delhome")) {
            messageManager.sendMessage(
                    invocation.sender(),
                    Message.DELHOME_USAGE
            );
            return;
        }

        chain.resolve(invocation, result);
    }

}
