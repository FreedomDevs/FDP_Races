package dev.fdp.races.updaters;

import dev.fdp.races.FDP_Races;
import dev.fdp.races.Race;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeSpeedUpdater implements IUpdater, IUnloadable {
    private static Map<String, List<String>> biomeSpeed = new HashMap<>();
    private static Integer taskid = null;

    @Override
    public void update(Race race, Player player) {
        String playerName = player.getName();

        if (taskid == null)
            taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(FDP_Races.getInstance(), task, 0, 180);

        if (race.getBiomeSpeed().size() == 0)
            biomeSpeed.remove(playerName);
        else
            biomeSpeed.put(playerName, race.getBiomeSpeed());
    }

    @Override
    public void unload(Player player) {
        biomeSpeed.remove(player.getName());
    }

    private Runnable task = () -> {
        for (Map.Entry<String, List<String>> entry : biomeSpeed.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null || !player.isOnline())
                continue;

            List<String> biomes = entry.getValue();
            Biome biome = player.getLocation().getWorld().getBiome(
                    player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(),
                    player.getLocation().getBlockZ());

            if (biomes.contains(biome.name()))
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 110, 1));
            else
                player.removePotionEffect(PotionEffectType.SPEED);
        }
    };
}
