package si.craft.cnRegionCheck;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class main extends JavaPlugin implements Listener {
	
	public static main plugin;
	
	@Override
	public void onEnable() {
		
		// set plugin
		plugin = this;
		
		PluginManager pluginmanager = this.getServer().getPluginManager();
		pluginmanager.registerEvents(this, this);
		
		getLogger().info("cnRegionCheck ENABLED!");
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void islandRegion(PlayerMoveEvent event) {
		ApplicableRegionSet regionlist = getWorldGuard().getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(event.getTo());
		
		for(ProtectedRegion region : regionlist) {
			if(region.getId().contains("island")) {
				Player player = event.getPlayer();
				if(!getWorldGuard().canBuild(player, player.getLocation().getBlock().getRelative(0, -1, 0))) {
					Location loc = new Location(player.getWorld(), 0, 200, 0);
					player.teleport(loc);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&3CN&8]&c You were teleported to spawn because you entered an island, that does not belong to you!"));
				}
			}
		}
	}
	
	@Override
	public void onDisable() {
		// console message on disable
		getLogger().info("cnRegionCheck DISABLED!");
	}
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
}
