//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.event.internal.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.hooks.*;

public class Acrobatics
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Acrobatics", new String[] { "Gain experience by landing jumps from great heights." }, "Acrobat", "leather_boots", 0, 1000L, new String[] { "stick", "feather", "string", "iron_nugget", "slime_ball" }, new XPSource[] { new XPSource("FALL_XP", "min=3 cap=8 mult=3", 0.0), new XPSource("FLY_FALL_XP", "min=3 cap=8 mult=0.5", 0.0) }, new Ability[] { new Ability("ACROBAT", "Damage=3.5 DamagePerLvl=0.0065", 5L, 0L, new DisplayInfo("Acrobat", "feather", 28, new String[] { "Breaking your fall with your fall sometimes works.", "You can negate some fall damage.", "Improves as your level of Acrobatics increases.", "", "Passive ability." })), new Ability("RESILIENCE", "Redux=0.025 ReduxPerLvl=0.0001", 50L, 0L, new DisplayInfo("Resilience", "iron_boots", 30, new String[] { "Endured physical strain has left you hardened to the world around you.", "You take reduced damage, and you cannot be affected by Poison.", "Improves as your level of Acrobatics increases.", "", "Passive ability." })), new Ability("LONG_JUMP", "Mult=1.5 MultPerLvl=0.002 CDPerLvl=-20", 100L, 30000L, new DisplayInfo("Long Jump", "rabbit_foot", 32, new String[] { "Your leaps can bridge continents.", "You can long jump.", "Improves as your level of Acrobatics increases.", "", "Bindable. Click to choose an Acrobatics item to bind this to." })).setBindable(), new Ability("HIGH_JUMP", "Mult=1.5 MultPerLvl=0.0015 CDPerLvl=-20", 100L, 30000L, new DisplayInfo("High Jump", "elytra", 34, new String[] { "Your leaps can reach space.", "You can high jump.", "Improves as your level of Acrobatics increases.", "", "Bindable. Click to choose an Acrobatics item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        Ability ability;
        HashMap map;
        long level;
        double damage;
        HookRegistry.get().registerMethodHook("ACROBAT", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability = (Ability)data.hookable;
                if (event.getSource() == DamageSource.FALL && data.getTrueLevel() >= ability.getLevelRequirement()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage") + level * map.get("DamagePerLvl");
                    event.setAmount(Math.max(0.0f, event.getAmount() - (float)damage));
                }
            }
            return;
        }));
        Ability ability2;
        HashMap map2;
        long level2;
        double redux;
        HookRegistry.get().registerMethodHook("RESILIENCE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability2 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    redux = map2.get("Redux") + level2 * map2.get("ReduxPerLvl");
                    event.setAmount(event.getAmount() - event.getAmount() * (float)redux);
                }
            }
            return;
        }));
        Ability ability3;
        HookRegistry.get().registerMethodHook("RESILIENCE", new MethodHook<Event>((Class<? extends Event>)PotionEvent.PotionApplicableEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability3 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability3.getLevelRequirement() && event.getPotionEffect().getPotion() == MobEffects.POISON) {
                    event.setResult(Event.Result.DENY);
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown;
        HashMap map3;
        long level3;
        long cd;
        double multiplier;
        EntityPlayerMP entity;
        EntityPlayerMP entity2;
        EntityPlayerMP entity3;
        HookRegistry.get().registerMethodHook("LONG_JUMP", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown() && (event.entity.motionX != 0.0 || event.entity.motionZ != 0.0)) {
                    map3 = AbilityHelper.getMap(ability4);
                    level3 = data.getTrueFunctionalLevel();
                    cd = ability4.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level3;
                    multiplier = map3.get("Mult") + map3.get("MultPerLvl") * level3;
                    entity = event.entity;
                    entity.motionX *= multiplier;
                    entity2 = event.entity;
                    entity2.motionZ *= multiplier;
                    entity3 = event.entity;
                    entity3.motionY *= multiplier / 2.0;
                    event.entity.velocityChanged = true;
                    cooldown.use(cd);
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown2;
        HashMap map4;
        long level4;
        long cd2;
        double multiplier2;
        EntityPlayerMP entity4;
        HookRegistry.get().registerMethodHook("HIGH_JUMP", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown() && event.entity.motionX == 0.0 && event.entity.motionZ == 0.0) {
                    map4 = AbilityHelper.getMap(ability5);
                    level4 = data.getTrueFunctionalLevel();
                    cd2 = ability5.getCooldownMilliseconds() + map4.get("CDPerLvl").longValue() * level4;
                    multiplier2 = map4.get("Mult") + map4.get("MultPerLvl") * level4;
                    entity4 = event.entity;
                    entity4.motionY *= multiplier2;
                    event.entity.velocityChanged = true;
                    cooldown2.use(cd2);
                }
            }
        }));
    }
}
