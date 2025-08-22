//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks;

import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import de.waterdu.aquaskills.file.*;

public class EventHook<T extends Event>
{
    private final Class<T> type;
    private final BiConsumer<T, Player> consumer;
    
    public EventHook(final Class<T> type, final BiConsumer<T, Player> consumer) {
        this.type = type;
        this.consumer = consumer;
    }
    
    public Class<T> getEvent() {
        return this.type;
    }
    
    public void consume(final Event event, final Player player) {
        if (event != null) {
            this.consumer.accept(this.type.cast(event), player);
        }
    }
}
