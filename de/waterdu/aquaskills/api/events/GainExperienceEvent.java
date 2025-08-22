//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.api.events;

import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.file.*;

@Cancelable
public class GainExperienceEvent extends Event
{
    public final Player player;
    public final Skill skill;
    public double experience;
    
    public GainExperienceEvent(final Player player, final Skill skill, final double experience) {
        this.player = player;
        this.skill = skill;
        this.experience = experience;
    }
}
