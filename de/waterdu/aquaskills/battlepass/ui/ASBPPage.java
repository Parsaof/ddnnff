//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass.ui;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.file.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.battlepass.*;
import de.waterdu.aquaskills.ui.*;
import de.waterdu.aquaapi.ui.internal.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaapi.ui.api.*;

public class ASBPPage implements IPage
{
    private final UIDef ui;
    private final ASBPSettings settings;
    private final ArrayList<ASBPReward> rewards;
    private final ASBPPlayer p;
    private int page;
    private ItemStack[] tempItems;
    private int timer;
    
    public ASBPPage(final EntityPlayerMP player) {
        this(Config.playerASBP(player), 0, ASBPRewards.getRewards());
    }
    
    public ASBPPage(final ASBPPlayer p, final int page, final ArrayList<ASBPReward> rewards) {
        this.ui = UI.getUI("asbp");
        this.settings = Config.settingsASBP();
        this.tempItems = null;
        this.timer = 0;
        this.p = p;
        this.page = page;
        (this.rewards = rewards).sort(Comparator.comparingInt(ASBPReward::getIndex));
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(this.ui.getRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public Set<Button> getButtons(final EntityPlayerMP player) {
        final ASBPSettings settings = Config.settingsASBP();
        final Set<Button> buttons = new HashSet<Button>();
        final int size = 7;
        final int count = this.rewards.size();
        final int maxPage = (count - 1) / size;
        if (this.page > maxPage) {
            this.page = maxPage;
        }
        if (this.page < 0) {
            this.page = 0;
        }
        final int n;
        int index;
        int i;
        final int n2;
        ASBPReward reward;
        final Set<Button> set;
        final ASBPSettings asbpSettings;
        final ASBPTrack[] array;
        int length;
        int k = 0;
        ASBPTrack track;
        final Button.Builder builder;
        this.p.getXP().ifPresent(xp -> {
            for (index = this.page * n, i = 0; i < n && index < n2; ++index, ++i) {
                reward = this.rewards.get(index);
                set.add(new Button.Builder().setIndex(1 + i).setName(Config.format("levelASBP", reward.getIndex())).setNameColour((xp.getTrueLevel() == reward.getIndex()) ? TextFormatting.GOLD : ((xp.getTrueLevel() > reward.getIndex()) ? TextFormatting.YELLOW : TextFormatting.RED)).setItem((Block)Blocks.STAINED_GLASS_PANE, 1, (xp.getTrueLevel() == reward.getIndex()) ? 1 : ((xp.getTrueLevel() > reward.getIndex()) ? 4 : 14)).build());
                asbpSettings.getTracks();
                for (length = array.length; k < length; ++k) {
                    track = array[k];
                    set.add(reward.getButton(this.p, track, track.getLine() * 9 + 1 + i));
                }
            }
            builder = this.ui.getButton(0).setName(TextFormatting.YELLOW + Config.format("levelASBP", xp.getTrueLevel())).addLoreLine(Config.format("xpASBP", xp.getDisplayXP(), xp.getExperienceRequiredForLevelUp()));
            if (this.p.canClaimSomething(player)) {
                builder.addLoreLine("").addLoreLine(Config.format("claimAll", new Object[0])).setClickAction(clickData -> {
                    if (clickData.clickState.getRate() == ClickState.ClickRate.DOUBLE && this.p.claimAll(clickData.player)) {
                        clickData.player.sendMessage((ITextComponent)new TextComponentString(Config.neutralASBP("claimedAll", new Object[0])));
                        if (clickData.clickState.getButton() != ClickState.MouseButton.RIGHT) {
                            AquaUI.openUI(clickData.player, (IPage)null);
                        }
                        else {
                            AquaUI.openUI(clickData.player, (IPage)new ASBPPage(this.p, this.page, this.rewards));
                        }
                    }
                    return;
                });
            }
            set.add(builder.build());
            return;
        });
        if (this.page > 0) {
            buttons.add(this.ui.getButton(1).build());
        }
        if (this.page < maxPage) {
            buttons.add(this.ui.getButton(2).build());
        }
        for (int j = 0; j < 2; ++j) {
            for (final ASBPTrack track2 : settings.getTracks()) {
                final int start = track2.getLine() * 9;
                final Button.Builder builder2 = new Button.Builder().setIndex((j == 0) ? start : (start + 8)).setName(TextHelper.format(track2.getName())).setItem(track2.getItem());
                final String[] splitA = TextHelper.format(track2.getLore()).split("\n");
                if (splitA.length > 1) {
                    for (final String lore : splitA) {
                        builder2.addLoreLine(lore);
                    }
                }
                buttons.add(builder2.build());
            }
        }
        return buttons;
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        if (index == -999) {
            AquaUI.openUI(player, (IPage)new MainMenuPage(Config.player(player)));
        }
        if (index == 29) {
            AquaUI.openUI(player, (IPage)new ASBPPage(this.p, this.page - 1, this.rewards));
        }
        else if (index == 33) {
            AquaUI.openUI(player, (IPage)new ASBPPage(this.p, this.page + 1, this.rewards));
        }
        else {
            for (final ASBPTrack track : Config.settingsASBP().getTracks()) {
                final int start = track.getLine() * 9 + 1;
                if (index >= start && index <= start + 6) {
                    final int i = this.page * 7 + index - start;
                    if (i < this.rewards.size() && i >= 0) {
                        final ASBPReward reward = this.rewards.get(i);
                        if (this.p.tryClaim(reward, track)) {
                            if (clickState.getButton() != ClickState.MouseButton.RIGHT) {
                                AquaUI.openUI(player, (IPage)null);
                            }
                            else {
                                AquaUI.openUI(player, (IPage)new ASBPPage(this.p, this.page, this.rewards));
                            }
                        }
                    }
                }
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
                for (int i = 0; i < 7; ++i) {
                    for (final ASBPTrack track : this.settings.getTracks()) {
                        final Button button = buttons.getButton(track.getLine() * 9 + 1 + i);
                        final int ordinal = this.page * 7 + i;
                        if (ordinal < this.rewards.size() && ordinal >= 0) {
                            final ASBPReward reward = this.rewards.get(ordinal);
                            if (button != null && reward.hasRewards(track) && track.has(player) && !this.p.hasClaimed(reward.getIndex(), track) && this.p.getLevel() >= reward.getIndex()) {
                                if (this.timer == 15) {
                                    this.tempItems[button.getIndex()] = button.item;
                                    button.setItem(new ItemStack(Items.NETHER_STAR));
                                }
                                else {
                                    button.setItem(this.tempItems[button.getIndex()]);
                                }
                            }
                        }
                    }
                }
            }
        }
        return buttons;
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return Config.format("rewardsTitle", new Object[0]);
    }
}
