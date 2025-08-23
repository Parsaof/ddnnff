//r
//
//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import java.util.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommandSender;
import de.waterdu.aquaskills.AquaSkills;
import de.waterdu.aquaskills.helper.Config;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;
import de.waterdu.aquaapi.ui.api.Button;

public class ASBPReward {

    private int index;
    private HashMap<String, Internal> rewards;

    public ASBPReward(final int index, final String[] freeRewards, final RewardDisplay freeDisplay, final String freeMessage,
                      final String[] premiumRewards, final RewardDisplay premiumDisplay, final String premiumMessage) {
        this.rewards = new HashMap<String, Internal>();
        this.index = index;
        if (freeRewards.length > 0) {
            this.rewards.put("free", new Internal(freeRewards, freeDisplay, freeMessage));
        }
        this.rewards.put("premium", new Internal(premiumRewards, premiumDisplay, premiumMessage));
    }

    public boolean hasRewards(final ASBPTrack track) {
        final Internal internal = this.rewards.get(track.getId());
        return internal != null && internal.rewards.length > 0;
    }

    public void executeTrack(final EntityPlayerMP player, final ASBPTrack track) {
        final Internal internal = this.rewards.get(track.getId());
        if (internal != null) {
            this.execute(player, internal.rewards, internal.message);
        }
    }

    public void execute(final EntityPlayerMP player, final String[] rewards, final String message) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        for (final String reward : rewards) {
            if (reward != null) {
                server.getCommandManager().executeCommand((ICommandSender) server, reward.replace("@p", player.getName()));
            } else {
                AquaSkills.log.error("Null reward in ASBP! Index " + this.index + ".");
            }
        }
        // Remplace & par § en UTF-8 de manière sûre
        player.sendMessage((ITextComponent) new TextComponentString(
                Config.format("prefixASBPNeutral", new Object[0]) + message.replace("&", "\u00A7")
        ));
    }

    public Button getButton(final ASBPPlayer p, final ASBPTrack track, final int index) {
        final EntityPlayerMP player = p.getPlayerEntity();
        final Internal internal = this.rewards.get(track.getId());
        if (player == null || internal == null || internal.getRewards().length == 0) {
            return RewardDisplay.nullReward(index);
        }
        final RewardDisplay display = internal.display;
        return display.getButton(track, track.has(player), p.hasClaimed(this.getIndex(), track), p.getLevel() >= this.getIndex(), index);
    }

    public int getIndex() {
        return this.index;
    }

    public HashMap<String, Internal> getRewards() {
        return this.rewards;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setRewards(final HashMap<String, Internal> rewards) {
        this.rewards = rewards;
    }

    public ASBPReward() {
        this.rewards = new HashMap<String, Internal>();
    }

    public ASBPReward(final int index, final HashMap<String, Internal> rewards) {
        this.rewards = new HashMap<String, Internal>();
        this.index = index;
        this.rewards = rewards;
    }

    public static class Internal {
        private String[] rewards;
        private RewardDisplay display;
        private String message;

        public String[] getRewards() {
            return this.rewards;
        }

        public RewardDisplay getDisplay() {
            return this.display;
        }

        public String getMessage() {
            return this.message;
        }

        public void setRewards(final String[] rewards) {
            this.rewards = rewards;
        }

        public void setDisplay(final RewardDisplay display) {
            this.display = display;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public Internal() { }

        public Internal(final String[] rewards, final RewardDisplay display, final String message) {
            this.rewards = rewards;
            this.display = display;
            this.message = message;
        }
    }
}
