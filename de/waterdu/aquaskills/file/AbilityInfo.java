//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import java.util.*;

public class AbilityInfo
{
    private ArrayList<Double> doubles;
    private ArrayList<String> strings;
    private ArrayList<Integer> integers;
    
    public ArrayList<Double> getDoubles() {
        return this.doubles;
    }
    
    public ArrayList<String> getStrings() {
        return this.strings;
    }
    
    public ArrayList<Integer> getIntegers() {
        return this.integers;
    }
    
    public void setDoubles(final ArrayList<Double> doubles) {
        this.doubles = doubles;
    }
    
    public void setStrings(final ArrayList<String> strings) {
        this.strings = strings;
    }
    
    public void setIntegers(final ArrayList<Integer> integers) {
        this.integers = integers;
    }
    
    public AbilityInfo() {
        this.doubles = new ArrayList<Double>();
        this.strings = new ArrayList<String>();
        this.integers = new ArrayList<Integer>();
    }
}
