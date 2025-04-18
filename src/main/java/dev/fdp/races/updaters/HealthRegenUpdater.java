package dev.fdp.races.updaters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import dev.fdp.races.FDP_Races;
import dev.fdp.races.Race;

public class HealthRegenUpdater implements IUpdater {
  private static Map<String, Double> playerToRegen = new HashMap<>();
  private static Integer taskid = null;

  @Override
  public void update(Race race, Player player) {
    if (taskid == null) {
      taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(FDP_Races.getInstance(), () -> {
        for (Iterator<Map.Entry<String, Double>> it = playerToRegen.entrySet().iterator(); it.hasNext();) {
          Map.Entry<String, Double> entry = it.next();
          String playerName = entry.getKey();
          double amount = entry.getValue();

          Player player_arbuz = Bukkit.getPlayerExact(playerName);

          if (player_arbuz != null && player_arbuz.isOnline()) {
            double newHealth = player_arbuz.getHealth() + amount;
            player_arbuz
                .setHealth(Math.min(newHealth, player_arbuz.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
          } else {
            // Удаляем мусор
            it.remove();
          }
        }

        if (playerToRegen.size() == 0) {
          Bukkit.getScheduler().cancelTask(taskid);
          taskid = null;
        }
      }, 0, 20);
    }

    double regenPerSec = race.getRegenerationPerSec();
    if (regenPerSec == 0)
      playerToRegen.remove(player.getName());
    else
      playerToRegen.put(player.getName(), regenPerSec);
  }
}
