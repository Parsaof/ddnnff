//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.wizard;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.helper.*;

public class FireBolt implements ISpell
{
    private final double damage;
    private final double range;
    private final double speed;
    private final int fire;
    
    public FireBolt(final double damage, final double range, final double speed, final int fire) {
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.fire = fire;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        ParticleHelper.drawInterpolatedParticles(EnumParticleTypes.LAVA, spell.getSize() / 2.0, 1, 0.0, 0.0, 0.0, 0.02, 3, spell, world);
        if (world.getTotalWorldTime() % 5L == 0L) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER, 0.2f, world.rand.nextFloat() + 0.5f);
        }
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (pos != null) {
            PlayerHelper.setBlock(spell.getCaster().getPlayerEntity(), pos.add(0, 1, 0), Blocks.FIRE.getDefaultState(), true);
        }
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        hit.attackEntityFrom(DamageHelper.causeSpellFireDamage(spell), (float)this.damage);
        hit.setFire(this.fire);
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
