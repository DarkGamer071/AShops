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
package pl.austindev.mc;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PermissionsProvider {
	public boolean has(CommandSender player, APermission permission);

	public boolean hasOneOf(CommandSender player, APermission... permissions);

	public boolean hasOneOf(CommandSender player, APermission permission1,
			APermission permission2);

	public boolean hasAll(CommandSender player, APermission... permissions);

	public boolean hasAll(CommandSender player, APermission permission1,
			APermission permission2);

	public Set<String> getGroups(Player player);

	public String[] getGroups();

	public Set<String> getGroups(String playerName, World world);

	public boolean has(String playerName, World world, APermission noTaxes);
}
