//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.warlock;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class EldritchBlast implements ISpell
{
    public double damage;
    public double range;
    public double speed;
    public int wither;
    public int slow;
    public boolean grasp;
    
    public EldritchBlast(final double damage, final double range, final double speed) {
        this.wither = 0;
        this.slow = 0;
        this.grasp = false;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        ParticleHelper.drawInterpolatedParticles(EnumParticleTypes.REDSTONE, spell.getSize() / 2.0, 0, -0.6799999999999999, 0.0, 0.71, 1.0, 3, spell, world);
        if (world.getTotalWorldTime() % 5L == 0L) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_SILVERFISH_AMBIENT, SoundCategory.MASTER, 0.2f, world.rand.nextFloat() + 0.5f);
        }
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        AquaSkills.EVENT_BUS.post((Event)new EldritchBlastEvent(spell.getCaster(), this));
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_SILVERFISH_HURT, SoundCategory.PLAYERS, 0.7f, 0.5f);
        hit.attackEntityFrom(DamageHelper.causeSpellDamage(spell), (float)this.damage);
        final EntityPlayerMP caster = spell.getCaster().getPlayerEntity();
        if (this.grasp && caster != null) {
            PlayerHelper.lookAt(new Position(caster.getPosition()), hit);
            NumberHelper.setVelocity((Entity)hit, -caster.getLookVec().x, caster.motionY + 0.25, -caster.getLookVec().z);
        }
        if (this.slow > 0) {
            PlayerHelper.addEffect(hit, MobEffects.SLOWNESS, this.slow, 1);
        }
        if (this.wither > 0) {
            PlayerHelper.addEffect(hit, MobEffects.WITHER, this.wither, 1);
        }
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
