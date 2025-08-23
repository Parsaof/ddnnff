//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.swordsmanship;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class Bladesinging implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        final EntityPlayerMP player = spell.getCaster().getPlayerEntity();
        world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1.0f, 1.0f);
        for (int i = 0; i < 100; ++i) {
            world.spawnParticle(EnumParticleTypes.PORTAL, false, player.posX + world.rand.nextDouble() * 2.0 - 1.0, player.posY + world.rand.nextDouble() * 2.0, player.posZ + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
        }
        player.connection.setPlayerLocation(spell.getPosX(), spell.getPosY() + 1.5, spell.getPosZ(), player.rotationYaw, player.rotationPitch);
        world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1.0f, 1.0f);
        for (int i = 0; i < 100; ++i) {
            world.spawnParticle(EnumParticleTypes.PORTAL, false, spell.getPosX() + world.rand.nextDouble() * 2.0 - 1.0, spell.getPosY() + 1.0 + world.rand.nextDouble() * 2.0, spell.getPosZ() + world.rand.nextDouble() * 2.0 - 1.0, 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
    }
    
    public double getRange() {
        return 15.0;
    }
    
    public double getSpeed() {
        return 2.0;
    }
    
    public double getSize() {
        return 1.0;
    }
}
