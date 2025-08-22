//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.spells.spells.necromancy.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Necromancy
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Necromancy", new String[] { "Life doesn't need things to live." }, "Necromancer", "rotten_flesh", 20, 1000L, new String[] { "bone", "rotten_flesh", "stick", "slime_ball", "magma_cream" }, new XPSource[] { new XPSource("CAST_XP", "Necromancy", 3.0), new XPSource("SPELL_XP", "Necromancy", 10.0) }, new Ability[] { new Ability("TOLL_THE_DEAD", "Damage=5 DamagePerLvl=0.01", 1L, 15000L, new DisplayInfo("Toll the Dead", "gold_ingot", 27, new String[] { "For whom the bell tolls?", "Deals damage to undead mobs in front of you.", "Improves as your level of Necromancy increases.", "", "Bindable. Click to choose a Necromancy item to bind this to." })).setBindable(), new Ability("TURN_UNDEAD", "Radius=7 RadiusPerLvl=0.02", 5L, 60000L, new DisplayInfo("Turn Undead", "rotten_flesh", 29, new String[] { "Can't fight them? Get them to leave instead!", "Undead mobs near you turn around and run away.", "Improves as your level of Necromancy increases.", "", "Bindable. Click to choose a Necromancy item to bind this to." })).setBindable(), new Ability("CREATE_UNDEAD", "", 25L, 60000L, new DisplayInfo("Create Undead", "bone", 31, new String[] { "Rise!", "Create a Zombie or Skeleton where you are looking.", "", "Bindable. Click to choose a Necromancy item to bind this to." })).setBindable(), new Ability("AURA_OF_DEATH", "", 50L, 0L, new DisplayInfo("Aura of Death", "iron_helmet", 33, new String[] { "With friends like these...", "Undead mobs no longer target you.", "", "Passive ability." })), new Ability("RAISE_ARMY", "Count=10 CountPerLvl=0.02", 100L, 1800000L, new DisplayInfo("Raise Army", "stone_sword", 35, new String[] { "Minions, servants, soldiers of the cold dark!", "Create many Zombies and Skeletons near you.", "Improves as your level of Necromancy increases.", "", "Bindable. Click to choose a Necromancy item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        float damage;
        HookRegistry.get().registerMethodHook("TOLL_THE_DEAD", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage").floatValue() + map.get("DamagePerLvl").floatValue() * level;
                    SpellEngine.get().castSpell(data, new TollTheDead(damage));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        EntityMob e;
        HookRegistry.get().registerMethodHook("AURA_OF_DEATH", new MethodHook<Event>((Class<? extends Event>)LivingSetAttackTargetEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement() && event.getEntityLiving() instanceof EntityMob) {
                e = (EntityMob)event.getEntityLiving();
                if (e.isEntityUndead()) {
                    e.setAttackTarget((EntityLivingBase)null);
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown2;
        EntityPlayerMP player;
        HashMap map2;
        long level2;
        double radius;
        final Iterator<EntityMob> iterator;
        EntityMob e2;
        double x;
        double z;
        HookRegistry.get().registerMethodHook("TURN_UNDEAD", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    player = data.player.getPlayerEntity();
                    map2 = AbilityHelper.getMap(ability3);
                    player.getServerWorld().playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.MASTER, 0.5f, 1.5f);
                    level2 = data.getTrueFunctionalLevel();
                    radius = map2.get("Radius") + map2.get("RadiusPerLvl") * level2;
                    player.getServerWorld().getEntitiesWithinAABB((Class)EntityMob.class, PlayerHelper.getAreaAround((Entity)player, radius)).iterator();
                    while (iterator.hasNext()) {
                        e2 = iterator.next();
                        if (e2.isEntityUndead()) {
                            x = e2.posX - player.posX;
                            z = e2.posZ - player.posZ;
                            e2.setAttackTarget((EntityLivingBase)null);
                            e2.getNavigator().tryMoveToXYZ(x * 3.0, e2.posY, z * 3.0, (double)(e2.getAIMoveSpeed() * 2.0f));
                        }
                    }
                    cooldown2.use(ability3.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        HookRegistry.get().registerMethodHook("CREATE_UNDEAD", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new CreateUndead());
                    cooldown3.use(ability4.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        HashMap map3;
        long level3;
        int count;
        HookRegistry.get().registerMethodHook("RAISE_ARMY", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability5);
                    level3 = data.getTrueFunctionalLevel();
                    count = (int)(map3.get("Count") + map3.get("CountPerLvl") * level3);
                    SpellEngine.get().castSpell(data, new RaiseArmy(count));
                    cooldown4.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
