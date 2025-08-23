//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import com.pixelmonmod.pixelmon.config.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraft.item.*;
import de.waterdu.aquaapi.ui.api.*;
import com.pixelmonmod.pixelmon.api.events.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Forging
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Forging", new String[] { "Gain experience through the results of your hammer." }, "Smith", "pixelmon:iron_hammer", 9, 1000L, new String[] { "wooden_hammer", "stone_hammer", "iron_hammer", "golden_hammer", "diamond_hammer" }, new XPSource[] { new XPSource("FORGE_XP", "pixelmon:dive_ball_lid,pixelmon:dusk_ball_lid,pixelmon:fast_ball_lid,pixelmon:friend_ball_lid,pixelmon:great_ball_lid,pixelmon:heal_ball_lid,pixelmon:heavy_ball_lid,pixelmon:level_ball_lid,pixelmon:love_ball_lid,pixelmon:lure_ball_lid,pixelmon:luxury_ball_lid,pixelmon:moon_ball_lid,pixelmon:nest_ball_lid,pixelmon:net_ball_lid,pixelmon:poke_ball_lid,pixelmon:premier_ball_lid,pixelmon:quick_ball_lid,pixelmon:repeat_ball_lid,pixelmon:safari_ball_lid,pixelmon:sport_ball_lid,pixelmon:timer_ball_lid,pixelmon:ultra_ball_lid", 50.0), new XPSource("FORGE_XP", "pixelmon:iron_base,pixelmon:aluminum_base,pixelmon:aluminium_plate", 25.0) }, new Ability[] { new Ability("BOUND_ANVIL", "", 5L, 0L, new DisplayInfo("Bound Anvil", "pixelmon:anvil", 28, new String[] { "No smith is truly complete without their anvil.", "You can store and summon an anvil.", "", "To store, sneak and right click an anvil.", "To summon, click this ability." }), false), new Ability("HAMMER_TIME", "Mult=1.1 MultPerLvl=0.0009", 25L, 0L, new DisplayInfo("Hammer Time", "pixelmon:gold_hammer", 30, new String[] { "Once you get into a rhythm, your hammer seems to take on a life of its own.", "You can hammer faster.", "Improves as your level of Forging increases.", "", "Passive ability." })), new Ability("TWINNED_FORGE", "Chance=0.01 ChancePerLvl=0.00009", 50L, 60000L, new DisplayInfo("Twinned Forge", "pixelmon:aluminium_plate", 32, new String[] { "Your hammer can be supernaturally efficient from time to time.", "You have a chance to forge two items instead of one.", "Improves as your level of Forging increases.", "", "Passive ability." })), new Ability("TRUE_STRIKE", "CDPerLvl=-30 Dur=10 DurPerLvl=0.01", 100L, 90000L, new DisplayInfo("True Strike", "pixelmon:diamond_hammer", 34, new String[] { "With great focus and precision, your anvil and hammer become one.", "Forging becomes instantaneous for a short while.", "Improves as your level of Forging increases.", "", "Bindable. Click to choose a Forging item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        EntityPlayerMP player;
        Cooldown cooldown;
        IBlockState state;
        final TextComponentString textComponentString;
        final Object o;
        HookRegistry.get().registerMethodHook("BOUND_ANVIL", new MethodHook((Class)PlayerInteractEvent.RightClickBlock.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                player = (EntityPlayerMP)event.getEntityPlayer();
                cooldown = data.getCooldownSafely();
                state = event.getWorld().getBlockState(event.getPos());
                if (player.isSneaking() && player.getHeldItemMainhand().isEmpty() && state.getBlock() == PixelmonBlocks.anvil) {
                    if (cooldown.getActiveUntil() == -1L) {
                        if (PlayerHelper.removeBlock(event.getPos(), player.world, player, true)) {
                            cooldown.useCooldownOnly(0L);
                            cooldown.setActiveUntil(0L);
                            data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                    }
                    else {
                        new TextComponentString(Config.negative("alreadyStored", new Object[] { Config.format("anvil", new Object[0]) }));
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
        HookRegistry.get().registerMethodHook("BOUND_ANVIL", new MethodHook((Class)PressAbilityEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (event.ability == ability2 && data.getTrueLevel() >= ability2.getLevelRequirement()) {
                player2 = event.player.getPlayerEntity();
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.getActiveUntil() != -1L) {
                    stack = new ItemStack(PixelmonBlocks.anvil);
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
        HookRegistry.get().registerMethodHook("TWINNED_FORGE", new MethodHook((Class)AnvilEvent.FinishedSmith.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability3);
                    level = data.getTrueFunctionalLevel();
                    chance = map.get("Chance") + level * map.get("ChancePerLvl");
                    count = event.getItem().getCount();
                    used = false;
                    for (i = 0; i < count; ++i) {
                        if (RandomHelper.nextDouble() < chance) {
                            event.getItem().grow(1);
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
        HashMap map2;
        long level2;
        long cd;
        long dur;
        HookRegistry.get().registerMethodHook("TRUE_STRIKE", new MethodHook((Class)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    map2 = AbilityHelper.getMap(ability4);
                    level2 = data.getTrueFunctionalLevel();
                    cd = ability4.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                    dur = map2.get("Dur").longValue() + map2.get("DurPerLvl").longValue() * level2;
                    cooldown4.use(cd, dur);
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        HookRegistry.get().registerMethodHook("TRUE_STRIKE", new MethodHook((Class)AnvilEvent.BeatAnvil.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (cooldown5.isActive()) {
                    event.setNeededHits(0);
                }
            }
            return;
        }));
        final Ability ability6;
        HashMap map3;
        long level3;
        double multiplier;
        HookRegistry.get().registerMethodHook("HAMMER_TIME", new MethodHook((Class)AnvilEvent.BeatAnvil.class, (event, data) -> {
            ability6 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability6.getLevelRequirement()) {
                map3 = AbilityHelper.getMap(ability6);
                level3 = data.getTrueFunctionalLevel();
                multiplier = map3.get("Mult") + level3 * map3.get("MultPerLvl");
                event.setForce((int)(event.getForce() * multiplier));
            }
        }));
    }
}
