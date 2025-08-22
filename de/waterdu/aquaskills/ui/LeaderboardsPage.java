//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import de.waterdu.aquaapi.ui.api.*;
import de.waterdu.aquaskills.file.*;

public class LeaderboardsPage implements IPage
{
    private final Player p;
    
    public LeaderboardsPage(final Player p) {
        this.p = p;
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(Config.settings().getSkillsPageRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public Set<Button> getButtons(final EntityPlayerMP player) {
        final Set<Button> buttons = new HashSet<Button>();
        for (final Experience xp : this.p.getXP()) {
            final Set<Button> set;
            xp.getSkill().ifPresent(skill -> {
                if (!skill.isASBP()) {
                    set.add(skill.getLeaderboardButton(player, (IPage)this, this.p, skill.getDisplayIndex()));
                }
                return;
            });
        }
        return buttons;
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        if (index == -999) {
            AquaUI.openUI(player, (IPage)new MainMenuPage(this.p));
        }
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return Config.format("leaderboardTitle", new Object[0]);
    }
}
