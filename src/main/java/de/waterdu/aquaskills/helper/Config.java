//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import de.waterdu.aquaskills.file.boosts.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.file.*;
import java.util.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.battlepass.*;

public class Config
{
    public static Settings settings() {
        return (Settings)AquaConfig.get("aquaskills", (Class)Settings.class);
    }
    
    public static ASBPSettings settingsASBP() {
        return (ASBPSettings)AquaConfig.get("aquaskills", (Class)ASBPSettings.class);
    }
    
    public static XPBoosts globalXP() {
        return (XPBoosts)AquaConfig.get("aquaskills", (Class)XPBoosts.class);
    }
    
    public static BlockLog log() {
        return AquaSkills.blockLog;
    }
    
    public static String ability(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)SkillMessages.class, key, args);
    }
    
    public static String format(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String negative(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixNegative", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String neutral(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixNeutral", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String positive(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixPositive", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String negativeASBP(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixASBPNegative", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String neutralASBP(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixASBPNeutral", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static String positiveASBP(final String key, final Object... args) {
        return AquaConfig.format("aquaskills", (Class)Messages.class, "prefixASBPPositive", new Object[0]) + AquaConfig.format("aquaskills", (Class)Messages.class, key, args);
    }
    
    public static Collection<Player> players() {
        return (Collection<Player>)AquaConfig.getAll("aquaskills", (Class)Player.class);
    }
    
    public static Player player(final UUID uuid) {
        return (Player)AquaConfig.get("aquaskills", (Class)Player.class, uuid);
    }
    
    public static Player player(final String name) {
        return (Player)AquaConfig.get("aquaskills", (Class)Player.class, name);
    }
    
    public static Player player(final EntityPlayerMP player) {
        return (Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getUniqueID());
    }
    
    public static void player(final Player player) {
        AquaConfig.put("aquaskills", (Class)Player.class, (IData)player);
    }
    
    public static ASBPPlayer playerASBP(final UUID uuid) {
        return (ASBPPlayer)AquaConfig.get("aquaskills", (Class)ASBPPlayer.class, uuid);
    }
    
    public static ASBPPlayer playerASBP(final String name) {
        return (ASBPPlayer)AquaConfig.get("aquaskills", (Class)ASBPPlayer.class, name);
    }
    
    public static ASBPPlayer playerASBP(final EntityPlayerMP player) {
        return (ASBPPlayer)AquaConfig.get("aquaskills", (Class)ASBPPlayer.class, player.getUniqueID());
    }
    
    public static void playerASBP(final ASBPPlayer player) {
        AquaConfig.put("aquaskills", (Class)ASBPPlayer.class, (IData)player);
    }
}
