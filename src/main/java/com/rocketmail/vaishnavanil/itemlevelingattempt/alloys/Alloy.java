package com.rocketmail.vaishnavanil.itemlevelingattempt.alloys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Alloy {
    private ItemStack item;
    private String name;
    public Alloy(String name,int ModelData){
        item = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setCustomModelData(ModelData);
        item.setItemMeta(meta);
    }


    public String getName(){
        return name;
    }
    public ItemStack getAlloyItem(int amount){
        return item.asQuantity(amount);
    }
    public ItemStack getAlloyItem(){
        return getAlloyItem(1);
    }

    public boolean compareAlloy(ItemStack i){
        if(i.getType() != Material.IRON_INGOT)return false;
        return  i.getItemMeta().getCustomModelData() == item.getItemMeta().getCustomModelData();
    }
}
