//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import de.waterdu.aquaskills.hooks.*;

public class MartialArts
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Martial Arts", new String[] { "Gain experience through the practice of unarmed or wooden combat." }, "Monk", "stick", 11, 1000L, new String[] { "wooden_sword", "stick" }, new XPSource[] { new XPSource("ATTACK_XP", "3.5 unarmed", 0.0), new XPSource("ATTACK_XP", "2.5 monk", 0.0) }, new Ability[] { new Ability("UNARMED_STRIKE", "Damage=1 DamagePerLvl=0.007", 5L, 0L, new DisplayInfo("Unarmed Strike", "bone", 28, new String[] { "Monastic teachings teaches you defense in absence of weaponry.", "Your unarmed attacks deal more damage.", "Improves as your level of Martial Arts increases.", "", "Passive ability." })), new Ability("FLURRY_OF_BLOWS", "Chance=0.1 ChancePerLvl=0.0003 Damage=1 DamagePerLvl=0.007", 25L, 3000L, new DisplayInfo("Flurry of Blows", "pixelmon:lucky_punch", 30, new String[] { "Striking with your weapon leaves opponents vulnerable for a jab from the other side.", "Occasionally attacks with your empty offhand after attacking with a Martial Arts weapon.", "Improves as your level of Martial Arts increases.", "", "Passive ability." })), new Ability("STUNNING_STRIKE", "CDPerLvl=-30 Dur=7 DurPerLvl=0.008", 50L, 60000L, new DisplayInfo("Stunning Strike", "pixelmon:kings_rock", 32, new String[] { "You have learnt the location of certain pressure points, allowing your strikes to disorient your foes.", "You disorient those you attack with a Martial Arts weapon.", "Improves as your level of Martial Arts increases.", "", "Activate by landing a hit with a Martial Arts weapon." })), new Ability("DEFLECT_MISSILES", "", 100L, 7500L, new DisplayInfo("Deflect Missiles", "arrow", 34, new String[] { "Your keen monk perception allows you to return the favor when your are under arrow fire.", "Arrows reflect back at their source while holding a Martial Arts weapon.", "", "Activates whenever it can." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        Ability ability;
        HashMap map;
        long level;
        double damage;
        HookRegistry.get().registerMethodHook("UNARMED_STRIKE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID()) && data.player.getPlayerEntity().getHeldItemMainhand().isEmpty() && !event.getSource().isExplosion() && !event.getSource().isProjectile() && !event.getSource().isMagicDamage() && !event.getSource().isFireDamage()) {
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
        Ability ability2;
        Cooldown cooldown;
        EntityPlayerMP player;
        HashMap map2;
        long level2;
        double chance;
        double damage2;
        HookRegistry.get().registerMethodHook("FLURRY_OF_BLOWS", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability2 = (Ability)data.hookable;
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    player = data.player.getPlayerEntity();
                    if (player.getHeldItemOffhand().isEmpty() && !event.getSource().isExplosion() && !event.getSource().isProjectile() && !event.getSource().isMagicDamage() && !event.getSource().isFireDamage() && (player.getHeldItemMainhand().isEmpty() || player.getHeldItemMainhand().getItem() == Items.STICK || player.getHeldItemMainhand().getItem() == Items.WOODEN_SWORD) && data.getTrueLevel() >= ability2.getLevelRequirement()) {
                        map2 = AbilityHelper.getMap(ability2);
                        level2 = data.getTrueFunctionalLevel();
                        chance = map2.get("Chance") + level2 * map2.get("ChancePerLvl");
                        if (RandomHelper.nextDouble() < chance) {
                            damage2 = map2.get("Damage") + level2 * map2.get("DamagePerLvl");
                            event.setAmount(event.getAmount() + (float)damage2);
                            cooldown.use(ability2.getCooldownMilliseconds());
                            data.player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 0.7f, 1.0f);
                        }
                    }
                }
            }
            return;
        }));
        Ability ability3;
        Cooldown cooldown2;
        EntityPlayerMP player2;
        HashMap map3;
        long level3;
        double dur;
        int durTicks;
        long cd;
        HookRegistry.get().registerMethodHook("STUNNING_STRIKE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability3 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                    cooldown2 = data.getCooldownSafely();
                    if (!cooldown2.isOnCooldown() && !event.getSource().isExplosion() && !event.getSource().isProjectile() && !event.getSource().isMagicDamage() && !event.getSource().isFireDamage()) {
                        player2 = data.player.getPlayerEntity();
                        if (player2.getHeldItemMainhand().isEmpty() || player2.getHeldItemMainhand().getItem() == Items.STICK || player2.getHeldItemMainhand().getItem() == Items.WOODEN_SWORD) {
                            map3 = AbilityHelper.getMap(ability3);
                            level3 = data.getTrueFunctionalLevel();
                            dur = map3.get("Dur") + map3.get("DurPerLvl") * level3;
                            durTicks = (int)(dur * 20.0);
                            PlayerHelper.addEffect(event.getEntityLiving(), MobEffects.NAUSEA, durTicks, 4, true, false);
                            PlayerHelper.addEffect(event.getEntityLiving(), MobEffects.SLOWNESS, durTicks, 4, true, false);
                            event.getEntityLiving().rotationPitch = 180.0f;
                            cd = ability3.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level3;
                            cooldown2.use(cd);
                            data.player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0f, 0.7f);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        EntityPlayerMP player3;
        Cooldown cooldown3;
        EntityArrow arrow;
        HookRegistry.get().registerMethodHook("DEFLECT_MISSILES", new MethodHook<Event>((Class<? extends Event>)ProjectileImpactEvent.Arrow.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                player3 = data.player.getPlayerEntity();
                if (player3.getHeldItemMainhand().isEmpty() || player3.getHeldItemMainhand().getItem() == Items.STICK || player3.getHeldItemMainhand().getItem() == Items.WOODEN_SWORD) {
                    cooldown3 = data.getCooldownSafely();
                    if (!cooldown3.isOnCooldown()) {
                        arrow = ((ItemArrow)Items.ARROW).createArrow((World)player3.getServerWorld(), new ItemStack(Items.ARROW), (EntityLivingBase)player3);
                        arrow.shoot((Entity)player3, player3.rotationPitch, player3.rotationYaw, 0.0f, 2.0f, 1.0f);
                        player3.getServerWorld().spawnEntity((Entity)arrow);
                        cooldown3.use(ability4.getCooldownMilliseconds());
                        event.setCanceled(true);
                        data.player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5f, 1.0f);
                    }
                }
            }
        }));
    }
}
