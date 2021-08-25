package me.TahaCheji.tradeItems;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XP implements Listener {

    public ItemStack getXPItem(int i) {
        ItemStack itemStack = new ItemStack(Material.SLIME_BALL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(ChatColor.GOLD  + "XP x" + i);
        lore.add("--------------------------");
        lore.add(ChatColor.GOLD + "Right click to claim " + i + " xp");
        lore.add("--------------------------");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("XP", i);
        itemStack = nbtItem.getItem();
        return itemStack;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getItem() == null) {
            return;
        }
        if(e.getItem().getItemMeta() == null) {
            return;
        }
        if(!e.getItem().getItemMeta().getDisplayName().contains("XP")) {
            return;
        }
        Player player = e.getPlayer();
        Economy economy = Main.getEcon();
        int i = new NBTItem(e.getItem()).getInteger("XP");
        player.sendMessage(ChatColor.GOLD + "You have claimed " + i + " XP");
        player.setTotalExperience(player.getTotalExperience() + i);
        economy.depositPlayer(player, i);
        ItemStack itemStack = e.getItem();
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount()  - 1);
            player.setItemInHand(itemStack);
        } else {
            player.setItemInHand(new ItemStack(Material.AIR));
        }
    }

}
