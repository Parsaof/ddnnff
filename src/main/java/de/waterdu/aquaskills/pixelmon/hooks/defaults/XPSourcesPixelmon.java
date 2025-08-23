//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.skill.elements.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import com.pixelmonmod.pixelmon.items.*;
import com.pixelmonmod.pixelmon.api.enums.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import de.waterdu.aquaskills.*;
import net.minecraft.block.*;
import com.pixelmonmod.pixelmon.api.events.*;
import java.util.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.*;
import de.waterdu.aquaskills.hooks.*;
import com.pixelmonmod.pixelmon.enums.*;
import com.pixelmonmod.pixelmon.battles.controller.participants.*;

public class XPSourcesPixelmon
{
    public static void init() {
        final XPSource source;
        final String[] array;
        int length;
        int i = 0;
        String str;
        Item item;
        HookRegistry.get().registerMethodHook("HARVEST_CROP_XP", new MethodHook((Class)ApricornEvent.PickApricorn.class, (event, data) -> {
            source = (XPSource)data.hookable;
            if (!source.areArgsParsed()) {
                source.getArgs().split(",");
                for (length = array.length; i < length; ++i) {
                    str = array[i];
                    if (str.equalsIgnoreCase("any_apricorn")) {
                        source.getParsedArgs().add(true);
                    }
                    else {
                        item = Item.getByNameOrId(str);
                        if (item != null) {
                            source.getParsedArgs().add(item);
                        }
                    }
                }
            }
            if (source.getParsedArgs().contains(true) || source.getParsedArgs().contains(event.getPickedStack().getItem())) {
                data.player.gainExperience(data.skill, source.getAmount());
            }
            return;
        }));
        final XPSource source2;
        final String[] array2;
        int length2;
        int j = 0;
        String str2;
        Item item2;
        HookRegistry.get().registerMethodHook("HARVEST_CROP_XP", new MethodHook((Class)BerryEvent.PickBerry.class, (event, data) -> {
            source2 = (XPSource)data.hookable;
            if (!source2.areArgsParsed()) {
                source2.getArgs().split(",");
                for (length2 = array2.length; j < length2; ++j) {
                    str2 = array2[j];
                    if (str2.equalsIgnoreCase("any_berry")) {
                        source2.getParsedArgs().add(true);
                    }
                    else {
                        item2 = Item.getByNameOrId(str2);
                        if (item2 != null) {
                            source2.getParsedArgs().add(item2);
                        }
                    }
                }
            }
            if (source2.getParsedArgs().contains(true) || source2.getParsedArgs().contains(event.getPickedStack().getItem())) {
                data.player.gainExperience(data.skill, source2.getAmount());
            }
            return;
        }));
        final XPSource source3;
        final String[] array3;
        int length3;
        int k = 0;
        String str3;
        Item item3;
        EntityItem item4;
        HookRegistry.get().registerMethodHook("FISHING_XP", new MethodHook((Class)FishingEvent.Reel.class, (event, data) -> {
            source3 = (XPSource)data.hookable;
            if (!source3.areArgsParsed()) {
                source3.getArgs().split(",");
                for (length3 = array3.length; k < length3; ++k) {
                    str3 = array3[k];
                    if (str3.equalsIgnoreCase("pokemon")) {
                        source3.getParsedArgs().add(true);
                    }
                    else {
                        item3 = Item.getByNameOrId(str3);
                        if (item3 != null) {
                            source3.getParsedArgs().add(item3);
                        }
                    }
                }
            }
            if (source3.getParsedArgs().contains(true) && event.isPokemon()) {
                data.player.gainExperience(data.skill, source3.getAmount());
            }
            else if (event.optEntity.isPresent() && event.optEntity.get() instanceof EntityItem) {
                item4 = event.optEntity.get();
                if (source3.getParsedArgs().contains(item4.getItem().getItem())) {
                    data.player.gainExperience(data.skill, source3.getAmount());
                }
            }
            return;
        }));
        final XPSource source4;
        final String[] array4;
        int length4;
        int l = 0;
        String str4;
        Item item5;
        HookRegistry.get().registerMethodHook("FORGE_XP", new MethodHook((Class)AnvilEvent.FinishedSmith.class, (event, data) -> {
            source4 = (XPSource)data.hookable;
            if (!source4.areArgsParsed()) {
                source4.getArgs().split(",");
                for (length4 = array4.length; l < length4; ++l) {
                    str4 = array4[l];
                    item5 = Item.getByNameOrId(str4);
                    if (item5 != null) {
                        source4.getParsedArgs().add(item5);
                    }
                    else {
                        source4.getParsedArgs().add(str4);
                    }
                }
            }
            if (source4.getParsedArgs().contains(event.getItem().getItem())) {
                data.player.gainExperience(data.skill, source4.getAmount());
            }
            else if (source4.getParsedArgs().contains("any_pokeballlid") && event.getItem().getItem() instanceof ItemPokeballLid) {
                data.player.gainExperience(data.skill, source4.getAmount());
            }
            return;
        }));
        XPSource source5;
        boolean candy;
        HookRegistry.get().registerMethodHook("LEVEL_UP_XP", new MethodHook((Class)ExperienceGainEvent.class, (event, data) -> {
            if (event.getExperience() >= event.pokemon.getExpToNextLevel()) {
                source5 = (XPSource)data.hookable;
                if (!source5.areArgsParsed()) {
                    source5.getParsedArgs().add(source5.getArgs().equalsIgnoreCase("candy"));
                }
                candy = source5.getParsedArgs().get(0);
                if (candy || event.getType() != ExperienceGainType.RARE_CANDY) {
                    data.player.gainExperience(data.skill, (event.pokemon.getLevel() + 1) * source5.getAmount());
                }
            }
            return;
        }));
        XPSource source6;
        boolean candy2;
        HookRegistry.get().registerMethodHook("LEVEL_UP_CONSTANT_XP", new MethodHook((Class)ExperienceGainEvent.class, (event, data) -> {
            if (event.getExperience() >= event.pokemon.getExpToNextLevel()) {
                source6 = (XPSource)data.hookable;
                if (!source6.areArgsParsed()) {
                    source6.getParsedArgs().add(source6.getArgs().equalsIgnoreCase("candy"));
                }
                candy2 = source6.getParsedArgs().get(0);
                if (candy2 || event.getType() != ExperienceGainType.RARE_CANDY) {
                    data.player.gainExperience(data.skill, source6.getAmount());
                }
            }
            return;
        }));
        final XPSource source7;
        final String[] array5;
        int length5;
        int n = 0;
        String str5;
        EnumSpecies species;
        String specStr;
        PokemonSpec spec;
        String error;
        final Iterator<PokemonSpec> iterator;
        PokemonSpec object;
        PokemonSpec spec2;
        HookRegistry.get().registerMethodHook("CATCH_XP", new MethodHook((Class)CaptureEvent.SuccessfulCapture.class, (event, data) -> {
            source7 = (XPSource)data.hookable;
            if (!source7.areArgsParsed()) {
                source7.getArgs().split(",");
                for (length5 = array5.length; n < length5; ++n) {
                    str5 = array5[n];
                    species = EnumSpecies.getFromNameAnyCaseNoTranslate(str5);
                    if (species != null) {
                        source7.getParsedArgs().add(species);
                    }
                    else if (str5.startsWith("spec")) {
                        specStr = str5.substring(4).trim();
                        spec = null;
                        try {
                            spec = PokemonSpec.from(specStr.split(" "));
                        }
                        catch (Exception e) {
                            error = "Invalid spec " + specStr + " in " + source7.getHook();
                            AquaSkills.log.error(error);
                            ASLogger.log(ASLogger.Severity.ERROR, error);
                            e.printStackTrace();
                        }
                        if (spec != null) {
                            source7.getParsedArgs().add(spec);
                        }
                    }
                    else {
                        source7.getParsedArgs().add(str5);
                    }
                }
            }
            if (source7.getParsedArgs().contains("normal")) {
                data.player.gainExperience(data.skill, source7.getAmount());
            }
            else if (source7.getParsedArgs().contains(event.getPokemon().getSpecies())) {
                data.player.gainExperience(data.skill, source7.getAmount());
            }
            else if (source7.getParsedArgs().contains("legendary") && event.getPokemon().getSpecies().isLegendary()) {
                data.player.gainExperience(data.skill, source7.getAmount());
            }
            else if (source7.getParsedArgs().contains("shiny") && event.getPokemon().getPokemonData().isShiny()) {
                data.player.gainExperience(data.skill, source7.getAmount());
            }
            else if (source7.getParsedArgs().contains("ultrabeast") && event.getPokemon().getSpecies().isUltraBeast()) {
                data.player.gainExperience(data.skill, source7.getAmount());
            }
            else {
                source7.getParsedArgs().iterator();
                while (iterator.hasNext()) {
                    object = iterator.next();
                    if (object instanceof PokemonSpec) {
                        spec2 = object;
                        if (spec2.matches(event.getPokemon().getPokemonData())) {
                            data.player.gainExperience(data.skill, source7.getAmount());
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            return;
        }));
        final XPSource source8;
        final String[] array6;
        int length6;
        int n2 = 0;
        String str6;
        EnumSpecies species2;
        String specStr2;
        PokemonSpec spec3;
        String error2;
        final Iterator<PokemonSpec> iterator2;
        PokemonSpec object2;
        PokemonSpec spec4;
        HookRegistry.get().registerMethodHook("HATCH_XP", new MethodHook((Class)EggHatchEvent.class, (event, data) -> {
            source8 = (XPSource)data.hookable;
            if (!source8.areArgsParsed()) {
                source8.getArgs().split(",");
                for (length6 = array6.length; n2 < length6; ++n2) {
                    str6 = array6[n2];
                    species2 = EnumSpecies.getFromNameAnyCaseNoTranslate(str6);
                    if (species2 != null) {
                        source8.getParsedArgs().add(species2);
                    }
                    else if (str6.startsWith("spec")) {
                        specStr2 = str6.substring(4).trim();
                        spec3 = null;
                        try {
                            spec3 = PokemonSpec.from(specStr2.split(" "));
                        }
                        catch (Exception e2) {
                            error2 = "Invalid spec " + specStr2 + " in " + source8.getHook();
                            AquaSkills.log.error(error2);
                            ASLogger.log(ASLogger.Severity.ERROR, error2);
                            e2.printStackTrace();
                        }
                        if (spec3 != null) {
                            source8.getParsedArgs().add(spec3);
                        }
                    }
                    else {
                        source8.getParsedArgs().add(str6);
                    }
                }
            }
            if (source8.getParsedArgs().contains("normal")) {
                data.player.gainExperience(data.skill, source8.getAmount());
            }
            else if (source8.getParsedArgs().contains(event.pokemon.getSpecies())) {
                data.player.gainExperience(data.skill, source8.getAmount());
            }
            else if (source8.getParsedArgs().contains("legendary") && event.pokemon.getSpecies().isLegendary()) {
                data.player.gainExperience(data.skill, source8.getAmount());
            }
            else if (source8.getParsedArgs().contains("shiny") && event.pokemon.isShiny()) {
                data.player.gainExperience(data.skill, source8.getAmount());
            }
            else if (source8.getParsedArgs().contains("ultrabeast") && event.pokemon.getSpecies().isUltraBeast()) {
                data.player.gainExperience(data.skill, source8.getAmount());
            }
            else {
                source8.getParsedArgs().iterator();
                while (iterator2.hasNext()) {
                    object2 = iterator2.next();
                    if (object2 instanceof PokemonSpec) {
                        spec4 = object2;
                        if (spec4.matches(event.pokemon)) {
                            data.player.gainExperience(data.skill, source8.getAmount());
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            return;
        }));
        final XPSource source9;
        final String[] array7;
        int length7;
        int n3 = 0;
        String str7;
        EnumSpecies species3;
        String specStr3;
        PokemonSpec spec5;
        String error3;
        final Iterator<PokemonSpec> iterator3;
        PokemonSpec object3;
        PokemonSpec spec6;
        HookRegistry.get().registerMethodHook("BREED_XP", new MethodHook((Class)BreedEvent.CollectEgg.class, (event, data) -> {
            source9 = (XPSource)data.hookable;
            if (!source9.areArgsParsed()) {
                source9.getArgs().split(",");
                for (length7 = array7.length; n3 < length7; ++n3) {
                    str7 = array7[n3];
                    species3 = EnumSpecies.getFromNameAnyCaseNoTranslate(str7);
                    if (species3 != null) {
                        source9.getParsedArgs().add(species3);
                    }
                    else if (str7.startsWith("spec")) {
                        specStr3 = str7.substring(4).trim();
                        spec5 = null;
                        try {
                            spec5 = PokemonSpec.from(specStr3.split(" "));
                        }
                        catch (Exception e3) {
                            error3 = "Invalid spec " + specStr3 + " in " + source9.getHook();
                            AquaSkills.log.error(error3);
                            ASLogger.log(ASLogger.Severity.ERROR, error3);
                            e3.printStackTrace();
                        }
                        if (spec5 != null) {
                            source9.getParsedArgs().add(spec5);
                        }
                    }
                    else {
                        source9.getParsedArgs().add(str7);
                    }
                }
            }
            if (source9.getParsedArgs().contains("normal")) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            else if (source9.getParsedArgs().contains(event.getEgg().getSpecies())) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            else if (source9.getParsedArgs().contains("legendary") && event.getEgg().getSpecies().isLegendary()) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            else if (source9.getParsedArgs().contains("shiny") && event.getEgg().isShiny()) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            else if (source9.getParsedArgs().contains("ultrabeast") && event.getEgg().getSpecies().isUltraBeast()) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            else {
                source9.getParsedArgs().iterator();
                while (iterator3.hasNext()) {
                    object3 = iterator3.next();
                    if (object3 instanceof PokemonSpec) {
                        spec6 = object3;
                        if (spec6.matches(event.getEgg())) {
                            data.player.gainExperience(data.skill, source9.getAmount());
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            return;
        }));
        XPSource source10;
        HookRegistry.get().registerMethodHook("DEFEAT_XP", new MethodHook((Class)PixelmonKnockoutEvent.class, (event, data) -> {
            if (event.source.getPlayerOwner() != null && event.source.getPlayerOwner().getUniqueID().equals(data.player.getUUID()) && event.source != event.pokemon) {
                source10 = (XPSource)data.hookable;
                if (event.pokemon.isWildPokemon()) {
                    doDefeatXP(source10, data, event);
                }
            }
            return;
        }));
        XPSource source11;
        HookRegistry.get().registerMethodHook("DEFEAT_PLAYER_XP", new MethodHook((Class)PixelmonKnockoutEvent.class, (event, data) -> {
            if (event.source.getPlayerOwner() != null && event.source.getPlayerOwner().getUniqueID().equals(data.player.getUUID()) && event.source != event.pokemon) {
                source11 = (XPSource)data.hookable;
                if (event.pokemon.getPlayerOwner() != null) {
                    doDefeatXP(source11, data, event);
                }
            }
            return;
        }));
        XPSource source12;
        HookRegistry.get().registerMethodHook("DEFEAT_TRAINER_XP", new MethodHook((Class)PixelmonKnockoutEvent.class, (event, data) -> {
            if (event.source.getPlayerOwner() != null && event.source.getPlayerOwner().getUniqueID().equals(data.player.getUUID()) && event.source != event.pokemon) {
                source12 = (XPSource)data.hookable;
                if (event.pokemon.getTrainerOwner() != null) {
                    doDefeatXP(source12, data, event);
                }
            }
            return;
        }));
        final XPSource source13;
        final String[] array8;
        int length8;
        int n4 = 0;
        String str8;
        Block block;
        HookRegistry.get().registerMethodHook("SHRINE_XP", new MethodHook((Class)PlayerActivateShrineEvent.class, (event, data) -> {
            source13 = (XPSource)data.hookable;
            if (!source13.areArgsParsed()) {
                source13.getArgs().split(",");
                for (length8 = array8.length; n4 < length8; ++n4) {
                    str8 = array8[n4];
                    block = Block.getBlockFromName(str8);
                    if (block != null) {
                        source13.getParsedArgs().add(block);
                    }
                }
            }
            if (source13.getParsedArgs().isEmpty() || source13.getParsedArgs().contains(event.block)) {
                data.player.gainExperience(data.skill, source13.getAmount());
            }
            return;
        }));
        final XPSource source14;
        final String[] array9;
        int length9;
        int n5 = 0;
        String str9;
        Block block2;
        HookRegistry.get().registerMethodHook("POKELOOT_XP", new MethodHook((Class)PokeLootClaimedEvent.class, (event, data) -> {
            source14 = (XPSource)data.hookable;
            if (!source14.areArgsParsed()) {
                source14.getArgs().split(",");
                for (length9 = array9.length; n5 < length9; ++n5) {
                    str9 = array9[n5];
                    block2 = Block.getBlockFromName(str9);
                    if (block2 != null) {
                        source14.getParsedArgs().add(block2);
                    }
                    else if (str9.equalsIgnoreCase("grotto")) {
                        source14.getParsedArgs().add("grotto");
                    }
                }
            }
            if (source14.getParsedArgs().isEmpty() || source14.getParsedArgs().contains(event.player.getServerWorld().getBlockState(event.chest.getPos()).getBlock())) {
                data.player.gainExperience(data.skill, source14.getAmount());
            }
            else if (source14.getParsedArgs().contains("grotto") && event.chest.isGrotto()) {
                data.player.gainExperience(data.skill, source14.getAmount());
            }
            return;
        }));
        final XPSource source15;
        final long timeNow;
        final long timeTest;
        final long dif;
        EVStore evs;
        HookRegistry.get().registerMethodHook("EV_XP", new MethodHook((Class)LevelUpEvent.class, (event, data) -> {
            source15 = (XPSource)data.hookable;
            timeNow = event.player.getServerWorld().getTotalWorldTime();
            timeTest = event.pokemon.getPokemon().getPersistentData().getLong("EVsMaxed");
            dif = timeNow - timeTest;
            if (timeTest == 0L || dif <= 5L) {
                evs = event.pokemon.getPokemon().getEVs();
                if (evs.getStat(StatsType.Attack) + evs.getStat(StatsType.Defence) + evs.getStat(StatsType.HP) + evs.getStat(StatsType.SpecialAttack) + evs.getStat(StatsType.SpecialDefence) + evs.getStat(StatsType.Speed) >= 510) {
                    data.player.gainExperience(data.skill, source15.getAmount());
                    event.pokemon.getPokemon().getPersistentData().setLong("EVsMaxed", timeNow);
                }
            }
        }));
    }
    
    private static void doDefeatXP(final XPSource source, final MethodData data, final PixelmonKnockoutEvent event) {
        if (!source.areArgsParsed()) {
            for (final String str : source.getArgs().split(",")) {
                final EnumSpecies species = EnumSpecies.getFromNameAnyCaseNoTranslate(str);
                if (species != null) {
                    source.getParsedArgs().add(species);
                }
                else if (str.startsWith("spec")) {
                    final String specStr = str.substring(4).trim();
                    PokemonSpec spec = null;
                    try {
                        spec = PokemonSpec.from(specStr.split(" "));
                    }
                    catch (Exception e) {
                        final String error = "Invalid spec " + specStr + " in " + source.getHook();
                        AquaSkills.log.error(error);
                        ASLogger.log(ASLogger.Severity.ERROR, error);
                        e.printStackTrace();
                    }
                    if (spec != null) {
                        source.getParsedArgs().add(spec);
                    }
                }
                else {
                    source.getParsedArgs().add(str);
                }
            }
        }
        final PixelmonWrapper pokemon = event.pokemon;
        if (source.getParsedArgs().contains("normal")) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains(pokemon.getSpecies())) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("boss1") && pokemon.entity.getBossMode() == EnumBossMode.Uncommon) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("boss2") && pokemon.entity.getBossMode() == EnumBossMode.Rare) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("boss3") && pokemon.entity.getBossMode() == EnumBossMode.Legendary) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("boss4") && pokemon.entity.getBossMode() == EnumBossMode.Ultimate) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("legendary") && pokemon.getSpecies().isLegendary()) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("shiny") && pokemon.pokemon.isShiny()) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else if (source.getParsedArgs().contains("ultrabeast") && pokemon.getSpecies().isUltraBeast()) {
            data.player.gainExperience(data.skill, source.getAmount());
        }
        else {
            for (final Object object : source.getParsedArgs()) {
                if (object instanceof PokemonSpec) {
                    final PokemonSpec spec2 = (PokemonSpec)object;
                    if (spec2.matches(pokemon.pokemon)) {
                        data.player.gainExperience(data.skill, source.getAmount());
                        break;
                    }
                    continue;
                }
            }
        }
    }
}
