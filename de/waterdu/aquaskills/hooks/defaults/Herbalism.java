//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraft.block.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.event.internal.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import de.waterdu.aquaskills.hooks.*;

public class Herbalism
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Herbalism", new String[] { "Gain experience by tending to the land and plants." }, "Druid", "wheat_seeds", 10, 1000L, new String[] { "wooden_hoe", "stone_hoe", "iron_hoe", "golden_hoe", "diamond_hoe" }, new XPSource[] { new XPSource("HARVEST_CROP_XP", "chorus_plant,tallgrass,grass,vine,mushroom_stem", 1.0), new XPSource("HARVEST_CROP_XP", "melon,cocoa,carrots,potatoes,beetroots,wheat", 5.0), new XPSource("HARVEST_CROP_XP", "brown_mushroom_block,red_mushroom_block", 3.0), new XPSource("HARVEST_CROP_XP", "deadbush", 30.0), new XPSource("HARVEST_CROP_XP", "any_apricorn,any_berry", 10.0), new XPSource("BREAK_BLOCK_XP", "any leaves,leaves2", 1.0), new XPSource("BREAK_BLOCK_XP", "any red_flower,yellow_flower,waterlily,double_plant,cactus,reeds,nether_wart", 5.0) }, new Ability[] { new Ability("CIRCLE_OF_THE_SPORES", "", 5L, 0L, new DisplayInfo("Circle of the Spores", "red_mushroom", 28, new String[] { "Your druidic senses allow you to infuse the earth with mushroom spores with ease.", "You can use mushrooms to make mycelium.", "", "Activate by hitting a mushroom on a dirt block." })), new Ability("CIRCLE_OF_THE_MOON", "Drops=1 DropsPerLvl=0.003", 25L, 0L, new DisplayInfo("Circle of the Moon", "firework_charge", 30, new String[] { "You focus your druidry under the moonlight, granting a bountiful harvest.", "You harvest more crops at night.", "Improves as your level of Herbalism increases.", "", "Passive ability." })), new Ability("CIRCLE_OF_THE_LAND", "CDPerLvl=-200 Radius=5 RadiusPerLvl=0.015 Str=1 StrPerLvl=0.002", 50L, 300000L, new DisplayInfo("Circle of the Land", "farmland", 32, new String[] { "You can channel druidic powers into the land, nursing growth.", "Fertilizes all crops around you in one go.", "Improves as your level of Herbalism increases.", "", "Bindable. Click to choose a Herbalism item to bind this to." })).setBindable(), new Ability("TIMELESS_BODY", "", 250L, 0L, new DisplayInfo("Timeless Body", "diamond", 34, new String[] { "Your link with the world around you has made you one with it.", "You no longer require food or air.", "", "Passive ability." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Block block;
        HookRegistry.get().registerMethodHook("CIRCLE_OF_THE_SPORES", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement() && event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.DIRT && event.getItemStack().getItem() instanceof ItemBlock) {
                block = Block.getBlockFromItem(event.getItemStack().getItem());
                if (block == Blocks.BROWN_MUSHROOM || block == Blocks.RED_MUSHROOM) {
                    PlayerHelper.setBlock((EntityPlayerMP)event.getEntityPlayer(), event.getPos(), Blocks.MYCELIUM.getDefaultState(), false, true);
                    event.getItemStack().shrink(1);
                    event.setCanceled(true);
                    data.getCooldown().ifPresent(Cooldown::use);
                    data.player.playSound(SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.PLAYERS, 0.5f, 0.8f + RandomHelper.nextFloat() * 0.2f);
                }
            }
            return;
        }));
        final Ability ability2;
        HashMap map;
        long level;
        double multiplier;
        final Iterator<ItemStack> iterator;
        ItemStack drop;
        HookRegistry.get().registerMethodHook("CIRCLE_OF_THE_MOON", new MethodHook<Event>((Class<? extends Event>)BlockEvent.HarvestDropsEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement() && event.getState().getBlock() instanceof BlockCrops && event.getWorld().getWorldTime() >= 12000L && event.getWorld().getWorldTime() <= 24000L) {
                map = AbilityHelper.getMap(ability2);
                level = data.getTrueFunctionalLevel();
                for (multiplier = map.get("Drops") + map.get("DropsPerLvl") * level; multiplier > 0.0; --multiplier) {
                    if (RandomHelper.nextDouble() < multiplier) {
                        event.getDrops().iterator();
                        while (iterator.hasNext()) {
                            drop = iterator.next();
                            drop.grow(1);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown;
        HashMap map2;
        long level2;
        long cd;
        int radius;
        int str;
        int x;
        int y;
        int z;
        BlockPos pos;
        IBlockState state;
        BlockCrops crop;
        int i;
        HookRegistry.get().registerMethodHook("CIRCLE_OF_THE_LAND", new MethodHook<Event>((Class<? extends Event>)BoundItemEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    map2 = AbilityHelper.getMap(ability3);
                    level2 = data.getTrueFunctionalLevel();
                    cd = ability3.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                    radius = (int)(map2.get("Radius") + map2.get("RadiusPerLvl") * level2) + 1;
                    str = (int)(map2.get("Str") + map2.get("StrPerLvl") * level2);
                    for (x = -radius; x <= radius; ++x) {
                        for (y = -1; y <= 1; ++y) {
                            for (z = -radius; z <= radius; ++z) {
                                pos = event.entity.getPosition().add(x, y, z);
                                state = event.entity.getServerWorld().getBlockState(pos);
                                if (state.getBlock() instanceof BlockCrops) {
                                    crop = (BlockCrops)state.getBlock();
                                    for (i = 0; i < str; ++i) {
                                        crop.grow((World)event.entity.getServerWorld(), event.entity.getServerWorld().rand, pos, state);
                                    }
                                }
                            }
                        }
                    }
                    cooldown.use(cd, 0L);
                    data.player.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
            return;
        }));
        final Ability ability4;
        HookRegistry.get().registerMethodHook("TIMELESS_BODY", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                event.player.getFoodStats().addStats(1, 1.0f);
                event.player.setAir(300);
            }
        }));
    }
}
