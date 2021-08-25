package me.TahaCheji;

import me.TahaCheji.command.MainCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        System.out.println(ChatColor.GOLD + "Starting: MafanaTradeNetwork");
        instance = this;

        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName, ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if (!setupEconomy()) {
            System.out.print("No econ plugin found.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("MafanaTrade").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "Stopping: MafanaTradeNetwork");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static Main getInstance() {
        return instance;
    }
}
