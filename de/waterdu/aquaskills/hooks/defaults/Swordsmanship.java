//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.spells.swordsmanship.*;
import de.waterdu.aquaskills.spells.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.hooks.*;

public class Swordsmanship
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Swordsmanship", new String[] { "Gain experience by damaging foes with your sword." }, "Knight", "iron_sword", 15, 1000L, new String[] { "wooden_sword", "stone_sword", "iron_sword", "golden_sword", "diamond_sword" }, new XPSource[] { new XPSource("ATTACK_XP", "2.5 sword", 0.0) }, new Ability[] { new Ability("EN_GARDE", "Damage=1 DamagePerLvl=0.007", 5L, 0L, new DisplayInfo("En Garde", "stone_sword", 27, new String[] { "True knights are determined by their dedication to their craft.", "Your swords deal more damage.", "Improves as your level of Swordsmanship increases.", "", "Passive ability." })), new Ability("DUAL_WIELDING", "Damage=0.5 DamagePerLvl=0.003 Redux=0.05 ReduxPerLvl=0.0002", 25L, 0L, new DisplayInfo("Dual Wielding", "golden_sword", 29, new String[] { "If one sword is good, surely two is even better?", "Your swords deal more damage and you take less damage while holding two swords.", "Improves as your level of Swordsmanship increases.", "", "Passive ability." })), new Ability("BLADESINGING", "", 50L, 60000L, new DisplayInfo("Bladesinging", "ender_pearl", 31, new String[] { "What is that melody?", "Teleport a short distance where you are looking.", "", "Bindable. Click to choose a Swordsmanship item to bind this to." })).setBindable(), new Ability("ATTACK_OF_OPPORTUNITY", "Damage=10 DamagePerLvl=0.008 Dur=1 DurPerLvl=0.003", 75L, 60000L, new DisplayInfo("Attack of Opportunity", "diamond_sword", 33, new String[] { "Opponents trying to flee often leave themselves vulnerable and open.", "Deals massive damage to and stuns fleeing targets.", "Improves as your level of Swordsmanship increases.", "", "Passive ability." })), new Ability("PARRY", "Chance=0.01 ChancePerLvl=0.00004", 125L, 180000L, new DisplayInfo("Parry", "shield", 35, new String[] { "Your sword and arm are one, allowing you to throw adversaries' weapons to the ground.", "Occasionally disarms attackers' swords.", "Improves as your level of Swordsmanship increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final EntityPlayerMP player;
        Ability ability;
        HashMap map;
        long level;
        double damage;
        double redux;
        HookRegistry.get().registerMethodHook("DUAL_WIELDING", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            player = data.player.getPlayerEntity();
            if (player.getHeldItemMainhand().getItem() instanceof ItemSword && player.getHeldItemOffhand().getItem() instanceof ItemSword) {
                ability = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    if (!event.getEntityLiving().getUniqueID().equals(player.getUniqueID())) {
                        damage = map.get("Damage") + level * map.get("DamagePerLvl");
                        event.setAmount(event.getAmount() + (float)damage);
                    }
                    else {
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
        double damage2;
        HookRegistry.get().registerMethodHook("EN_GARDE", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                player2 = data.player.getPlayerEntity();
                if (player2.getHeldItemMainhand().getItem() instanceof ItemSword) {
                    ability2 = (Ability)data.hookable;
                    if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                        map2 = AbilityHelper.getMap(ability2);
                        level2 = data.getTrueFunctionalLevel();
                        damage2 = map2.get("Damage") + level2 * map2.get("DamagePerLvl");
                        event.setAmount(event.getAmount() + (float)damage2);
                    }
                }
            }
            return;
        }));
        Ability ability3;
        Cooldown cooldown;
        EntityPlayerMP player3;
        float pYaw;
        float tYaw;
        float test1;
        float test2;
        HashMap map3;
        long level3;
        double damage3;
        double dur;
        int durTicks;
        HookRegistry.get().registerMethodHook("ATTACK_OF_OPPORTUNITY", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                ability3 = (Ability)data.hookable;
                if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                    cooldown = data.getCooldownSafely();
                    player3 = data.player.getPlayerEntity();
                    pYaw = player3.rotationYaw;
                    tYaw = event.getEntityLiving().rotationYaw;
                    test1 = Math.abs(pYaw - tYaw);
                    if (pYaw > tYaw) {
                        test2 = Math.abs(pYaw - 360.0f - tYaw);
                    }
                    else {
                        test2 = Math.abs(tYaw - 360.0f - pYaw);
                    }
                    if ((test1 <= 50.0f || test2 <= 50.0f) && (event.getEntityLiving().motionX > 0.0 || event.getEntityLiving().motionZ > 0.0) && !cooldown.isOnCooldown() && player3.getHeldItemMainhand().getItem() instanceof ItemSword) {
                        map3 = AbilityHelper.getMap(ability3);
                        level3 = data.getTrueFunctionalLevel();
                        damage3 = map3.get("Damage") + map3.get("DamagePerLvl") * level3;
                        dur = map3.get("Dur") + map3.get("DurPerLvl") * level3;
                        durTicks = (int)(dur * 20.0);
                        event.setAmount(event.getAmount() + (float)damage3);
                        PlayerHelper.addEffect(event.getEntityLiving(), MobEffects.SLOWNESS, durTicks, 4, true, false);
                        cooldown.use(ability3.getCooldownMilliseconds());
                        data.player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            }
            return;
        }));
        final EntityPlayerMP player4;
        EntityPlayerMP target;
        Ability ability4;
        Cooldown cooldown2;
        HashMap map4;
        long level4;
        double chance;
        ItemStack stack;
        ItemStack newStack;
        EntityItem item;
        HookRegistry.get().registerMethodHook("PARRY", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            player4 = data.player.getPlayerEntity();
            if (event.getEntityLiving() instanceof EntityPlayerMP && player4.getHeldItemMainhand().getItem() instanceof ItemSword && event.getEntityLiving().getHeldItemMainhand().getItem() instanceof ItemSword) {
                target = (EntityPlayerMP)event.getEntityLiving();
                ability4 = (Ability)data.hookable;
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown() && data.getTrueLevel() >= ability4.getLevelRequirement() && !event.getEntityLiving().getUniqueID().equals(player4.getUniqueID())) {
                    map4 = AbilityHelper.getMap(ability4);
                    level4 = data.getTrueFunctionalLevel();
                    chance = map4.get("Chance") + level4 * map4.get("ChancePerLvl");
                    if (RandomHelper.nextDouble() < chance) {
                        stack = target.getHeldItemMainhand();
                        newStack = stack.copy();
                        item = new EntityItem((World)player4.getServerWorld(), target.posX + 0.5, target.posY + 1.0, target.posZ + 0.5, newStack);
                        item.setPickupDelay(60);
                        item.motionX = (RandomHelper.nextDouble() - 0.5) * 1.0;
                        item.motionY = RandomHelper.nextDouble() * 1.0;
                        item.motionZ = (RandomHelper.nextDouble() - 0.5) * 1.0;
                        player4.getServerWorld().spawnEntity((Entity)item);
                        stack.shrink(stack.getCount());
                        target.inventory.markDirty();
                        event.setAmount(0.0f);
                        target.sendMessage((ITextComponent)new TextComponentString(Config.neutral("disarmed", new Object[0])));
                        cooldown2.use(ability4.getCooldownMilliseconds());
                        data.player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.65f, 1.0f);
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown3;
        HookRegistry.get().registerMethodHook("BLADESINGING", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new Bladesinging());
                    cooldown3.use(ability5.getCooldownMilliseconds());
                }
            }
        }));
    }
}
