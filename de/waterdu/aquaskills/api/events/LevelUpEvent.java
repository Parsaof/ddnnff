//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.api.events;

import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.file.*;

@Cancelable
public class LevelUpEvent extends Event
{
    public final Player player;
    public final Skill skill;
    public final long level;
    
    public LevelUpEvent(final Player player, final Skill skill, final long level) {
        this.player = player;
        this.skill = skill;
        this.level = level;
    }
}
