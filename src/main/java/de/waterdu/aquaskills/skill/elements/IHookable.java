//r

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
