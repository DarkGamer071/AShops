/*
 * AShops Bukkit Plugin
 * Copyright 2013 Austin Reuter (_austinho)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.austindev.ashops.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.austindev.ashops.AShops;
import pl.austindev.ashops.listeners.ASListener;
import pl.austindev.mc.PlayerUtil;

public class MenuListener extends ASListener {

	public MenuListener(AShops plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent event) {
		if (PlayerUtil.isPlayer(event.getWhoClicked())) {
			Player player = (Player) event.getWhoClicked();
			Menu menu = getPlugin().getMenu(player.getName());
			if (menu != null) {
				event.setCancelled(true);
				menu.getStep().next(event.getRawSlot());
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onPlayerQuit(PlayerQuitEvent event) {
		getPlugin().unregisterMenu(event.getPlayer().getName());
	}
}
