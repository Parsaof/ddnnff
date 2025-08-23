//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public interface ISpell
{
    void onTick(final EntitySpell p0, final WorldServer p1);
    
    void onCreate(final EntitySpell p0, final WorldServer p1);
    
    void onExpire(final EntitySpell p0, final WorldServer p1, final BlockPos p2);
    
    void onCollide(final EntitySpell p0, final EntityLivingBase p1, final WorldServer p2);
    
    double getRange();
    
    double getSpeed();
    
    default double getSize() {
        return 0.5;
    }
    
    default boolean doesExpireOnImpact() {
        return true;
    }
    
    default boolean hasNoclip() {
        return false;
    }
    
    default boolean canCollideMultipleTimes() {
        return false;
    }
    
    default boolean hasArmorStand() {
        return false;
    }
}
