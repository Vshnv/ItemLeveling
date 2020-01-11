package com.rocketmail.vaishnavanil.itemlevelingattempt.alloys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Nugget {
    private ItemStack item;
    private String name;
    public Nugget(String name,int ModelData){
        item = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setCustomModelData(ModelData);
        item.setItemMeta(meta);
    }


    public String getName(){
        return name;
    }
    public ItemStack getNuggetItem(int amount){
        return item.asQuantity(amount);
    }
    public ItemStack getNuggetItem(){
        return getNuggetItem(1);
    }

    public boolean compareNugget(ItemStack i){
        if(i.getType() != Material.IRON_NUGGET)return false;
        return  i.getItemMeta().getCustomModelData() == item.getItemMeta().getCustomModelData();
    }
}
