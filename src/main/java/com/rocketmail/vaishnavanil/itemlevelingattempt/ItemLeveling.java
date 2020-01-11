package com.rocketmail.vaishnavanil.itemlevelingattempt;


import com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge.AlloyForge;
import com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge.AlloyForgeHandler;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ItemLeveling extends JavaPlugin {
    private static ItemLeveling ins;
    private List<Listener> listeners = new ArrayList<>();
    private HashMap<String, CommandExecutor> commandMap = new HashMap<>();
    private ItemStack crushedStone = new ItemStack(Material.STONE_BUTTON);
    private ItemStack metallicPowder = new ItemStack(Material.WHITE_DYE);
    private NamespacedKey cStoneKey;
    public void setUpListeners(){
        listen(new AlloyForgeHandler());
    }

    @Override
    public void onEnable() {
        ins = this;
        cStoneKey = new NamespacedKey(this,"cStone");
                AlloyForge.setup();

        setUpListeners();
        metallicPowder.getItemMeta().getPersistentDataContainer().set(cStoneKey, PersistentDataType.INTEGER,2);
        metallicPowder.addUnsafeEnchantment(Enchantment.DURABILITY,10);
        metallicPowder.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        crushedStone.getItemMeta().getPersistentDataContainer().set(cStoneKey, PersistentDataType.INTEGER,1);
        crushedStone.addUnsafeEnchantment(Enchantment.DURABILITY,10);
        crushedStone.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        for(Listener l:listeners){
            this.getServer().getPluginManager().registerEvents(l,this);
        }
    }
    public ItemStack getMpowder(int amount){
        return metallicPowder.asQuantity(amount);
    }
    public ItemStack getMpowder(){
        return getMpowder(1);
    }
    public boolean isMpowder(ItemStack is){
        if(is.getType() != Material.WHITE_DYE)return false;

        if(is.getItemMeta().getPersistentDataContainer().has(cStoneKey,PersistentDataType.INTEGER))return true;

        return false;
    }
    public ItemStack getCStone(int amount){
        return crushedStone.asQuantity(amount);
    }
    public ItemStack getCStone(){
        return getCStone(1);
    }
    public boolean isCStone(ItemStack is){
        if(is.getType() != Material.STONE_BUTTON)return false;

        if(is.getItemMeta().getPersistentDataContainer().has(cStoneKey,PersistentDataType.INTEGER))return true;

        return false;
    }
    public void listen(Listener lis){
        listeners.add(lis);
    }

    @Override
    public void onDisable() {
    }


    public static ItemLeveling get(){
        return ins;
    }
}
