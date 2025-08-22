//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.event.internal;

import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.spells.spells.warlock.*;

public class EldritchBlastEvent extends Event
{
    public final Player player;
    public final EldritchBlast eldritchBlast;
    
    public EldritchBlastEvent(final Player player, final EldritchBlast eldritchBlast) {
        this.player = player;
        this.eldritchBlast = eldritchBlast;
    }
}
