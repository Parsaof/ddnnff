//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import de.waterdu.aquaapi.file.api.*;
import java.text.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;

public class Settings implements IConfiguration
{
    private boolean async;
    private int threadPoolSize;
    private int xpGainSaveBuffer;
    private boolean autoswitchHotbarDefault;
    private boolean playSoundsDefault;
    private boolean fadeOutXPDefault;
    private boolean useDefaultSkillHooks;
    private int skillsPageRows;
    private int skillPageRows;
    private boolean canRebindItems;
    private int hotbarRefreshRate;
    private int hotbarDecimalPlaces;
    private String goBackItem;
    private String commandName;
    private String[] commandAliases;
    private boolean logToConsole;
    private static transient DecimalFormat df;
    
    public Settings() {
        this.async = false;
        this.threadPoolSize = 10;
        this.xpGainSaveBuffer = 20;
        this.autoswitchHotbarDefault = true;
        this.playSoundsDefault = true;
        this.fadeOutXPDefault = true;
        this.useDefaultSkillHooks = true;
        this.skillsPageRows = 3;
        this.skillPageRows = 6;
        this.canRebindItems = false;
        this.hotbarRefreshRate = 10;
        this.hotbarDecimalPlaces = 2;
        this.goBackItem = "barrier";
        this.commandName = "aquaskills";
        this.commandAliases = new String[] { "mmo", "skills", "skill", "aquaskill", "as" };
        this.logToConsole = false;
    }
    
    public Item goBackItem() {
        Item item = Item.getByNameOrId(this.goBackItem);
        if (item == null) {
            item = Item.getItemFromBlock(Blocks.BARRIER);
        }
        return item;
    }
    
    public static DecimalFormat getDecimalFormat() {
        if (Settings.df == null) {
            final StringBuilder builder = new StringBuilder("#");
            for (int i = 0; i < Config.settings().hotbarDecimalPlaces; ++i) {
                if (i == 0) {
                    builder.append(".");
                }
                builder.append("#");
            }
            Settings.df = new DecimalFormat(builder.toString());
        }
        return Settings.df;
    }
    
    public boolean isAsync() {
        return this.async;
    }
    
    public int getThreadPoolSize() {
        return this.threadPoolSize;
    }
    
    public int getXpGainSaveBuffer() {
        return this.xpGainSaveBuffer;
    }
    
    public boolean isAutoswitchHotbarDefault() {
        return this.autoswitchHotbarDefault;
    }
    
    public boolean isPlaySoundsDefault() {
        return this.playSoundsDefault;
    }
    
    public boolean isFadeOutXPDefault() {
        return this.fadeOutXPDefault;
    }
    
    public boolean isUseDefaultSkillHooks() {
        return this.useDefaultSkillHooks;
    }
    
    public int getSkillsPageRows() {
        return this.skillsPageRows;
    }
    
    public int getSkillPageRows() {
        return this.skillPageRows;
    }
    
    public boolean isCanRebindItems() {
        return this.canRebindItems;
    }
    
    public int getHotbarRefreshRate() {
        return this.hotbarRefreshRate;
    }
    
    public int getHotbarDecimalPlaces() {
        return this.hotbarDecimalPlaces;
    }
    
    public String getGoBackItem() {
        return this.goBackItem;
    }
    
    public String getCommandName() {
        return this.commandName;
    }
    
    public String[] getCommandAliases() {
        return this.commandAliases;
    }
    
    public boolean isLogToConsole() {
        return this.logToConsole;
    }
    
    public void setAsync(final boolean async) {
        this.async = async;
    }
    
    public void setThreadPoolSize(final int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
    
    public void setXpGainSaveBuffer(final int xpGainSaveBuffer) {
        this.xpGainSaveBuffer = xpGainSaveBuffer;
    }
    
    public void setAutoswitchHotbarDefault(final boolean autoswitchHotbarDefault) {
        this.autoswitchHotbarDefault = autoswitchHotbarDefault;
    }
    
    public void setPlaySoundsDefault(final boolean playSoundsDefault) {
        this.playSoundsDefault = playSoundsDefault;
    }
    
    public void setFadeOutXPDefault(final boolean fadeOutXPDefault) {
        this.fadeOutXPDefault = fadeOutXPDefault;
    }
    
    public void setUseDefaultSkillHooks(final boolean useDefaultSkillHooks) {
        this.useDefaultSkillHooks = useDefaultSkillHooks;
    }
    
    public void setSkillsPageRows(final int skillsPageRows) {
        this.skillsPageRows = skillsPageRows;
    }
    
    public void setSkillPageRows(final int skillPageRows) {
        this.skillPageRows = skillPageRows;
    }
    
    public void setCanRebindItems(final boolean canRebindItems) {
        this.canRebindItems = canRebindItems;
    }
    
    public void setHotbarRefreshRate(final int hotbarRefreshRate) {
        this.hotbarRefreshRate = hotbarRefreshRate;
    }
    
    public void setHotbarDecimalPlaces(final int hotbarDecimalPlaces) {
        this.hotbarDecimalPlaces = hotbarDecimalPlaces;
    }
    
    public void setGoBackItem(final String goBackItem) {
        this.goBackItem = goBackItem;
    }
    
    public void setCommandName(final String commandName) {
        this.commandName = commandName;
    }
    
    public void setCommandAliases(final String[] commandAliases) {
        this.commandAliases = commandAliases;
    }
    
    public void setLogToConsole(final boolean logToConsole) {
        this.logToConsole = logToConsole;
    }
    
    static {
        Settings.df = null;
    }
}
