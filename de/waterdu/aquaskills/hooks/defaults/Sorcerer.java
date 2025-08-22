//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.spells.spells.sorcerer.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import de.waterdu.aquaskills.hooks.*;

public class Sorcerer
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Sorcerer", new String[] { "Control the world around you by tapping into the arcane." }, "Sorcerer", "diamond", 22, 1000L, new String[] { "diamond", "emerald", "blaze_powder" }, new XPSource[] { new XPSource("CAST_XP", "Sorcerer", 3.0), new XPSource("SPELL_XP", "Sorcerer", 10.0) }, new Ability[] { new Ability("MIND_SLIVER", "Damage=1 DamagePerLvl=0.015 Range=10 RangePerLvl=0.02 Speed=0.5 SpeedPerLvl=0.0075 NauseaTicks=60 NauseaTicksPerLvl=0.1", 1L, 15000L, new DisplayInfo("Mind Sliver", "arrow", 27, new String[] { "The biggest weakness is someone's mind.", "Shoot a small mote of psi in the direction you are facing, which causes nausea in targets.", "Improves as your level of Sorcerer increases.", "", "Bindable. Click to choose a Sorcerer item to bind this to." })).setBindable(), new Ability("MAGE_ARMOR", "Dur=100 DurPerLvl=0.5", 15L, 120000L, new DisplayInfo("Mage Armor", "diamond_chestplate", 29, new String[] { "Magic can be as good as metal, when used properly.", "Gain resistance for a short while.", "Improves as your level of Sorcerer increases.", "", "Bindable. Click to choose a Sorcerer item to bind this to." })).setBindable(), new Ability("LIGHTNING_BOLT", "", 40L, 120000L, new DisplayInfo("Lightning Bolt", "redstone", 31, new String[] { "It's feeling electric in here.", "Summon lightning where you are looking.", "", "Bindable. Click to choose a Sorcerer item to bind this to." })).setBindable(), new Ability("WALL_OF_FIRE", "Dur=100 DurPerLvl=0.5 FireTicks=4 FireTicksPerLvl=0.05 Radius=3 RadiusPerLvl=0.005", 75L, 120000L, new DisplayInfo("Wall of Fire", "fire_charge", 33, new String[] { "Hot hot hot!", "Summon a pillar of fire in front of you.", "", "Bindable. Click to choose a Sorcerer item to bind this to." })).setBindable(), new Ability("PLANE_SHIFT", "0 -1 1", 150L, 600000L, new DisplayInfo("Plane Shift", "ender_pearl", 35, new String[] { "Going up, going down.", "Change dimension. No guarantee of safety!", "", "Bindable. Click to choose a Sorcerer item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        double damage;
        double range;
        double speed;
        int nausea;
        HookRegistry.get().registerMethodHook("MIND_SLIVER", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    damage = map.get("Damage") + map.get("DamagePerLvl") * level;
                    range = map.get("Range") + map.get("RangePerLvl") * level;
                    speed = map.get("Speed") + map.get("SpeedPerLvl") * level;
                    nausea = (int)(map.get("NauseaTicks") + map.get("NauseaTicksPerLvl") * level);
                    SpellEngine.get().castSpell(data, new MindSliver(damage, range, speed, nausea));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown2;
        EntityPlayerMP player2;
        HashMap map2;
        long level2;
        int dur;
        HookRegistry.get().registerMethodHook("MAGE_ARMOR", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    player2 = data.player.getPlayerEntity();
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    dur = (int)(map2.get("Dur") + map2.get("DurPerLvl") * level2);
                    ParticleHelper.drawParticleCloud(30, EnumParticleTypes.SPELL_MOB, player2.getServerWorld(), player2.posX, player2.posY, player2.posZ, 3, 207, 252);
                    player2.getServerWorld().playSound((EntityPlayer)null, player2.posX, player2.posY, player2.posZ, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundCategory.PLAYERS, 1.0f, 1.2f);
                    PlayerHelper.addEffect((EntityLivingBase)player2, MobEffects.RESISTANCE, dur, 1);
                    cooldown2.use(ability2.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        HookRegistry.get().registerMethodHook("LIGHTNING_BOLT", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new LightningBolt());
                    cooldown3.use(ability3.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        HashMap map3;
        long level3;
        int dur2;
        int fire;
        double radius;
        HookRegistry.get().registerMethodHook("WALL_OF_FIRE", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability4);
                    level3 = data.getTrueFunctionalLevel();
                    dur2 = (int)(map3.get("Dur") + map3.get("DurPerLvl") * level3);
                    fire = (int)(map3.get("FireTicks") + map3.get("FireTicksPerLvl") * level3);
                    radius = map3.get("Radius") + map3.get("RadiusPerLvl") * level3;
                    SpellEngine.get().castSpell(data, new WallOfFire(dur2, fire, radius));
                    cooldown4.use(ability4.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown5;
        final Ability ability6;
        String[] args;
        ArrayList<Integer> dims;
        int i;
        int home;
        ArrayList<Integer> remote;
        WorldServer world;
        int j;
        int dim;
        HookRegistry.get().registerMethodHook("PLANE_SHIFT", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    data.ifPlayerPresent(player -> {
                        if (!ability6.areArgsParsed()) {
                            args = ability6.getArgs().split(" ");
                            ability6.getParsedArgs().add(Integer.parseInt(args[0]));
                            dims = new ArrayList<Integer>();
                            for (i = 1; i < args.length; ++i) {
                                dims.add(Integer.parseInt(args[i]));
                            }
                            ability6.getParsedArgs().add(dims);
                        }
                        home = ability6.getParsedArgs().get(0);
                        remote = ability6.getParsedArgs().get(1);
                        world = player.getServerWorld();
                        world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 1.0f, 1.0f);
                        for (j = 0; j < 100; ++j) {
                            world.spawnParticle(EnumParticleTypes.PORTAL, false, player.posX + world.rand.nextDouble() * 2.0 - 1.0, player.posY + world.rand.nextDouble() * 2.0, player.posZ + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
                        }
                        if (player.dimension == home) {
                            dim = (int)RandomHelper.getRandomElementFromCollection((Collection)remote);
                        }
                        else {
                            dim = home;
                        }
                        PlayerHelper.teleport(player, dim, player.posX, player.posY, player.posZ);
                        return;
                    });
                    cooldown5.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
