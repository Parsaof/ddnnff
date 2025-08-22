//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.entity.*;

public class NumberHelper
{
    public static double boundDouble(final double d) {
        return isFinite(d) ? d : ((d > 0.0) ? Double.MAX_VALUE : Double.MAX_VALUE);
    }
    
    public static float boundFloat(final double d) {
        return boundFloat((float)d);
    }
    
    public static float boundFloat(final float f) {
        return isFinite(f) ? f : ((f > 0.0f) ? Float.MAX_VALUE : -3.4028235E38f);
    }
    
    public static void addVelocity(final Entity e, final double x, final double y, final double z) {
        e.motionX = boundFloat(e.motionX + x);
        e.motionY = boundFloat(e.motionY + y);
        e.motionZ = boundFloat(e.motionZ + z);
        e.velocityChanged = true;
    }
    
    public static void setVelocity(final Entity e, final double x, final double y, final double z) {
        e.motionX = boundFloat(x);
        e.motionY = boundFloat(y);
        e.motionZ = boundFloat(z);
        e.velocityChanged = true;
    }
    
    public static boolean isFinite(final double d) {
        return Math.abs(d) <= Double.MAX_VALUE;
    }
    
    public static boolean isFinite(final float f) {
        return Math.abs(f) <= Float.MAX_VALUE;
    }
}
