//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

public enum Tristate
{
    ALL, 
    SOME, 
    NONE;
    
    public static Tristate get(final int count, final int max) {
        if (count >= max) {
            return Tristate.ALL;
        }
        if (count > 0) {
            return Tristate.SOME;
        }
        return Tristate.NONE;
    }
    
    public boolean areEnabled() {
        return this.ordinal() < 2;
    }
}
