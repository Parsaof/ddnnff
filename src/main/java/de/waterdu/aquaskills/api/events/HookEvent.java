//r

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
