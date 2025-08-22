//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.monster.*;
import de.waterdu.aquaskills.spells.spells.paladin.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.hooks.*;

public class Paladin
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Paladin", new String[] { "Swordsmanship with added divinity." }, "Paladin", "golden_helmet", 21, 1000L, new String[] { "iron_sword", "golden_sword", "gold_ingot", "gold_nugget", "book" }, new XPSource[] { new XPSource("CAST_XP", "Paladin", 3.0), new XPSource("SPELL_XP", "Paladin", 10.0), new XPSource("ATTACK_XP", "1.5 sword", 0.0) }, new Ability[] { new Ability("HEROISM", "Absorption=2 AbsorptionPerLvl=0.019", 1L, 180000L, new DisplayInfo("Heroism", "golden_helmet", 27, new String[] { "Once more unto the breach!", "Give bonus health to whatever is in front of you, or yourself if nothing is there.", "Improves as your level of Paladin increases.", "", "Bindable. Click to choose a Paladin item to bind this to." })).setBindable(), new Ability("DIVINE_SMITE", "Mult=1.25 MultPerLvl=0.00075", 5L, 0L, new DisplayInfo("Divine Smite", "glowstone_dust", 29, new String[] { "The light purges!", "Your damage against undead is multiplied.", "Improves as your level of Paladin increases.", "", "Passive ability." })), new Ability("BANISHMENT", "", 25L, 180000L, new DisplayInfo("Banishment", "iron_bars", 31, new String[] { "Rather than defeating a monster, why not just remove it from the picture?", "Instantly remove a monster in front of you.", "", "Bindable. Click to choose a Paladin item to bind this to." })).setBindable(), new Ability("DEATH_WARD", "", 50L, 360000L, new DisplayInfo("Death Ward", "totem_of_undying", 33, new String[] { "Stayin' alive, stayin' alive.", "Prevents death.", "", "Activates whenever it can." })), new Ability("DIVINE_INTERVENTION", "", 100L, 1800000L, new DisplayInfo("Divine Intervention", "golden_chestplate", 35, new String[] { "This has to work sometimes, right?", "Restore all your health, gain temporary health, and gain increased speed for a short while.", "", "Bindable. Click to choose a Paladin item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        float absorption;
        HookRegistry.get().registerMethodHook("HEROISM", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    absorption = map.get("Absorption").floatValue() + map.get("AbsorptionPerLvl").floatValue() * level;
                    SpellEngine.get().castSpell(data, new Heroism(absorption));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        Ability ability2;
        HashMap map2;
        long level2;
        float damage;
        HookRegistry.get().registerMethodHook("DIVINE_SMITE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability2 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability2.getLevelRequirement() && event.getEntityLiving() instanceof EntityMob && event.getEntityLiving().isEntityUndead()) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    damage = map2.get("Mult").floatValue() + level2 * map2.get("MultPerLvl").floatValue();
                    event.setAmount(event.getAmount() * damage);
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown2;
        HookRegistry.get().registerMethodHook("BANISHMENT", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new Banishment());
                    cooldown2.use(ability3.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        EntityPlayerMP player;
        HookRegistry.get().registerMethodHook("DEATH_WARD", new MethodHook<Event>((Class<? extends Event>)LivingDamageEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    player = data.player.getPlayerEntity();
                    if (event.getEntityLiving().getUniqueID().equals(player.getUniqueID()) && player.getHealth() - event.getAmount() <= 0.0f) {
                        event.setAmount(0.0f);
                        player.heal(player.getMaxHealth() / 2.0f);
                        player.getServerWorld().playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        cooldown3.use(ability4.getCooldownMilliseconds());
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        EntityPlayerMP player2;
        HookRegistry.get().registerMethodHook("DIVINE_INTERVENTION", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    player2 = data.player.getPlayerEntity();
                    player2.getServerWorld().playSound((EntityPlayer)null, player2.posX, player2.posY, player2.posZ, SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 2.0f, 1.0f);
                    player2.getServerWorld().playSound((EntityPlayer)null, player2.posX, player2.posY, player2.posZ, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.PLAYERS, 0.6f, 1.5f);
                    ParticleHelper.drawParticleCloud(50, EnumParticleTypes.CRIT_MAGIC, player2.getServerWorld(), player2.posX, player2.posY, player2.posZ, 0.15);
                    player2.heal(player2.getMaxHealth());
                    player2.setAbsorptionAmount(player2.getMaxHealth());
                    PlayerHelper.addEffect((EntityLivingBase)player2, MobEffects.SPEED, 100, 1);
                    cooldown4.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
