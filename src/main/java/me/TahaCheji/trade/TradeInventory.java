package me.TahaCheji.trade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TradeInventory implements InventoryHolder {

    Inventory gui;
    private final Player p1;
    private final Player p2;
    private TradeStatus tradeStatus;


    public TradeInventory(Player p1, Player p2, TradeStatus tradeStatus) {
        this.p1 = p1;
        this.p2 = p2;
        this.tradeStatus = tradeStatus;
        gui = Bukkit.createInventory(this, 54, "MafanaTradeNetwork");
        List<String> lore = new ArrayList<>();
        ItemStack newItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta newmeta = newItem.getItemMeta();
        newmeta.setDisplayName(ChatColor.GRAY + "");
        newmeta.setLore(lore);
        newItem.setItemMeta(newmeta);

        List<String> newLore = new ArrayList<>();
        ItemStack Item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = Item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "");
        newLore.add(ChatColor.GOLD + "<----------- " + p1.getDisplayName());
        newLore.add(ChatColor.GOLD + p2.getDisplayName() + " ----------->" );
        meta.setLore(newLore);
        Item.setItemMeta(meta);

        gui.setItem(0, newItem);
        gui.setItem(1, newItem);
        gui.setItem(2, newItem);
        gui.setItem(3, newItem);
        gui.setItem(4, newItem);
        gui.setItem(5, newItem);
        gui.setItem(6, newItem);
        gui.setItem(7, newItem);
        gui.setItem(8, newItem);

        gui.setItem(13, Item);
        gui.setItem(22, Item);
        gui.setItem(31, Item);
        gui.setItem(40, Item);
        gui.setItem(49, Item);

        gui.setItem(48, getAccept());
        gui.setItem(50, getAccept());
    }

    public void setAcceptedP1(boolean check) {
        if (check) {
            List<String> lore = new ArrayList<>();
            ItemStack newItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta newmeta = newItem.getItemMeta();
            newmeta.setDisplayName(ChatColor.GRAY + "");
            newmeta.setLore(lore);
            newItem.setItemMeta(newmeta);
            gui.setItem(0, newItem);
            gui.setItem(1, newItem);
            gui.setItem(2, newItem);
            gui.setItem(3, newItem);
            gui.setItem(48, getAccepted());
        } if(!check) {
            List<String> lore = new ArrayList<>();
            ItemStack newItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta newmeta = newItem.getItemMeta();
            newmeta.setDisplayName(ChatColor.GRAY + "");
            newmeta.setLore(lore);
            newItem.setItemMeta(newmeta);
            gui.setItem(0, newItem);
            gui.setItem(1, newItem);
            gui.setItem(2, newItem);
            gui.setItem(3, newItem);
            gui.setItem(48, getAccept());
        }
    }

    public void setAcceptedP2(boolean check) {
        if (check) {
            List<String> lore = new ArrayList<>();
            ItemStack newItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta newmeta = newItem.getItemMeta();
            newmeta.setDisplayName(ChatColor.GRAY + "");
            newmeta.setLore(lore);
            newItem.setItemMeta(newmeta);
            gui.setItem(5, newItem);
            gui.setItem(6, newItem);
            gui.setItem(7, newItem);
            gui.setItem(8, newItem);
            gui.setItem(50, getAccepted());
        } else {
            List<String> lore = new ArrayList<>();
            ItemStack newItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta newmeta = newItem.getItemMeta();
            newmeta.setDisplayName(ChatColor.GRAY + "");
            newmeta.setLore(lore);
            newItem.setItemMeta(newmeta);
            gui.setItem(5, newItem);
            gui.setItem(6, newItem);
            gui.setItem(7, newItem);
            gui.setItem(8, newItem);
            gui.setItem(50, getAccept());
        }
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public ItemStack getAccept() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.RED + "Accept");
        lore.add("--------------------------");
        lore.add(ChatColor.GOLD + "Click to accept the trade");
        lore.add("--------------------------");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getAccepted() {
        ItemStack item = new ItemStack(Material.GREEN_DYE);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setDisplayName(ChatColor.GREEN + "Accepted");
        lore.add("--------------------------");
        lore.add(ChatColor.GOLD + "You have accepted the trade");
        lore.add("--------------------------");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }


}
