package me.TahaCheji.trade;

import me.TahaCheji.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TradeListener implements Listener {

    int[] tradeSlotsP1 = {9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38, 39, 45, 46, 47};
    int[] tradeSlotsP2 = {14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 45, 51, 52, 53};

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof TradeInventory)) {
            return;
        }
        TradeInventory tradeInventory = (TradeInventory) e.getInventory().getHolder();
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
                if (tradeInventory.getP1().getUniqueId() == p.getUniqueId()) {
                    if (e.getSlot() == 48) {
                        if (e.getInventory().getItem(48).getItemMeta().getDisplayName().contains("Accepted")) {
                            tradeInventory.setAcceptedP1(false);
                            return;
                        } else if (e.getInventory().getItem(50).getItemMeta().getDisplayName().contains("Accepted")) {
                            tradeInventory.setAcceptedP1(true);
                            List<ItemStack> p1Items = new ArrayList<>();
                            for (int i : tradeSlotsP1) {
                                if (e.getInventory().getItem(i) == null) {
                                    continue;
                                }
                                p1Items.add(e.getInventory().getItem(i));
                            }
                            List<ItemStack> p2Items = new ArrayList<>();
                            for (int i : tradeSlotsP2) {
                                if (e.getInventory().getItem(i) == null) {
                                    continue;
                                }
                                p2Items.add(e.getInventory().getItem(i));
                                completeTrade(tradeInventory, p1Items, p2Items);
                                return;
                            }

                        } else {
                            tradeInventory.setAcceptedP1(true);
                        }
                    }
                    ItemStack itemStack;
                    if (e.getClickedInventory() == p.getInventory()) {
                        if (e.getCurrentItem() == null) {
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta() == null) {
                            return;
                        }
                        itemStack = e.getCurrentItem();
                        for (int i : tradeSlotsP1) {
                            if (e.getInventory().getItem(i) != null) {
                                continue;
                            }
                            e.getInventory().setItem(i, itemStack);
                            break;
                        }
                        tradeInventory.setAcceptedP1(false);
                        e.setCurrentItem(new ItemStack(Material.AIR));
                    } else {
                        if (e.getCurrentItem() == null) {
                            return;
                        }
                        for (int i : tradeSlotsP1) {
                            if (e.getSlot() == i) {
                                p.getInventory().addItem(e.getCurrentItem());
                                e.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
                                tradeInventory.setAcceptedP1(false);
                            }
                        }

                    }
                } else if (tradeInventory.getP2().getUniqueId() == p.getUniqueId()) {
                    if (e.getSlot() == 50) {
                        if (e.getInventory().getItem(50).getItemMeta().getDisplayName().contains("Accepted")) {
                            tradeInventory.setAcceptedP2(false);
                            return;
                        } else if(e.getInventory().getItem(48).getItemMeta().getDisplayName().contains("Accepted")) {
                            tradeInventory.setAcceptedP2(true);
                            List<ItemStack> p1Items = new ArrayList<>();
                            for (int i : tradeSlotsP1) {
                                if (e.getInventory().getItem(i) == null) {
                                    continue;
                                }
                                p1Items.add(e.getInventory().getItem(i));
                            }
                            List<ItemStack> p2Items = new ArrayList<>();
                            for (int i : tradeSlotsP2) {
                                if (e.getInventory().getItem(i) == null) {
                                    continue;
                                }
                                p2Items.add(e.getInventory().getItem(i));
                                completeTrade(tradeInventory, p1Items, p2Items);
                                return;
                            }
                        } else {
                            tradeInventory.setAcceptedP2(true);
                        }
                    }
                    ItemStack itemStack;
                    if (e.getClickedInventory() == p.getInventory()) {
                        if (e.getCurrentItem() == null) {
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta() == null) {
                            return;
                        }
                        itemStack = e.getCurrentItem();
                        for (int i : tradeSlotsP2) {
                            if (e.getInventory().getItem(i) != null) {
                                continue;
                            }
                            e.getInventory().setItem(i, itemStack);
                            break;
                        }
                        tradeInventory.setAcceptedP2(false);
                        e.setCurrentItem(new ItemStack(Material.AIR));
                    } else {
                        if (e.getCurrentItem() == null) {
                            return;
                        }
                        for (int i : tradeSlotsP2) {
                            if (e.getSlot() == i) {
                                p.getInventory().addItem(e.getCurrentItem());
                                e.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
                                tradeInventory.setAcceptedP2(false);
                            }
                        }
                    }
                }
            }




    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof TradeInventory)) {
            return;
        }
        TradeInventory tradeInventory = (TradeInventory) e.getInventory().getHolder();
        Player player = (Player) e.getPlayer();

        if(tradeInventory.getP1().getUniqueId() == player.getUniqueId() || tradeInventory.getP2().getUniqueId() == player.getUniqueId()) {
            if(tradeInventory.getTradeStatus() == TradeStatus.REJECTED || tradeInventory.getTradeStatus() == TradeStatus.ACCEPTED) {
                return;
            }
            for(int i : tradeSlotsP1) {
                if(e.getInventory().getItem(i) == null) {
                    continue;
                }
                tradeInventory.getP1().getInventory().addItem(e.getInventory().getItem(i));
            }
            for(int i : tradeSlotsP2) {
                if(e.getInventory().getItem(i) == null) {
                    continue;
                }
                tradeInventory.getP2().getInventory().addItem(e.getInventory().getItem(i));
            }
            tradeInventory.setTradeStatus(TradeStatus.REJECTED);
            if(tradeInventory.getP1().getUniqueId() == player.getUniqueId()) {
                tradeInventory.getP1().sendMessage(ChatColor.RED + "The trade have been canceled");
                tradeInventory.getP2().closeInventory();
            }

            if(tradeInventory.getP2().getUniqueId() == player.getUniqueId()) {
                tradeInventory.getP2().sendMessage(ChatColor.RED + "The trade have been canceled");
                tradeInventory.getP1().closeInventory();
            }

        }

    }

    public void completeTrade(TradeInventory tradeInventory, List<ItemStack> p1Items, List<ItemStack> p2Items) {
        tradeInventory.setTradeStatus(TradeStatus.ACCEPTED);
        tradeInventory.getP1().sendMessage(ChatColor.GOLD + "Thank you for using MafanaTradeNetwork");
        tradeInventory.getP2().sendMessage(ChatColor.GOLD + "Thank you for using MafanaTradeNetwork");

        tradeInventory.getP1().sendMessage(ChatColor.GREEN + "Trade Complete");
        tradeInventory.getP2().sendMessage(ChatColor.GREEN + "Trade Complete");
        for(ItemStack itemStack : p1Items) {
            tradeInventory.getP2().getInventory().addItem(itemStack);
            tradeInventory.getP2().sendMessage(ChatColor.GREEN + "+ " + itemStack.getItemMeta().getDisplayName());
            tradeInventory.getP1().sendMessage(ChatColor.RED + "- " + itemStack.getItemMeta().getDisplayName());
        }
        for(ItemStack itemStack : p2Items) {
            tradeInventory.getP1().getInventory().addItem(itemStack);
            tradeInventory.getP1().sendMessage(ChatColor.GREEN + "+ " + itemStack.getItemMeta().getDisplayName());
            tradeInventory.getP2().sendMessage(ChatColor.RED + "- " + itemStack.getItemMeta().getDisplayName());
        }
        tradeInventory.getP1().closeInventory();
        tradeInventory.getP2().closeInventory();

    }



}
