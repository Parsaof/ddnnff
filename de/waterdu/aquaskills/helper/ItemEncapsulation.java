//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.item.*;

public class ItemEncapsulation
{
    private Item item;
    private int count;
    private int meta;
    
    public ItemEncapsulation(final String item, final int count, final int meta) {
        this.item = Item.getByNameOrId(item);
        this.count = count;
        this.meta = meta;
    }
    
    public ItemStack generate() {
        if (this.item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(this.item, this.count, this.meta);
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int getMeta() {
        return this.meta;
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public void setMeta(final int meta) {
        this.meta = meta;
    }
    
    public ItemEncapsulation() {
    }
    
    public ItemEncapsulation(final Item item, final int count, final int meta) {
        this.item = item;
        this.count = count;
        this.meta = meta;
    }
}
