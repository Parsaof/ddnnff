//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file.boosts;

import java.util.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.helper.*;

public class XPBoost
{
    private UUID player;
    private UUID skill;
    private long expiry;
    private double xpRate;
    private double xpBoost;
    
    public XPBoost(final Player player, final Skill skill, final int durationSeconds, final double xpRate, final double xpBoost) {
        this.player = ((player == null) ? null : player.getUUID());
        this.skill = ((skill == null) ? null : skill.getUUID());
        this.expiry = ((durationSeconds < 0) ? -1L : (System.currentTimeMillis() + durationSeconds * 1000L));
        this.xpRate = xpRate;
        this.xpBoost = xpBoost;
    }
    
    public boolean apply(final AtomicDouble experience, final Player player, final Skill skill, final long time) {
        if (this.expiry > 0L && this.expiry < time) {
            return true;
        }
        if ((this.player == null || this.player.equals(player.getUUID())) && (this.skill == null || this.skill.equals(skill.getUUID()))) {
            experience.setValue(experience.getValue() * this.xpRate + this.xpBoost);
        }
        return false;
    }
    
    public UUID getPlayer() {
        return this.player;
    }
    
    public UUID getSkill() {
        return this.skill;
    }
    
    public long getExpiry() {
        return this.expiry;
    }
    
    public double getXpRate() {
        return this.xpRate;
    }
    
    public double getXpBoost() {
        return this.xpBoost;
    }
    
    public void setPlayer(final UUID player) {
        this.player = player;
    }
    
    public void setSkill(final UUID skill) {
        this.skill = skill;
    }
    
    public void setExpiry(final long expiry) {
        this.expiry = expiry;
    }
    
    public void setXpRate(final double xpRate) {
        this.xpRate = xpRate;
    }
    
    public void setXpBoost(final double xpBoost) {
        this.xpBoost = xpBoost;
    }
    
    public XPBoost() {
    }
}
