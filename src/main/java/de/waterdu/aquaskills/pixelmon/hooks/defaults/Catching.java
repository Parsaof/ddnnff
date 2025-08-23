//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import com.pixelmonmod.pixelmon.api.events.*;
import com.pixelmonmod.pixelmon.api.events.spawning.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import de.waterdu.aquaskills.hooks.*;

public class Catching
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Catching", new String[] { "Gain experience by catching many a number of Pok\u00e9mon." }, "Catcher", "pixelmon:poke_ball", 5, 1000L, new String[] { "pixelmon:rare_candy" }, new XPSource[] { new XPSource("CATCH_XP", "legendary", 2500.0), new XPSource("CATCH_XP", "shiny", 1000.0), new XPSource("CATCH_XP", "ultrabeast", 250.0), new XPSource("CATCH_XP", "normal", 25.0) }, new Ability[] { new Ability("EXPERIENCED_CATCHING", "Mult=1.1 MultPerLvl=0.0014", 5L, 0L, new DisplayInfo("Experienced Catching", "pixelmon:poke_ball", 28, new String[] { "Time spent perfecting your Pok\u00e9 Ball throwing means you're more likely to hit the mark.", "You have an improved catch rate.", "Improves as your level of Catching increases.", "", "Passive ability." })), new Ability("HUMANITARIAN_CAPTURE", "Friendship=20 FriendshipPerLvl=0.08", 25L, 0L, new DisplayInfo("Humanitarian Capture", "pixelmon:heal_ball", 30, new String[] { "You have learnt techniques by which catching can be done with grace.", "Caught Pok\u00e9mon are fully healed, and gain friendship.", "Improves as your level of Catching increases.", "", "Passive ability." })), new Ability("KEEN_EYE", "Mult=1.05 MultPerLvl=0.0001", 50L, 0L, new DisplayInfo("Keen Eye", "pixelmon:zoom_lens", 32, new String[] { "You have an eye for spotting the higher pedigree Pok\u00e9mon.", "Caught Pok\u00e9mon gain extra IVs.", "Improves as your level of Catching increases.", "", "Passive ability." })), new Ability("ALL_THAT_GLITTERS", "Rate=4096 RatePerLvl=-2", 100L, 0L, new DisplayInfo("All That Glitters", "pixelmon:shiny_stone", 34, new String[] { "Your skill in capturing rarities and curiosities occasionally leads them to you.", "You have an improved Shiny spawn rate.", "Improves as your level of Catching increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        HashMap map;
        long level;
        double multiplier;
        HookRegistry.get().registerMethodHook("EXPERIENCED_CATCHING", new MethodHook((Class)CaptureEvent.StartCapture.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                map = AbilityHelper.getMap(ability);
                level = data.getTrueFunctionalLevel();
                multiplier = map.get("Mult") + level * map.get("MultPerLvl");
                event.setCatchRate((int)(event.getCatchRate() * multiplier));
            }
            return;
        }));
        final Ability ability2;
        Entity e;
        HashMap map2;
        long level2;
        int rate;
        EntityPixelmon pixelmon;
        HookRegistry.get().registerMethodHook("ALL_THAT_GLITTERS", new MethodHook((Class)SpawnEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                e = event.action.getOrCreateEntity();
                if (e instanceof EntityPixelmon) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    rate = map2.get("Rate").intValue() + (int)level2 * map2.get("RatePerLvl").intValue();
                    pixelmon = (EntityPixelmon)e;
                    if (!pixelmon.getPokemonData().isShiny()) {
                        pixelmon.getPokemonData().setShiny(RandomHelper.nextInt(rate) == 0);
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        HashMap map3;
        long level3;
        double multiplier2;
        Pokemon pokemon;
        int[] ivs;
        int i;
        HookRegistry.get().registerMethodHook("KEEN_EYE", new MethodHook((Class)CaptureEvent.SuccessfulCapture.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                map3 = AbilityHelper.getMap(ability3);
                level3 = data.getTrueFunctionalLevel();
                multiplier2 = map3.get("Mult") + level3 * map3.get("MultPerLvl");
                pokemon = event.getPokemon().getPokemonData();
                for (ivs = pokemon.getIVs().getArray(), i = 0; i < ivs.length; ++i) {
                    ivs[i] = MathHelper.clamp((int)(ivs[i] * multiplier2), 0, 31);
                }
                pokemon.getIVs().fillFromArray(ivs);
            }
            return;
        }));
        final Ability ability4;
        HashMap map4;
        long level4;
        int friendship;
        Pokemon pokemon2;
        HookRegistry.get().registerMethodHook("HUMANITARIAN_CAPTURE", new MethodHook((Class)CaptureEvent.SuccessfulCapture.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                map4 = AbilityHelper.getMap(ability4);
                level4 = data.getTrueFunctionalLevel();
                friendship = (int)(map4.get("Friendship") + level4 * map4.get("FriendshipPerLvl"));
                pokemon2 = event.getPokemon().getPokemonData();
                pokemon2.heal();
                pokemon2.setFriendship(Math.max(pokemon2.getFriendship(), friendship));
            }
        }));
    }
}
