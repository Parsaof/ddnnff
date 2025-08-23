//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.sorcerer;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;

public class MindSliver implements ISpell
{
    private final double damage;
    private final double range;
    private final double speed;
    private final int nausea;
    
    public MindSliver(final double damage, final double range, final double speed, final int nausea) {
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.nausea = nausea;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        ParticleHelper.drawInterpolatedParticles(EnumParticleTypes.PORTAL, spell.getSize() / 2.0, 1, 0.0, 0.0, 0.0, 0.02, 3, spell, world);
        if (world.getTotalWorldTime() % 5L == 0L) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_ENDERMEN_AMBIENT, SoundCategory.MASTER, 0.2f, world.rand.nextFloat() + 0.5f);
        }
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_ENDERMEN_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        hit.attackEntityFrom(DamageHelper.causeSpellDamage(spell), (float)this.damage);
        PlayerHelper.addEffect(hit, MobEffects.NAUSEA, this.nausea, 2);
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
