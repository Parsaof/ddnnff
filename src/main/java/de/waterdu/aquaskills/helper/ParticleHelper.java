//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.util.*;
import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;

public class ParticleHelper
{
    public static void drawInterpolatedParticles(final EnumParticleTypes particle, final double size, final int particles, final double ox, final double oy, final double oz, final double spd, final int density, final EntitySpell spell, final WorldServer world) {
        drawInterpolatedParticles(particle, size, particles, ox, oy, oz, spd, density, spell, world, 0.0, 0.0, 0.0);
    }
    
    public static void drawInterpolatedParticles(final EnumParticleTypes particle, final double size, final int particles, final double ox, final double oy, final double oz, final double spd, final int density, final EntitySpell spell, final WorldServer world, final double xMod, final double yMod, final double zMod) {
        drawInterpolatedParticles(particle, size, particles, ox, oy, oz, spd, density, spell, world, xMod, yMod, zMod, false);
    }
    
    public static void drawInterpolatedParticles(final EnumParticleTypes particle, final double size, final int particles, final double ox, final double oy, final double oz, final double spd, final int density, final EntitySpell spell, final WorldServer world, final double xMod, final double yMod, final double zMod, final boolean override) {
        drawInterpolatedParticles(false, particle, size, particles, ox, oy, oz, spd, density, spell, world, xMod, yMod, zMod, override);
    }
    
    public static void drawInterpolatedParticles(final boolean longRange, final EnumParticleTypes particle, final double size, final int particles, final double ox, final double oy, final double oz, final double spd, final int density, final EntitySpell spell, final WorldServer world, final double xMod, final double yMod, final double zMod, final boolean override) {
        for (int i = 0; i < density; ++i) {
            for (int count = (particles == 0) ? 1 : particles, j = 0; j < count; ++j) {
                final Position pos = spell.getInterpolatedPosition(i / (double)density);
                final Position sphere = RandomHelper.nextSpherePoint(size);
                world.spawnParticle(particle, longRange, override ? xMod : (pos.getX() + sphere.getX() + xMod), override ? yMod : (pos.getY() + sphere.getY() + yMod), override ? zMod : (pos.getZ() + sphere.getZ() + zMod), (int)((particles != 0) ? 1 : 0), ox, oy, oz, spd, new int[0]);
            }
        }
    }
    
    public static void drawParticleCloud(final int particles, final EnumParticleTypes particle, final WorldServer world, final double posX, final double posY, final double posZ, final double speed) {
        for (int i = 0; i < particles; ++i) {
            world.spawnParticle(particle, false, posX + world.rand.nextDouble() * 2.0 - 1.0, posY + world.rand.nextDouble() * 2.0, posZ + world.rand.nextDouble() * 2.0 - 1.0, 1, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * 2.0 - 1.0, world.rand.nextDouble() * speed, new int[0]);
        }
    }
    
    public static void drawParticleCloud(final int particles, final EnumParticleTypes particle, final WorldServer world, final double posX, final double posY, final double posZ, final int r, final int g, final int b) {
        for (int i = 0; i < particles; ++i) {
            world.spawnParticle(particle, false, posX + world.rand.nextDouble() * 2.0 - 1.0, posY + world.rand.nextDouble() * 2.0, posZ + world.rand.nextDouble() * 2.0 - 1.0, 0, r / 255.0, g / 255.0, b / 255.0, 1.0, new int[0]);
        }
    }
}
