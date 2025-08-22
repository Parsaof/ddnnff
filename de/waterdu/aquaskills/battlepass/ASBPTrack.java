//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.command.*;

public class ASBPTrack
{
    private String id;
    private String perm;
    private String item;
    private String name;
    private String lore;
    private String require;
    private int line;
    
    public boolean has(final EntityPlayerMP player) {
        return this.perm == null || this.perm.isEmpty() || PermHelper.canUse(this.perm, (ICommandSender)player);
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getPerm() {
        return this.perm;
    }
    
    public String getItem() {
        return this.item;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLore() {
        return this.lore;
    }
    
    public String getRequire() {
        return this.require;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public void setPerm(final String perm) {
        this.perm = perm;
    }
    
    public void setItem(final String item) {
        this.item = item;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setLore(final String lore) {
        this.lore = lore;
    }
    
    public void setRequire(final String require) {
        this.require = require;
    }
    
    public void setLine(final int line) {
        this.line = line;
    }
    
    public ASBPTrack() {
    }
    
    public ASBPTrack(final String id, final String perm, final String item, final String name, final String lore, final String require, final int line) {
        this.id = id;
        this.perm = perm;
        this.item = item;
        this.name = name;
        this.lore = lore;
        this.require = require;
        this.line = line;
    }
}
