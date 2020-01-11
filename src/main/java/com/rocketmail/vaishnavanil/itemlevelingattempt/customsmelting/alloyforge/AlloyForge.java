package com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge;

import com.rocketmail.vaishnavanil.itemlevelingattempt.ItemLeveling;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Alloys;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Nuggets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlloyForge {
    private static String name = ChatColor.GRAY + "Alloy Forge";
    private static List<AlloyForgeRecipe> recipes = new ArrayList<>();
    private static int[] in = {11,19,20,21,29},out = {15,23,24,25,33};
    public static void setup(){
        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemStack ironN = new ItemStack(Material.IRON_NUGGET);
        ItemStack goldN = new ItemStack(Material.GOLD_NUGGET);
        AlloyForgeRecipe recipe1 = new AlloyForgeRecipe(
                AlloyForgeRecipe.toMatrix(ItemLeveling.get().getCStone(5),iron.asQuantity(5), Alloys.A7.getAlloy().getAlloyItem(3),goldN.asQuantity(4),iron.asQuantity(5)),
                AlloyForgeRecipe.toMatrix(null,null, Nuggets.N19.getNugget().getNuggetItem(12),ironN.asQuantity(6),null)
        );
        AlloyForgeRecipe recipe2 = new AlloyForgeRecipe(
                AlloyForgeRecipe.toMatrix(Nuggets.N8.getNugget().getNuggetItem(6),ironN.asQuantity(5), Alloys.A19.getAlloy().getAlloyItem(3),iron.asQuantity(5),iron.asQuantity(5)),
                AlloyForgeRecipe.toMatrix(null,null, Nuggets.N11.getNugget().getNuggetItem(16),ironN.asQuantity(3),null)
        );
        AlloyForgeRecipe recipe3 = new AlloyForgeRecipe(
                AlloyForgeRecipe.toMatrix(Nuggets.N8.getNugget().getNuggetItem(7),Nuggets.N8.getNugget().getNuggetItem(7), Alloys.A3.getAlloy().getAlloyItem(3),Nuggets.N5.getNugget().getNuggetItem(2),Nuggets.N5.getNugget().getNuggetItem(2)),
                AlloyForgeRecipe.toMatrix(null,null, Nuggets.N12.getNugget().getNuggetItem(36),Alloys.A18.getAlloy().getAlloyItem(2),null)
        );
        AlloyForgeRecipe recipe4 = new AlloyForgeRecipe(
                AlloyForgeRecipe.toMatrix(Alloys.A3.getAlloy().getAlloyItem(2),Alloys.A3.getAlloy().getAlloyItem(2), Alloys.A9.getAlloy().getAlloyItem(9),Alloys.A5.getAlloy().getAlloyItem(7),Alloys.A5.getAlloy().getAlloyItem(7)),
                AlloyForgeRecipe.toMatrix(null,ItemLeveling.get().getMpowder(7), Nuggets.N21.getNugget().getNuggetItem(12),Alloys.A12.getAlloy().getAlloyItem(2),ItemLeveling.get().getCStone(5))
        );

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        recipes.add(recipe4);

        //TODO:: add recipes
    }
    private static ItemStack colorTile(Material m){
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }
    private AlloyForge(){}
    static ItemStack bsg = colorTile(Material.BLACK_STAINED_GLASS_PANE);
    static ItemStack bsgGray = colorTile(Material.GRAY_STAINED_GLASS_PANE);
    static ItemStack bsgRed = colorTile(Material.RED_STAINED_GLASS_PANE);
    static ItemStack bsgYellow = colorTile(Material.YELLOW_STAINED_GLASS_PANE);
    static ItemStack convert = colorTile(Material.GREEN_STAINED_GLASS_PANE);
    public static AlloyForgeRecipe getMatchingRecipe(InventoryView iv){
        for(AlloyForgeRecipe r:recipes){
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
        inv.setItem(36,bsgGray);
        inv.setItem(40,bsgGray);
        inv.setItem(44,bsgGray);
        inv.setItem(22,convert);
        inv.setItem(47,bsgRed);
        inv.setItem(48,bsgYellow);
        inv.setItem(49,bsgRed);
        inv.setItem(50,bsgYellow);
        inv.setItem(51,bsgRed);
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
        for(AlloyForgeRecipe r:recipes){
            if(r.compareRecipe(AlloyForgeRecipe.getInputRecipe(iv,in))){
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
    public static List<AlloyForgeRecipe> getRecipes() {
        return recipes;
    }
}
