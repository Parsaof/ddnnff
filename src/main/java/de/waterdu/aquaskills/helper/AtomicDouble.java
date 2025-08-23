//r

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
