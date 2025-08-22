//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.battlepass;

import de.waterdu.aquaapi.ui.api.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.*;
import net.minecraft.init.*;
import de.waterdu.aquaskills.helper.*;

public class RewardDisplay
{
    private String item;
    private boolean sprite;
    private boolean glow;
    private String name;
    private String[] lore;
    
    public static Button nullReward(final int index) {
        return new Button.Builder().setName(Config.format("noReward", new Object[0])).setNameColour(TextFormatting.DARK_RED).setIndex(index).setItem(Blocks.BARRIER).build();
    }
    
    public Button getButton(final ASBPTrack track, final boolean hasTrack, final boolean claimed, final boolean canClaim, final int index) {
        final Button.Builder builder = new Button.Builder();
        if (this.sprite) {
            try {
                final Class<?> clazz = Class.forName("de.waterdu.aquaskills.pixelmon.PixelmonSpriteMaker");
                builder.setItem((ItemStack)clazz.getMethod("makeSprite", String.class).invoke(null, this.item));
            }
            catch (Exception e) {
                AquaSkills.log.info("Couldn't find Pixelmon addon, but sprite was in use in ASBP reward! Set to cake.");
                builder.setItem(Items.CAKE);
            }
        }
        else {
            builder.setItem(this.item);
        }
        builder.setEffect(this.glow);
        TextFormatting color;
        if (!hasTrack) {
            color = TextFormatting.RED;
        }
        else if (canClaim && !claimed) {
            color = TextFormatting.AQUA;
        }
        else if (claimed) {
            color = TextFormatting.DARK_AQUA;
        }
        else {
            color = TextFormatting.DARK_GRAY;
        }
        builder.setName(color + "" + (claimed ? TextFormatting.STRIKETHROUGH : "") + this.name).setIndex(index);
        for (final String line : this.lore) {
            builder.addLoreLine(line);
        }
        if (!claimed && canClaim && hasTrack) {
            builder.addLoreLine("").addLoreLine(Config.format("clickToClaim", new Object[0])).addLoreLine(Config.format("rightClickClaim", new Object[0]));
        }
        else if (canClaim && !hasTrack) {
            builder.addLoreLine("").addLoreLine(TextHelper.format(track.getRequire()));
        }
        return builder.build();
    }
    
    public String getItem() {
        return this.item;
    }
    
    public boolean isSprite() {
        return this.sprite;
    }
    
    public boolean isGlow() {
        return this.glow;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public void setItem(final String item) {
        this.item = item;
    }
    
    public void setSprite(final boolean sprite) {
        this.sprite = sprite;
    }
    
    public void setGlow(final boolean glow) {
        this.glow = glow;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setLore(final String[] lore) {
        this.lore = lore;
    }
    
    public RewardDisplay() {
        this.sprite = false;
        this.glow = false;
    }
    
    public RewardDisplay(final String item, final boolean sprite, final boolean glow, final String name, final String[] lore) {
        this.sprite = false;
        this.glow = false;
        this.item = item;
        this.sprite = sprite;
        this.glow = glow;
        this.name = name;
        this.lore = lore;
    }
}
