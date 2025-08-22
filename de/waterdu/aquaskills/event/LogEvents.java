//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.event;

import de.waterdu.aquaskills.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.api.events.*;

public class LogEvents
{
    public LogEvents() {
        AquaSkills.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onAbilityUse(final AbilityUseEvent event) {
        final EntityPlayerMP player = event.player.getPlayerEntity();
        if (player != null) {
            ASLogger.log(ASLogger.Severity.INFO, player.getName() + " (" + player.getUniqueID().toString() + ") used " + event.ability.getName() + " in " + event.skill.getName() + " at " + player.getPosition().toString() + " in dimension " + player.dimension);
        }
    }
    
    @SubscribeEvent
    public void onGainXP(final GainExperienceEvent event) {
        final EntityPlayerMP player = event.player.getPlayerEntity();
        if (player != null) {
            ASLogger.log(ASLogger.Severity.INFO, player.getName() + " (" + player.getUniqueID().toString() + ") gained " + event.experience + " XP in " + event.skill.getName() + " at " + player.getPosition().toString() + " in dimension " + player.dimension);
        }
    }
    
    @SubscribeEvent
    public void onLevelUp(final LevelUpEvent event) {
        final EntityPlayerMP player = event.player.getPlayerEntity();
        if (player != null) {
            ASLogger.log(ASLogger.Severity.INFO, player.getName() + " (" + player.getUniqueID().toString() + ") achieved level " + event.level + " in " + event.skill.getName() + " at " + player.getPosition().toString() + " in dimension " + player.dimension);
        }
    }
}
