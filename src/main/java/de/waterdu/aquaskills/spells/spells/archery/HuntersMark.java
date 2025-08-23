//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.archery;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;

public class HuntersMark implements ISpell
{
    private final int dur;
    
    public HuntersMark(final int dur) {
        this.dur = dur;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        PlayerHelper.addEffect(hit, MobEffects.WEAKNESS, this.dur, 1);
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_ARROW_HIT, SoundCategory.PLAYERS, 1.0f, 0.5f);
        ParticleHelper.drawParticleCloud(10, EnumParticleTypes.SPELL_MOB, world, hit.posX, hit.posY, hit.posZ, 30, 112, 0);
    }
    
    public double getRange() {
        return 20.0;
    }
    
    public double getSpeed() {
        return 2.0;
    }
    
    public double getSize() {
        return 1.0;
    }
}
