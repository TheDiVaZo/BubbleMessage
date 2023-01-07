package thedivazo.supports.chatlistener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import thedivazo.MessageOverHear;
import thedivazo.config.ConfigManager;

import java.util.HashSet;
import java.util.Set;

public final class DefaultChatListener implements Listener, ChatListener<AsyncPlayerChatEvent, PlayerCommandPreprocessEvent> {

    @Override
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        MessageOverHear.createBubbleMessage(MessageOverHear.getConfigManager().getConfigBubble("messages"), e.getPlayer(), e.getMessage());
    }

    @Override
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPrivateChat(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().trim();
        String[] args = cmd.split(" ");
        if(args.length < 3) return;
        if(!args[0].equals("/msg") && !args[0].equals("/tell")) return;
        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if(targetPlayer==null) return;
        Player player = e.getPlayer();
        String message = args[2];

        MessageOverHear.createBubbleMessage(MessageOverHear.getConfigManager().getConfigBubble("messages"), player, message, targetPlayer);
    }

    @Override
    public void disableListener() {
        PlayerCommandPreprocessEvent.getHandlerList().unregister(MessageOverHear.getInstance());
        AsyncPlayerChatEvent.getHandlerList().unregister(MessageOverHear.getInstance());
    }
}
