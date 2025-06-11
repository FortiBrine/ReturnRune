package me.fortibrine.returnrune.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import me.fortibrine.returnrune.ReturnRunePlugin;
import me.fortibrine.returnrune.home.HomeManager;
import org.bukkit.command.CommandSender;

public class HomeArgument extends ArgumentResolver<CommandSender, String> {

    public static final String KEY = "home";

    private final ReturnRunePlugin plugin = ReturnRunePlugin.getInstance();
    private final HomeManager homeManager = plugin.getHomeManager();

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<String> argument, SuggestionContext context) {
        return SuggestionResult.of(
                homeManager.getPlayerHomes(invocation.sender().getName())
        );
    }

    @Override
    protected ParseResult<String> parse(Invocation<CommandSender> invocation, Argument<String> context, String argument) {
        return ParseResult.success(argument);
    }

}
