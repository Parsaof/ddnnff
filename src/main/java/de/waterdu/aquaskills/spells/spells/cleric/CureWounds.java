//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.cleric;

import net.minecraft.entity.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;

public class CureWounds implements ISpell
{
    private final float heal;
    private EntityLivingBase collided;
    
    public CureWounds(final float heal) {
        this.collided = null;
        this.heal = heal;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 2.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        if (this.collided == null) {
            this.collided = (EntityLivingBase)spell.getCaster().getPlayerEntity();
        }
        if (this.collided != null) {
            this.collided.heal(this.heal);
            ParticleHelper.drawParticleCloud(10, EnumParticleTypes.SPELL_MOB, world, this.collided.posX, this.collided.posY, this.collided.posZ, 227, 198, 120);
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
