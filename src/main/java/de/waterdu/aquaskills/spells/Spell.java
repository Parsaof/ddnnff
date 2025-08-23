//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class Spell implements ISpell
{
    private final ISpell callback;
    
    public Spell(final ISpell callback) {
        this.callback = callback;
    }
    
    public ISpell getInternal() {
        return this.callback;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        this.callback.onTick(spell, world);
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        this.callback.onCreate(spell, world);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        this.callback.onExpire(spell, world, pos);
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        this.callback.onCollide(spell, hit, world);
    }
    
    public double getRange() {
        return this.callback.getRange();
    }
    
    public double getSpeed() {
        return this.callback.getSpeed();
    }
    
    public double getSize() {
        return this.callback.getSize();
    }
    
    public boolean doesExpireOnImpact() {
        return this.callback.doesExpireOnImpact();
    }
    
    public boolean hasNoclip() {
        return this.callback.hasNoclip();
    }
    
    public boolean canCollideMultipleTimes() {
        return this.callback.canCollideMultipleTimes();
    }
    
    public boolean hasArmorStand() {
        return this.callback.hasArmorStand();
    }
}
