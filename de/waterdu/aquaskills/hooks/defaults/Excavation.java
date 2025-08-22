//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.world.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.*;
import de.waterdu.aquaskills.hooks.*;

public class Excavation
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Excavation", new String[] { "Gain experience by digging up land with a shovel." }, "Excavator", "iron_shovel", 7, 1000L, new String[] { "wooden_shovel", "stone_shovel", "iron_shovel", "golden_shovel", "diamond_shovel" }, new XPSource[] { new XPSource("BREAK_BLOCK_XP", "shovels snow,dirt,grass,gravel,sand,soul_sand,coarse_dirt,podzol", 1.0), new XPSource("BREAK_BLOCK_XP", "shovels clay,mycelium", 2.0) }, new Ability[] { new Ability("DIGGERS_FORTUNE", "Eff=1.5 EffPerLvl=0.0035 FortPerLvl=0.001 CDPerLvl=-200 Dur=5000 DurPerLvl=20", 5L, 300000L, new DisplayInfo("Digger's Delve", "golden_shovel", 27, new String[] { "Hones your senses, improving your skills with a shovel for a brief moment.", "Gain Haste and Speed, as well as Fortune on shovels, for a brief time.", "Improves as your level of Excavation increases.", "", "Bindable. Click to choose an Excavation item to bind this to." })).setBindable(), new Ability("CAREFUL_TOUCH", "Chance=0.2 ChancePerLvl=0.0008", 25L, 0L, new DisplayInfo("Careful Touch", "diamond_shovel", 29, new String[] { "Your skill with the humble spade lets you preserve their quality.", "Reduces durability lost from using shovels.", "Improves as your level of Excavation increases.", "", "Passive ability." })), new Ability("LANDSCAPING", "CDPerLvl=-120 Radius=7 RadiusPerLvl=0.008 Blocks=50 BlocksPerLvl=0.2", 50L, 500000L, new DisplayInfo("Landscaping", "grass", 31, new String[] { "The land around you bends to your will.", "Destroys grass, dirt, sand and gravel around you automatically for a short while, while holding a shovel.", "Improves as your level of Excavation increases.", "", "Bindable. Click to choose an Excavation item to bind this to." })).setBindable(), new Ability("LAYER_BY_LAYER", "Depth=2 DepthPerLevel=0.005 Blocks=grass,mycelium,snow", 75L, 0L, new DisplayInfo("Layer By Layer", "mycelium", 33, new String[] { "Your precision in removing layers of topsoil is quite arcane.", "Allows you to dig up areas of topsoil in one go. Cannot be used while Landscaping is active.", "Improves as your level of Excavation increases.", "", "Activate by sneaking and breaking grass, mycelium, or snow with a shovel." })), new Ability("EXTRACTINATOR", "CDPerLvl=200 Mult=6 MultPerLvl=0.042", 150L, 300000L, new DisplayInfo("Extractinator", "flint", 35, new String[] { "What some consider trash, you consider treasure.", "Causes a shovelled block to drop many times its normal drops.", "Improves as your level of Excavation increases.", "", "Activates whenever it can." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Optional landscaping;
        Cooldown cooldown;
        String[] split;
        int i;
        final String[] array;
        int length;
        int k = 0;
        String str;
        String[] kv;
        HashSet<Block> blocks;
        String[] split2;
        String[] innerSplit;
        int length2;
        int l = 0;
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
        final Iterator<BlockPos> iterator;
        BlockPos scan;
        Block toCheck;
        ItemStack item;
        HookRegistry.get().registerMethodHook("LAYER_BY_LAYER", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            Label_0701_7: {
                if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                    landscaping = data.player.getCooldown("LANDSCAPING");
                    if ((!landscaping.isPresent() || !landscaping.get().isActive()) && event.getPlayer().isSneaking() && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSpade) {
                        cooldown = data.getCooldownSafely();
                        if (!cooldown.isOnCooldown()) {
                            if (!ability.areArgsParsed()) {
                                ability.getParsedArgs().add(0.0);
                                ability.getParsedArgs().add(0.0);
                                split = ability.getArgs().split(" ");
                                i = 0;
                                for (length = array.length; k < length; ++k) {
                                    str = array[k];
                                    kv = str.split("=");
                                    if (i < 2) {
                                        ability.getParsedArgs().set(kv[0].equalsIgnoreCase("Depth") ? 0 : 1, Double.parseDouble(kv[1]));
                                    }
                                    else {
                                        blocks = new HashSet<Block>();
                                        innerSplit = (split2 = kv[1].split(","));
                                        for (length2 = split2.length; l < length2; ++l) {
                                            blockStr = split2[l];
                                            block = Block.getBlockFromName(blockStr);
                                            if (block != null) {
                                                blocks.add(block);
                                            }
                                        }
                                        ability.getParsedArgs().add(blocks);
                                    }
                                    ++i;
                                }
                            }
                            depthBase = ability.getParsedArgs().get(0);
                            depthLevel = ability.getParsedArgs().get(1);
                            depth = (int)(depthBase + depthLevel * data.getTrueFunctionalLevel());
                            blocks2 = ability.getParsedArgs().get(2);
                            block2 = event.getState().getBlock();
                            if (blocks2.contains(block2)) {
                                used = false;
                                new HashSet(Arrays.asList(event.getPos().up(), event.getPos().down(), event.getPos().north(), event.getPos().south(), event.getPos().east(), event.getPos().west()));
                                toScan = (HashSet<Object>)set;
                                for (j = 0; j < depth; ++j) {
                                    newScan = new HashSet<BlockPos>();
                                    toScan.iterator();
                                    while (iterator.hasNext()) {
                                        scan = iterator.next();
                                        toCheck = event.getWorld().getBlockState(scan).getBlock();
                                        if (toCheck == block2) {
                                            if (!used) {
                                                cooldown.use(500L);
                                                used = true;
                                            }
                                            PlayerHelper.removeBlock((EntityPlayerMP)event.getPlayer(), scan);
                                            item = event.getPlayer().getHeldItemMainhand();
                                            if (!item.isEmpty() && item.getItem() instanceof ItemSpade) {
                                                if (item.getItemDamage() >= item.getMaxDamage()) {
                                                    break Label_0701_7;
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
                                                break Label_0701_7;
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
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown2;
        HashMap map;
        long level;
        long cd;
        long dur;
        int eff;
        int potionDur;
        HookRegistry.get().registerMethodHook("DIGGERS_FORTUNE", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    map = AbilityHelper.getMap(ability2);
                    level = data.getTrueFunctionalLevel();
                    cd = ability2.getCooldownMilliseconds() + map.get("CDPerLvl").longValue() * level;
                    dur = map.get("Dur").longValue() + map.get("DurPerLvl").longValue() * level;
                    eff = Math.max(1, (int)(map.get("Eff") + map.get("EffPerLvl") * level));
                    potionDur = (int)(dur / 50L);
                    PlayerHelper.addEffect((EntityLivingBase)event.entity, MobEffects.HASTE, potionDur, eff, true, false);
                    PlayerHelper.addEffect((EntityLivingBase)event.entity, MobEffects.SPEED, potionDur, eff, true, false);
                    cooldown2.use(cd, dur);
                    data.player.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1.0f, RandomHelper.nextFloat() * 0.1f + 0.9f);
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown3;
        HashMap map2;
        double fortune;
        final Iterator<ItemStack> iterator2;
        ItemStack stack;
        HookRegistry.get().registerMethodHook("DIGGERS_FORTUNE", new MethodHook<Event>((Class<? extends Event>)BlockEvent.HarvestDropsEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (cooldown3.isActive() && event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemSpade && !event.isSilkTouching() && !AquaSkills.blockLog.contains(event.getPos())) {
                    map2 = AbilityHelper.getMap(ability3);
                    fortune = map2.get("FortPerLvl") * data.getTrueFunctionalLevel();
                    if (RandomHelper.nextDouble() < fortune) {
                        event.getDrops().iterator();
                        while (iterator2.hasNext()) {
                            stack = iterator2.next();
                            stack.grow(1);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        HashMap map3;
        double chance;
        HookRegistry.get().registerMethodHook("CAREFUL_TOUCH", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement() && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSpade) {
                map3 = AbilityHelper.getMap(ability4);
                chance = map3.get("Chance") + map3.get("ChancePerLvl") * data.getTrueFunctionalLevel();
                if (RandomHelper.nextDouble() < chance) {
                    event.getPlayer().getHeldItemMainhand().attemptDamageItem(-1, event.getPlayer().world.rand, (EntityPlayerMP)null);
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        long level2;
        HashMap map4;
        double multiplier;
        long cd2;
        HookRegistry.get().registerMethodHook("EXTRACTINATOR", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement()) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown() && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSpade && event.getState().getBlock().isToolEffective("shovel", event.getState())) {
                    level2 = data.getTrueFunctionalLevel();
                    map4 = AbilityHelper.getMap(ability5);
                    for (multiplier = map4.get("Mult") + map4.get("MultPerLvl") * data.getTrueFunctionalLevel(); multiplier > 0.0; --multiplier) {
                        if (RandomHelper.nextDouble() < multiplier) {
                            event.getState().getBlock().dropBlockAsItemWithChance(event.getWorld(), event.getPos(), event.getState(), 1.0f, 0);
                        }
                    }
                    cd2 = ability5.getCooldownMilliseconds() + map4.get("CDPerLvl").longValue() * level2;
                    cooldown4.use(cd2);
                }
            }
            return;
        }));
        final Ability ability6;
        Cooldown cooldown5;
        long level3;
        HashMap map5;
        long cd3;
        long blocks3;
        HookRegistry.get().registerMethodHook("LANDSCAPING", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability6 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability6.getLevelRequirement()) {
                cooldown5 = data.getCooldownSafely();
                if (!cooldown5.isOnCooldown()) {
                    level3 = data.getTrueFunctionalLevel();
                    map5 = AbilityHelper.getMap(ability6);
                    cd3 = ability6.getCooldownMilliseconds() + map5.get("CDPerLvl").longValue() * level3;
                    blocks3 = (long)(map5.get("Blocks") + map5.get("BlocksPerLvl") * level3);
                    cooldown5.useOverride(cd3, blocks3);
                }
            }
            return;
        }));
        final Ability ability7;
        Cooldown cooldown6;
        long level4;
        HashMap map6;
        double radius;
        double x;
        double y;
        double z;
        BlockPos pos;
        IBlockState state;
        HookRegistry.get().registerMethodHook("LANDSCAPING", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability7 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability7.getLevelRequirement()) {
                cooldown6 = data.getCooldownSafely();
                if (cooldown6.getActiveUntil() > 0L) {
                    if (event.player.getHeldItemMainhand().getItem() instanceof ItemSpade) {
                        level4 = data.getTrueFunctionalLevel();
                        map6 = AbilityHelper.getMap(ability7);
                        radius = map6.get("Radius") + map6.get("RadiusPerLvl").longValue() * level4;
                        x = event.player.posX + RandomHelper.nextDouble() * radius * 2.0 - radius;
                        y = event.player.posY + RandomHelper.nextDouble() * radius - radius / 2.0;
                        z = event.player.posZ + RandomHelper.nextDouble() * radius * 2.0 - radius;
                        pos = new BlockPos(x, y, z);
                        if (!AquaSkills.blockLog.contains(pos)) {
                            state = event.player.world.getBlockState(pos);
                            if (state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockSand || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockGravel) {
                                PlayerHelper.removeBlock((EntityPlayerMP)event.player, pos);
                                cooldown6.setActiveUntil(cooldown6.getActiveUntil() - 1L);
                            }
                        }
                    }
                    else {
                        cooldown6.setActiveUntil(0L);
                    }
                }
            }
        }));
    }
}
