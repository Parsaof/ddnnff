//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.spells.spells.warlock.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Warlock
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Warlock", new String[] { "You've got your perfect warlock." }, "Warlock", "ender_eye", 23, 1000L, new String[] { "diamond", "emerald", "blaze_rod", "blaze_powder", "spider_eye", "book", "paper", "bone" }, new XPSource[] { new XPSource("CAST_XP", "Warlock", 3.0), new XPSource("SPELL_XP", "Warlock", 10.0) }, new Ability[] { new Ability("ELDRITCH_BLAST", "Damage=3 DamagePerLvl=0.005 Range=15 RangePerLvl=0.04 Speed=1 SpeedPerLvl=0.004", 1L, 6000L, new DisplayInfo("Eldritch Blast", "ender_eye", 27, new String[] { "Eldritch Blast.", "Shoot a blast of energy in the direction you are facing, which harms targets.", "Improves as your level of Warlock increases.", "Augmented by other Warlock abilities.", "", "Bindable. Click to choose a Warlock item to bind this to." })).setBindable(), new Ability("GRASP_OF_HADAR", "", 5L, 0L, new DisplayInfo("Grasp of Hadar", "ender_eye", 29, new String[] { "Get over here!", "Eldritch Blast pulls targets towards you.", "", "Passive ability." })), new Ability("LANCE_OF_LETHARGY", "Dur=40 DurPerLvl=0.16", 10L, 0L, new DisplayInfo("Lance of Lethargy", "ender_eye", 31, new String[] { "Take it nice and slow.", "Eldritch Blast inflicts Slow.", "Improves as your level of Warlock increases.", "", "Passive ability." })), new Ability("ELDRITCH_SPEAR", "", 20L, 0L, new DisplayInfo("Eldritch Spear", "ender_eye", 33, new String[] { "Snipin's a good job, mate.", "Eldritch Blast has triple range.", "", "Passive ability." })), new Ability("AGONIZING_BLAST", "Dur=40 DurPerLvl=0.16", 40L, 0L, new DisplayInfo("Agonizing Blast", "ender_eye", 35, new String[] { "It's always good to hurt more.", "Eldritch Blast inflicts Wither.", "Improves as your level of Warlock increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        double damage;
        double range;
        double speed;
        HookRegistry.get().registerMethodHook("ELDRITCH_BLAST", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage") + map.get("DamagePerLvl") * level;
                    range = map.get("Range") + map.get("RangePerLvl") * level;
                    speed = map.get("Speed") + map.get("SpeedPerLvl") * level;
                    SpellEngine.get().castSpell(data, new EldritchBlast(damage, range, speed));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown2;
        HookRegistry.get().registerMethodHook("GRASP_OF_HADAR", new MethodHook<Event>((Class<? extends Event>)EldritchBlastEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    event.eldritchBlast.grasp = true;
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        HashMap map2;
        long level2;
        HookRegistry.get().registerMethodHook("LANCE_OF_LETHARGY", new MethodHook<Event>((Class<? extends Event>)EldritchBlastEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map2 = AbilityHelper.getMap(ability3);
                    level2 = data.getTrueFunctionalLevel();
                    event.eldritchBlast.slow = (int)(map2.get("Dur") + map2.get("DurPerLvl") * level2);
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        EldritchBlast eldritchBlast;
        HookRegistry.get().registerMethodHook("ELDRITCH_SPEAR", new MethodHook<Event>((Class<? extends Event>)EldritchBlastEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    eldritchBlast = event.eldritchBlast;
                    eldritchBlast.range *= 3.0;
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        HashMap map3;
        long level3;
        HookRegistry.get().registerMethodHook("AGONIZING_BLAST", new MethodHook<Event>((Class<? extends Event>)EldritchBlastEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability5);
                    level3 = data.getTrueFunctionalLevel();
                    event.eldritchBlast.wither = (int)(map3.get("Dur") + map3.get("DurPerLvl") * level3);
                }
            }
        }));
    }
}
