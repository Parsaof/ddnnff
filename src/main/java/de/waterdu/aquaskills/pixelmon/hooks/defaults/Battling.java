//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.helper.*;
import com.pixelmonmod.pixelmon.*;
import com.pixelmonmod.pixelmon.battles.status.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import com.pixelmonmod.pixelmon.api.economy.*;
import com.pixelmonmod.pixelmon.api.events.battles.*;
import com.pixelmonmod.pixelmon.api.events.*;
import com.pixelmonmod.pixelmon.api.enums.*;
import com.pixelmonmod.pixelmon.battles.controller.participants.*;
import de.waterdu.aquaskills.player.*;
import com.pixelmonmod.pixelmon.storage.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import com.pixelmonmod.pixelmon.battles.attacks.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Battling
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Battling", new String[] { "Gain experience by defeating foes in the art of the Pok\u00e9mon Battle." }, "Battler", "pixelmon:x_attack", 3, 1000L, new String[] { "flint", "book", "sugar" }, new XPSource[] { new XPSource("DEFEAT_XP", "shiny", 250.0), new XPSource("DEFEAT_XP", "normal", 20.0), new XPSource("DEFEAT_XP", "legendary", 250.0), new XPSource("DEFEAT_XP", "ultrabeast", 150.0), new XPSource("DEFEAT_XP", "boss1", 50.0), new XPSource("DEFEAT_XP", "boss2", 100.0), new XPSource("DEFEAT_XP", "boss3", 200.0), new XPSource("DEFEAT_XP", "boss4", 400.0) }, new Ability[] { new Ability("EXPERIENCED_BATTLER", "Mult=1.1 MultPerLvl=0.0014", 5L, 0L, new DisplayInfo("Experienced Battler", "pixelmon:rare_candy", 28, new String[] { "Your experience in besting your opponents allows you to make the most out of it.", "Your Pok\u00e9mon earn extra experience through trainer battles.", "Improves as your level of Battling increases.", "", "Passive ability." })), new Ability("FIRST_AID", "CDPerLvl=-150 Amount=1 AmountPerLvl=0.029", 25L, 180000L, new DisplayInfo("First Aid", "pixelmon:healer", 30, new String[] { "You have been taught ways to soothe your party after defeat.", "Stabilizes your fainted Pok\u00e9mon after battles.", "Improves as your level of Battling increases.", "", "Passive ability." })), new Ability("BATTLE_CRY", "CDPerLvl=-450 Amount=0.15 AmountPerLvl=0.00085", 50L, 600000L, new DisplayInfo("Battle Cry", "fireworks", 32, new String[] { "Your words of inspiration get your party ready to fight again.", "Restores HP and PP of your party.", "Improves as your level of Battling increases.", "", "Bindable. Click to choose a Battling item to bind this to." })).setBindable(), new Ability("SILVER_TONGUE", "CDPerLvl=-120 Amount=500 AmountPerLvl=1", 100L, 300000L, new DisplayInfo("Silver Tongue", "pixelmon:amulet_coin", 34, new String[] { "Your performance in battle, combined with some honeyed words, allows you to gleam more winnings.", "Earn extra Pok\u00e9dollars from winning battles.", "Improves as your level of Battling increases.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        long cd;
        double amount;
        PlayerPartyStorage pps;
        final Pokemon[] array;
        int length;
        int j = 0;
        Pokemon pokemon;
        int i;
        Attack attack;
        int toGive;
        HookRegistry.get().registerMethodHook("BATTLE_CRY", new MethodHook((Class)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (event.ability == ability && data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    cd = ability.getCooldownMilliseconds() + map.get("CDPerLvl").longValue() * level;
                    amount = map.get("Amount") + map.get("AmountPerLvl") * level;
                    pps = Pixelmon.storageManager.getParty(data.player.getPlayerEntity());
                    pps.getAll();
                    for (length = array.length; j < length; ++j) {
                        pokemon = array[j];
                        if (pokemon != null && !pokemon.isEgg()) {
                            pokemon.setStatus((StatusPersist)NoStatus.noStatus);
                            pokemon.setHealthPercentage(Math.min(100.0f, pokemon.getHealthPercentage() + (float)amount * 100.0f));
                            pokemon.getMoveset().healAllPP();
                            for (i = 0; i < pokemon.getMoveset().size(); ++i) {
                                attack = pokemon.getMoveset().attacks[i];
                                if (attack != null) {
                                    toGive = Math.max(1, (int)(attack.getMaxPP() * amount));
                                    attack.pp = Math.min(attack.getMaxPP(), attack.pp + toGive);
                                }
                            }
                            pokemon.getMoveset().tryNotifyPokemon();
                        }
                    }
                    data.player.playSound(SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    cooldown.use(cd);
                }
            }
            return;
        }));
        final Ability ability2;
        final Cooldown cooldown2;
        EntityPlayerMP player;
        HashMap map2;
        long level2;
        long cd2;
        int amount2;
        Optional optional;
        IPixelmonBankAccount bank;
        HookRegistry.get().registerMethodHook("SILVER_TONGUE", new MethodHook((Class)BeatTrainerEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            cooldown2 = data.getCooldownSafely();
            if (event.trainer != null && event.trainer.winMoney > 0 && !cooldown2.isOnCooldown()) {
                player = data.player.getPlayerEntity();
                if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                    map2 = AbilityHelper.getMap(ability2);
                    level2 = data.getTrueFunctionalLevel();
                    cd2 = ability2.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                    amount2 = (int)(map2.get("Amount").intValue() + map2.get("AmountPerLvl").intValue() * level2);
                    if (amount2 > event.trainer.winMoney) {
                        amount2 = event.trainer.winMoney;
                    }
                    optional = Pixelmon.moneyManager.getBankAccount(player);
                    if (optional.isPresent()) {
                        bank = (IPixelmonBankAccount)optional.get();
                        bank.changeMoney(amount2);
                        cooldown2.use(cd2);
                        data.player.sendAbilityMessage("SILVER_TONGUE", new Object[] { amount2 });
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        HashMap map3;
        long level3;
        long cd3;
        int amount3;
        PlayerPartyStorage pps2;
        boolean healed;
        final Pokemon[] array2;
        int length2;
        int k = 0;
        Pokemon pokemon2;
        HookRegistry.get().registerMethodHook("FIRST_AID", new MethodHook((Class)BattleEndEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability3);
                    level3 = data.getTrueFunctionalLevel();
                    cd3 = ability3.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level3;
                    amount3 = (int)(map3.get("Amount") + map3.get("AmountPerLvl") * level3);
                    pps2 = Pixelmon.storageManager.getParty(data.player.getPlayerEntity());
                    healed = false;
                    pps2.getAll();
                    for (length2 = array2.length; k < length2; ++k) {
                        pokemon2 = array2[k];
                        if (pokemon2 != null && !pokemon2.isEgg() && pokemon2.getHealth() <= 0) {
                            pokemon2.setHealth(amount3);
                            pokemon2.setStatus((StatusPersist)NoStatus.noStatus);
                            healed = true;
                        }
                    }
                    if (healed) {
                        data.player.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        cooldown3.use(cd3);
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        boolean wild;
        final Iterator<BattleParticipant> iterator;
        BattleParticipant bp;
        HashMap map4;
        long level4;
        double multiplier;
        HookRegistry.get().registerMethodHook("EXPERIENCED_BATTLER", new MethodHook((Class)ExperienceGainEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement() && event.getType() == ExperienceGainType.BATTLE && event.pokemon.getBattleController() != null) {
                wild = false;
                event.pokemon.getBattleController().participants.iterator();
                while (iterator.hasNext()) {
                    bp = iterator.next();
                    if (bp instanceof WildPixelmonParticipant) {
                        wild = true;
                        break;
                    }
                }
                if (!wild) {
                    map4 = AbilityHelper.getMap(ability4);
                    level4 = data.getTrueFunctionalLevel();
                    multiplier = map4.get("Mult") + level4 * map4.get("MultPerLvl");
                    event.setExperience((int)(event.getExperience() * multiplier));
                }
            }
        }));
    }
}
