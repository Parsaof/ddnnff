//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.leaderboard.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaapi.ui.api.*;

public class LeaderboardPage implements IPage
{
    private final UIDef ui;
    private final int size;
    private final Skill skill;
    private int offset;
    private final IPage parent;
    private final ArrayList<Player> sortedPlayers;
    
    public LeaderboardPage(final Skill skill, final IPage parent) {
        this.ui = UI.getUI("leaderboard");
        this.size = this.ui.getButtonCount() - 3;
        this.skill = skill;
        this.offset = 0;
        this.parent = parent;
        this.sortedPlayers = (ArrayList<Player>)Leaderboard.get().getLeaderboard(this.skill);
    }
    
    public LeaderboardPage(final Skill skill, final int offset, final IPage parent, final ArrayList<Player> sortedPlayers) {
        this.ui = UI.getUI("leaderboard");
        this.size = this.ui.getButtonCount() - 3;
        this.skill = skill;
        this.offset = offset;
        this.parent = parent;
        this.sortedPlayers = sortedPlayers;
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setTitleAlignment(PageOptions.TextAlignment.CENTER).setRows(this.ui.getRows()).setInventoryHidden(true).build();
    }
    
    public void addButtons(final EntityPlayerMP player, final Set<Button> buttons) {
        final Player p = Config.player(player);
        if (this.sortedPlayers.size() > this.size) {
            if (this.offset < 0) {
                this.offset = 0;
            }
            if (this.offset > this.sortedPlayers.size() - this.size) {
                this.offset = this.sortedPlayers.size() - this.size;
            }
        }
        else {
            this.offset = 0;
        }
        if (this.offset > 0) {
            buttons.add(this.ui.getButton(1).build());
        }
        if (this.offset < this.sortedPlayers.size() - this.size) {
            buttons.add(this.ui.getButton(2).build());
        }
        for (int i = 0; i < this.size; ++i) {
            final int index = i + this.offset;
            if (index < this.sortedPlayers.size()) {
                final Player o = this.sortedPlayers.get(index);
                final Button.Builder builder = this.ui.getButton(i + 3);
                this.skill.addDetailsToButton(builder, o, o.getUUID().equals(player.getUniqueID()), index);
                buttons.add(builder.build());
            }
        }
        final Button.Builder builder2 = this.ui.getButton(0);
        this.skill.addDetailsToButton(builder2, p, true, Leaderboard.get().getPos(this.skill, p));
        buttons.add(builder2.build());
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickType) {
        if (index == -999) {
            AquaUI.openUI(player, this.parent);
        }
        else if (index == 9) {
            AquaUI.openUI(player, (IPage)new LeaderboardPage(this.skill, this.offset - this.size, this.parent, this.sortedPlayers));
        }
        else if (index == 17) {
            AquaUI.openUI(player, (IPage)new LeaderboardPage(this.skill, this.offset + this.size, this.parent, this.sortedPlayers));
        }
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return this.ui.getTitle().replace("%1", this.skill.getDisplayName());
    }
}
