package dev.fdp.races.events;

import dev.fdp.races.RacesReloader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        RacesReloader.reloadRaceForPlayer(event.getPlayer());
    }
}
