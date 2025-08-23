//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import de.waterdu.aquaapi.file.api.*;
import java.util.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.*;
import de.waterdu.aquaskills.*;

public class ASBPSettings implements IConfiguration
{
    private boolean use;
    private boolean asbpOnly;
    private String asbpSkill;
    private ASBPTrack[] tracks;
    private String commandName;
    private String[] commandAliases;
    
    public ASBPSettings() {
        this.use = true;
        this.asbpOnly = false;
        this.asbpSkill = "Leveller";
        this.tracks = new ASBPTrack[] { new ASBPTrack("free", "", "pixelmon:up-grade", "&eStandard Pass", "", "", 1), new ASBPTrack("premium", "battlepass.premium.a", "pixelmon:dubious_disc", "&dPremium Pass", "\nRewards can be claimed retroactively upon purchase of a Premium Pass.", "&4&lYou need a Premium Pass to be able to claim this!", 2) };
        this.commandName = "battlepass";
        this.commandAliases = new String[] { "pass", "rewards" };
    }
    
    public Optional<ASBPTrack> getTrack(final String track) {
        for (final ASBPTrack t : this.tracks) {
            if (t.getId().equalsIgnoreCase(track)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }
    
    public boolean isASBPEnabled() {
        return this.use;
    }
    
    public boolean isOnlyASBPEnabled() {
        return this.isASBPEnabled() && this.asbpOnly;
    }
    
    public String getASBPSkillName() {
        return this.asbpSkill;
    }
    
    public Skill getASBPSkill() {
        final Optional<Skill> skill = SkillMap.get(this.asbpSkill);
        if (skill.isPresent()) {
            return skill.get();
        }
        AquaSkills.log.error("ASBP skill not found! skill = " + this.asbpSkill);
        return null;
    }
    
    public boolean isUse() {
        return this.use;
    }
    
    public boolean isAsbpOnly() {
        return this.asbpOnly;
    }
    
    public ASBPTrack[] getTracks() {
        return this.tracks;
    }
    
    public String getCommandName() {
        return this.commandName;
    }
    
    public String[] getCommandAliases() {
        return this.commandAliases;
    }
    
    public void setUse(final boolean use) {
        this.use = use;
    }
    
    public void setAsbpOnly(final boolean asbpOnly) {
        this.asbpOnly = asbpOnly;
    }
    
    public void setAsbpSkill(final String asbpSkill) {
        this.asbpSkill = asbpSkill;
    }
    
    public void setTracks(final ASBPTrack[] tracks) {
        this.tracks = tracks;
    }
    
    public void setCommandName(final String commandName) {
        this.commandName = commandName;
    }
    
    public void setCommandAliases(final String[] commandAliases) {
        this.commandAliases = commandAliases;
    }
}
