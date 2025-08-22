//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill.elements;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.hooks.*;

public interface IHookable
{
    String getHook();
    
    String getPermission();
    
    MethodData prepare(final Player p0, final Skill p1);
    
    void reset();
}
