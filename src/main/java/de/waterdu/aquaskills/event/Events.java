//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.event;

import net.minecraftforge.common.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.text.*;
import net.minecraft.server.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import de.waterdu.aquaskills.leaderboard.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraftforge.event.world.*;
import de.waterdu.aquaskills.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Events
{
    private static final UUID UUID_Waterdude;
    private int refresh;
    private int refreshRate;
    
    public Events() {
        this.refresh = 0;
        this.refreshRate = -999;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP)event.player;
            Player p = (Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getPersistentID());
            if (p == null) {
                p = new Player(player);
                AquaConfig.put("aquaskills", (Class)Player.class, (IData)p);
            }
            else {
                p.init();
            }
            p.setName(player.getName());
            p.save(true);
            if (p.getHotbar() != null) {
                final Optional<Experience> xp = p.getXP(p.getHotbar());
                if (xp.isPresent()) {
                    xp.get().setHotbar();
                }
                else {
                    p.setHotbar(null, player);
                }
            }
            else {
                PlayerHelper.clearBottomTitle(player);
            }
            sendSpecialLoginMessage(player);
        }
    }
    
    public static void sendSpecialLoginMessage(final EntityPlayerMP player) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        final UUID uuid = player.getPersistentID();
        if (uuid.equals(Events.UUID_Waterdude)) {
            server.getPlayerList().sendMessage((ITextComponent)new TextComponentString(TextFormatting.AQUA + "Waterdude" + TextFormatting.GOLD + ", the developer of " + TextFormatting.AQUA + "Aqua" + TextFormatting.DARK_AQUA + "Skills" + TextFormatting.GOLD + ", has joined!"));
        }
    }
    
    @SubscribeEvent
    public void onLogout(final PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            final Player p = (Player)AquaConfig.get("aquaskills", (Class)Player.class, event.player.getPersistentID());
            if (p != null) {
                p.setPlayerEntity(null);
                p.save(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START) {
            TimeHelper.tick();
            ++this.refresh;
            if (this.refresh >= 6000) {
                this.refresh = 0;
                Leaderboard.get().refresh();
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START) {
            if (this.refreshRate == -999) {
                this.refreshRate = Config.settings().getHotbarRefreshRate();
            }
            if (this.refreshRate > 0) {
                final EntityPlayerMP player = (EntityPlayerMP)event.player;
                final Player p = (Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getUniqueID());
                if (p != null && !p.shouldFadeOutXP() && player.world.getWorldTime() % this.refreshRate == 0L && p.getHotbar() != null) {
                    p.getXP(p.getHotbar()).ifPresent(Experience::setHotbar);
                }
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlaceBlock(final BlockEvent.EntityPlaceEvent event) {
        AquaSkills.blockLog.add(event.getPos());
    }
    
    @SubscribeEvent
    public void onClone(final net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        event.getEntityPlayer().getEntityData().setIntArray("VisitedBiomes", event.getOriginal().getEntityData().getIntArray("VisitedBiomes"));
    }
    
    static {
        UUID_Waterdude = UUID.fromString("04e10682-dfe3-4c2f-9bcb-5e04ec7b647d");
    }
}
