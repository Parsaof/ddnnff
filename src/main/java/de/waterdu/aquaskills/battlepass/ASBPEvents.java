//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.file.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.battlepass.ui.*;
import de.waterdu.aquaapi.ui.api.*;

public class ASBPEvents
{
    public ASBPEvents() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP)event.player;
            ASBPPlayer p = (ASBPPlayer)AquaConfig.get("aquaskills", (Class)ASBPPlayer.class, player.getUniqueID());
            if (p == null) {
                p = new ASBPPlayer(player);
                AquaConfig.put("aquaskills", (Class)ASBPPlayer.class, (IData)p);
            }
            p.setName(player.getName());
            p.save();
            if (Config.settingsASBP().isOnlyASBPEnabled()) {
                final Player asPlayer = (Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getUniqueID());
                if (asPlayer != null) {
                    asPlayer.setAutoswitchHotbar(true);
                    asPlayer.setHotbar(Config.settingsASBP().getASBPSkillName(), player);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onCommand(final CommandEvent event) {
        if (Config.settingsASBP().isAsbpOnly() && event.getCommand().getName().equalsIgnoreCase("aquaskills") && (!PermHelper.canUse("asbp.admin", event.getSender()) || event.getParameters().length == 0)) {
            event.setCanceled(true);
            if (event.getSender() instanceof EntityPlayerMP) {
                final EntityPlayerMP player = (EntityPlayerMP)event.getSender();
                AquaUI.openUI(player, (IPage)new ASBPPage(player));
            }
        }
    }
}
