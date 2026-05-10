package me.greenmoon.addon.ffa;

import me.bedtwL.AliveFFA.api.effect.PureKillEffect;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class FlyingHeadKillEffect extends PureKillEffect {

    @Override
    public void killEffect(Location location, Player victim, Player killer) {
        // 播放發射音效
        location.getWorld().playSound(location, Sound.valueOf("FIREWORK_LAUNCH"), 1.0f, 1.0f);
        spawnFlyingHead(location, victim.getName());
    }

    @Override
    public String getName() {
        return "飛天頭顱";
    }

    @Override
    public String getItemNameKey() {
        return "flying_head";
    }

    @Override
    public ItemStack getItemBase() {
        // feather in gui
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c飛天頭顱§6特效");
            meta.setLore(Collections.singletonList("§7掉頭就走！讓敵人的頭顱旋轉升天。"));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void spawnFlyingHead(Location loc, String playerName) {

        ItemStack skull = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null) {
            try { meta.setOwner(playerName); } catch (Exception e) {}
            skull.setItemMeta(meta);
        }

        // create stand
        final ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
        stand.setVisible(false);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setHelmet(skull);

        final double MAX_HEIGHT = 7.0;
        final int TOTAL_TICKS = 30; //  1.5s
        final double ASCENT_PER_TICK = MAX_HEIGHT / TOTAL_TICKS;

        new BukkitRunnable() {
            int tick = 0;
            float yaw = 0;

            @Override
            public void run() {
                if (!stand.isValid() || tick >= TOTAL_TICKS) {
                    stand.getWorld().playEffect(stand.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK.getId());
                    stand.remove();
                    this.cancel();
                    return;
                }

                if (tick % 4 == 0) {
                    stand.getWorld().playEffect(stand.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, 0);
                }

                Location next = stand.getLocation();
                next.add(0, ASCENT_PER_TICK, 0);
                yaw = (yaw + 25f) % 360;
                next.setYaw(yaw);
                stand.teleport(next);

                tick++;
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(FlyingHeadKillEffect.class), 0L, 1L);
    }
}