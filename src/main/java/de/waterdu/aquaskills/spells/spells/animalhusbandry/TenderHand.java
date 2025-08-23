//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.animalhusbandry;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;

public class TenderHand implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 2.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        if (hit instanceof EntityAnimal) {
            hit.heal(hit.getMaxHealth());
            ParticleHelper.drawParticleCloud(10, EnumParticleTypes.SPELL_MOB, world, hit.posX, hit.posY, hit.posZ, 254, 222, 255);
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
