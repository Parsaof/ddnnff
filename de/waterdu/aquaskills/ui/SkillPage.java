//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import de.waterdu.aquaskills.file.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import java.util.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.player.*;
import java.util.function.*;
import de.waterdu.aquaapi.ui.internal.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import de.waterdu.aquaapi.ui.api.*;

public class SkillPage implements IPage
{
    private final Player p;
    private final Skill skill;
    private final Experience experience;
    
    public SkillPage(final Player p, final Skill skill, final Experience experience) {
        this.p = p;
        this.skill = skill;
        (this.experience = experience).setUnread(false);
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(Config.settings().getSkillPageRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public Set<Button> getButtons(final EntityPlayerMP player) {
        final Set<Button> buttons = new HashSet<Button>();
        buttons.add(this.skill.getButton(this.experience, this.skill.getAbilityButtonIndex(), false));
        for (final Ability ability : this.skill.getAbilities()) {
            final Button button = ability.getButton(this.p, this.skill);
            final boolean locked = this.experience.getTrueLevel() < ability.getLevelRequirement();
            if (!locked && ability.isBindable()) {
                button.setClickAction(clickData -> AquaUI.openUI(clickData.player, (IPage)new BindAbilityPage(this.p, this.skill, this.experience, ability)));
            }
            buttons.add(button);
        }
        buttons.add(new Button(Config.format("goBack", new Object[0]), (String)null, new ItemStack(Config.settings().goBackItem()), this.skill.getExitButtonIndex()));
        final boolean chat = this.experience.isChat();
        buttons.add(new Button(Config.format(chat ? "chat" : "noChat", new Object[0]), (String)null, new ItemStack(chat ? Items.ENDER_EYE : Items.ENDER_PEARL), this.skill.getChatButtonIndex()));
        final boolean display = this.p.getHotbar() != null && this.p.getHotbar().equals(this.skill.getName());
        buttons.add(new Button(Config.format(display ? "display" : "noDisplay", new Object[0]), (String)null, new ItemStack(display ? Items.ENDER_EYE : Items.ENDER_PEARL), this.skill.getDisplayButtonIndex()));
        return buttons;
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        for (final Ability ability : this.skill.getAbilities()) {
            if (ability.getDisplayInfo().getIndex() == index) {
                if (clickState.getButton() == ClickState.MouseButton.LEFT) {
                    AquaSkills.EVENT_BUS.post((Event)new PressAbilityEvent(this.p, this.skill, ability));
                }
                else if (clickState.getButton() == ClickState.MouseButton.RIGHT) {
                    this.p.getCooldown(ability).ifPresent(Cooldown::toggleMessages);
                    this.reopen(player);
                }
                else if (clickState.getButton() == ClickState.MouseButton.MIDDLE) {
                    this.p.getCooldown(ability).ifPresent(Cooldown::toggleEnabled);
                    this.reopen(player);
                }
                return;
            }
        }
        if (index == this.skill.getExitButtonIndex()) {
            AquaUI.openUI(player, (IPage)new SkillsPage(this.p));
        }
        else if (index == this.skill.getChatButtonIndex()) {
            this.experience.toggleMessages();
            this.reopen(player);
        }
        else if (index == this.skill.getDisplayButtonIndex()) {
            this.p.toggleHotbar(this.skill, player);
            this.reopen(player);
        }
        else if (index == this.skill.getAbilityButtonIndex()) {
            if (clickState.getButton() == ClickState.MouseButton.RIGHT) {
                this.skill.toggleAllMessages(this.p);
                this.reopen(player);
            }
            else if (clickState.getButton() == ClickState.MouseButton.MIDDLE) {
                this.skill.toggleAllAbilities(this.p);
                this.reopen(player);
            }
        }
    }
    
    private void reopen(final EntityPlayerMP player) {
        AquaUI.openUI(player, (IPage)new SkillPage(this.p, this.skill, this.experience));
    }
    
    public Buttons onUpdate(final long tick, final EntityPlayerMP player, final Buttons buttons) {
        for (final Button button : buttons.getInterfaceButtons()) {
            if (button != null) {
                for (final Ability ability : this.skill.getAbilities()) {
                    if (ability.getDisplayInfo().getIndex() == button.index) {
                        final Ability ability2;
                        final String lore;
                        final boolean locked;
                        final Button button2;
                        this.p.getCooldown(ability).ifPresent(cooldown -> {
                            lore = ability2.getSingleLineLore(this.skill, this.experience, cooldown);
                            locked = (this.experience.getTrueLevel() < ability2.getLevelRequirement());
                            if (!button2.lore.equalsIgnoreCase(lore)) {
                                button2.setLore(lore);
                            }
                            if (cooldown.isOnCooldown()) {
                                if (button2.item.getItem() != Items.CLOCK) {
                                    button2.setItem(new ItemStack(Items.CLOCK));
                                }
                            }
                            else if (button2.item.getItem() == Items.CLOCK) {
                                button2.setItem(ability2.getDisplayInfo().getItem());
                            }
                            if (locked || !cooldown.isEnabled()) {
                                if (button2.item.getItem() != Item.getItemFromBlock(Blocks.BARRIER)) {
                                    button2.setItem(new ItemStack(Blocks.BARRIER));
                                }
                            }
                            else if (button2.item.getItem() == Item.getItemFromBlock(Blocks.BARRIER)) {
                                button2.setItem(ability2.getDisplayInfo().getItem());
                            }
                            return;
                        });
                        break;
                    }
                }
            }
        }
        return buttons;
    }
    
    public String getDisplayName(final EntityPlayerMP player) {
        return Config.format("skillTitle", new Object[] { this.skill.getDisplayName() });
    }
}
