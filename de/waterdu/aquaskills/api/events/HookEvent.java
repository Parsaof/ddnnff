//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.api.events;

import de.waterdu.aquaskills.hooks.*;
import net.minecraftforge.fml.common.eventhandler.*;

public abstract class HookEvent extends Event
{
    public final MethodData data;
    
    public HookEvent(final MethodData data) {
        this.data = data;
    }
    
    @Cancelable
    public static class Pre extends HookEvent
    {
        public Pre(final MethodData data) {
            super(data);
        }
    }
    
    public static class Post extends HookEvent
    {
        public Post(final MethodData data) {
            super(data);
        }
    }
}
