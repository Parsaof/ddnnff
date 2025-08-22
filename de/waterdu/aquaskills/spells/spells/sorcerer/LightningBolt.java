//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.sorcerer;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;

public class LightningBolt implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
        final EntityLightningBolt bolt = new EntityLightningBolt((World)world, spell.getPosX(), spell.getPosY(), spell.getPosZ(), false);
        world.weatherEffects.add(bolt);
        final SPacketSpawnGlobalEntity packet = new SPacketSpawnGlobalEntity((Entity)bolt);
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendToAllNearExcept((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), 100.0, world.provider.getDimension(), (Packet)packet);
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
    }
    
    public double getRange() {
        return 50.0;
    }
    
    public double getSpeed() {
        return 5.0;
    }
    
    public double getSize() {
        return 1.0;
    }
}
