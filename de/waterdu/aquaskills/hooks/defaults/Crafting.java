//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.api.events.*;
import de.waterdu.aquaapi.ui.api.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.gameevent.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Crafting
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Crafting", new String[] { "Gain experience through the creation of new items." }, "Carpenter", "book", 6, 1000L, new String[] { "crafting_table", "stick", "paper" }, new XPSource[] { new XPSource("CRAFT_XP", "planks,stick,torch,crafting_table,furnace,chest,ladder,fence,boat,slabs,sign,door,glowstone_block,snow,bricks,sandstone,note_block,jack_o_lantern,arrow,map,book,book_and_quill,mushroom_stew,rabbit_stew,paper", 5.0), new XPSource("CRAFT_XP", "tnt,bookshelf,sea_lantern,repeater,dropper,dispenser,comparator,hopper,anvil,daylight_sensor,compass,clock,bucket,piston,sticky_piston,rail,minecart,powered_rail,detector_rail,observer,bed,brewing_stand,pixelmon:pc", 15.0), new XPSource("CRAFT_XP", "cake,enchanting_table,ender_chest,golden_apple,beacon,pixelmon:healer", 25.0) }, new Ability[] { new Ability("BOUND_CRAFTING_TABLE", "", 5L, 0L, new DisplayInfo("Bound Crafting Table", "crafting_table", 28, new String[] { "No craftsman is ever far from their trusty table.", "You can store and summon a crafting table.", "", "To store, sneak and right click a crafting table.", "To summon, click this ability." }), false), new Ability("EXPERIENCED_CRAFTING", "ironpickaxe=2 ironaxe=2 ironsword=2 ironhoe=2 ironshovel=2 goldenpickaxe=4 goldenaxe=4 goldensword=4 goldenhoe=4 goldenshovel=4 diamondpickaxe=8 diamondaxe=8 diamondsword=8 diamondhoe=8 diamondshovel=8", 25L, 0L, new DisplayInfo("Experienced Crafting", "experience_bottle", 30, new String[] { "Nothing beats a good day of crafting.", "Crafting certain items gives enchanting experience.", "", "Passive ability." })), new Ability("TWINNED_CRAFT", "Chance=0.01 ChancePerLvl=0.00009 Items=iron_pickaxe,iron_shovel,iron_hoe,iron_sword,iron_axe,golden_pickaxe,golden_shovel,golden_hoe,golden_sword,golden_axe,diamond_pickaxe,diamond_shovel,diamond_hoe,diamond_sword,diamond_axe", 50L, 60000L, new DisplayInfo("Twinned Craft", "crafting_table", 32, new String[] { "When you really get into the flow, you just can't stop yourself.", "You have a chance to craft two items instead of one.", "Improves as your level of Crafting increases.", "", "Passive ability." })), new Ability("EQUIVALENT_EXCHANGE", "CDPerLvl=-50 Sizing=4 Ladder=log,coal,iron_ingot,gold_ingot,diamond,emerald", 100L, 60000L, new DisplayInfo("Equivalent Exchange", "end_crystal", 34, new String[] { "Crafting tables can be used for transmutation, if you know how.", "You can exchange materials up and down.", "Improves as your level of Crafting increases.", "", "Activate by left clicking materials on a crafting table." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        EntityPlayerMP player;
        Cooldown cooldown;
        IBlockState state;
        final TextComponentString textComponentString;
        final Object o;
        HookRegistry.get().registerMethodHook("BOUND_CRAFTING_TABLE", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.RightClickBlock.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                player = (EntityPlayerMP)event.getEntityPlayer();
                cooldown = data.getCooldownSafely();
                state = event.getWorld().getBlockState(event.getPos());
                if (player.isSneaking() && player.getHeldItemMainhand().isEmpty() && state.getBlock() == Blocks.CRAFTING_TABLE) {
                    if (cooldown.getActiveUntil() == -1L) {
                        if (PlayerHelper.removeBlock(player, event.getPos(), false)) {
                            cooldown.useCooldownOnly(0L);
                            cooldown.setActiveUntil(0L);
                            data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                    }
                    else {
                        new TextComponentString(Config.negative("alreadyStored", new Object[] { Config.format("table", new Object[0]) }));
                        ((EntityPlayerMP)o).sendMessage((ITextComponent)textComponentString);
                    }
                    event.setCanceled(true);
                }
            }
            return;
        }));
        final Ability ability2;
        EntityPlayerMP player2;
        Cooldown cooldown2;
        ItemStack stack;
        HookRegistry.get().registerMethodHook("BOUND_CRAFTING_TABLE", new MethodHook<Event>((Class<? extends Event>)PressAbilityEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (event.ability == ability2 && data.getTrueLevel() >= ability2.getLevelRequirement()) {
                player2 = event.player.getPlayerEntity();
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.getActiveUntil() != -1L) {
                    stack = new ItemStack(Blocks.CRAFTING_TABLE);
                    player2.dropItem(stack, true);
                    AquaUI.openUI(player2, (IPage)null);
                    cooldown2.useCooldownOnly(0L);
                    cooldown2.setActiveUntil(-1L);
                    data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        ItemStack stack2;
        String[] split;
        ArrayList<Item> itemLadder;
        String[] split2;
        String[] ladder;
        int length;
        int k = 0;
        String rung;
        Item item;
        EntityPlayerMP player3;
        IBlockState state2;
        ArrayList<Item> itemLadder2;
        int index;
        long level;
        long cd;
        int sizing;
        boolean used;
        Item prev;
        ItemStack newStack;
        Item next;
        ItemStack newStack2;
        HookRegistry.get().registerMethodHook("EQUIVALENT_EXCHANGE", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    stack2 = event.getItemStack();
                    if (!ability3.areArgsParsed()) {
                        split = ability3.getArgs().split(" ");
                        ability3.getParsedArgs().add(Double.parseDouble(split[0].split("=")[1]));
                        ability3.getParsedArgs().add(Integer.parseInt(split[1].split("=")[1]));
                        itemLadder = new ArrayList<Item>();
                        ladder = (split2 = split[2].split("=")[1].split(","));
                        for (length = split2.length; k < length; ++k) {
                            rung = split2[k];
                            item = Item.getByNameOrId(rung);
                            if (item != null) {
                                itemLadder.add(item);
                            }
                        }
                        ability3.getParsedArgs().add(itemLadder);
                    }
                    if (!stack2.isEmpty()) {
                        player3 = data.player.getPlayerEntity();
                        state2 = player3.getServerWorld().getBlockState(event.getPos());
                        if (state2.getBlock() == Blocks.CRAFTING_TABLE) {
                            itemLadder2 = ability3.getParsedArgs().get(2);
                            index = itemLadder2.indexOf(stack2.getItem());
                            if (index > -1) {
                                level = data.getTrueFunctionalLevel();
                                cd = ability3.getCooldownMilliseconds() + (int)(double)ability3.getParsedArgs().get(0) * level;
                                sizing = ability3.getParsedArgs().get(1);
                                used = false;
                                if (stack2.getCount() < sizing || index == itemLadder2.size() - 1) {
                                    if (index > 0) {
                                        prev = itemLadder2.get(index - 1);
                                        stack2.shrink(1);
                                        newStack = new ItemStack(prev, sizing);
                                        player3.dropItem(newStack, true);
                                        used = true;
                                    }
                                }
                                else {
                                    next = itemLadder2.get(index + 1);
                                    stack2.shrink(4);
                                    newStack2 = new ItemStack(next, 1);
                                    player3.dropItem(newStack2, true);
                                    used = true;
                                }
                                if (used) {
                                    cooldown3.use(cd);
                                    data.player.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
                                }
                            }
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        String[] items;
        ArrayList<Item> parsedItems;
        final String[] array;
        int length2;
        int l = 0;
        String item2;
        Item i;
        HashMap map;
        ArrayList<Item> parsedItems2;
        long level2;
        double chance;
        int count;
        boolean used2;
        int j;
        HookRegistry.get().registerMethodHook("TWINNED_CRAFT", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemCraftedEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability4, arg -> {
                        items = arg.split(",");
                        parsedItems = new ArrayList<Item>();
                        for (length2 = array.length; l < length2; ++l) {
                            item2 = array[l];
                            i = Item.getByNameOrId(item2);
                            if (i != null) {
                                parsedItems.add(i);
                            }
                        }
                        return parsedItems;
                    });
                    parsedItems2 = ability4.getParsedArgs().get(1);
                    if (parsedItems2.contains(event.crafting.getItem())) {
                        level2 = data.getTrueFunctionalLevel();
                        chance = map.get("Chance") + level2 * map.get("ChancePerLvl");
                        count = event.crafting.getCount();
                        used2 = false;
                        for (j = 0; j < count; ++j) {
                            if (RandomHelper.nextDouble() < chance) {
                                event.crafting.grow(1);
                                used2 = true;
                            }
                        }
                        if (used2) {
                            cooldown4.use(ability4.getCooldownMilliseconds());
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        HashMap map2;
        String key;
        Double amount;
        HookRegistry.get().registerMethodHook("EXPERIENCED_CRAFTING", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemCraftedEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    map2 = AbilityHelper.getMap(ability5);
                    key = event.crafting.getItem().getRegistryName().toString().replace("_", "").replace("minecraft:", "");
                    amount = map2.get(key);
                    if (amount != null) {
                        event.player.addExperience(amount.intValue());
                    }
                }
            }
        }));
    }
}
