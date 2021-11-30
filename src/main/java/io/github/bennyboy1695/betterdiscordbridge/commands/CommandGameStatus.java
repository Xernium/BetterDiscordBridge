package io.github.bennyboy1695.betterdiscordbridge.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import io.github.bennyboy1695.betterdiscordbridge.BetterDiscordBridge;
import net.dv8tion.jda.api.entities.Activity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

public class CommandGameStatus implements SimpleCommand {
    /*
     This used to be "BetterDiscordBridge instance"
     But we want things to be as clean and understanding as possible.
     So "instance" has been changed to "bridge" for the whole plugin.
     */
    private final BetterDiscordBridge bridge;

    public CommandGameStatus(BetterDiscordBridge bridge) {
        this.bridge = bridge;
    }

    /*
    This is very deprecated and will need to be rebuilt,
    However this currently works and that is all that matter.
    Refer to Kyori.Adventure and also brig commands when re working this.

    This is the GameStatus command.
    This allows you to change the bots status seen in discord.

    The deprecations are "execute" and "sendMessage" method.
     */
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        @NonNull String[] args = invocation.arguments();
        if (source.hasPermission("betterdiscordbridge.command.gamestatus")) {
            if (args.length <= 1) {
                source.sendMessage(Component.text("Invalid usage!").color(NamedTextColor.RED));
                source.sendMessage(Component.text("Invalid usage!").color(NamedTextColor.RED));
                source.sendMessage(Component.text("Usage: /gamestatus <playing|listening|watching> a Velocity server!").color(NamedTextColor.RED));
                return;
            }

            //Easier way to get the args :P
            String fullArgs;
            String str = Arrays.toString(args);
            fullArgs = str.substring(1, str.length()-1).replace(",", "");

                if (args[0].startsWith("Playing") || args[0].startsWith("playing")) {
                    bridge.getJDA().getPresence().setActivity(Activity.playing(fullArgs.replace("playing", "").replace("Playing", "")));
                    source.sendMessage(Component.text("Set bots status to: " + fullArgs, NamedTextColor.GREEN));
                    bridge.getConfig().getConfigNode().getNode("discord", "info", "status").setValue(fullArgs);
                    bridge.getConfig().saveConfig();
                } else if (args[0].startsWith("Watching") || args[0].startsWith("watching")) {
                    bridge.getJDA().getPresence().setActivity(Activity.watching(fullArgs.replace("watching", "").replace("Watching", "")));
                    source.sendMessage(Component.text("Set bots status to: " + fullArgs, NamedTextColor.GREEN));
                } else if (args[0].startsWith("Listening") || args[0].startsWith("listening")) {
                    bridge.getJDA().getPresence().setActivity(Activity.listening(fullArgs.replace("listening", "").replace("Listening", "")));
                    source.sendMessage(Component.text("Set bots status to: " + fullArgs, NamedTextColor.GREEN));
                } else {
                    source.sendMessage(Component.text("Invalid usage!").color(NamedTextColor.RED));
                    source.sendMessage(Component.text("Usage: /gamestatus <playing|listening|watching> a Velocity server!").color(NamedTextColor.RED));
                }
        }
    }
}




