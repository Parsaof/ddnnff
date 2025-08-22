//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import java.util.*;

public class TransientData
{
    public static final HashSet<UUID> CAPTURED_POKEMON;
    
    public static void gc() {
        TransientData.CAPTURED_POKEMON.clear();
    }
    
    static {
        CAPTURED_POKEMON = new HashSet<UUID>();
    }
}
