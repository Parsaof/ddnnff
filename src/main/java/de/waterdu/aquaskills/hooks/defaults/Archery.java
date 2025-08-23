//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.spells.archery.*;
import de.waterdu.aquaskills.spells.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.hooks.*;

public class Archery
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Archery", new String[] { "Gain experience by landing shots with your bow on your foes." }, "Archer", "bow", 1, 1000L, new String[] { "arrow", "bow" }, new XPSource[] { new XPSource("ATTACK_XP", "2.5 bow", 0.0) }, new Ability[] { new Ability("SHARPSHOOTER", "Damage=1 DamagePerLvl=0.007", 5L, 0L, new DisplayInfo("Sharpshooter", "arrow", 27, new String[] { "Practice makes perfect.", "Your arrows deal more damage.", "Improves as your level of Archery increases.", "", "Passive ability." })), new Ability("HUNTERS_MARK", "Dur=100 DurPerLvl=0.5", 25L, 0L, new DisplayInfo("Hunter's Mark", "arrow", 29, new String[] { "Always track your prey.", "Inflicts Weakness on someone in front of you.", "Improves as your level of Archery increases.", "", "Bindable. Click to choose an Archery item to bind this to." })).setBindable(), new Ability("SIMPLE_GEOMETRY", "CDPerLvl=-40", 50L, 60000L, new DisplayInfo("Simple Geometry", "paper", 31, new String[] { "Your arrows always find their mark.", "Fires five arrows in a line instead of one.", "Improves as your level of Archery increases.", "", "Activate by sneaking and firing a bow." })), new Ability("POISONED_ARROW", "Chance=0.01 ChancePerLvl=0.00004 Dur=4 DurPerLvl=0.006", 75L, 30000L, new DisplayInfo("Poisoned Arrow", "spider_eye", 33, new String[] { "Arrows are the perfect delivery system for a unique blend of toxins.", "Your arrows occasionally cause poison damage and slowness.", "Improves as your level of Archery increases.", "", "Passive ability." })), new Ability("RAPID_FIRE", "Mult=0.1 MultPerLvl=0.0005", 125L, 0L, new DisplayInfo("Rapid Fire", "tipped_arrow", 35, new String[] { "Nock, draw, loose. Nock, draw, loose. Nock, draw, loose.", "Allows you to shoot fully charged arrows with less drawback.", "Improves as your level of Archery increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        Ability ability;
        HashMap map;
        long level;
        double damage;
        HookRegistry.get().registerMethodHook("SHARPSHOOTER", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID()) && event.getSource().damageType.equalsIgnoreCase("arrow")) {
                ability = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage") + level * map.get("DamagePerLvl");
                    event.setAmount(event.getAmount() + (float)damage);
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown;
        EntityPlayerMP player;
        HashMap map2;
        long level2;
        long cd;
        int i;
        float yaw;
        EntityArrow arrow;
        HookRegistry.get().registerMethodHook("SIMPLE_GEOMETRY", new MethodHook<Event>((Class<? extends Event>)ArrowLooseEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    player = data.player.getPlayerEntity();
                    if (player.isSneaking()) {
                        map2 = AbilityHelper.getMap(ability2);
                        level2 = data.getTrueFunctionalLevel();
                        cd = ability2.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                        cooldown.use(cd);
                        for (i = 0; i < 4; ++i) {
                            if (i == 0) {
                                yaw = -20.0f;
                            }
                            else if (i == 1) {
                                yaw = -10.0f;
                            }
                            else if (i == 2) {
                                yaw = 10.0f;
                            }
                            else {
                                yaw = 20.0f;
                            }
                            arrow = ((ItemArrow)Items.ARROW).createArrow((World)player.getServerWorld(), new ItemStack(Items.ARROW), (EntityLivingBase)player);
                            arrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                            arrow.shoot((Entity)player, player.rotationPitch, player.rotationYaw + yaw, 0.0f, ItemBow.getArrowVelocity(event.getCharge()) * 3.0f, 1.0f);
                            player.getServerWorld().spawnEntity((Entity)arrow);
                            data.player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.7f, 1.0f);
                        }
                    }
                }
            }
            return;
        }));
        Ability ability3;
        Cooldown cooldown2;
        HashMap map3;
        long level3;
        double chance;
        double dur;
        int durTicks;
        HookRegistry.get().registerMethodHook("POISONED_ARROW", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability3 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                    cooldown2 = data.getCooldownSafely();
                    if (!cooldown2.isOnCooldown() && event.getSource().damageType.equalsIgnoreCase("arrow")) {
                        map3 = AbilityHelper.getMap(ability3);
                        level3 = data.getTrueFunctionalLevel();
                        chance = map3.get("Chance") + map3.get("ChancePerLvl") * level3;
                        if (RandomHelper.nextDouble() < chance) {
                            dur = map3.get("Dur") + map3.get("DurPerLvl") * level3;
                            durTicks = (int)(dur * 20.0);
                            PlayerHelper.addEffect(event.getEntityLiving(), MobEffects.POISON, durTicks, 1);
                            PlayerHelper.addEffect(event.getEntityLiving(), MobEffects.SLOWNESS, durTicks, 1);
                            cooldown2.use(ability3.getCooldownMilliseconds());
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        HashMap map4;
        long level4;
        double multiplier;
        HookRegistry.get().registerMethodHook("RAPID_FIRE", new MethodHook<Event>((Class<? extends Event>)ArrowLooseEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                map4 = AbilityHelper.getMap(ability4);
                level4 = data.getTrueFunctionalLevel();
                multiplier = map4.get("Mult") + map4.get("MultPerLvl") * level4;
                event.setCharge(event.getCharge() + (int)(event.getCharge() * multiplier));
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown3;
        HashMap map5;
        long level5;
        int dur2;
        HookRegistry.get().registerMethodHook("HUNTERS_MARK", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map5 = AbilityHelper.getMap(ability5);
                    level5 = data.getTrueFunctionalLevel();
                    dur2 = (int)(map5.get("Dur") + map5.get("DurPerLvl") * level5);
                    SpellEngine.get().castSpell(data, new HuntersMark(dur2));
                    cooldown3.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
