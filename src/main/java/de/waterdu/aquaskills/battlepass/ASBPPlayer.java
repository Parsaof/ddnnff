//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import de.waterdu.aquaskills.file.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.function.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaapi.file.api.*;

public class ASBPPlayer implements IPlayerData
{
    private UUID uuid;
    private String name;
    private HashMap<String, HashSet<Integer>> claimedRewards;
    private transient Player player;
    
    public ASBPPlayer(final EntityPlayerMP player) {
        this.claimedRewards = new HashMap<String, HashSet<Integer>>();
        this.player = null;
        this.uuid = player.getPersistentID();
        this.name = player.getName();
    }
    
    public ASBPPlayer(final UUID player) {
        this.claimedRewards = new HashMap<String, HashSet<Integer>>();
        this.player = null;
        this.uuid = player;
        this.name = "";
    }
    
    public Optional<Player> getPlayer() {
        if (this.player == null) {
            this.player = Config.player(this.uuid);
        }
        return Optional.ofNullable(this.player);
    }
    
    public Optional<Experience> getXP() {
        return this.getPlayer().flatMap(player -> player.getXP(Config.settingsASBP().getASBPSkill()));
    }
    
    public long getLevel() {
        return this.getXP().map((Function<? super Experience, ? extends Long>)Experience::getTrueLevel).orElse(0L);
    }
    
    public void reset() {
        this.claimedRewards.clear();
        for (final ASBPTrack track : Config.settingsASBP().getTracks()) {
            this.claimedRewards.put(track.getId(), new HashSet<Integer>());
        }
        this.save();
    }
    
    public boolean hasClaimed(final int index, final ASBPTrack track) {
        final HashSet<Integer> claims = this.claimedRewards.computeIfAbsent(track.getId(), k -> new HashSet());
        return claims.contains(index);
    }
    
    public void claim(final int index, final ASBPTrack track) {
        final HashSet<Integer> claims = this.claimedRewards.computeIfAbsent(track.getId(), k -> new HashSet());
        claims.add(index);
        this.save();
    }
    
    public boolean canClaim(final ASBPReward reward, final ASBPTrack track) {
        return this.canClaim(reward, track, this.getPlayerEntity());
    }
    
    public boolean canClaimSomething(final EntityPlayerMP player) {
        if (player != null) {
            final ASBPSettings settings = Config.settingsASBP();
            for (final ASBPTrack track : settings.getTracks()) {
                if (track.has(player)) {
                    for (final ASBPReward reward : ASBPRewards.getRewards()) {
                        if (this.canClaimSkipPermCheck(reward, track)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean claimAll(final EntityPlayerMP player) {
        boolean claimed = false;
        if (player != null) {
            final ASBPSettings settings = Config.settingsASBP();
            for (final ASBPTrack track : settings.getTracks()) {
                if (track.has(player)) {
                    for (final ASBPReward reward : ASBPRewards.getRewards()) {
                        if (this.tryClaimSkipPermCheck(reward, track)) {
                            claimed = true;
                        }
                    }
                }
            }
        }
        return claimed;
    }
    
    protected boolean canClaimSkipPermCheck(final ASBPReward reward, final ASBPTrack track) {
        return this.getLevel() >= reward.getIndex() && reward.hasRewards(track) && !this.hasClaimed(reward.getIndex(), track);
    }
    
    public boolean canClaim(final ASBPReward reward, final ASBPTrack track, final EntityPlayerMP player) {
        return player != null && this.getLevel() >= reward.getIndex() && reward.hasRewards(track) && track.has(player) && !this.hasClaimed(reward.getIndex(), track);
    }
    
    public boolean tryClaim(final ASBPReward reward, final ASBPTrack track) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (this.canClaim(reward, track, player)) {
            reward.executeTrack(player, track);
            this.claim(reward.getIndex(), track);
            return true;
        }
        return false;
    }
    
    protected boolean tryClaimSkipPermCheck(final ASBPReward reward, final ASBPTrack track) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (player != null && this.canClaimSkipPermCheck(reward, track)) {
            reward.executeTrack(player, track);
            this.claim(reward.getIndex(), track);
            return true;
        }
        return false;
    }
    
    public EntityPlayerMP getPlayerEntity() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.uuid);
    }
    
    public void sendMessage(final String message) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (player != null) {
            player.sendMessage((ITextComponent)new TextComponentString(message));
        }
    }
    
    public void save() {
        AquaConfig.save("aquaskills", (IData)this);
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public void setUUID(final UUID uuid) {
        this.uuid = uuid;
    }
    
    public String getFilename() {
        return this.uuid.toString();
    }
    
    public String getName() {
        return this.name;
    }
    
    public HashMap<String, HashSet<Integer>> getClaimedRewards() {
        return this.claimedRewards;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setClaimedRewards(final HashMap<String, HashSet<Integer>> claimedRewards) {
        this.claimedRewards = claimedRewards;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public ASBPPlayer() {
        this.claimedRewards = new HashMap<String, HashSet<Integer>>();
        this.player = null;
    }
}
