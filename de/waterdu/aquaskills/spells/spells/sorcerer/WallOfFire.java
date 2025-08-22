//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.sorcerer;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class WallOfFire implements ISpell
{
    private int duration;
    private final int fire;
    private final double radius;
    private boolean ignited;
    
    public WallOfFire(final int duration, final int fire, final double radius) {
        this.ignited = false;
        this.duration = duration;
        this.fire = fire;
        this.radius = radius;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
        if (spell.age()) {
            this.ignited = true;
            spell.setMotionX(0.0);
            spell.setMotionY(0.0);
            spell.setMotionZ(0.0);
            --this.duration;
            if (this.duration <= 0) {
                spell.setDead(world);
            }
            else {
                for (int i = 0; i < 150; ++i) {
                    final Position sphere = RandomHelper.nextSpherePoint(this.radius);
                    world.spawnParticle(EnumParticleTypes.FLAME, false, spell.getPosX() + sphere.getX(), spell.getPosY() + world.rand.nextDouble() * this.radius, spell.getPosZ() + sphere.getZ(), 1, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * 0.2, new int[0]);
                }
                if (world.getTotalWorldTime() % 3L == 0L) {
                    world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.MASTER, 0.2f, world.rand.nextFloat() + 0.5f);
                }
            }
        }
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        spell.setAge(5);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        if (this.ignited) {
            hit.setFire(this.fire);
        }
    }
    
    public double getRange() {
        return 100.0;
    }
    
    public double getSpeed() {
        return 3.0;
    }
    
    public double getSize() {
        return this.radius * 2.0;
    }
    
    public boolean doesExpireOnImpact() {
        return false;
    }
    
    public boolean hasNoclip() {
        return true;
    }
    
    public boolean canCollideMultipleTimes() {
        return true;
    }
}
