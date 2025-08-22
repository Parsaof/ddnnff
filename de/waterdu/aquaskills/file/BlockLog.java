//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import net.minecraftforge.common.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.math.*;

public class BlockLog
{
    private static final String ARRAY = "AquaSkillsBlocks";
    private final HashMap<ChunkPos, HashSet<Long>> map;
    
    public BlockLog() {
        this.map = new HashMap<ChunkPos, HashSet<Long>>();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onChunkLoad(final ChunkDataEvent.Load event) {
        if (!event.getWorld().isRemote) {
            final NBTTagCompound tag = event.getData().getCompoundTag("Level");
            HashSet<Long> set;
            if (tag.hasKey("AquaSkillsBlocks")) {
                set = this.readFromNBT(tag);
            }
            else {
                set = new HashSet<Long>();
            }
            this.map.put(event.getChunk().getPos(), set);
        }
    }
    
    @SubscribeEvent
    public void onChunkSave(final ChunkDataEvent.Save event) {
        if (!event.getWorld().isRemote) {
            this.writeToNBT(event.getData().getCompoundTag("Level"), this.map.get(event.getChunk().getPos()));
            if (event.getChunk().unloadQueued) {
                this.map.remove(event.getChunk().getPos());
            }
        }
    }
    
    private HashSet<Long> readFromNBT(final NBTTagCompound tag) {
        final NBTTagList list = tag.getTagList("AquaSkillsBlocks", 4);
        final HashSet<Long> posSet = new HashSet<Long>();
        for (final NBTBase pos : list) {
            posSet.add(((NBTTagLong)pos).getLong());
        }
        return posSet;
    }
    
    private NBTTagCompound writeToNBT(final NBTTagCompound tag, final HashSet<Long> set) {
        if (set != null) {
            final NBTTagList list = new NBTTagList();
            for (final long pos : set) {
                list.appendTag((NBTBase)new NBTTagLong(pos));
            }
            tag.setTag("AquaSkillsBlocks", (NBTBase)list);
        }
        else {
            tag.setTag("AquaSkillsBlocks", (NBTBase)new NBTTagList());
        }
        return tag;
    }
    
    public boolean contains(final BlockPos pos) {
        final ChunkPos cpos = new ChunkPos(pos);
        this.map.putIfAbsent(cpos, new HashSet<Long>());
        return this.map.get(cpos).contains(pos.toLong());
    }
    
    public void add(final BlockPos pos) {
        final ChunkPos cpos = new ChunkPos(pos);
        this.map.putIfAbsent(cpos, new HashSet<Long>());
        this.map.get(cpos).add(pos.toLong());
    }
    
    public void remove(final BlockPos pos) {
        final ChunkPos cpos = new ChunkPos(pos);
        this.map.putIfAbsent(cpos, new HashSet<Long>());
        this.map.get(cpos).remove(pos.toLong());
    }
}
