//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.player.*;
import java.util.function.*;
import com.pixelmonmod.pixelmon.config.*;
import de.waterdu.aquaskills.async.*;
import de.waterdu.aquaskills.api.events.*;
import de.waterdu.aquaapi.ui.api.*;
import de.waterdu.aquaskills.helper.*;
import com.pixelmonmod.pixelmon.api.events.pokemon.*;
import com.pixelmonmod.pixelmon.api.events.*;
import com.pixelmonmod.pixelmon.api.enums.*;
import com.pixelmonmod.pixelmon.battles.controller.participants.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Training
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Training", new String[] { "Gain experience through the level up of your Pok\u00e9mon." }, "Trainer", "pixelmon:rare_candy", 16, 1000L, new String[] { "pixelmon:rare_candy" }, new XPSource[] { new XPSource("LEVEL_UP_XP", "!candy", 2.0) }, new Ability[] { new Ability("I_WANNA_BE_THE_VERY_BEST", "Amount=0.01 AmountPerLvl=0.00009", 5L, 0L, new DisplayInfo("I Wanna Be The Very Best", "pixelmon:poke_ball", 28, new String[] { "Your dreams become drive, becomes power.", "Your Pok\u00e9mon retain some experience when they level.", "Improves as your level of Training increases.", "", "Passive ability." })), new Ability("LIKE_NOONE_EVER_WAS", "", 25L, 0L, new DisplayInfo("Like No-one Ever Was", "pixelmon:great_ball", 30, new String[] { "Determination will allow you to achieve your goal.", "Your Pok\u00e9mon gain double EVs.", "", "Toggle by clicking this ability." })), new Ability("TO_CATCH_THEM_IS_MY_REAL_TEST", "Mult=1.2 MultPerLvl=0.0008", 50L, 0L, new DisplayInfo("To Catch Them Is My Real Test", "pixelmon:ultra_ball", 32, new String[] { "Your party learn more through the gauntlet of battle.", "Your Pok\u00e9mon earn extra experience through wild battles.", "Improves as your level of Training increases.", "", "Passive ability." })), new Ability("TO_TRAIN_THEM_IS_MY_CAUSE", "Chance=0.005 ChancePerLvl=0.000005", 100L, 0L, new DisplayInfo("To Train Them Is My Cause", "pixelmon:master_ball", 34, new String[] { "Your training can improve those who receive it in many ways.", "Your Pok\u00e9mon have a chance to gain an IV point when they level up.", "Improves as your level of Training increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        HashMap map;
        long level;
        double chance;
        int iv;
        Pokemon pokemon;
        int[] array;
        final Object o;
        final int n;
        HookRegistry.get().registerMethodHook("TO_TRAIN_THEM_IS_MY_CAUSE", new MethodHook((Class)LevelUpEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                map = AbilityHelper.getMap(ability);
                level = data.getTrueFunctionalLevel();
                chance = map.get("Chance") + level * map.get("ChancePerLvl");
                if (RandomHelper.nextDouble() < chance) {
                    iv = RandomHelper.nextInt(6);
                    pokemon = event.pokemon.getPokemon();
                    array = pokemon.getIVs().getArray();
                    if (array[iv] < 31) {
                        ++o[n];
                        pokemon.getIVs().fillFromArray(array);
                        data.getCooldown().ifPresent(Cooldown::use);
                    }
                }
            }
            return;
        }));
        final Ability ability2;
        HashMap map2;
        long level2;
        double amount;
        int xpToGive;
        final int n2;
        HookRegistry.get().registerMethodHook("I_WANNA_BE_THE_VERY_BEST", new MethodHook((Class)LevelUpEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement() && event.newLevel < PixelmonConfig.maxLevel) {
                map2 = AbilityHelper.getMap(ability2);
                level2 = data.getTrueFunctionalLevel();
                amount = map2.get("Amount") + level2 * map2.get("AmountPerLvl");
                xpToGive = (int)(event.pokemon.getExpToNextLevel() * amount);
                ThreadPool.schedule(() -> {
                    event.pokemon.setExp(n2);
                    data.player.sendAbilityMessage("I_WANNA_BE_THE_VERY_BEST", new Object[] { event.pokemon.getNickname(), n2 });
                }, 1000L);
            }
            return;
        }));
        final Ability ability3;
        EntityPlayerMP player;
        Cooldown cooldown;
        final ITextComponent textComponent;
        final Object o2;
        HookRegistry.get().registerMethodHook("LIKE_NOONE_EVER_WAS", new MethodHook((Class)PressAbilityEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (event.ability == ability3 && data.getTrueLevel() >= ability3.getLevelRequirement()) {
                player = event.player.getPlayerEntity();
                cooldown = data.getCooldownSafely();
                AquaUI.openUI(player, (IPage)null);
                cooldown.setActiveUntil((cooldown.getActiveUntil() == 1L) ? -1L : 1L);
                new TextComponentString(Config.neutral((cooldown.getActiveUntil() == 1L) ? "enabled" : "disabled", new Object[] { ability3.getName() }));
                ((EntityPlayerMP)o2).sendMessage(textComponent);
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown2;
        int i;
        HookRegistry.get().registerMethodHook("LIKE_NOONE_EVER_WAS", new MethodHook((Class)EVsGainedEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.getActiveUntil() == 1L) {
                    for (i = 0; i < event.evs.length; ++i) {
                        event.evs[i] *= 2;
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        boolean wild;
        final Iterator<BattleParticipant> iterator;
        BattleParticipant bp;
        HashMap map3;
        long level3;
        double multiplier;
        HookRegistry.get().registerMethodHook("TO_CATCH_THEM_IS_MY_REAL_TEST", new MethodHook((Class)ExperienceGainEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement() && event.getType() == ExperienceGainType.BATTLE && event.pokemon.getBattleController() != null) {
                wild = false;
                event.pokemon.getBattleController().participants.iterator();
                while (iterator.hasNext()) {
                    bp = iterator.next();
                    if (bp instanceof WildPixelmonParticipant) {
                        wild = true;
                        break;
                    }
                }
                if (wild) {
                    map3 = AbilityHelper.getMap(ability5);
                    level3 = data.getTrueFunctionalLevel();
                    multiplier = map3.get("Mult") + level3 * map3.get("MultPerLvl");
                    event.setExperience((int)(event.getExperience() * multiplier));
                }
            }
        }));
    }
}
