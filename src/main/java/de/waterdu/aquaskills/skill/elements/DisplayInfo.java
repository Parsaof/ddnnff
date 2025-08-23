//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill.elements;

import net.minecraft.item.*;
import org.apache.commons.lang3.*;
import net.minecraft.init.*;

public class DisplayInfo
{
    private String name;
    private String[] lore;
    private String item;
    private int index;
    private transient ItemStack stack;
    
    public DisplayInfo(final String name, final String item, final int index, final String... lore) {
        this.stack = null;
        this.name = name;
        this.item = item;
        this.index = index;
        this.lore = lore;
    }
    
    public ItemStack getItem() {
        if (this.stack == null) {
            Item item = null;
            int meta = 0;
            final String[] split = this.item.split(":");
            if (split.length == 2 || split.length == 1) {
                item = Item.getByNameOrId(this.item);
            }
            else if (split.length == 3) {
                item = Item.getByNameOrId(split[0] + ":" + split[1]);
                if (StringUtils.isNumeric((CharSequence)split[2])) {
                    meta = Integer.parseInt(split[2]);
                }
            }
            if (item == null) {
                item = Items.AIR;
            }
            this.stack = new ItemStack(item, 1, meta);
        }
        return this.stack;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setLore(final String[] lore) {
        this.lore = lore;
    }
    
    public void setItem(final String item) {
        this.item = item;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public void setStack(final ItemStack stack) {
        this.stack = stack;
    }
    
    public DisplayInfo() {
        this.stack = null;
    }
}
