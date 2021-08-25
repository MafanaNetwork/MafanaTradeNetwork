package me.TahaCheji.command;

import me.TahaCheji.Main;
import me.TahaCheji.trade.TradeInventory;
import me.TahaCheji.trade.TradeListener;
import me.TahaCheji.trade.TradeStatus;
import me.TahaCheji.tradeItems.Coin;
import me.TahaCheji.tradeItems.XP;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class MainCommand implements CommandExecutor  {
    HashMap<Player,Player> requestTrade = new HashMap<Player,Player>();
    TradeListener tradeListener = new TradeListener();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("MafanaTrade")) {
            Player p = (Player) sender;
            if(args[0].equalsIgnoreCase("exchange")) {
                if(args.length == 1) {
                    return true;
                }
                if(args[1].equalsIgnoreCase("coins")) {
                    if(args.length == 2) {
                        return true;
                    }
                    int i = Integer.parseInt(args[2]);
                    Economy economy = Main.getEcon();
                    if(economy.getBalance(p) < i) {
                        p.sendMessage(ChatColor.RED + "You cant take that much money out");
                        return true;
                    }
                    economy.withdrawPlayer(p, i);
                    p.getInventory().addItem(new Coin().getCoinItem(i));
                }
                if(args[1].equalsIgnoreCase("xp")) {
                    if(args.length == 2) {
                        return true;
                    }
                    int i = Integer.parseInt(args[2]);
                    if(p.getTotalExperience() < i) {
                        p.sendMessage(ChatColor.RED + "You cant take that much xp out");
                        return true;
                    }
                    p.setTotalExperience(p.getTotalExperience() - i);
                    p.getInventory().addItem(new XP().getXPItem(i));
                }
            }
            if(args[0].equalsIgnoreCase("trade")) {
                Player tradeWith = Bukkit.getPlayer(args[1]);
                if(tradeWith == null) {
                    return true;
                }
                if(tradeWith.getUniqueId() == p.getUniqueId()) {
                    p.sendMessage(ChatColor.RED + "You cannot trade your self");
                    return true;
                }
                if(Bukkit.getOnlinePlayers().contains(tradeWith)){
                    p.sendMessage(ChatColor.GOLD + "You sent a trade request to: " + ChatColor.YELLOW + args[1]);
                    requestTrade.put(tradeWith, p);
                    tradeWith.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GOLD + " wants to trade with you");
                } else {
                    p.sendMessage(ChatColor.RED + args[1] + ChatColor.RED + " is not online");
                }
            }
            if(args[0].equalsIgnoreCase("accept")) {
                if(requestTrade.containsKey(p)){
                    Player tradeWith = requestTrade.get(p);
                    if(Bukkit.getOnlinePlayers().contains(tradeWith)){
                        Inventory tradeInv = new TradeInventory(p, tradeWith, TradeStatus.IN_PROGRESS).getInventory();
                        p.openInventory(tradeInv);
                        tradeWith.openInventory(tradeInv);
                        requestTrade.remove(p);
                    } else {
                        p.sendMessage(ChatColor.RED + "Player is not online anymore");
                        requestTrade.remove(p);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "There is no one that wants to trade with you :(");
                }
            }
        }
        return false;
    }


}
