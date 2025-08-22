//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.spells.spells.animalhusbandry.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class AnimalHusbandry
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Animal Husbandry", new String[] { "Gain experience by tending to the animals of the wilds." }, "Husband", "wheat", 25, 1000L, new String[] { "carrot", "golden_carrot", "seeds", "string" }, new XPSource[] { new XPSource("TAME_XP", "EntityCow EntitySheep EntityPig", 10.0), new XPSource("TAME_XP", "EntityWolf EntityOcelot", 25.0), new XPSource("BABY_XP", "EntityCow EntitySheep EntityPig", 30.0) }, new Ability[] { new Ability("TENDER_HAND", "", 5L, 0L, new DisplayInfo("Tender Hand", "feather", 28, new String[] { "Sometimes, that's all that's needed.", "Fully heal an animal in front of you.", "", "Bindable. Click to choose an Animal Husbandry item to bind this to." })).setBindable(), new Ability("WARDEN_OF_THE_WILDS", "", 25L, 0L, new DisplayInfo("Warden of the Wilds", "wheat", 30, new String[] { "Nature smiles upon you.", "Animals near you cannot be affected by potion effects, and cannot be lit on fire.", "", "Passive ability." })), new Ability("HERDING_CALL", "Radius=10 RadiusPerLvl=0.02", 75L, 300000L, new DisplayInfo("Herding Call", "lead", 32, new String[] { "The animals come to you, for they know you care.", "Nearby animals move to you.", "Improves as your level of Animal Husbandry increases.", "", "Bindable. Click to choose an Animal Husbandry item to bind this to." })).setBindable(), new Ability("TWINS", "Chance=0.05 ChancePerLvl=0.00045", 125L, 0L, new DisplayInfo("Twins", "egg", 34, new String[] { "A miracle of the natural world.", "Animals you breed have a chance to make twins.", "Improves as your level of Animal Husbandry increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HookRegistry.get().registerMethodHook("TENDER_HAND", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    SpellEngine.get().castSpell(data, new TenderHand());
                    cooldown.use(ability.getCooldownMilliseconds());
                }
            }
            return;
        }));
        final Ability ability2;
        EntityPlayerMP player;
        final Iterator<EntityAnimal> iterator;
        EntityAnimal a;
        HookRegistry.get().registerMethodHook("WARDEN_OF_THE_WILDS", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                player = data.player.getPlayerEntity();
                if (TimeHelper.isSecond()) {
                    player.getServerWorld().getEntitiesWithinAABB((Class)EntityAnimal.class, PlayerHelper.getAreaAround((Entity)player, 15.0)).iterator();
                    while (iterator.hasNext()) {
                        a = iterator.next();
                        a.clearActivePotions();
                        a.setFire(0);
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown2;
        HashMap map;
        long level;
        double radius;
        EntityPlayerMP player2;
        final Iterator<EntityAnimal> iterator2;
        EntityAnimal a2;
        HookRegistry.get().registerMethodHook("HERDING_CALL", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability3);
                    level = data.getTrueFunctionalLevel();
                    radius = map.get("Radius") + map.get("RadiusPerLvl") * level;
                    player2 = data.player.getPlayerEntity();
                    data.player.playSound(SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO, SoundCategory.PLAYERS, 1.0f, 1.5f);
                    player2.getServerWorld().getEntitiesWithinAABB((Class)EntityAnimal.class, PlayerHelper.getAreaAround((Entity)player2, radius)).iterator();
                    while (iterator2.hasNext()) {
                        a2 = iterator2.next();
                        a2.getNavigator().tryMoveToEntityLiving((Entity)player2, (double)a2.getAIMoveSpeed());
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        HashMap map2;
        long level2;
        double chance;
        EntityAgeable a3;
        EntityAgeable b;
        EntityAgeable newBaby;
        HookRegistry.get().registerMethodHook("TWINS", new MethodHook<Event>((Class<? extends Event>)BabyEntitySpawnEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (event.getChild() != null) {
                if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                    cooldown3 = data.getCooldownSafely();
                    if (!cooldown3.isOnCooldown()) {
                        map2 = AbilityHelper.getMap(ability4);
                        level2 = data.getTrueFunctionalLevel();
                        chance = map2.get("Chance") + map2.get("ChancePerLvl") * level2;
                        if (RandomHelper.nextDouble() < chance && event.getParentA() instanceof EntityAgeable && event.getParentB() instanceof EntityAgeable) {
                            a3 = (EntityAgeable)event.getParentA();
                            b = (EntityAgeable)event.getParentB();
                            newBaby = a3.createChild(b);
                            if (newBaby != null) {
                                newBaby.copyLocationAndAnglesFrom((Entity)event.getChild());
                                event.getChild().world.spawnEntity((Entity)newBaby);
                            }
                        }
                    }
                }
            }
        }));
    }
}
