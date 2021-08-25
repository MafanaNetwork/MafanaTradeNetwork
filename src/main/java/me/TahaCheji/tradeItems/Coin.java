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

public class Coin implements Listener {

    public ItemStack getCoinItem(int i) {
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(ChatColor.GOLD  + "Coins x" + i);
        lore.add("--------------------------");
        lore.add(ChatColor.GOLD + "Right click to claim " + i + " coins");
        lore.add("--------------------------");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("Coins", i);
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
        if(!e.getItem().getItemMeta().getDisplayName().contains("Coins")) {
            return;
        }
        Player player = e.getPlayer();
        Economy economy = Main.getEcon();
        int i = new NBTItem(e.getItem()).getInteger("Coins");
        player.sendMessage(ChatColor.GOLD + "You have claimed " + i + " coins");
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
