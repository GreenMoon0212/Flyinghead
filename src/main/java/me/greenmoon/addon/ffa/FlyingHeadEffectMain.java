package me.greenmoon.addon.ffa;

import me.bedtwL.AliveFFA.api.effect.EffectAddon;
import me.bedtwL.AliveFFA.api.effect.EffectRegistry;
import me.bedtwL.AliveFFA.api.effect.EffectRegistryProvider;
import me.bedtwL.AliveFFA.api.effect.SimpleEffectRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class FlyingHeadEffectMain extends JavaPlugin {

    @Override
    public void onEnable() {
        FFAHandler handler = new FFAHandler();

        // resign effect
        FlyingHeadKillEffect effect = new FlyingHeadKillEffect();
        effect.registerKillEffect(handler);

        getLogger().info("飛天頭顱 Addon loaded");
    }


    public class FFAHandler implements EffectAddon, EffectRegistryProvider {
        private final EffectRegistry registry = new SimpleEffectRegistry();

        @Override
        public EffectRegistry getEffectRegistry() {
            return this.registry;
        }

        @Override
        public String getName() {
            return "FlyingHeadAddon";
        }

        @Override
        public String getAuthor() {
            return "GreenMoon_TW";
        }

        @Override
        public UUID authorUUID() {

            return UUID.fromString("53aaa7fb-569e-4391-9323-5762af38f255");
        }

        @Override
        public void onEnable() {}
        @Override
        public void onDisable() {}
    }
}