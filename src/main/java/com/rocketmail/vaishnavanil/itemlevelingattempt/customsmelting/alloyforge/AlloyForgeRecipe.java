package com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge;

import com.rocketmail.vaishnavanil.itemlevelingattempt.ItemLeveling;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class AlloyForgeRecipe {
    // =Recipe-Matrix=
    //        1
    //       234
    //        5
    private ItemStack[] recipe;
    private ItemStack[] result;
    public int getMultiplier(ItemStack[] input){
        int least = 0;
        for(int i = 0; i<recipe.length;i++){
            if(input[i] == null || recipe[i] == null)continue;
            int v = input[i].getAmount()/recipe[i].getAmount();
            if(least == 0)least = v;
            if(v<least && v!=0){
                least = v;
            }
        }
        return least;
    }
    public AlloyForgeRecipe(ItemStack[] recipe,ItemStack[] result){
        this.recipe =recipe;
        this.result = result;
    }
    public static ItemStack[] toMatrix(ItemStack a,ItemStack b, ItemStack c,ItemStack d,ItemStack e){
        return new ItemStack[]{a,b,c,d,e};
    }
    public void pasteResult(InventoryView iv,int[] output){
        int index = 0;
        int mul = getMultiplier(getInputRecipe(iv,AlloyForge.getInputSlots()));
        for(int o : output){
            if(result[index] != null)iv.setItem(o,result[index].asQuantity(mul*result[index].getAmount()));
            index++;
        }
        ((Player)iv.getPlayer()).updateInventory();

    }
    public static int getModel(ItemStack i){
        return i.getItemMeta().hasCustomModelData() ? i.getItemMeta().getCustomModelData():0;
    }
    public boolean compareRecipe(ItemStack[] recipeMatrix){
        try {
            for (int i = 0; i < recipe.length; i++) {
                if(recipe[i] == null && recipeMatrix[i] != null)return false;
                if(recipe[i] != null && recipeMatrix[i] == null)return false;
                if(recipe[i] == null && recipeMatrix[i] == null)continue;
                if (recipe[i].getType() != recipeMatrix[i].getType()) return false;

                if (getModel(recipe[i]) != getModel(recipeMatrix[i]))
                    return false;
                if(recipe[i].getAmount() > recipeMatrix[i].getAmount())return false;
                if(recipe[i].hasItemMeta()){
                    if(ItemLeveling.get().isCStone(recipe[i])){
                        if(recipeMatrix[i].hasItemMeta()){
                            if(!ItemLeveling.get().isCStone(recipeMatrix[i])){
                                return false;
                            }
                        }else return false;
                    }
                    if(ItemLeveling.get().isMpowder(recipe[i])){
                        if(recipeMatrix[i].hasItemMeta()){
                            if(!ItemLeveling.get().isMpowder(recipeMatrix[i])){
                                return false;
                            }
                        }else return false;
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }
    public void reduce(InventoryView iv,int[] inp,int mul){
        int index = 0;
        for(ItemStack i:recipe){
            removeItem(iv,inp[index],i.getAmount()*mul);
            index++;
        }
        ((Player)iv.getPlayer()).updateInventory();
    }
    public static void removeItem(InventoryView iv,int slot, int amount){
        int remain = iv.getItem(slot).getAmount() - amount;

        if(remain<=0){
            iv.setItem(slot,null);
        }else{
            iv.getItem(slot).setAmount(remain);
        }

    }
    public ItemStack[] getResult(){
        return result;
    }
    public static ItemStack[] getInputRecipe(InventoryView iv,int[] input){
        ItemStack[] recipe = new ItemStack[input.length];
        int index = 0;
        for(int i:input){
            recipe[index++] = iv.getItem(i);
        }
        return recipe;
    }
}
