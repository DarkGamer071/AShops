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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.austindev.ashops.AShops;
import pl.austindev.ashops.InventoryUtils;
import pl.austindev.ashops.data.DataAccessException;
import pl.austindev.ashops.keys.ASCommand;
import pl.austindev.ashops.keys.ASMessage;
import pl.austindev.ashops.keys.ASPermission;
import pl.austindev.mc.MessageTranslator;

public enum Option {
	CREATE_OWN_SHOP(1, "54", ASMessage.OP_CREATE_OWN_SHOP,
			ASPermission.OWN_BUY_SHOP, ASPermission.OWN_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			if (plugin.getShopsManager().canHaveMoreShops(player)) {
				int shopPrice = plugin.getShopsManager().getShopPrice(player);
				if (plugin.getEconomy().has(player.getName(), shopPrice)) {
					plugin.getTemporaryValues().put(player.getName(),
							ASCommand.ASHOP, shopPrice);
					tell(plugin, player, ASMessage.SELECT_CHEST);
				} else {
					tell(plugin, player, ASMessage.NO_MONEY, shopPrice);
				}
			} else {
				tell(plugin, player, ASMessage.LIMIT);
			}
		}
	},
	REMOVE_SHOP(1, "46", ASMessage.OP_REMOVE_SHOP, ASPermission.OWN_BUY_SHOP,
			ASPermission.OWN_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			plugin.getTemporaryValues().put(player.getName(),
					ASCommand.AREMOVE, player.getName());
			tell(plugin, player, ASMessage.SELECT_CHEST);
		}
	},
	BUY(1, "35:14", ASMessage.OP_BUY, ASPermission.OWN_BUY_SHOP,
			ASPermission.OWN_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			// TODO:
		}
	},
	SELL(1, "35:5", ASMessage.OP_SELL, ASPermission.OWN_BUY_SHOP,
			ASPermission.OWN_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();

			// TODO:
		}
	},
	TOGGLE(1, "323", ASMessage.OP_TOGGLE, ASPermission.OWN_BUY_SHOP,
			ASPermission.OWN_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			plugin.getTemporaryValues().put(player.getName(),
					ASCommand.ATOGGLE, ASCommand.ATOGGLE);
			tell(plugin, player, ASMessage.SELECT_CHEST);
		}
	},
	CREATE_OTHERS_SHOP(2, "54", ASMessage.OP_CREATE_OTHERS_SHOP,
			ASPermission.OTHERS_BUY_SHOP, ASPermission.OTHERS_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			// TODO Auto-generated method stub

		}
	},
	AREPAIR(2, "145", ASMessage.OP_REPAIR, ASPermission.OTHERS_BUY_SHOP,
			ASPermission.OTHERS_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			tell(plugin,
					player,
					plugin.getShopsManager().toggleRepairMode(player.getName()) ? ASMessage.REPAIR_MODE
							: ASMessage.NORMAL_MODE);
		}
	},
	CREATE_SERVER_SHOP(2, "130", ASMessage.OP_CREATE_SERVER_SHOP,
			ASPermission.SERVER_BUY_SHOP, ASPermission.SERVER_SELL_SHOP) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			plugin.getTemporaryValues().put(player.getName(), ASCommand.ASSHOP,
					player.getName());
			tell(plugin, player, ASMessage.SELECT_CHEST);
		}
	},
	RELOAD(3, "260", ASMessage.OP_RELOAD, ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			plugin.reloadConfig();
			plugin.getShopsManager().loadConfigProperties();
			tell(plugin, player, ASMessage.RELOADED);
		}
	},
	SAVE(3, "386", ASMessage.OP_SAVE, ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			try {
				plugin.getShopsManager().save();
				tell(plugin, player, ASMessage.FILE_SAVED);
			} catch (DataAccessException e) {
				e.printStackTrace();
				tell(plugin, player, ASMessage.ERROR);
			}
		}
	},
	LOAD(3, "387", ASMessage.OP_LOAD, ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			try {
				plugin.getShopsManager().load();
				tell(plugin, player, ASMessage.FILE_LOADED);
			} catch (DataAccessException e) {
				e.printStackTrace();
				tell(plugin, player, ASMessage.ERROR);
			}
		}
	},
	CLEAR_ALL(3, "291", ASMessage.OP_CLEAR_ALL, ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			try {
				plugin.getShopsManager().clearPlayerShops();
				plugin.getShopsManager().clearServerShops();
				tell(plugin, player, ASMessage.SHOPS_CLEARED);
			} catch (DataAccessException e) {
				e.printStackTrace();
				tell(plugin, player, ASMessage.ERROR);
			}
		}
	},
	CLEAR_SERVER_SHOPS(3, "292", ASMessage.OP_CLEAR_SERVER_SHOPS,
			ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			try {
				plugin.getShopsManager().clearServerShops();
				tell(plugin, player, ASMessage.SHOPS_CLEARED);
			} catch (DataAccessException e) {
				e.printStackTrace();
				tell(plugin, player, ASMessage.ERROR);
			}
		}
	},
	CLEAR_PLAYER_SHOPS(3, "293", ASMessage.OP_CLEAR_PLAYER_SHOPS,
			ASPermission.OPERATOR) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			try {
				plugin.getShopsManager().clearPlayerShops();
				tell(plugin, player, ASMessage.SHOPS_CLEARED);
			} catch (DataAccessException e) {
				e.printStackTrace();
				tell(plugin, player, ASMessage.ERROR);
			}
		}
	},
	CLEAR_PLAYER_S_SHOPS(3, "294", ASMessage.OP_CLEAR_PLAYER_S_SHOPS) {
		@Override
		public void select(Menu menu, AShops plugin, Player player) {
			player.closeInventory();
			// TODO Auto-generated method stub

		}
	};

	private final int level;
	private final String iconCode;
	private final ASMessage title;
	private final ASPermission[] permissions;

	private final static Map<String, ItemStack> icons = new HashMap<String, ItemStack>();

	static {
		for (Option option : values())
			icons.put(option.iconCode, InventoryUtils.getItem(option.iconCode));
	}

	private Option(int level, String iconCode, ASMessage title,
			ASPermission... permissions) {
		this.level = level;
		this.iconCode = iconCode;
		this.title = title;
		this.permissions = permissions;
	}

	public int getLevel() {
		return level;
	}

	public ASPermission[] getPermissions() {
		return permissions;
	}

	public ItemStack getIcon(MessageTranslator translator) {
		ItemStack item = icons.get(iconCode);
		ItemMeta meta;
		if (item.hasItemMeta())
			meta = item.getItemMeta();
		else
			meta = Bukkit.getItemFactory().getItemMeta(item.getType());
		List<String> lore = new LinkedList<String>();
		lore.add(ChatColor.WHITE + translator.translate(title));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	private static void tell(AShops plugin, Player player, ASMessage message) {
		player.closeInventory();
		player.sendMessage(plugin.$(message));
	}

	private static void tell(AShops plugin, Player player, ASMessage message,
			Object... arguments) {
		player.sendMessage(plugin.$(message, arguments));
	}

	public abstract void select(Menu menu, AShops plugin, Player player);
}