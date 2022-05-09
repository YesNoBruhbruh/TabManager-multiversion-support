package me.maanraj514.versionsupport_1_8_r3;

import me.maanraj514.animatedtabapi.TabManager;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class versionsupport_1_8_R3 implements TabManager {

    private final List<ChatComponentText> headers = new ArrayList<>();
    private final List<ChatComponentText> footers = new ArrayList<>();

    private final Plugin plugin;
    public versionsupport_1_8_R3(Plugin plugin) {
        this.plugin = plugin;
    }

    public void showTab() {
        if (headers.isEmpty() && footers.isEmpty()) return;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            int count1 = 0;
            int count2 = 0;

            @Override
            public void run() {
                try{

                    Field a = packet.getClass().getDeclaredField("a");
                    a.setAccessible(true);
                    Field b = packet.getClass().getDeclaredField("b");
                    b.setAccessible(true);

                    if (count1 >= headers.size()){
                        count1 = 0;
                    }
                    if (count2 >= footers.size()) {
                        count2 = 0;
                    }

                    a.set(packet, headers.get(count1));
                    b.set(packet, footers.get(count2));

                    if (Bukkit.getOnlinePlayers().size() != 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                        }
                    }

                    count1++;
                    count2++;

                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }, 10, 20 * 2);
    }

    public void addHeader(String header) {
        headers.add(new ChatComponentText(format(header)));
    }

    public void addFooter(String footer) {
        footers.add(new ChatComponentText(format(footer)));
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
