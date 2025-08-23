//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import de.waterdu.aquaskills.file.*;
import net.minecraft.entity.player.*;
import java.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.skill.*;
import de.waterdu.aquaapi.ui.api.*;

public class OptionsPage implements IPage
{
    private final Player p;
    
    public OptionsPage(final Player p) {
        this.p = p;
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(5).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public Set<Button> getButtons(final EntityPlayerMP player) {
        final Set<Button> buttons = new HashSet<Button>();
        final int chat = this.p.getMessageState();
        if (chat == 0) {
            buttons.add(new Button(Config.format("hidingAll", new Object[0]), (String)null, new ItemStack(Items.ENDER_PEARL), 10));
        }
        else if (chat == 1) {
            buttons.add(new Button(Config.format("showingSome", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), 10));
        }
        else if (chat == 2) {
            buttons.add(new Button(Config.format("showingAll", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), 10));
        }
        final boolean asbp = Config.settingsASBP().isUse();
        final boolean showingASBP = !this.p.isAutoswitchHotbar() && this.p.getHotbar() != null && SkillMap.isASBP(this.p.getHotbar());
        if (asbp) {
            if (showingASBP) {
                buttons.add(new Button(Config.format("showingBattlePass", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), 14));
            }
            else {
                buttons.add(new Button(Config.format("noshowingBattlePass", new Object[0]), (String)null, new ItemStack(Items.ENDER_PEARL), 14));
            }
        }
        final boolean display = this.p.isAutoswitchHotbar();
        if (display) {
            buttons.add(new Button(Config.format("autoswitch", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), asbp ? 12 : 13));
        }
        else {
            buttons.add(new Button(Config.format("noAutoswitch", new Object[0]), (String)null, new ItemStack(Items.ENDER_PEARL), asbp ? 12 : 13));
        }
        final boolean sounds = this.p.shouldPlaySounds();
        if (sounds) {
            buttons.add(new Button(Config.format("playingSounds", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), 16));
        }
        else {
            buttons.add(new Button(Config.format("notPlayingSounds", new Object[0]), (String)null, new ItemStack(Items.ENDER_PEARL), 16));
        }
        final boolean fadeOutXP = this.p.shouldFadeOutXP();
        if (fadeOutXP) {
            buttons.add(new Button(Config.format("fadingOutXP", new Object[0]), (String)null, new ItemStack(Items.ENDER_EYE), 31));
        }
        else {
            buttons.add(new Button(Config.format("notFadingOutXP", new Object[0]), (String)null, new ItemStack(Items.ENDER_PEARL), 31));
        }
        return buttons;
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        if (index == -999) {
            AquaUI.openUI(player, (IPage)new MainMenuPage(this.p));
        }
        final boolean asbp = Config.settingsASBP().isUse();
        if (index == 10) {
            this.p.setMessageState(this.p.getMessageState() == 0);
            AquaUI.openUI(player, (IPage)new OptionsPage(this.p));
        }
        else if (index == (asbp ? 12 : 13)) {
            this.p.setAutoswitchHotbar(!this.p.isAutoswitchHotbar());
            if (Config.settingsASBP().getASBPSkillName().equals(Config.settingsASBP().getASBPSkillName())) {
                this.p.setHotbar((String)null, player);
            }
            AquaUI.openUI(player, (IPage)new OptionsPage(this.p));
        }
        else if (asbp && index == 14) {
            final boolean showingASBP = !this.p.isAutoswitchHotbar() && this.p.getHotbar() != null && SkillMap.isASBP(this.p.getHotbar());
            if (showingASBP) {
                this.p.setAutoswitchHotbar(true);
                this.p.setHotbar((String)null, player);
            }
            else {
                this.p.setAutoswitchHotbar(false);
                this.p.setHotbar(Config.settingsASBP().getASBPSkillName(), player);
            }
            AquaUI.openUI(player, (IPage)new OptionsPage(this.p));
        }
        else if (index == 16) {
            this.p.toggleSounds();
            AquaUI.openUI(player, (IPage)new OptionsPage(this.p));
        }
        else if (index == 31) {
            this.p.toggleFadeOutXP();
            AquaUI.openUI(player, (IPage)new OptionsPage(this.p));
        }
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return Config.format("optionsTitle", new Object[0]);
    }
}
