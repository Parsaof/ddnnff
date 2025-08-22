//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import com.pixelmonmod.pixelmon.api.events.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import com.pixelmonmod.pixelmon.*;
import java.util.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import com.pixelmonmod.pixelmon.storage.*;
import de.waterdu.aquaskills.hooks.*;

public class Breeding
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Breeding", new String[] { "Gain experience through hatching Pok\u00e9mon eggs." }, "Breeder", "egg", 4, 1000L, new String[] { "magma_cream" }, new XPSource[] { new XPSource("HATCH_XP", "shiny", 2000.0), new XPSource("HATCH_XP", "normal", 50.0) }, new Ability[] { new Ability("EXPERIENCED_BREEDING", "Mult=1.1 MultPerLvl=0.0014", 5L, 0L, new DisplayInfo("Experienced Breeding", "pixelmon:ranch", 28, new String[] { "You know how to handle ranches, and what to do for efficient results.", "You have an improved breeding rate.", "Improves as your level of Breeding increases.", "", "Passive ability." })), new Ability("INCUBATOR", "Amount=0.25 AmountPerLvl=0.0005", 25L, 0L, new DisplayInfo("Incubator", "minecraft:bed:14", 30, new String[] { "Your technique for caring for eggs causes them to crack earlier than others.", "You have an improved hatching rate.", "Improves as your level of Breeding increases.", "", "Passive ability." })), new Ability("DOUBLE_YOLK", "Chance=0.01 ChancePerLvl=0.00009", 50L, 0L, new DisplayInfo("Double Yolk", "egg", 32, new String[] { "Your skill in breeding allows you spot eggs that might have potential for twice the life.", "Occasionally, you will find two eggs.", "Improves as your level of Breeding increases.", "", "Passive ability." })), new Ability("CRISPR", "Rate=2048 RatePerLvl=-1 Mult=1.05 MultPerLvl=0.0001", 100L, 0L, new DisplayInfo("CRISPR", "pixelmon:pc", 34, new String[] { "Gene editing, while controversial, does create improved results.", "Your eggs have an improved Shiny rate, and gain extra IVs.", "Improves as your level of Breeding increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        HashMap map;
        long level;
        double multiplier;
        int rate;
        boolean shiny;
        int[] ivs;
        int i;
        HookRegistry.get().registerMethodHook("CRISPR", new MethodHook((Class)BreedEvent.CollectEgg.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                map = AbilityHelper.getMap(ability);
                level = data.getTrueFunctionalLevel();
                multiplier = map.get("Mult") + level * map.get("MultPerLvl");
                rate = map.get("Rate").intValue() + (int)level * map.get("RatePerLvl").intValue();
                if (!event.getEgg().isShiny()) {
                    shiny = (RandomHelper.nextInt(rate) == 0);
                    event.getEgg().setShiny(shiny);
                    if (shiny) {
                        data.getCooldown().ifPresent(Cooldown::use);
                    }
                }
                for (ivs = event.getEgg().getIVs().getArray(), i = 0; i < ivs.length; ++i) {
                    ivs[i] = MathHelper.clamp((int)(ivs[i] * multiplier), 0, 31);
                }
                event.getEgg().getIVs().fillFromArray(ivs);
            }
            return;
        }));
        final Ability ability2;
        HashMap map2;
        long level2;
        double chance;
        Pokemon copy;
        PlayerPartyStorage storage;
        HookRegistry.get().registerMethodHook("DOUBLE_YOLK", new MethodHook((Class)BreedEvent.CollectEgg.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                map2 = AbilityHelper.getMap(ability2);
                level2 = data.getTrueFunctionalLevel();
                chance = map2.get("Chance") + level2 * map2.get("ChancePerLvl");
                if (RandomHelper.nextDouble() < chance) {
                    try {
                        copy = Pixelmon.pokemonFactory.create(PokemonSpec.from(new String[] { event.getEgg().getSpecies().getPokemonName(), "level:1" }));
                        if (copy != null) {
                            copy.setEggCycles(Integer.valueOf(event.getEgg().getEggCycles()));
                            storage = Pixelmon.storageManager.getParty(data.player.getPlayerEntity());
                            storage.add(copy);
                            data.getCooldown().ifPresent(Cooldown::use);
                        }
                    }
                    catch (Exception ex) {}
                }
            }
            return;
        }));
        final Ability ability3;
        HashMap map3;
        long level3;
        double multiplier2;
        int ticks;
        HookRegistry.get().registerMethodHook("EXPERIENCED_BREEDING", new MethodHook((Class)BreedEvent.BreedingTicks.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                map3 = AbilityHelper.getMap(ability3);
                level3 = data.getTrueFunctionalLevel();
                multiplier2 = map3.get("Mult") + level3 * map3.get("MultPerLvl") - 1.0;
                ticks = (int)(event.getBreedingTicks() * multiplier2);
                event.setBreedingTicks(event.getBreedingTicks() - ticks);
            }
            return;
        }));
        final Ability ability4;
        HashMap map4;
        long level4;
        double amount;
        int cycles;
        HookRegistry.get().registerMethodHook("INCUBATOR", new MethodHook((Class)BreedEvent.CollectEgg.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                map4 = AbilityHelper.getMap(ability4);
                level4 = data.getTrueFunctionalLevel();
                amount = map4.get("Amount") + level4 * map4.get("AmountPerLvl");
                cycles = (int)(event.getEgg().getEggCycles() * amount);
                event.getEgg().setEggCycles(Integer.valueOf(event.getEgg().getEggCycles() - cycles));
            }
        }));
    }
}
