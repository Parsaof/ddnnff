//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.wizard;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class Fireball implements ISpell
{
    private final double power;
    private final double range;
    private final double speed;
    
    public Fireball(final double power, final double range, final double speed) {
        this.power = power;
        this.range = range;
        this.speed = speed;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        ParticleHelper.drawInterpolatedParticles(EnumParticleTypes.LAVA, spell.getSize() / 2.0, 1, 0.0, 0.0, 0.0, 0.0, 3, spell, world);
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        world.newExplosion((Entity)spell.getCaster().getPlayerEntity(), spell.getPosX(), spell.getPosY(), spell.getPosZ(), (float)this.power, true, false);
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
    }
    
    public double getRange() {
        return this.range;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public double getSize() {
        return 0.25;
    }
}
