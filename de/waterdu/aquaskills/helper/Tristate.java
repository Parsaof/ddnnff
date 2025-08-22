//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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
