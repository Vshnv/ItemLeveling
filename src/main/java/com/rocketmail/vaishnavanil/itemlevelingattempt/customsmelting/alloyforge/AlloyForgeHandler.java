package com.rocketmail.vaishnavanil.itemlevelingattempt.customsmelting.alloyforge;

import com.rocketmail.vaishnavanil.itemlevelingattempt.ItemLeveling;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Alloys;
import com.rocketmail.vaishnavanil.itemlevelingattempt.alloys.Nuggets;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class AlloyForgeHandler implements Listener {
    @EventHandler
    public void click(InventoryClickEvent e){
        InventoryView view = e.getView();
        if(AlloyForge.isOf(view)){
            alloyForgeInteract(e);
            return;
        }
    }

    @EventHandler
    public void drag(InventoryDragEvent e){
        InventoryView view = e.getView();
        if(AlloyForge.isOf(view)){
            alloyForgeInteract(e);
            return;
        }
    }


    public void alloyForgeInteract(InventoryInteractEvent e){

        if(e instanceof InventoryClickEvent){

            InventoryClickEvent click = (InventoryClickEvent)e;
            if(click.getRawSlot() == click.getSlot()) {

                    if (!AlloyForge.isInputSlot(click.getRawSlot())) {
                        e.setCancelled(true);
                        if(click.getRawSlot() == 22){
                            convert(e.getView());
                        }
                    }

            }
        }else if(e instanceof InventoryDragEvent){
            InventoryDragEvent drag = (InventoryDragEvent)e;

            for(int slot:drag.getRawSlots()){
                if(slot <= e.getView().getTopInventory().getSize()) {
                    if (!AlloyForge.isInputSlot(slot)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        new BukkitRunnable(){

            @Override
            public void run() {
                InventoryView iv = e.getView();
                AlloyForge.tryRecipeComplete(iv);
            }
        }.runTaskLater(ItemLeveling.get(),2);

    }

    public static void convert(InventoryView iv){
        AlloyForgeRecipe recipe = AlloyForge.getMatchingRecipe(iv);
        if(recipe == null)return;
        ItemStack[] result = recipe.getResult();
        Location ploc = iv.getPlayer().getLocation();
        int mul = recipe.getMultiplier(AlloyForgeRecipe.getInputRecipe(iv,AlloyForge.getInputSlots()));

        recipe.reduce(iv,AlloyForge.getInputSlots(),mul);
        AlloyForge.tryRecipeComplete(iv);
        Arrays.stream(result).forEach(
                i-> {
                    if(i!=null)        iv.getPlayer().getInventory().addItem(i.asQuantity(i.getAmount()*mul)).values().iterator().forEachRemaining(j -> {if(j!=null)ploc.getWorld().dropItemNaturally(ploc,j);});
                }
        );
    }

    @EventHandler
    public void Debug(PlayerDropItemEvent e){
        e.getPlayer().openInventory(AlloyForge.getNewForge());
    }
    @EventHandler
    public void Debug(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Arrays.stream(Alloys.values()).forEach(i->p.getInventory().addItem(i.getAlloy().getAlloyItem(64)).values().iterator().forEachRemaining(j->p.getLocation().getWorld().dropItemNaturally(p.getLocation(),j)));
        Arrays.stream(Nuggets.values()).forEach(i->p.getInventory().addItem(i.getNugget().getNuggetItem(64)).values().iterator().forEachRemaining(j->p.getLocation().getWorld().dropItemNaturally(p.getLocation(),j)));
        p.getInventory().addItem(ItemLeveling.get().getCStone(64)).values().iterator().forEachRemaining(j->p.getLocation().getWorld().dropItemNaturally(p.getLocation(),j));
        p.getInventory().addItem(ItemLeveling.get().getMpowder(64)).values().iterator().forEachRemaining(j->p.getLocation().getWorld().dropItemNaturally(p.getLocation(),j));

    }
}
