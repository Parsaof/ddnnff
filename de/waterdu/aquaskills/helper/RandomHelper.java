//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import java.util.*;

public class RandomHelper
{
    private static Random random;
    
    public static int nextInt(final int bound) {
        return RandomHelper.random.nextInt(bound);
    }
    
    public static float nextFloat() {
        return RandomHelper.random.nextFloat();
    }
    
    public static double nextDouble() {
        return RandomHelper.random.nextDouble();
    }
    
    public static double nextVariance(final double range) {
        return range * (RandomHelper.random.nextDouble() - 0.5);
    }
    
    public static Position nextSpherePoint(final double radius) {
        final double theta = RandomHelper.random.nextDouble() * 2.0 * 3.141592653589793;
        final double phi = (RandomHelper.random.nextDouble() - 0.5) * 3.141592653589793;
        final double rad = RandomHelper.random.nextDouble() * radius;
        final double x = rad * Math.cos(theta) * Math.cos(phi);
        final double y = rad * Math.sin(phi);
        final double z = rad * Math.sin(theta) * Math.cos(phi);
        return new Position(x, y, z);
    }
    
    public static <T> T getRandomElementFromCollection(final Collection<T> collection) {
        final int index = nextInt(collection.size());
        final Iterator<T> iterator = collection.iterator();
        for (int i = 0; i < index; ++i) {
            iterator.next();
        }
        return iterator.next();
    }
    
    static {
        RandomHelper.random = new Random();
    }
}
