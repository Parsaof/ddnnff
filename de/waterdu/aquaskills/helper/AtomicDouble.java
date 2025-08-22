//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

public class AtomicDouble
{
    private double value;
    
    public void add(final double value) {
        this.value += value;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    public AtomicDouble() {
        this.value = 0.0;
    }
    
    public AtomicDouble(final double value) {
        this.value = 0.0;
        this.value = value;
    }
}
