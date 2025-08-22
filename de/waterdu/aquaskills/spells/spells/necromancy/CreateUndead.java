//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.necromancy;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class CreateUndead implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        EntityMob e;
        if (world.rand.nextBoolean()) {
            e = (EntityMob)new EntitySkeleton((World)world);
        }
        else {
            e = (EntityMob)new EntityZombie((World)world);
        }
        e.setPositionAndRotation(spell.getPosX(), spell.getPosY() + 1.0, spell.getPosZ(), world.rand.nextFloat() * 360.0f, world.rand.nextFloat() * 360.0f);
        world.spawnEntity((Entity)e);
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ParticleHelper.drawParticleCloud(50, EnumParticleTypes.SPELL_MOB, world, spell.getPosX(), spell.getPosY() + 1.0, spell.getPosZ(), 164, 17, 217);
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
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
