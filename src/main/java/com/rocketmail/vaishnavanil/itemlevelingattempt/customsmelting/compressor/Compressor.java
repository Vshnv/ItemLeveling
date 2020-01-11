package com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.compressor;

import com.rocketmail.vaishnavanil.itemlevelingattempt.ItemLeveling;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Alloys;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Nuggets;
import com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge.AlloyForge;
import com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge.AlloyForgeRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compressor {
    private static String name = ChatColor.GRAY + "Compressor";
    private static List<CompressorRecipe> recipes = new ArrayList<>();
    private static int[] in = {19,20,21},out = {24};
    public static void setup(){
        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemStack ironN = new ItemStack(Material.IRON_NUGGET);
        ItemStack goldN = new ItemStack(Material.GOLD_NUGGET);


        //TODO:: add recipes
    }
    private static ItemStack colorTile(Material m){
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }
    private Compressor(){}
    static ItemStack bsg = colorTile(Material.BLACK_STAINED_GLASS_PANE);
    static ItemStack bsgGray = colorTile(Material.GRAY_STAINED_GLASS_PANE);
    static ItemStack bsgRed = colorTile(Material.RED_STAINED_GLASS_PANE);
    static ItemStack bsgYellow = colorTile(Material.YELLOW_STAINED_GLASS_PANE);
    static ItemStack convert = colorTile(Material.GREEN_STAINED_GLASS_PANE);
    static ItemStack bsgWhite = colorTile(Material.WHITE_STAINED_GLASS_PANE);
    public static CompressorRecipe getMatchingRecipe(InventoryView iv){
        for(CompressorRecipe r:recipes){
            if(r.compareRecipe(AlloyForgeRecipe.getInputRecipe(iv,in))){
                return r;
            }
        }
        return null;
    }
    public static Inventory getNewForge()
    {
        Inventory inv = Bukkit.createInventory(null,9*6,name);
        fillInv(inv,bsg);
        inv.setItem(0,bsgGray);
        inv.setItem(4,bsgGray);
        inv.setItem(8,bsgGray);
        Arrays.stream(in).forEach(i-> {
            inv.setItem(i-9,bsgWhite);
            inv.setItem(i+9,bsgWhite);
        });
        inv.setItem(36,bsgGray);
        inv.setItem(40,bsgGray);
        inv.setItem(44,bsgGray);
        inv.setItem(22,convert);
        inv.setItem(47,bsgRed);
        inv.setItem(48,bsgYellow);
        inv.setItem(49,bsgRed);
        inv.setItem(50,bsgYellow);
        inv.setItem(51,bsgRed);//15,23,24,25,33
        inv.setItem(15,bsgWhite);
        inv.setItem(23,bsgWhite);
        inv.setItem(25,bsgWhite);
        inv.setItem(33,bsgWhite);
        Arrays.stream(in).forEach(i -> inv.setItem(i,null));
        Arrays.stream(out).forEach(i -> inv.setItem(i,null));
        return inv;
    }
    private static void fillInv(Inventory inv,ItemStack is){
        ItemStack[] contents = inv.getContents();
        for(int i = 0; i<inv.getContents().length;i++){
            contents[i] = bsg;
        }
        inv.setContents(contents);
    }
    public static void clearOutput(InventoryView iv){
        new BukkitRunnable(){

            @Override
            public void run() {
                Arrays.stream(out).forEach(i -> iv.setItem(i,null));

            }
        }.runTaskLater(ItemLeveling.get(),1);

    }

    public static void tryRecipeComplete(InventoryView iv){
        for(CompressorRecipe r:recipes){
            if(r.compareRecipe(CompressorRecipe.getInputRecipe(iv,in))){
                r.pasteResult(iv,out);

                return;
            }
        }
        clearOutput(iv);
        return;
    }

    public static boolean isOf(InventoryView iv){
        return(name.equals(iv.getTitle()));
    }

    public static boolean isInputSlot(int rawSlot) {
        return Arrays.binarySearch(in,rawSlot) >=0;
    }
    public static int[] getInputSlots(){
        return in;
    }
    public static List<CompressorRecipe> getRecipes() {
        return recipes;
    }
}
