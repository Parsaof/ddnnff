//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.spells.cleric.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.world.*;
import de.waterdu.aquaskills.file.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Cleric
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Cleric", new String[] { "A little healing goes a long way." }, "Cleric", "apple", 19, 1000L, new String[] { "iron_sword", "stone_sword", "emerald", "diamond", "paper", "sugar", "totem_of_undying" }, new XPSource[] { new XPSource("CAST_XP", "Cleric", 3.0), new XPSource("SPELL_XP", "Cleric", 10.0) }, new Ability[] { new Ability("MENDING", "Repair=1 RepairPerLvl=0.02", 1L, 5000L, new DisplayInfo("Mending", "anvil", 27, new String[] { "It's a band aid for your armor.", "Repair your armor a little.", "Improves as your level of Cleric increases.", "", "Bindable. Click to choose a Cleric item to bind this to." })).setBindable(), new Ability("CURE_WOUNDS", "Heal=3 HealPerLvl=0.007", 5L, 30000L, new DisplayInfo("Cure Wounds", "apple", 29, new String[] { "A magical heal a day keeps the doctor away.", "Heal whatever is in front of you, or yourself if nothing is there.", "Improves as your level of Cleric increases.", "", "Bindable. Click to choose a Cleric item to bind this to." })).setBindable(), new Ability("AURA_OF_LIFE", "Radius=5 RadiusPerLvl=0.02", 25L, 60000L, new DisplayInfo("Aura of Life", "golden_apple", 31, new String[] { "A light in the darkness.", "Those around you cannot be affected by Hunger or Wither.", "Improves as your level of Cleric increases.", "", "Passive ability." })), new Ability("MASS_HEAL", "Radius=5 RadiusPerLvl=0.02", 50L, 1800000L, new DisplayInfo("Mass Heal", "totem_of_undying", 33, new String[] { "Heroes never die.", "Restore all those around you to full health and food.", "Improves as your level of Cleric increases.", "", "Bindable. Click to choose a Cleric item to bind this to." })).setBindable(), new Ability("WORD_OF_RECALL", "", 100L, 1800000L, new DisplayInfo("Word of Recall", "bed", 35, new String[] { "Take me home, country roads.", "Set a place of recall, after which you can teleport to.", "", "Bindable. Click to choose a Cleric item to bind this to.", "Use once to set location. Subsequent uses will take you there." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        float heal;
        HookRegistry.get().registerMethodHook("CURE_WOUNDS", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    heal = map.get("Heal").floatValue() + map.get("HealPerLvl").floatValue() * level;
                    SpellEngine.get().castSpell(data, new CureWounds(heal));
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        EntityPlayerMP player2;
        HashMap map2;
        long level2;
        double radius;
        final Iterator<EntityPlayerMP> iterator;
        EntityPlayerMP p;
        HookRegistry.get().registerMethodHook("AURA_OF_LIFE", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                player2 = data.player.getPlayerEntity();
                if (TimeHelper.isSecond()) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    radius = map2.get("Radius") + map2.get("RadiusPerLvl") * level2;
                    player2.getServerWorld().getEntitiesWithinAABB((Class)EntityPlayerMP.class, PlayerHelper.getAreaAround((Entity)player2, radius)).iterator();
                    while (iterator.hasNext()) {
                        p = iterator.next();
                        p.removePotionEffect(MobEffects.HUNGER);
                        p.removePotionEffect(MobEffects.WITHER);
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown2;
        EntityPlayerMP player3;
        HashMap map3;
        long level3;
        double radius2;
        final Iterator<EntityLivingBase> iterator2;
        EntityLivingBase e;
        EntityPlayerMP p2;
        HookRegistry.get().registerMethodHook("MASS_HEAL", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    player3 = data.player.getPlayerEntity();
                    map3 = AbilityHelper.getMap(ability3);
                    level3 = data.getTrueFunctionalLevel();
                    radius2 = map3.get("Radius") + map3.get("RadiusPerLvl") * level3;
                    player3.getServerWorld().getEntitiesWithinAABB((Class)EntityLivingBase.class, PlayerHelper.getAreaAround((Entity)player3, radius2)).iterator();
                    while (iterator2.hasNext()) {
                        e = iterator2.next();
                        e.heal(e.getMaxHealth());
                        if (e instanceof EntityPlayerMP) {
                            p2 = (EntityPlayerMP)e;
                            p2.getFoodStats().addStats(20, 20.0f);
                        }
                        ParticleHelper.drawParticleCloud(30, EnumParticleTypes.SPELL_MOB, player3.getServerWorld(), e.posX, e.posY, e.posZ, 227, 198, 120);
                    }
                    cooldown2.use(ability3.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        HashMap map4;
        long level4;
        int repair;
        final Iterator<ItemStack> iterator3;
        ItemStack stack;
        final int n;
        HookRegistry.get().registerMethodHook("MENDING", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map4 = AbilityHelper.getMap(ability4);
                    level4 = data.getTrueFunctionalLevel();
                    repair = (int)(map4.get("Repair") + map4.get("RepairPerLvl") * level4);
                    data.ifPlayerPresent(player -> {
                        player.inventory.armorInventory.iterator();
                        while (iterator3.hasNext()) {
                            stack = iterator3.next();
                            if (!stack.isEmpty()) {
                                stack.setItemDamage(Math.max(0, stack.getItemDamage() - n));
                            }
                        }
                        ParticleHelper.drawParticleCloud(50, EnumParticleTypes.CRIT_MAGIC, player.getServerWorld(), player.posX, player.posY, player.posZ, 0.1);
                        player.getServerWorld().playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        return;
                    });
                    cooldown3.use(ability4.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        WorldServer world;
        AbilityInfo info;
        ArrayList integers;
        final Cooldown cooldown5;
        final Ability ability6;
        int d;
        int x;
        int y;
        int z;
        int i;
        int j;
        HookRegistry.get().registerMethodHook("WORD_OF_RECALL", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    data.ifPlayerPresent(player -> {
                        world = player.getServerWorld();
                        info = data.player.getPersistentInfo("WORD_OF_RECALL");
                        integers = info.getIntegers();
                        if (integers.isEmpty()) {
                            player.sendMessage((ITextComponent)new TextComponentString(Config.ability("WORD_OF_RECALL1", new Object[0])));
                            integers.add(world.provider.getDimension());
                            integers.add(player.getPosition().getX());
                            integers.add(player.getPosition().getY());
                            integers.add(player.getPosition().getZ());
                            cooldown5.use(ability6.getCooldownMilliseconds());
                        }
                        else {
                            d = integers.get(0);
                            if (world.provider.getDimension() == d) {
                                x = integers.get(1);
                                y = integers.get(2);
                                z = integers.get(3);
                                world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 1.0f, 1.0f);
                                for (i = 0; i < 100; ++i) {
                                    world.spawnParticle(EnumParticleTypes.PORTAL, false, player.posX + world.rand.nextDouble() * 2.0 - 1.0, player.posY + world.rand.nextDouble() * 2.0, player.posZ + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
                                }
                                player.connection.setPlayerLocation((double)x, y + 1.5, (double)z, player.rotationYaw, player.rotationPitch);
                                world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 1.0f, 1.0f);
                                for (j = 0; j < 100; ++j) {
                                    world.spawnParticle(EnumParticleTypes.PORTAL, false, x + world.rand.nextDouble() * 2.0 - 1.0, y + 1 + world.rand.nextDouble() * 2.0, z + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
                                }
                                cooldown5.use(ability6.getCooldownMilliseconds());
                            }
                            else {
                                player.sendMessage((ITextComponent)new TextComponentString(Config.ability("WORD_OF_RECALL2", new Object[0])));
                            }
                        }
                    });
                }
            }
        }));
    }
}
