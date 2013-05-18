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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import pl.austindev.ashops.AShops;

public class MainMenu implements MenuStep {
	private final AShops plugin;
	private final Inventory inventory;
	private final Menu menu;
	private final Player player;
	private final ConcurrentMap<Integer, Option> options = new ConcurrentHashMap<Integer, Option>();

	public MainMenu(AShops plugin, Player player, Menu menu) {
		this.plugin = plugin;
		this.menu = menu;
		this.player = player;
		this.inventory = Bukkit.createInventory(null, setOptions(player) * 9,
				"AShops");
		insertIcons();
	}

	public void open() {
		player.openInventory(inventory);
	}

	public void next(int slot) {
		Option option = options.get(slot);
		if (option != null) {
			option.select(menu, plugin, player);
		}
	}

	private int setOptions(Player player) {
		int maxLevel = 1;
		int firstLevel = 9 * 0;
		int secondLevel = 9 * 1;
		int thirdLevel = 9 * 2;
		for (Option option : Option.values()) {
			if (plugin.getPermissions().hasOneOf(player,
					option.getPermissions())) {
				int optionLevel = option.getLevel();
				if (optionLevel == 1)
					options.put(firstLevel++, option);
				else if (optionLevel == 2)
					options.put(secondLevel++, option);
				else if (optionLevel == 3)
					options.put(thirdLevel++, option);
				if (optionLevel > maxLevel)
					maxLevel = optionLevel;
			}
		}
		return maxLevel;
	}

	private void insertIcons() {
		for (Map.Entry<Integer, Option> o : options.entrySet()) {
			inventory.setItem(o.getKey(),
					o.getValue().getIcon(plugin.getTranslator()));
		}
	}
}