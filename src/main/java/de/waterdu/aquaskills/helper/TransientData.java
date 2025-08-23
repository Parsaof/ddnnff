//r

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
