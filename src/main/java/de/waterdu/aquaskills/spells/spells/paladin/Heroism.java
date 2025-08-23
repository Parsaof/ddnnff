//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.paladin;

import net.minecraft.entity.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;

public class Heroism implements ISpell
{
    private final float absorption;
    private EntityLivingBase collided;
    
    public Heroism(final float absorption) {
        this.collided = null;
        this.absorption = absorption;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        if (this.collided == null) {
            this.collided = (EntityLivingBase)spell.getCaster().getPlayerEntity();
        }
        if (this.collided != null) {
            this.collided.setAbsorptionAmount(this.absorption);
            ParticleHelper.drawParticleCloud(10, EnumParticleTypes.SPELL_MOB, world, this.collided.posX, this.collided.posY, this.collided.posZ, 207, 172, 0);
        }
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        this.collided = hit;
    }
    
    public double getRange() {
        return 10.0;
    }
    
    public double getSpeed() {
        return 2.0;
    }
    
    public double getSize() {
        return 1.0;
    }
}
