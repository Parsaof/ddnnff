//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.enchantment.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.stats.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import de.waterdu.aquaskills.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import de.waterdu.aquaskills.hooks.*;

public class Fishing
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Fishing", new String[] { "Gain experience by reeling in those bites on your hook." }, "Angler", "fishing_rod", 8, 1000L, new String[] { "slime_ball", "magma_cream", "dye" }, new XPSource[] { new XPSource("FISHING_XP", "pokemon", 35.0), new XPSource("FISHING_XP", "dye,tripwire_hook,waterlily,bowl,leather,leather_boots,rotten_flesh,stick,string,potion,bone", 10.0), new XPSource("FISHING_XP", "fish", 50.0), new XPSource("FISHING_XP", "bow,fishing_rod", 75.0), new XPSource("FISHING_XP", "saddle,name_tag,enchanted_book", 125.0), new XPSource("FISHING_XP", "pixelmon:red_shard,pixelmon:blue_shard", 500.0) }, new Ability[] { new Ability("LUCK_OF_THE_SEA", "Mult=1.15 MultPerLvl=0.002", 5L, 0L, new DisplayInfo("Luck of the Sea", "fish", 27, new String[] { "Your attachment to the water allows your hook to find the best spots.", "Gain improved drops and encounters with all rods.", "Improves as your level of Fishing increases.", "", "Passive ability." })), new Ability("ICE_FISHING", "", 25L, 5000L, new DisplayInfo("Ice Fishing", "ice", 29, new String[] { "A crafty angler can find fishing opportunities anywhere.", "Creates an opening to the water below ice where your hook lands.", "", "Passive ability." })), new Ability("OPEN_SEAS", "", 50L, 0L, new DisplayInfo("Open Seas", "boat", 31, new String[] { "The trusty boat is a fisherman's second best friend.", "You can store and summon a boat.", "", "To store, sneak and right click a boat.", "To summon, right click on a block below water." }), false), new Ability("WATERBREATHING", "", 100L, 0L, new DisplayInfo("Waterbreathing", "water_bucket", 33, new String[] { "Ocean man, take me by the hand.", "You can breathe underwater.", "", "Passive ability." })), new Ability("PICKPOCKET_ANGLING", "CDPerLvl=-50", 150L, 60000L, new DisplayInfo("Pickpocket Angling", "gold_nugget", 35, new String[] { "Sometimes, the greatest catch isn't that from the waters.", "Your hook drags items out of the pockets of creatures hit.", "Improves as your level of Fishing increases.", "", "Activates whenever it can." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        HashMap map;
        long level;
        double multiplier;
        EntityPlayerMP player;
        WorldServer world;
        LootContext.Builder builder;
        List result;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        EntityItem item;
        double d0;
        double d2;
        double d3;
        double d4;
        Item i;
        HookRegistry.get().registerMethodHook("LUCK_OF_THE_SEA", new MethodHook<Event>((Class<? extends Event>)ItemFishedEvent.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                map = AbilityHelper.getMap(ability);
                level = data.getTrueFunctionalLevel();
                multiplier = map.get("Mult") + map.get("MultPerLvl") * level;
                player = data.player.getPlayerEntity();
                while (multiplier > 0.0) {
                    if (player != null && RandomHelper.nextDouble() < multiplier) {
                        world = player.getServerWorld();
                        builder = new LootContext.Builder(world);
                        builder.withLuck(EnchantmentHelper.getFishingLuckBonus(player.getHeldItemMainhand()) + player.getLuck()).withPlayer((EntityPlayer)player).withLootedEntity((Entity)event.getHookEntity());
                        result = world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(world.rand, builder.build());
                        result.iterator();
                        while (iterator.hasNext()) {
                            stack = iterator.next();
                            item = new EntityItem((World)world, event.getHookEntity().posX, event.getHookEntity().posY, event.getHookEntity().posZ, stack);
                            d0 = player.posX - event.getHookEntity().posX;
                            d2 = player.posY - event.getHookEntity().posY;
                            d3 = player.posZ - event.getHookEntity().posZ;
                            d4 = MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
                            item.motionX = d0 * 0.1;
                            item.motionY = d2 * 0.1 + MathHelper.sqrt(d4) * 0.08;
                            item.motionZ = d3 * 0.1;
                            world.spawnEntity((Entity)item);
                            i = stack.getItem();
                            if (i == Items.FISH || i == Items.COOKED_FISH) {
                                player.addStat(StatList.FISH_CAUGHT, 1);
                            }
                        }
                    }
                    --multiplier;
                }
            }
            return;
        }));
        final Ability ability2;
        Cooldown cooldown;
        BlockPos testA;
        BlockPos testB;
        IBlockState a;
        IBlockState b;
        BlockPos toBreak;
        int x;
        int z;
        BlockPos pos;
        boolean set;
        EntityPlayerMP player2;
        HookRegistry.get().registerMethodHook("ICE_FISHING", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement() && event.player.fishEntity != null && event.player.fishEntity.onGround && event.player.fishEntity.caughtEntity == null) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    testA = event.player.fishEntity.getPosition();
                    testB = testA.add(0, -1, 0);
                    a = event.player.world.getBlockState(testA);
                    b = event.player.world.getBlockState(testB);
                    toBreak = null;
                    if (a.getBlock() instanceof BlockIce) {
                        toBreak = testA;
                    }
                    else if (b.getBlock() instanceof BlockIce) {
                        toBreak = testB;
                    }
                    if (toBreak != null) {
                        for (x = 0; x < 5; ++x) {
                            for (z = 0; z < 5; ++z) {
                                pos = toBreak.add(x - 2, 0, z - 2);
                                set = false;
                                if ((z == 0 || z == 4) && x == 2) {
                                    set = true;
                                }
                                else if ((z == 1 || z == 3) && x != 0 && x != 4) {
                                    set = true;
                                }
                                else if (z == 2) {
                                    set = true;
                                }
                                if (set && event.player.world.getBlockState(pos).getBlock() instanceof BlockIce && !AquaSkills.blockLog.contains(pos)) {
                                    player2 = (EntityPlayerMP)event.player;
                                    if (PlayerHelper.removeBlock(player2, pos)) {
                                        PlayerHelper.setBlock(player2, pos, Blocks.WATER.getDefaultState(), true);
                                    }
                                }
                            }
                        }
                        cooldown.use(ability2.getCooldownMilliseconds());
                    }
                }
            }
            return;
        }));
        final Ability ability3;
        EntityPlayerMP player3;
        Cooldown cooldown2;
        EntityBoat boat;
        final TextComponentString textComponentString;
        final Object o;
        HookRegistry.get().registerMethodHook("OPEN_SEAS", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.EntityInteract.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                player3 = (EntityPlayerMP)event.getEntityPlayer();
                cooldown2 = data.getCooldownSafely();
                if (player3.isSneaking() && event.getTarget() instanceof EntityBoat) {
                    if (cooldown2.getActiveUntil() == -1L) {
                        boat = (EntityBoat)event.getTarget();
                        if (boat.getPassengers().isEmpty()) {
                            cooldown2.useCooldownOnly(0L);
                            cooldown2.setActiveUntil(boat.getBoatType().ordinal());
                            boat.setDead();
                            data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                    }
                    else {
                        new TextComponentString(Config.negative("alreadyStored", new Object[] { Config.format("boat", new Object[0]) }));
                        ((EntityPlayerMP)o).sendMessage((ITextComponent)textComponentString);
                    }
                    event.setCanceled(true);
                }
            }
            return;
        }));
        final Ability ability4;
        EntityPlayerMP player4;
        Cooldown cooldown3;
        Block block;
        EntityBoat.Type type;
        EntityBoat boat2;
        HookRegistry.get().registerMethodHook("OPEN_SEAS", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.RightClickBlock.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                player4 = (EntityPlayerMP)event.getEntityPlayer();
                cooldown3 = data.getCooldownSafely();
                block = event.getWorld().getBlockState(event.getPos().add(0, 1, 0)).getBlock();
                if (player4.isSneaking() && (block == Blocks.WATER || block == Blocks.FLOWING_WATER) && cooldown3.getActiveUntil() != -1L) {
                    type = EntityBoat.Type.values()[(int)cooldown3.getActiveUntil()];
                    boat2 = new EntityBoat((World)player4.getServerWorld());
                    boat2.setBoatType(type);
                    boat2.setPositionAndUpdate(event.getPos().getX() + 0.5, event.getPos().getY() + 2.5, event.getPos().getZ() + 0.5);
                    player4.getServerWorld().spawnEntity((Entity)boat2);
                    cooldown3.useCooldownOnly(0L);
                    cooldown3.setActiveUntil(-1L);
                    data.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                }
            }
            return;
        }));
        final Ability ability5;
        Cooldown cooldown4;
        EntityPlayerMP player5;
        EntityPlayerMP target;
        int slot;
        ItemStack stack2;
        int j;
        HashMap map2;
        long level2;
        long cd;
        ItemStack newStack;
        EntityItem item2;
        HookRegistry.get().registerMethodHook("PICKPOCKET_ANGLING", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability5 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability5.getLevelRequirement() && event.player.fishEntity != null && event.player.fishEntity.caughtEntity instanceof EntityPlayerMP) {
                cooldown4 = data.getCooldownSafely();
                if (!cooldown4.isOnCooldown()) {
                    player5 = data.player.getPlayerEntity();
                    target = (EntityPlayerMP)event.player.fishEntity.caughtEntity;
                    slot = RandomHelper.nextInt(target.inventory.mainInventory.size());
                    stack2 = target.inventory.getStackInSlot(slot);
                    if (!stack2.isEmpty() && target.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                        j = event.player.fishEntity.handleHookRetraction();
                        target.getHeldItemMainhand().damageItem(j, (EntityLivingBase)player5);
                        player5.swingArm(EnumHand.MAIN_HAND);
                        player5.getServerWorld().playSound((EntityPlayer)null, player5.posX, player5.posY, player5.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f / (player5.getServerWorld().rand.nextFloat() * 0.4f + 0.8f));
                        map2 = AbilityHelper.getMap(ability5);
                        level2 = data.getTrueFunctionalLevel();
                        cd = ability5.getCooldownMilliseconds() + map2.get("CDPerLvl").longValue() * level2;
                        cooldown4.use(cd);
                        newStack = stack2.copy();
                        newStack.setCount(1);
                        item2 = new EntityItem((World)target.getServerWorld(), target.posX + 0.5, target.posY + 1.0, target.posZ + 0.5, newStack);
                        item2.setPickupDelay(60);
                        item2.motionX = (RandomHelper.nextDouble() - 0.5) * 1.0;
                        item2.motionY = RandomHelper.nextDouble() * 1.0;
                        item2.motionZ = (RandomHelper.nextDouble() - 0.5) * 1.0;
                        target.getServerWorld().spawnEntity((Entity)item2);
                        target.sendMessage((ITextComponent)new TextComponentString(Config.neutral("pickpocket", new Object[0])));
                        stack2.shrink(1);
                        target.inventory.markDirty();
                        data.player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 0.1f, 1.0f);
                    }
                }
            }
            return;
        }));
        final Ability ability6;
        EntityPlayerMP player6;
        HookRegistry.get().registerMethodHook("WATERBREATHING", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            ability6 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability6.getLevelRequirement() && TimeHelper.isSecond()) {
                player6 = data.player.getPlayerEntity();
                player6.setAir(300);
            }
        }));
    }
}
