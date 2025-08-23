//r

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
import net.minecraft.item.*;
import de.waterdu.aquaapi.ui.api.*;
import net.minecraftforge.fml.common.gameevent.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.tileentity.*;
import de.waterdu.aquaskills.hooks.*;

public class Smelting
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Smelting", new String[] { "Gain experience by melting down ores into ingots." }, "Smelter", "coal", 14, 1000L, new String[] { "iron_ingot", "iron_nugget", "gold_nugget", "gold_ingot" }, new XPSource[] { new XPSource("SMELT_XP", "stone,glass", 1.0), new XPSource("SMELT_XP", "charcoal,brick", 3.0), new XPSource("SMELT_XP", "iron_ingot,pixelmon:aluminium_ingot,pixelmon:silicon", 15.0), new XPSource("SMELT_XP", "gold_ingot", 35.0) }, new Ability[] { new Ability("BOUND_FURNACE", "", 5L, 0L, new DisplayInfo("Bound Furnace", "furnace", 28, new String[] { "You never know when the need to smelt will arise.", "You can store and summon a furnace.", "", "To store, sneak and right click an unlit furnace.", "To summon, click this ability." }), false), new Ability("STOKE_THE_FLAMES", "Mult=1.5 MultPerLvl=0.0035 CDPerLvl=-150", 25L, 300000L, new DisplayInfo("Stoke the Flames", "flint_and_steel", 30, new String[] { "Learning how to get your fires burning brighter is quite useful for a smelter.", "Extends the duration of fuel in a furnace.", "Improves as your level of Smelting increases.", "", "Activate by left clicking a lit furnace." })), new Ability("TWINNED_SMELT", "Chance=0.01 ChancePerLvl=0.00009", 50L, 60000L, new DisplayInfo("Twinned Smelt", "furnace", 32, new String[] { "Your skilled hands allow for perfect casts.", "You have a chance to smelt two items instead of one.", "Improves as your level of Smelting increases.", "", "Passive ability." })), new Ability("ETERNAL_FLAME", "CDPerLvl=-50 Dur=40000 DurPerLvl=10", 100L, 7600000L, new DisplayInfo("Eternal Flame", "fire_charge", 34, new String[] { "You have heard rumors of a mystical fire that can never be snuffed out...", "Fuels a furnace for a very long time.", "Improves as your level of Smelting increases.", "", "Activate by sneaking and left clicking an unlit furnace." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        EntityPlayerMP player;
        Cooldown cooldown;
        IBlockState state;
        final TextComponentString textComponentString;
        final Object o;
        HookRegistry.get().registerMethodHook("BOUND_FURNACE", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.RightClickBlock.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                player = (EntityPlayerMP)event.getEntityPlayer();
                cooldown = data.getCooldownSafely();
                state = event.getWorld().getBlockState(event.getPos());
                if (player.isSneaking() && player.getHeldItemMainhand().isEmpty() && state.getBlock() == Blocks.FURNACE) {
                    if (cooldown.getActiveUntil() == -1L) {
                        if (PlayerHelper.removeBlock(event.getPos(), player.world, player, true)) {
                            cooldown.useCooldownOnly(0L);
                            cooldown.setActiveUntil(0L);
                            data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                    }
                    else {
                        new TextComponentString(Config.negative("alreadyStored", new Object[] { Config.format("furnace", new Object[0]) }));
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
        HookRegistry.get().registerMethodHook("BOUND_FURNACE", new MethodHook<Event>((Class<? extends Event>)PressAbilityEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (event.ability == ability2 && data.getTrueLevel() >= ability2.getLevelRequirement()) {
                player2 = event.player.getPlayerEntity();
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.getActiveUntil() != -1L) {
                    stack = new ItemStack(Blocks.FURNACE);
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
        HashMap map;
        long level;
        double chance;
        int count;
        boolean used;
        int i;
        HookRegistry.get().registerMethodHook("TWINNED_SMELT", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemSmeltedEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability3);
                    level = data.getTrueFunctionalLevel();
                    chance = map.get("Chance") + level * map.get("ChancePerLvl");
                    count = event.smelting.getCount();
                    used = false;
                    for (i = 0; i < count; ++i) {
                        if (RandomHelper.nextDouble() < chance) {
                            event.smelting.grow(1);
                            used = true;
                        }
                    }
                    if (used) {
                        cooldown3.use(ability3.getCooldownMilliseconds());
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        IBlockState state2;
        TileEntity te;
        TileEntityFurnace furnace;
        HashMap map2;
        long level2;
        long cd;
        double multiplier;
        HookRegistry.get().registerMethodHook("STOKE_THE_FLAMES", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    state2 = event.getWorld().getBlockState(event.getPos());
                    if (!event.getEntityPlayer().isSneaking() && state2.getBlock() == Blocks.LIT_FURNACE) {
                        te = event.getWorld().getTileEntity(event.getPos());
                        if (te instanceof TileEntityFurnace) {
                            furnace = (TileEntityFurnace)te;
                            map2 = AbilityHelper.getMap(ability4);
                            level2 = data.getTrueFunctionalLevel();
                            cd = ability4.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                            multiplier = map2.get("Mult") + level2 * map2.get("MultPerLvl");
                            furnace.setField(0, (int)(furnace.getField(0) * multiplier));
                            cooldown4.use(cd);
                            data.player.playSound(SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, (RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.2f + 1.0f);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        IBlockState state3;
        TileEntity te2;
        TileEntityFurnace furnace2;
        HashMap map3;
        long level3;
        long cd2;
        int duration;
        HookRegistry.get().registerMethodHook("ETERNAL_FLAME", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    state3 = event.getWorld().getBlockState(event.getPos());
                    if (event.getEntityPlayer().isSneaking() && state3.getBlock() == Blocks.FURNACE) {
                        te2 = event.getWorld().getTileEntity(event.getPos());
                        if (te2 instanceof TileEntityFurnace) {
                            furnace2 = (TileEntityFurnace)te2;
                            map3 = AbilityHelper.getMap(ability5);
                            level3 = data.getTrueFunctionalLevel();
                            cd2 = ability5.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level3;
                            duration = map3.get("Dur").intValue() + (int)level3 * map3.get("DurPerLvl").intValue();
                            furnace2.setField(0, duration);
                            cooldown5.use(cd2);
                            data.player.playSound(SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, (RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.2f + 1.0f);
                            data.player.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }));
    }
}
