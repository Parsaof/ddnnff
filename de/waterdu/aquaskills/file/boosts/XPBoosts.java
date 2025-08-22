//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file.boosts;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.helper.*;
import java.util.*;

public class XPBoosts implements IConfiguration
{
    private HashSet<XPBoost> boosts;
    
    public static void addBoost(final Player player, final Skill skill, final int duration, final double rate, final double boost) {
        ((XPBoosts)AquaConfig.get("aquaskills", (Class)XPBoosts.class)).boosts.add(new XPBoost(player, skill, duration, rate, boost));
        AquaConfig.save("aquaskills", (Class)XPBoosts.class);
    }
    
    public static double apply(final double experience, final Player player, final Skill skill) {
        final long time = System.currentTimeMillis();
        final AtomicDouble atomicExperience = new AtomicDouble(experience);
        boolean removed = false;
        final XPBoosts boosts = (XPBoosts)AquaConfig.get("aquaskills", (Class)XPBoosts.class);
        final Iterator<XPBoost> iterator = boosts.boosts.iterator();
        while (iterator.hasNext()) {
            final XPBoost boost = iterator.next();
            if (boost.apply(atomicExperience, player, skill, time)) {
                iterator.remove();
                removed = true;
            }
        }
        if (removed) {
            AquaConfig.save("aquaskills", (Class)XPBoosts.class);
        }
        return atomicExperience.getValue();
    }
    
    public HashSet<XPBoost> getBoosts() {
        return this.boosts;
    }
    
    public void setBoosts(final HashSet<XPBoost> boosts) {
        this.boosts = boosts;
    }
    
    public XPBoosts() {
        this.boosts = new HashSet<XPBoost>();
    }
}
