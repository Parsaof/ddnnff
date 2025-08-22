//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import de.waterdu.aquaapi.ui.api.*;
import de.waterdu.aquaapi.ui.internal.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.file.*;

public class SkillsPage implements IPage
{
    private ItemStack[] tempItems;
    private int timer;
    private final Player p;
    
    public SkillsPage(final EntityPlayerMP player) {
        this((Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getUniqueID()));
    }
    
    public SkillsPage(final Player p) {
        this.tempItems = null;
        this.timer = 0;
        this.p = p;
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(Config.settings().getSkillsPageRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public Set<Button> getButtons(final EntityPlayerMP player) {
        final Set<Button> buttons = new HashSet<Button>();
        for (final Experience xp : this.p.getXP()) {
            final Set<Button> set;
            final Experience experience;
            xp.getSkill().ifPresent(skill -> {
                if (!skill.isASBP()) {
                    set.add(skill.getButton(experience, skill.getDisplayIndex(), true));
                }
                return;
            });
        }
        return buttons;
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        if (this.p.getUUID().equals(player.getUniqueID())) {
            if (index != -999) {
                for (final Experience xp : this.p.getXP()) {
                    final Experience experience;
                    xp.getSkill().ifPresent(skill -> {
                        if (!skill.isASBP() && skill.getDisplayIndex() == index) {
                            AquaUI.openUI(player, (IPage)new SkillPage(this.p, skill, experience));
                        }
                    });
                }
            }
            else {
                AquaUI.openUI(player, (IPage)new MainMenuPage(this.p));
            }
        }
    }
    
    public Buttons onUpdate(final long tick, final EntityPlayerMP player, final Buttons buttons) {
        if (this.p.getUUID().equals(player.getUniqueID())) {
            if (this.tempItems == null) {
                this.tempItems = new ItemStack[buttons.getInterfaceButtons().length];
            }
            ++this.timer;
            if (this.timer == 15 || this.timer == 30) {
                if (this.timer == 30) {
                    this.timer = 0;
                }
                for (int i = 0; i < buttons.getInterfaceButtons().length; ++i) {
                    final Button button = buttons.getButton(i);
                    if (button != null) {
                        for (final Experience xp : this.p.getXP()) {
                            if (xp.isUnread()) {
                                final int finalI = i;
                                final int n;
                                final Button button2;
                                xp.getSkill().ifPresent(skill -> {
                                    if (!skill.isASBP() && skill.getDisplayIndex() == n) {
                                        if (this.timer == 15) {
                                            this.tempItems[n] = button2.item;
                                            button2.setItem(new ItemStack(Items.NETHER_STAR));
                                        }
                                        else {
                                            button2.setItem(this.tempItems[n]);
                                        }
                                    }
                                    return;
                                });
                            }
                        }
                    }
                }
            }
        }
        return buttons;
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return Config.format(this.p.getUUID().equals(player.getUniqueID()) ? "skillsTitle" : "skillsOtherTitle", new Object[] { this.p.getName() });
    }
}
