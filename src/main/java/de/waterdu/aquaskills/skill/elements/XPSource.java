//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill.elements;

import java.util.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.hooks.*;

public class XPSource implements IHookable
{
    private String hook;
    private String permission;
    private String args;
    private double amount;
    private transient ArrayList<Object> parsedArgs;
    
    public XPSource(final String hook, final String args, final double amount) {
        this.permission = "";
        this.parsedArgs = new ArrayList<Object>();
        this.hook = hook;
        this.args = args;
        this.amount = amount;
    }
    
    public MethodData prepare(final Player player, final Skill skill) {
        return new MethodData(player, skill, (IHookable)this);
    }
    
    public boolean areArgsParsed() {
        return !this.parsedArgs.isEmpty();
    }
    
    public void reset() {
        this.parsedArgs.clear();
    }
    
    public String getHook() {
        return this.hook;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public String getArgs() {
        return this.args;
    }
    
    public double getAmount() {
        return this.amount;
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
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
    
    public void setParsedArgs(final ArrayList<Object> parsedArgs) {
        this.parsedArgs = parsedArgs;
    }
    
    public XPSource() {
        this.permission = "";
        this.parsedArgs = new ArrayList<Object>();
    }
    
    public XPSource(final String hook, final String permission, final String args, final double amount, final ArrayList<Object> parsedArgs) {
        this.permission = "";
        this.parsedArgs = new ArrayList<Object>();
        this.hook = hook;
        this.permission = permission;
        this.args = args;
        this.amount = amount;
        this.parsedArgs = parsedArgs;
    }
}
