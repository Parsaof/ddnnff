//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import java.util.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaapi.ui.api.*;

public class UIDef
{
    private int rows;
    private String title;
    private ArrayList<Data> data;
    
    public String getTitle() {
        return TextHelper.format(this.title);
    }
    
    public Data getDataForOrdinal(final int ordinal) {
        return this.data.get(ordinal);
    }
    
    public Button.Builder getButton(final int ordinal) {
        final Data data = this.getDataForOrdinal(ordinal);
        final Button.Builder builder = Button.builder().setIndex(data.getIndex()).setItem(data.getItem(), 1, data.getMeta()).setName(TextHelper.format(data.getName()));
        for (final String lore : data.getLore()) {
            builder.addLoreLine(TextHelper.format(lore));
        }
        return builder;
    }
    
    public int getButtonCount() {
        return this.data.size();
    }
    
    public int getRows() {
        return this.rows;
    }
    
    public ArrayList<Data> getData() {
        return this.data;
    }
    
    public void setRows(final int rows) {
        this.rows = rows;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public void setData(final ArrayList<Data> data) {
        this.data = data;
    }
    
    public UIDef(final int rows, final String title, final ArrayList<Data> data) {
        this.rows = 3;
        this.title = "";
        this.data = new ArrayList<Data>();
        this.rows = rows;
        this.title = title;
        this.data = data;
    }
    
    public UIDef() {
        this.rows = 3;
        this.title = "";
        this.data = new ArrayList<Data>();
    }
    
    public static class Data
    {
        private int index;
        private String item;
        private int meta;
        private String name;
        private String[] lore;
        
        public Data(final int index, final String item, final String name, final String... lore) {
            this.meta = 0;
            this.index = index;
            this.item = item;
            this.name = name;
            this.lore = lore;
        }
        
        public Data(final int index, final String item, final int meta, final String name, final String... lore) {
            this.meta = 0;
            this.index = index;
            this.item = item;
            this.meta = meta;
            this.name = name;
            this.lore = lore;
        }
        
        public static Data of(final int index, final String item, final String name, final String... lore) {
            return new Data(index, item, name, lore);
        }
        
        public static Data of(final int index, final String item, final int meta, final String name, final String... lore) {
            return new Data(index, item, meta, name, lore);
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public String getItem() {
            return this.item;
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String[] getLore() {
            return this.lore;
        }
        
        public void setIndex(final int index) {
            this.index = index;
        }
        
        public void setItem(final String item) {
            this.item = item;
        }
        
        public void setMeta(final int meta) {
            this.meta = meta;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public void setLore(final String[] lore) {
            this.lore = lore;
        }
        
        public Data() {
            this.meta = 0;
        }
    }
}
