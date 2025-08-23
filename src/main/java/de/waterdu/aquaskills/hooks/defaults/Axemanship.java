//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaskills.hooks.*;

public class Axemanship
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Axemanship", new String[] { "Gain experience by hacking down your foes with your axe." }, "Barbarian", "stone_axe", 2, 1000L, new String[] { "wooden_axe", "stone_axe", "iron_axe", "golden_axe", "diamond_axe" }, new XPSource[] { new XPSource("ATTACK_XP", "2.5 axe", 0.0) }, new Ability[] { new Ability("HACK_AND_SLASH", "Damage=1 DamagePerLvl=0.007", 5L, 0L, new DisplayInfo("Hack and Slash", "stone_axe", 28, new String[] { "The path of the barbarian is a ferocious one.", "Your axes deal more damage.", "Improves as your level of Axemanship increases.", "", "Passive ability." })), new Ability("UNARMORED_DEFENSE", "Redux=0.15 ReduxPerLvl=0.00025", 25L, 0L, new DisplayInfo("Unarmored Defense", "leather_chestplate", 30, new String[] { "Who needs armor anyway?", "You take reduced damage while wearing no armor.", "Improves as your level of Axemanship increases.", "", "Passive ability." })), new Ability("SHIELDBREAKER", "Durability=10 DurabilityPerLvl=0.02 AxeFactor=0.5", 50L, 0L, new DisplayInfo("Shieldbreaker", "shield", 32, new String[] { "Wooden shields stand no chance against the might of the axe.", "At cost to your axe's durability, wear down opponent's shields.", "Improves as your level of Axemanship increases.", "", "Activate by attacking a shield." })), new Ability("FURY_OF_THE_AXE", "Dur=8 DurPerLvl=0.01 CDPerLvl=-40", 100L, 240000L, new DisplayInfo("Fury of the Axe", "diamond_axe", 34, new String[] { "You and your axe become one, insulating yourself from all forms of pain.", "Allows you to gain beneficial buffs, but also begin to wither.", "Improves as your level of Axemanship increases.", "", "Bindable. Click to choose an Axemanship item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final EntityPlayerMP player;
        boolean noArmor;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        Ability ability;
        HashMap map;
        long level;
        double redux;
        HookRegistry.get().registerMethodHook("UNARMORED_DEFENSE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            player = data.player.getPlayerEntity();
            if (event.getEntityLiving().getUniqueID().equals(player.getUniqueID())) {
                noArmor = true;
                player.inventory.armorInventory.iterator();
                while (iterator.hasNext()) {
                    stack = iterator.next();
                    if (stack.getItem() instanceof ItemArmor) {
                        noArmor = false;
                        break;
                    }
                }
                if (noArmor) {
                    ability = (Ability)data.hookable;
                    if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                        map = AbilityHelper.getMap(ability);
                        level = data.getTrueFunctionalLevel();
                        redux = map.get("Redux") + level * map.get("ReduxPerLvl");
                        event.setAmount(event.getAmount() - event.getAmount() * (float)redux);
                    }
                }
            }
            return;
        }));
        EntityPlayerMP player2;
        Ability ability2;
        HashMap map2;
        long level2;
        double damage;
        HookRegistry.get().registerMethodHook("HACK_AND_SLASH", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                player2 = data.player.getPlayerEntity();
                if (player2.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                    ability2 = (Ability)data.hookable;
                    if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                        map2 = AbilityHelper.getMap(ability2);
                        level2 = data.getTrueFunctionalLevel();
                        damage = map2.get("Damage") + level2 * map2.get("DamagePerLvl");
                        event.setAmount(event.getAmount() + (float)damage);
                    }
                }
            }
            return;
        }));
        Ability ability3;
        EntityPlayerMP player3;
        ItemStack offhand;
        HashMap map3;
        long level3;
        int durability;
        double axeFactor;
        HookRegistry.get().registerMethodHook("SHIELDBREAKER", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability3 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                    player3 = data.player.getPlayerEntity();
                    offhand = event.getEntityLiving().getHeldItemOffhand();
                    if (offhand.getItem() instanceof ItemShield && player3.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                        map3 = AbilityHelper.getMap(ability3);
                        level3 = data.getTrueFunctionalLevel();
                        durability = (int)(map3.get("Durability") + level3 * map3.get("DurabilityPerLvl"));
                        axeFactor = map3.get("AxeFactor");
                        player3.getHeldItemMainhand().damageItem((int)(durability * axeFactor), (EntityLivingBase)player3);
                        offhand.damageItem(durability, event.getEntityLiving());
                        data.player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 0.5f, 0.7f);
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        final Cooldown cooldown;
        EntityPlayerMP player4;
        HashMap map4;
        long level4;
        long cd;
        long dur;
        int durTicks;
        HookRegistry.get().registerMethodHook("FURY_OF_THE_AXE", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            cooldown = data.getCooldownSafely();
            if (!cooldown.isOnCooldown() && data.getTrueLevel() >= ability4.getLevelRequirement()) {
                player4 = data.player.getPlayerEntity();
                map4 = AbilityHelper.getMap(ability4);
                level4 = data.getTrueFunctionalLevel();
                cd = ability4.getCooldownMilliseconds() + map4.get("CDPerLvl").longValue() * level4;
                dur = map4.get("Dur").longValue() + map4.get("DurPerLvl").longValue() * level4;
                durTicks = (int)(dur * 20.0) * 2;
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.NAUSEA, durTicks, 2, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.WITHER, durTicks, 1, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.FIRE_RESISTANCE, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.RESISTANCE, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.HUNGER, durTicks, 1, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.INSTANT_HEALTH, durTicks, 2, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.ABSORPTION, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.JUMP_BOOST, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.SPEED, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.STRENGTH, durTicks, 3, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.NIGHT_VISION, durTicks, 0, true, false);
                PlayerHelper.addEffect((EntityLivingBase)player4, MobEffects.WATER_BREATHING, durTicks, 0, true, false);
                cooldown.use(cd);
                data.player.playSound(SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }));
    }
}
