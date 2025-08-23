//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.ui;

import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaskills.skill.elements.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.file.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaapi.ui.api.*;

public class BindAbilityPage implements IPage
{
    private final UIDef ui;
    private final Player p;
    private final Skill skill;
    private final Experience xp;
    private final Ability ability;
    private ItemStack stack;
    
    public BindAbilityPage(final Player p, final Skill skill, final Experience xp, final Ability ability) {
        this.ui = UI.getUI("bind");
        this.stack = ItemStack.EMPTY;
        this.p = p;
        this.skill = skill;
        this.xp = xp;
        this.ability = ability;
    }
    
    public BindAbilityPage(final BindAbilityPage page, final ItemStack item) {
        this.ui = UI.getUI("bind");
        this.stack = ItemStack.EMPTY;
        this.p = page.p;
        this.skill = page.skill;
        this.ability = page.ability;
        this.xp = page.xp;
        this.stack = item;
    }
    
    public PageOptions getPageOptions(final EntityPlayerMP player) {
        return PageOptions.builder().setRows(this.ui.getRows()).setInventoryHidden(true).setTitleAlignment(PageOptions.TextAlignment.CENTER).build();
    }
    
    public void addButtons(final EntityPlayerMP player, final Set<Button> buttons) {
        final int inventoryStart = this.ui.getRows() * 9;
        final int hotbarStart = inventoryStart + 27;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                switch (this.skill.isItemBindable(stack)) {
                    case 1: {
                        final Button button = new Button(stack.copy(), hotbarStart + i);
                        button.setClickAction(clickData -> AquaUI.openUI(clickData.player, (IPage)new BindAbilityPage(this, stack)));
                        buttons.add(button);
                        break;
                    }
                    case 0: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(hotbarStart + i).setName(Config.format("cannotBind", new Object[0])).build());
                        break;
                    }
                    case -1: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(hotbarStart + i).setName(Config.format("alreadyBound", new Object[0])).build());
                        break;
                    }
                    case -2: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(hotbarStart + i).setName(Config.format("cannotBindStack", new Object[0])).build());
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < 27; ++i) {
            final ItemStack stack = player.inventory.getStackInSlot(i + 9);
            if (!stack.isEmpty()) {
                switch (this.skill.isItemBindable(stack)) {
                    case 1: {
                        final Button button = new Button(stack.copy(), inventoryStart + i);
                        final ItemStack stack2;
                        button.setClickAction(clickData -> AquaUI.openUI(clickData.player, (IPage)new BindAbilityPage(this, stack2)));
                        buttons.add(button);
                        break;
                    }
                    case 0: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(inventoryStart + i).setName(Config.format("cannotBind", new Object[0])).build());
                        break;
                    }
                    case -1: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(inventoryStart + i).setName(Config.format("alreadyBound", new Object[0])).build());
                        break;
                    }
                    case -2: {
                        buttons.add(Button.builder().setItem(Blocks.BARRIER).setIndex(inventoryStart + i).setName(Config.format("cannotBindStack", new Object[0])).build());
                        break;
                    }
                }
            }
        }
        if (this.stack.isEmpty()) {
            buttons.add(this.ui.getButton(2).build());
        }
        else {
            buttons.add(this.ui.getButton(0).setClickAction(clickData -> AquaUI.openUI(clickData.player, (IPage)new BindAbilityPage(this, ItemStack.EMPTY))).build());
            final EntityPlayerMP player2;
            final TextComponentString textComponentString;
            buttons.add(this.ui.getButton(1).setClickAction(clickData -> {
                player2 = clickData.player;
                new TextComponentString(Config.neutral("boundItem", new Object[] { this.ability.getName(), this.stack.getDisplayName() }));
                player2.sendMessage((ITextComponent)textComponentString);
                this.skill.bindItem(this.stack, this.ability);
                AquaUI.closeUI(clickData.player);
                return;
            }).build());
            final ItemStack copy = this.skill.bindItem(this.stack.copy(), this.ability);
            final int index = this.ui.getButton(2).build().getIndex();
            buttons.add(new Button(copy, index));
        }
    }
    
    public void onButtonClick(final EntityPlayerMP player, final int index, final ClickState clickState) {
        if (index == -999) {
            AquaUI.openUI(player, (IPage)new SkillPage(this.p, this.skill, this.xp));
        }
    }
    
    public String getDisplayName(final EntityPlayerMP entityPlayerMP) {
        return TextHelper.format(this.ui.getTitle()).replace("%1", this.ability.getName());
    }
}
