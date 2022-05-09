package me.maanraj514.animatedtab;

import me.maanraj514.animatedtabapi.TabManager;
import me.maanraj514.versionsupport_1_15_r1.versionsupport_1_15_R1;
import me.maanraj514.versionsupport_1_8_r3.versionsupport_1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnimatedTab extends JavaPlugin {
    public TabManager tab;

    @Override
    public void onEnable() {
        if (!setupManager()) {
            getLogger().severe("FAILED TO SETUP AnimatedTab! RUNNING ON NON-COMPATIBLE SERVER VERSION! THIS PLUGIN ONLY SUPPORTS 1.8.8 AND 1.15.1 ");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        tab.addHeader("&a&lMaanraj514's Test nms plugin for a tab manager");

        tab.addFooter("&c&lPlayers Online: " + Bukkit.getOnlinePlayers().size());
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getName().equalsIgnoreCase("pkp0")){
                tab.addFooter("&e&lTHE GOD PKP0 IS IN YOUR SERVER :)");
            }
        }

        tab.showTab();
    }

    @Override
    public void onDisable() {
    }

    private boolean setupManager() {

        String sversion = "N/A";

        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch (ArrayIndexOutOfBoundsException ex){
            return false;
        }

        switch (sversion){
            case "v1_8_R3":
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDetected server version 1.8.8 enabling tab support for 1.8.8"));
                tab = new versionsupport_1_8_R3(this);
                break;
            case "v1_15_R1":
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDetected server version 1.15.1 enabling tab support for 1.15.1"));
                tab = new versionsupport_1_15_R1(this);
                break;
        }
        return tab != null;
    }
}
