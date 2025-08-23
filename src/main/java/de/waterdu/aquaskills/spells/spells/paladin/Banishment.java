//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.paladin;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;

public class Banishment implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        if (hit instanceof EntityMob && hit.isNonBoss()) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            ParticleHelper.drawParticleCloud(50, EnumParticleTypes.ENCHANTMENT_TABLE, world, hit.posX, hit.posY, hit.posZ, 0.05);
            hit.setDead();
        }
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
