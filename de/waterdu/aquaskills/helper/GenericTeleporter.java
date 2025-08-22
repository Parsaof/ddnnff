//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class GenericTeleporter extends Teleporter
{
    private final WorldServer worldServerInstance;
    private final Random random;
    
    public GenericTeleporter(final WorldServer worldIn) {
        super(worldIn);
        this.worldServerInstance = worldIn;
        this.random = worldIn.rand;
    }
    
    public void placeInPortal(final Entity entityIn, final float rotationYaw) {
    }
    
    public boolean placeInExistingPortal(final Entity entityIn, final float rotationYaw) {
        return false;
    }
    
    public boolean makePortal(final Entity entityIn) {
        return false;
    }
    
    public static class PortalPosition extends BlockPos
    {
        public long lastUpdateTime;
        
        public PortalPosition(final BlockPos pos, final long lastUpdate) {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.lastUpdateTime = lastUpdate;
        }
    }
}
