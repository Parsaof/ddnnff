//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.spells.spells.wizard.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import de.waterdu.aquaskills.hooks.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class Wizard
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Wizard", new String[] { "Control the world around you by tapping into the arcane." }, "Wizard", "fire_charge", 24, 1000L, new String[] { "stick", "diamond", "emerald", "blaze_rod", "blaze_powder", "fire_charge", "flint" }, new XPSource[] { new XPSource("CAST_XP", "Wizard", 3.0), new XPSource("SPELL_XP", "Wizard", 10.0) }, new Ability[] { new Ability("FIRE_BOLT", "Damage=1 DamagePerLvl=0.02 Range=6 RangePerLvl=0.02 Speed=0.2 SpeedPerLvl=0.004 FireTicks=1 FireTicksPerLvl=0.02", 1L, 15000L, new DisplayInfo("Fire Bolt", "flint_and_steel", 27, new String[] { "The most elementary spell.", "Shoot a small mote of fire in the direction you are facing, which ignites targets.", "Improves as your level of Wizard increases.", "", "Bindable. Click to choose a Wizard item to bind this to." })).setBindable(), new Ability("INVISIBILITY", "Dur=100 DurPerLvl=0.5", 15L, 120000L, new DisplayInfo("Invisibility", "glass", 29, new String[] { "Passing unseen is usually beneficial.", "Become invisible for a short while.", "Improves as your level of Wizard increases.", "", "Bindable. Click to choose a Wizard item to bind this to." })).setBindable(), new Ability("FIREBALL", "Power=4 PowerPerLvl=0.02 Range=20 RangePerLvl=0.02 Speed=0.5 SpeedPerLvl=0.004", 40L, 60000L, new DisplayInfo("Fireball", "fire_charge", 31, new String[] { "What's better than Fire Bolt? A big Fire Bolt.", "Shoot a small mote of fire in the direction you are facing, which explodes on impact.", "Improves as your level of Wizard increases.", "", "Bindable. Click to choose a Wizard item to bind this to." })).setBindable(), new Ability("POLYMORPH", "", 75L, 90000L, new DisplayInfo("Polymorph", "wool", 33, new String[] { "Not so dangerous now?", "Transform a hostile mob in front of you into a random animal.", "", "Bindable. Click to choose a Wizard item to bind this to." })).setBindable(), new Ability("TELEPORT", "", 150L, 600000L, new DisplayInfo("Teleport", "ender_pearl", 35, new String[] { "Can't win? Just leave instead!", "Returns you to your spawn point.", "", "Bindable. Click to choose a Wizard item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        double damage;
        double range;
        double speed;
        int fire;
        HookRegistry.get().registerMethodHook("FIRE_BOLT", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage") + map.get("DamagePerLvl") * level;
                    range = map.get("Range") + map.get("RangePerLvl") * level;
                    speed = map.get("Speed") + map.get("SpeedPerLvl") * level;
                    fire = (int)(map.get("FireTicks") + map.get("FireTicksPerLvl") * level);
                    SpellEngine.get().castSpell(data, new FireBolt(damage, range, speed, fire));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown2;
        HashMap map2;
        long level2;
        double power;
        double range2;
        double speed2;
        HookRegistry.get().registerMethodHook("FIREBALL", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    power = map2.get("Power") + map2.get("PowerPerLvl") * level2;
                    range2 = map2.get("Range") + map2.get("RangePerLvl") * level2;
                    speed2 = map2.get("Speed") + map2.get("SpeedPerLvl") * level2;
                    SpellEngine.get().castSpell(data, new Fireball(power, range2, speed2));
                    cooldown2.use(ability2.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        HookRegistry.get().registerMethodHook("POLYMORPH", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new Polymorph());
                    cooldown3.use(ability3.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        HashMap map3;
        long level3;
        int dur;
        HookRegistry.get().registerMethodHook("INVISIBILITY", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability4);
                    level3 = data.getTrueFunctionalLevel();
                    dur = (int)(map3.get("Dur") + map3.get("DurPerLvl") * level3);
                    data.ifPlayerPresent(player -> {
                        PlayerHelper.addEffect(player, MobEffects.INVISIBILITY, dur, 0, true, false);
                        ((EntityPlayerMP)player).getServerWorld().playSound((EntityPlayer)null, ((EntityPlayerMP)player).posX, ((EntityPlayerMP)player).posY, ((EntityPlayerMP)player).posZ, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        return;
                    });
                    cooldown4.use(ability4.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        WorldServer world;
        BlockPos spawn;
        int i;
        int j;
        HookRegistry.get().registerMethodHook("TELEPORT", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    data.ifPlayerPresent(player -> {
                        world = player.getServerWorld();
                        spawn = player.getBedLocation();
                        if (spawn == null) {
                            spawn = world.getSpawnPoint();
                        }
                        world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 1.0f, 1.0f);
                        for (i = 0; i < 100; ++i) {
                            world.spawnParticle(EnumParticleTypes.PORTAL, false, player.posX + world.rand.nextDouble() * 2.0 - 1.0, player.posY + world.rand.nextDouble() * 2.0, player.posZ + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
                        }
                        player.connection.setPlayerLocation((double)spawn.getX(), spawn.getY() + 1.5, (double)spawn.getZ(), player.rotationYaw, player.rotationPitch);
                        world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 1.0f, 1.0f);
                        for (j = 0; j < 100; ++j) {
                            world.spawnParticle(EnumParticleTypes.PORTAL, false, spawn.getX() + world.rand.nextDouble() * 2.0 - 1.0, spawn.getY() + 1 + world.rand.nextDouble() * 2.0, spawn.getZ() + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
                        }
                        return;
                    });
                    cooldown5.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
