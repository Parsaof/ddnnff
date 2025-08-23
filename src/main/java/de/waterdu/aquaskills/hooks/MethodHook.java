//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks;

import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;

public class MethodHook<T extends Event>
{
    private final Class<T> type;
    private final BiConsumer<T, MethodData> consumer;
    
    public MethodHook(final Class<T> type, final BiConsumer<T, MethodData> consumer) {
        this.type = type;
        this.consumer = consumer;
    }
    
    public void consume(final Event event, final MethodData data) {
        if (event != null && event.getClass().equals(this.type)) {
            this.consumer.accept(this.type.cast(event), data);
        }
    }
    
    public Class<T> getType() {
        return this.type;
    }
}
