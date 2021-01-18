package io.github.bennyboy1695.betterdiscordbridge.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.*;
import com.velocitypowered.api.event.connection.*;
import io.github.bennyboy1695.betterdiscordbridge.BetterDiscordBridge;
import io.github.bennyboy1695.betterdiscordbridge.utils.DiscordMethods;

import java.util.regex.Matcher;

public class VelocityEventListener {
    //h//
    private final BetterDiscordBridge bridge;

    public VelocityEventListener(BetterDiscordBridge bridge) {
        this.bridge = bridge;
    }

    @Subscribe
    public void onChat(PlayerChatEvent event) {
        String serverName;
        if (!bridge.getConfig().getUseConfigServerNames()) {
            serverName = bridge.getConfig().getServerNames(event.getPlayer().getCurrentServer().get().getServerInfo().getName());
        } else {
            serverName = event.getPlayer().getCurrentServer().get().getServerInfo().getName();
        }

        String message = bridge.getConfig().getFormats("discord_to")
                .replaceAll("<Server>", Matcher.quoteReplacement(serverName))
                .replaceAll("<User>", event.getPlayer().getUsername())
                .replaceAll("<Message>", event.getMessage());

        if (!bridge.getConfig().getChatMode().equals("separated")) {
            DiscordMethods.sendMessage(bridge.getJDA(), bridge.getConfig().getChannels("global"), message);
        } else {
           DiscordMethods.sendMessage(bridge.getJDA(), bridge.getConfig().getChannels(serverName), message);
        }
    }

    @Subscribe
    public void onPlayerJoin(PlayerChooseInitialServerEvent e)
    {
        DiscordMethods.sendMessage(bridge.getJDA(), bridge.getConfig().getChannels("global"), "> **[ + ]** " + e.getPlayer().getUsername() + " connected");
    }

    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent e)
    {
        DiscordMethods.sendMessage(bridge.getJDA(), bridge.getConfig().getChannels("global"), "> **[ - ]** " + e.getPlayer().getUsername() + " disconnected");
    }


}
