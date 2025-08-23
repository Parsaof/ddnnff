//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class DamageHelper
{
    public static DamageSource causeSpellFireDamage(final EntitySpell spell) {
        return new EntityDamageSource("onFire", (Entity)spell.getCaster().getPlayerEntity()).setFireDamage().setProjectile();
    }
    
    public static DamageSource causeSpellDamage(final EntitySpell spell) {
        return new EntityDamageSource("magic", (Entity)spell.getCaster().getPlayerEntity()).setFireDamage().setProjectile();
    }
}
