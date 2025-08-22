//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.world.*;
import net.minecraft.block.*;
import de.waterdu.aquaskills.player.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import de.waterdu.aquaskills.hooks.*;

public class Mining
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Mining", new String[] { "Gain experience by digging through the ground with a pickaxe." }, "Miner", "iron_pickaxe", 12, 1000L, new String[] { "wooden_pickaxe", "stone_pickaxe", "iron_pickaxe", "golden_pickaxe", "diamond_pickaxe" }, new XPSource[] { new XPSource("BREAK_BLOCK_XP", "pickaxes netherrack,end_stone", 1.0), new XPSource("BREAK_BLOCK_XP", "pickaxes stone,cobblestone", 2.0), new XPSource("BREAK_BLOCK_XP", "pickaxes mossy_cobblestone", 5.0), new XPSource("BREAK_BLOCK_XP", "pickaxes prismarine", 10.0), new XPSource("BREAK_BLOCK_XP", "pickaxes coal_ore", 15.0), new XPSource("BREAK_BLOCK_XP", "pickaxes redstone_ore,lit_redstone_ore,obsidian,iron_ore,pixelmon:bauxite_ore", 20.0), new XPSource("BREAK_BLOCK_XP", "pickaxes quartz_ore,pixelmon:dawn_dusk_ore,pixelmon:fire_stone_ore,pixelmon:water_stone_ore,pixelmon:leaf_stone_ore,pixelmon:thunder_stone_ore,pixelmon:sun_stone_ore,pixelmon:silicon_ore", 25.0), new XPSource("BREAK_BLOCK_XP", "pickaxes lapis_ore,pixelmon:sapphire_ore,pixelmon:ruby_ore,pixelmon:amethyst_ore,gold_ore,pixelmon:crystal_ore", 30.0), new XPSource("BREAK_BLOCK_XP", "pickaxes diamond_ore", 50.0), new XPSource("BREAK_BLOCK_XP", "pickaxes emerald_ore", 75.0) }, new Ability[] { new Ability("MINERS_MADNESS", "Eff=2 EffPerLvl=0.002 FortPerLvl=0.0015 CDPerLvl=-200 Dur=5000 DurPerLvl=20", 5L, 300000L, new DisplayInfo("Miner's Madness", "golden_pickaxe", 27, new String[] { "Go into a frenzy, improving your skills with a pickaxe for a brief moment.", "Gain Haste and Speed, as well as Fortune on pickaxes, for a brief time.", "Improves as your level of Mining increases.", "", "Bindable. Click to choose a Mining item to bind this to." })).setBindable(), new Ability("EXPERIENCED_MINER", "Mult=1 MultPerLvl=0.02", 25L, 0L, new DisplayInfo("Experienced Miner", "experience_bottle", 29, new String[] { "Your experience in mining lets you gather every last drop out of an ore.", "Increases the amount of experience dropped by ores.", "Improves as your level of Mining increases.", "", "Passive ability." })), new Ability("VEIN_MINER", "Depth=2 DepthPerLevel=0.005 Blocks=coal_ore,redstone_ore,lit_redstone_ore,iron_ore,pixelmon:bauxite_ore,quartz_ore,pixelmon:silicon_ore,lapis_ore,pixelmon:sapphire_ore,pixelmon:ruby_ore,pixelmon:amethyst_ore,gold_ore,pixelmon:crystal_ore,diamond_ore,emerald_ore", 50L, 0L, new DisplayInfo("Vein Miner", "gold_ore", 31, new String[] { "Your precision in mining along veins of ore is quite uncanny.", "Allows you to mine veins of ore in one go.", "Improves as your level of Mining increases.", "", "Activate by sneaking and mining ore with a pickaxe." })), new Ability("MAGNETIC_FIELD", "Radius=10 RadiusPerLvl=0.027 CDPerLvl=-80", 75L, 100000L, new DisplayInfo("Magnetic Field", "pixelmon:magnet", 33, new String[] { "Attunement to the spoils of mining allows you to bring them to you from afar.", "Picks up items in a very large radius.", "Improves as your level of Mining increases.", "", "Bindable. Click to choose a Mining item to bind this to." })).setBindable(), new Ability("EXPLOSIVE_MINING", "Power=6 PowerPerLvl=0.032 ExtraDrops=0 ExtraDropsPerLvl=0.004 CDPerLvl=60", 150L, 540000L, new DisplayInfo("Explosive Mining", "tnt", 35, new String[] { "Unorthodox mining techniques sometimes prove effective.", "Allows you to explosively mine, while getting more out of ores than normal.", "Improves as your level of Mining increases.", "", "Bindable. Click to choose a Mining item to bind this to." })).setBindable() }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
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
        HookRegistry.get().registerMethodHook("MINERS_MADNESS", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
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
        HookRegistry.get().registerMethodHook("MINERS_MADNESS", new MethodHook<Event>((Class<? extends Event>)BlockEvent.HarvestDropsEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (cooldown2.isActive() && event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemPickaxe && !event.isSilkTouching() && !AquaSkills.blockLog.contains(event.getPos())) {
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
        Cooldown cooldown3;
        String[] split3;
        String[] split;
        int length;
        int k = 0;
        String str;
        String[] kv;
        double multBase;
        double multLevel;
        double mult;
        HookRegistry.get().registerMethodHook("EXPERIENCED_MINER", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (cooldown3.isActive()) {
                    if (!ability3.areArgsParsed()) {
                        ability3.getParsedArgs().add(0.0);
                        ability3.getParsedArgs().add(0.0);
                        split = (split3 = ability3.getArgs().split(" "));
                        for (length = split3.length; k < length; ++k) {
                            str = split3[k];
                            kv = str.split("=");
                            ability3.getParsedArgs().set(kv[0].equalsIgnoreCase("Mult") ? 0 : 1, Double.parseDouble(kv[1]));
                        }
                    }
                    multBase = ability3.getParsedArgs().get(0);
                    multLevel = ability3.getParsedArgs().get(1);
                    mult = multBase + multLevel * data.getTrueFunctionalLevel();
                    event.setExpToDrop((int)(event.getExpToDrop() * mult));
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown4;
        String[] split2;
        int i;
        final String[] array;
        int length2;
        int l = 0;
        String str2;
        String[] kv2;
        HashSet<Block> blocks;
        String[] split4;
        String[] innerSplit;
        int length3;
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
        HookRegistry.get().registerMethodHook("VEIN_MINER", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            Label_0700_4: {
                if (data.getTrueLevel() >= ability4.getLevelRequirement() && event.getPlayer().isSneaking() && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                    cooldown4 = data.getCooldownSafely();
                    if (!cooldown4.isOnCooldown()) {
                        if (!ability4.areArgsParsed()) {
                            ability4.getParsedArgs().add(0.0);
                            ability4.getParsedArgs().add(0.0);
                            split2 = ability4.getArgs().split(" ");
                            i = 0;
                            for (length2 = array.length; l < length2; ++l) {
                                str2 = array[l];
                                kv2 = str2.split("=");
                                if (i < 2) {
                                    ability4.getParsedArgs().set(kv2[0].equalsIgnoreCase("Depth") ? 0 : 1, Double.parseDouble(kv2[1]));
                                }
                                else {
                                    blocks = new HashSet<Block>();
                                    innerSplit = (split4 = kv2[1].split(","));
                                    for (length3 = split4.length; n < length3; ++n) {
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
                                    if (toCheck == block2 || (block2 == Blocks.REDSTONE_ORE && toCheck == Blocks.LIT_REDSTONE_ORE) || (block2 == Blocks.LIT_REDSTONE_ORE && toCheck == Blocks.REDSTONE_ORE)) {
                                        if (!used) {
                                            cooldown4.use(500L);
                                            used = true;
                                        }
                                        PlayerHelper.removeBlock((EntityPlayerMP)event.getPlayer(), scan);
                                        item = event.getPlayer().getHeldItemMainhand();
                                        if (!item.isEmpty() && item.getItem() instanceof ItemPickaxe) {
                                            if (item.getItemDamage() >= item.getMaxDamage()) {
                                                break Label_0700_4;
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
                                            break Label_0700_4;
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
        Cooldown cooldown5;
        HashMap map3;
        long level2;
        double radius;
        long cd2;
        EntityPlayerMP player;
        AxisAlignedBB aabb;
        WorldServer world;
        List items;
        final Iterator<EntityItem> iterator3;
        EntityItem item2;
        HookRegistry.get().registerMethodHook("MAGNETIC_FIELD", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    map3 = AbilityHelper.getMap(ability5);
                    level2 = data.getTrueFunctionalLevel();
                    radius = map3.get("Radius") + map3.get("RadiusPerLvl") * level2;
                    cd2 = ability5.getCooldownMilliseconds() + map3.get("CDPerLvl").longValue() * level2;
                    player = data.player.getPlayerEntity();
                    cooldown5.use(cd2, 0L);
                    if (player != null) {
                        aabb = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                        world = player.getServerWorld();
                        items = world.getEntitiesWithinAABB((Class)EntityItem.class, aabb);
                        items.iterator();
                        while (iterator3.hasNext()) {
                            item2 = iterator3.next();
                            item2.setPositionAndUpdate(player.posX, player.posY + 0.5, player.posZ);
                        }
                        data.player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.1f, 0.5f);
                    }
                }
            }
            return;
        }));
        final Ability ability6;
        EntityPlayerMP player2;
        Cooldown cooldown6;
        HashMap map4;
        long level3;
        double power;
        long cd3;
        WorldServer world2;
        HookRegistry.get().registerMethodHook("EXPLOSIVE_MINING", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability6 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability6.getLevelRequirement()) {
                player2 = data.player.getPlayerEntity();
                cooldown6 = data.getCooldownSafely();
                if (!cooldown6.isOnCooldown()) {
                    map4 = AbilityHelper.getMap(ability6);
                    level3 = data.getTrueFunctionalLevel();
                    power = map4.get("Power") + map4.get("PowerPerLvl") * level3;
                    cd3 = ability6.getCooldownMilliseconds() + map4.get("CDPerLvl").longValue() * level3;
                    cooldown6.use(cd3, 500L);
                    world2 = player2.getServerWorld();
                    world2.newExplosion((Entity)player2, event.entity.posX, event.entity.posY + 0.5, event.entity.posZ, (float)power, false, true);
                }
            }
            return;
        }));
        final Ability ability7;
        EntityPlayerMP player3;
        Cooldown cooldown7;
        long level4;
        HashMap map5;
        double drops;
        World world3;
        final Iterator<BlockPos> iterator4;
        BlockPos pos;
        double d;
        IBlockState state;
        final Entity entity;
        Entity drop;
        HookRegistry.get().registerMethodHook("EXPLOSIVE_MINING", new MethodHook<Event>((Class<? extends Event>)ExplosionEvent.Detonate.class, (event, data) -> {
            ability7 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability7.getLevelRequirement() && event.getExplosion().getExplosivePlacedBy() instanceof EntityPlayerMP) {
                player3 = (EntityPlayerMP)event.getExplosion().getExplosivePlacedBy();
                if (player3.getUniqueID().equals(data.player.getUUID())) {
                    cooldown7 = data.getCooldownSafely();
                    if (cooldown7.isActive()) {
                        level4 = data.getTrueFunctionalLevel();
                        map5 = AbilityHelper.getMap(ability7);
                        drops = map5.get("ExtraDrops") + map5.get("ExtraDropsPerLvl") * level4 + 1.0;
                        event.getAffectedEntities().clear();
                        world3 = event.getWorld();
                        event.getAffectedBlocks().iterator();
                        while (iterator4.hasNext()) {
                            pos = iterator4.next();
                            d = drops;
                            state = world3.getBlockState(pos);
                            if (PlayerHelper.canRemoveBlock(player3, pos) && state.getBlock() instanceof BlockOre) {
                                while (d > 0.0) {
                                    if (RandomHelper.nextDouble() < d) {
                                        new EntityItem(world3, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), new ItemStack(state.getBlock().getItemDropped(state, world3.rand, 0)));
                                        drop = entity;
                                        world3.spawnEntity(drop);
                                    }
                                    --d;
                                }
                            }
                        }
                    }
                }
            }
        }));
    }
}
