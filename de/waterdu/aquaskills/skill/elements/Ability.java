//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill.elements;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.hooks.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.helper.*;
import java.util.*;
import de.waterdu.aquaapi.ui.api.*;

public class Ability implements IHookable
{
    private String hook;
    private String permission;
    private String args;
    private boolean bindable;
    private long levelRequirement;
    private long cooldownMilliseconds;
    private boolean resettable;
    private DisplayInfo displayInfo;
    private transient ArrayList<Object> parsedArgs;
    
    public Ability(final String hook, final String args, final long levelRequirement, final long cooldownMilliseconds, final DisplayInfo displayInfo) {
        this(hook, args, levelRequirement, cooldownMilliseconds, displayInfo, true);
    }
    
    public Ability(final String hook, final String args, final long levelRequirement, final long cooldownMilliseconds, final DisplayInfo displayInfo, final boolean resettable) {
        this.permission = "";
        this.bindable = false;
        this.resettable = true;
        this.parsedArgs = new ArrayList<Object>();
        this.hook = hook;
        this.args = args;
        this.levelRequirement = levelRequirement;
        this.cooldownMilliseconds = cooldownMilliseconds;
        this.displayInfo = displayInfo;
        this.resettable = resettable;
    }
    
    public Ability setBindable() {
        this.bindable = true;
        return this;
    }
    
    @Override
    public MethodData prepare(final Player player, final Skill skill) {
        return new MethodData(player, skill, (IHookable)this);
    }
    
    public boolean areArgsParsed() {
        return !this.parsedArgs.isEmpty();
    }
    
    public String getName() {
        return this.getDisplayInfo().getName();
    }
    
    public ArrayList<String> getLore(final Player player, final Skill skill, final Cooldown cooldown) {
        return player.getXP(skill).map(xp -> this.getLore(skill, xp, cooldown)).orElse(new ArrayList<String>());
    }
    
    public ArrayList<String> getLore(final Skill skill, final Experience experience, final Cooldown cooldown) {
        final ArrayList<String> lore = new ArrayList<String>();
        final boolean locked = experience.getTrueLevel() < this.levelRequirement;
        lore.add("");
        if (cooldown.isOnCooldown()) {
            lore.add(TextFormatting.RED + Config.format("onCooldown", new Object[] { cooldown.getCooldownString() }));
            lore.add("");
        }
        for (final String line : this.displayInfo.getLore()) {
            lore.add((locked ? TextFormatting.DARK_GRAY : TextFormatting.GRAY) + "" + (cooldown.isEnabled() ? "" : TextFormatting.STRIKETHROUGH) + TextHelper.format(line));
        }
        lore.add("");
        if (locked) {
            lore.add(Config.format("lockedAbility", new Object[] { this.levelRequirement, skill.getDisplayName() }));
        }
        else {
            lore.add(Config.format(cooldown.isEnabled() ? "abilityEnabled" : "abilityDisabled", new Object[0]));
            lore.add(Config.format(cooldown.isMessages() ? "messageEnabled" : "messageDisabled", new Object[0]));
        }
        return lore;
    }
    
    public String getSingleLineLore(final Skill skill, final Experience experience, final Cooldown cooldown) {
        final ArrayList<String> lore = this.getLore(skill, experience, cooldown);
        final StringBuilder builder = new StringBuilder();
        for (final String line : lore) {
            if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(TextFormatting.RESET).append(TextFormatting.GRAY).append(line);
        }
        return builder.toString();
    }
    
    public Button getButton(final Player player, final Skill skill) {
        final Button.Builder builder = new Button.Builder();
        final boolean enabled;
        final Button.Builder builder2;
        final Iterator<String> iterator;
        String line;
        player.getCooldown(this).ifPresent(cooldown -> {
            enabled = cooldown.isEnabled();
            builder2.setName((enabled ? "" : TextFormatting.STRIKETHROUGH) + this.displayInfo.getName()).setNameColour(TextFormatting.AQUA).setItem(this.displayInfo.getItem()).setIndex(this.displayInfo.getIndex());
            this.getLore(player, skill, cooldown).iterator();
            while (iterator.hasNext()) {
                line = iterator.next();
                builder2.addLoreLine(line);
            }
            return;
        });
        return builder.build();
    }
    
    @Override
    public void reset() {
        this.parsedArgs.clear();
    }
    
    public <T> T getParsedArg(final int ordinal) {
        return (T)this.parsedArgs.get(ordinal);
    }
    
    @Override
    public String getHook() {
        return this.hook;
    }
    
    @Override
    public String getPermission() {
        return this.permission;
    }
    
    public String getArgs() {
        return this.args;
    }
    
    public boolean isBindable() {
        return this.bindable;
    }
    
    public long getLevelRequirement() {
        return this.levelRequirement;
    }
    
    public long getCooldownMilliseconds() {
        return this.cooldownMilliseconds;
    }
    
    public boolean isResettable() {
        return this.resettable;
    }
    
    public DisplayInfo getDisplayInfo() {
        return this.displayInfo;
    }
    
    public ArrayList<Object> getParsedArgs() {
        return this.parsedArgs;
    }
    
    public void setHook(final String hook) {
        this.hook = hook;
    }
    
    public void setPermission(final String permission) {
        this.permission = permission;
    }
    
    public void setArgs(final String args) {
        this.args = args;
    }
    
    public void setBindable(final boolean bindable) {
        this.bindable = bindable;
    }
    
    public void setLevelRequirement(final long levelRequirement) {
        this.levelRequirement = levelRequirement;
    }
    
    public void setCooldownMilliseconds(final long cooldownMilliseconds) {
        this.cooldownMilliseconds = cooldownMilliseconds;
    }
    
    public void setResettable(final boolean resettable) {
        this.resettable = resettable;
    }
    
    public void setDisplayInfo(final DisplayInfo displayInfo) {
        this.displayInfo = displayInfo;
    }
    
    public void setParsedArgs(final ArrayList<Object> parsedArgs) {
        this.parsedArgs = parsedArgs;
    }
    
    public Ability() {
        this.permission = "";
        this.bindable = false;
        this.resettable = true;
        this.parsedArgs = new ArrayList<Object>();
    }
    
    public Ability(final String hook, final String permission, final String args, final boolean bindable, final long levelRequirement, final long cooldownMilliseconds, final boolean resettable, final DisplayInfo displayInfo, final ArrayList<Object> parsedArgs) {
        this.permission = "";
        this.bindable = false;
        this.resettable = true;
        this.parsedArgs = new ArrayList<Object>();
        this.hook = hook;
        this.permission = permission;
        this.args = args;
        this.bindable = bindable;
        this.levelRequirement = levelRequirement;
        this.cooldownMilliseconds = cooldownMilliseconds;
        this.resettable = resettable;
        this.displayInfo = displayInfo;
        this.parsedArgs = parsedArgs;
    }
}
