//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import de.waterdu.aquaskills.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.block.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import de.waterdu.aquaskills.hooks.*;

public class Woodcutting
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Woodcutting", new String[] { "Gain experience by chopping down lumber with an axe." }, "Lumberjack", "iron_axe", 17, 1000L, new String[] { "wooden_axe", "stone_axe", "iron_axe", "golden_axe", "diamond_axe" }, new XPSource[] { new XPSource("BREAK_BLOCK_XP", "axes planks,planks2", 1.0), new XPSource("BREAK_BLOCK_XP", "axes log,log2", 3.0) }, new Ability[] { new Ability("TIMBERS_TOUCH", "Eff=1.5 EffPerLvl=0.0035 FortPerLvl=0.001 CDPerLvl=-200 Dur=5000 DurPerLvl=20", 5L, 300000L, new DisplayInfo("Timber's Touch", "golden_axe", 27, new String[] { "Become one with the trees, improving your skills with an axe for a brief moment.", "Gain Haste and Speed, as well as Fortune on axes, for a brief time.", "Improves as your level of Woodcutting increases.", "", "Bindable. Click to choose a Woodcutting item to bind this to." })).setBindable(), new Ability("DEFOLIATE", "", 25L, 0L, new DisplayInfo("Defoliate", "leaves", 29, new String[] { "When it comes to getting the job done, leaves won't get in your way.", "Your axes can destroy leaves instantly.", "", "Passive ability." })), new Ability("PERFECT_CHOP", "Chance=0 ChancePerLvl=0.0001 Items=1*bookshelf,2*oak_stairs,2*spruce_stairs,2*birch_stairs,2*jungle_stairs,2*acacia_stairs,2*dark_oak_stairs,4*wooden_slab,4*3*ladder,1*crafting_table,1*chest,1*jukebox,3*fence,3*spruce_fence,3*birch_fence,3*jungle_fence,3*dark_oak_fence,3*acacia_fence,1*bed:12,1*item_frame,1*armor_stand,1*noteblock,1*sticky_piston,1*piston,3*wooden_pressure_plate,2*trapdoor,2*fence_gate,2*tripwire_hook,8*wooden_button,1*trapped_chest,1*daylight_detector,2*spruce_fence_gate,2*birch_fence_gate,2*jungle_fence_gate,2*dark_oak_fence_gate,2*acacia_fence_gate,1*wooden_door,1*spruce_door,1*birch_door,1*jungle_door,1*acacia_door,1*dark_oak_door,1*boat,1*spruce_boat,1*birch_boat,1*jungle_boat,1*dark_oak_boat,1*acacia_boat,3*bowl,1*bow,1*fishing_rod,1*shield", 50L, 0L, new DisplayInfo("Perfect Chop", "crafting_table", 31, new String[] { "Your precision with an axe lets you craft in mid air.", "Occasionally, a random wooden crafted item will drop instead of a log.", "Improves as your level of Woodcutting increases.", "", "Passive ability." })), new Ability("WOOD_STRIPPER", "Depth=2 DepthPerLevel=0.005 Blocks=log,log2", 75L, 0L, new DisplayInfo("Wood Stripper", "log", 33, new String[] { "Your precision in stripping down segments of tree trunks is quite mystical.", "Allows you to break segments of trees in one go.", "Improves as your level of Woodcutting increases.", "", "Activate by sneaking and breaking logs with an axe." })), new Ability("TREE_FELLER", "CDPerLvl=-120", 150L, 300000L, new DisplayInfo("Tree Feller", "log", 35, new String[] { "Work smarter, not harder, and you may just find the whole tree comes down.", "You can fell trees.", "Improves as your level of Woodcutting increases.", "", "Activate by left clicking the base of a tree with an axe 5 times." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        HashMap map;
        long level;
        long cd;
        long dur;
        int eff;
        int potionDur;
        HookRegistry.get().registerMethodHook("TIMBERS_TOUCH", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability);
                    level = data.getTrueFunctionalLevel();
                    cd = ability.getCooldownMilliseconds() + map.get("CDPerLvl").longValue() * level;
                    dur = map.get("Dur").longValue() + map.get("DurPerLvl").longValue() * level;
                    eff = Math.max(1, (int)(map.get("Eff") + map.get("EffPerLvl") * level));
                    potionDur = (int)(dur / 50L);
                    PlayerHelper.addEffect((EntityLivingBase)event.entity, MobEffects.HASTE, potionDur, eff, true, false);
                    PlayerHelper.addEffect((EntityLivingBase)event.entity, MobEffects.SPEED, potionDur, eff, true, false);
                    cooldown.use(cd, dur);
                    data.player.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0f, RandomHelper.nextFloat() * 0.1f + 0.9f);
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown2;
        HashMap map2;
        double fortune;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        HookRegistry.get().registerMethodHook("TIMBERS_TOUCH", new MethodHook<Event>((Class<? extends Event>)BlockEvent.HarvestDropsEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.isActive() && event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemAxe && !event.isSilkTouching() && !AquaSkills.blockLog.contains(event.getPos())) {
                    map2 = AbilityHelper.getMap(ability2);
                    fortune = map2.get("FortPerLvl") * data.getTrueFunctionalLevel();
                    if (RandomHelper.nextDouble() < fortune) {
                        event.getDrops().iterator();
                        while (iterator.hasNext()) {
                            stack = iterator.next();
                            stack.grow(1);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        HookRegistry.get().registerMethodHook("DEFOLIATE", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.BreakSpeed.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement() && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemAxe && event.getState().getBlock() instanceof BlockLeaves && !AquaSkills.blockLog.contains(event.getPos())) {
                PlayerHelper.animateBlockBreak(event.getEntityPlayer().world, event.getPos(), event.getState());
                event.setNewSpeed(15.0f);
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        String[] split;
        int i;
        final String[] array;
        int length;
        int l = 0;
        String str;
        String[] kv;
        HashSet<Block> blocks;
        String[] split4;
        String[] innerSplit;
        int length2;
        int n = 0;
        String blockStr;
        Block block;
        double depthBase;
        double depthLevel;
        int depth;
        HashSet<Block> blocks2;
        Block block2;
        boolean used;
        final HashSet set;
        HashSet<Object> toScan;
        int j;
        HashSet<BlockPos> newScan;
        final Iterator<BlockPos> iterator2;
        BlockPos scan;
        Block toCheck;
        ItemStack item;
        HookRegistry.get().registerMethodHook("WOOD_STRIPPER", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            Label_0668_3: {
                if (data.getTrueLevel() >= ability4.getLevelRequirement() && event.getPlayer().isSneaking() && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemAxe) {
                    cooldown3 = data.getCooldownSafely();
                    if (!cooldown3.isOnCooldown()) {
                        if (!ability4.areArgsParsed()) {
                            ability4.getParsedArgs().add(0.0);
                            ability4.getParsedArgs().add(0.0);
                            split = ability4.getArgs().split(" ");
                            i = 0;
                            for (length = array.length; l < length; ++l) {
                                str = array[l];
                                kv = str.split("=");
                                if (i < 2) {
                                    ability4.getParsedArgs().set(kv[0].equalsIgnoreCase("Depth") ? 0 : 1, Double.parseDouble(kv[1]));
                                }
                                else {
                                    blocks = new HashSet<Block>();
                                    innerSplit = (split4 = kv[1].split(","));
                                    for (length2 = split4.length; n < length2; ++n) {
                                        blockStr = split4[n];
                                        block = Block.getBlockFromName(blockStr);
                                        if (block != null) {
                                            blocks.add(block);
                                        }
                                    }
                                    ability4.getParsedArgs().add(blocks);
                                }
                                ++i;
                            }
                        }
                        depthBase = ability4.getParsedArgs().get(0);
                        depthLevel = ability4.getParsedArgs().get(1);
                        depth = (int)(depthBase + depthLevel * data.getTrueFunctionalLevel());
                        blocks2 = ability4.getParsedArgs().get(2);
                        block2 = event.getState().getBlock();
                        if (blocks2.contains(block2)) {
                            used = false;
                            new HashSet(Arrays.asList(event.getPos().up(), event.getPos().down(), event.getPos().north(), event.getPos().south(), event.getPos().east(), event.getPos().west()));
                            toScan = (HashSet<Object>)set;
                            for (j = 0; j < depth; ++j) {
                                newScan = new HashSet<BlockPos>();
                                toScan.iterator();
                                while (iterator2.hasNext()) {
                                    scan = iterator2.next();
                                    toCheck = event.getWorld().getBlockState(scan).getBlock();
                                    if (toCheck == block2) {
                                        if (!used) {
                                            cooldown3.use(500L);
                                            used = true;
                                        }
                                        PlayerHelper.removeBlock((EntityPlayerMP)event.getPlayer(), scan);
                                        item = event.getPlayer().getHeldItemMainhand();
                                        if (!item.isEmpty() && item.getItem() instanceof ItemAxe) {
                                            if (item.getItemDamage() >= item.getMaxDamage()) {
                                                break Label_0668_3;
                                            }
                                            else {
                                                newScan.add(scan.up());
                                                newScan.add(scan.down());
                                                newScan.add(scan.north());
                                                newScan.add(scan.south());
                                                newScan.add(scan.east());
                                                newScan.add(scan.west());
                                            }
                                        }
                                        else {
                                            break Label_0668_3;
                                        }
                                    }
                                }
                                toScan.clear();
                                toScan.addAll(newScan);
                            }
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        HashMap map3;
        long count;
        long posHash;
        BlockPos pos;
        long hash;
        long level2;
        long cd2;
        World world;
        boolean hasLog;
        int y;
        int dx;
        int dz;
        BlockPos test;
        IBlockState state;
        ItemStack item2;
        HookRegistry.get().registerMethodHook("TREE_FELLER", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            Label_0410_2: {
                if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                    cooldown4 = data.getCooldownSafely();
                    if (!cooldown4.isOnCooldown() && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemAxe && !AquaSkills.blockLog.contains(event.getPos())) {
                        map3 = AbilityHelper.getMap(ability5);
                        count = cooldown4.getLastUsed();
                        posHash = cooldown4.getActiveUntil();
                        pos = event.getPos();
                        hash = pos.hashCode();
                        if (posHash == hash) {
                            cooldown4.setLastUsed(count + 1L);
                            data.player.playSound(SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0f, 0.4f + RandomHelper.nextFloat() * 0.4f);
                        }
                        else {
                            cooldown4.setLastUsed(0L);
                            cooldown4.setActiveUntil(hash);
                        }
                        if (cooldown4.getLastUsed() >= 4L) {
                            level2 = data.getTrueFunctionalLevel();
                            cd2 = ability5.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level2;
                            cooldown4.setActiveUntil(0L);
                            cooldown4.setLastUsed(0L);
                            cooldown4.useCooldownOnly(cd2);
                            data.player.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.PLAYERS, 1.0f, RandomHelper.nextFloat() * 0.4f + 0.4f);
                            world = event.getWorld();
                            hasLog = true;
                            y = pos.getY();
                            while (hasLog) {
                                hasLog = false;
                                for (dx = -1; dx <= 1; ++dx) {
                                    for (dz = -1; dz <= 1; ++dz) {
                                        test = new BlockPos(pos.getX() + dx, y, pos.getZ() + dz);
                                        if (!AquaSkills.blockLog.contains(test)) {
                                            state = world.getBlockState(test);
                                            if (state.getBlock() instanceof BlockLog) {
                                                PlayerHelper.removeBlock((EntityPlayerMP)event.getEntityPlayer(), test);
                                                item2 = event.getEntityPlayer().getHeldItemMainhand();
                                                if (!item2.isEmpty() && item2.getItem() instanceof ItemAxe) {
                                                    if (item2.getItemDamage() >= item2.getMaxDamage()) {
                                                        break Label_0410_2;
                                                    }
                                                    else {
                                                        hasLog = true;
                                                    }
                                                }
                                                else {
                                                    break Label_0410_2;
                                                }
                                            }
                                        }
                                    }
                                }
                                ++y;
                            }
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability6;
        Cooldown cooldown5;
        String[] args;
        ArrayList<ItemEncapsulation> items;
        final String[] array2;
        int length3;
        int n2 = 0;
        String arg;
        String[] kv2;
        String k;
        String v;
        String[] split5;
        String[] itemStrs;
        int length4;
        int n3 = 0;
        String item3;
        String[] split2;
        int count2;
        String[] split3;
        final ItemEncapsulation e;
        final Object o;
        long level3;
        double chance;
        ArrayList<ItemEncapsulation> items2;
        ItemEncapsulation item4;
        ItemStack stack2;
        HookRegistry.get().registerMethodHook("PERFECT_CHOP", new MethodHook<Event>((Class<? extends Event>)BlockEvent.HarvestDropsEvent.class, (event, data) -> {
            ability6 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability6.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown() && event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemAxe && event.getState().getBlock() instanceof BlockLog) {
                    if (!ability6.areArgsParsed()) {
                        args = ability6.getArgs().split(" ");
                        ability6.getParsedArgs().add(0.0);
                        ability6.getParsedArgs().add(0.0);
                        items = new ArrayList<ItemEncapsulation>();
                        for (length3 = array2.length; n2 < length3; ++n2) {
                            arg = array2[n2];
                            kv2 = arg.split("=");
                            k = kv2[0];
                            v = kv2[1];
                            if (k.equalsIgnoreCase("Chance")) {
                                ability6.getParsedArgs().set(0, Double.parseDouble(v));
                            }
                            else if (k.equalsIgnoreCase("ChancePerLvl")) {
                                ability6.getParsedArgs().set(1, Double.parseDouble(v));
                            }
                            else if (k.equalsIgnoreCase("Items")) {
                                itemStrs = (split5 = v.split(","));
                                for (length4 = split5.length; n3 < length4; ++n3) {
                                    item3 = split5[n3];
                                    split2 = item3.split("\\*");
                                    count2 = Integer.parseInt(split2[0]);
                                    split3 = split2[1].split(":");
                                    if (split3.length == 2 || split3.length == 1) {
                                        items.add(new ItemEncapsulation(split2[1], count2, 0));
                                    }
                                    else if (split3.length == 3) {
                                        new ItemEncapsulation(split3[0] + ":" + split3[1], count2, Integer.parseInt(split3[2]));
                                        ((ArrayList<ItemEncapsulation>)o).add(e);
                                    }
                                }
                            }
                        }
                        ability6.getParsedArgs().add(items);
                    }
                    level3 = data.getTrueFunctionalLevel();
                    chance = ability6.getParsedArgs().get(0) + ability6.getParsedArgs().get(1) * level3;
                    if (RandomHelper.nextDouble() < chance) {
                        items2 = ability6.getParsedArg(2);
                        item4 = (ItemEncapsulation)RandomHelper.getRandomElementFromCollection((Collection)items2);
                        stack2 = item4.generate();
                        if (!stack2.isEmpty()) {
                            event.getDrops().clear();
                            event.getDrops().add(stack2);
                            cooldown5.use();
                        }
                    }
                }
            }
        }));
    }
}
