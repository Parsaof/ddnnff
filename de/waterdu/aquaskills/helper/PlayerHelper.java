//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class PlayerHelper
{
    public static Player getPlayer(final String name) {
        for (final Player player : AquaConfig.getAll("aquaskills", (Class)Player.class)) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
    
    public static boolean setBlock(final EntityPlayerMP player, final BlockPos pos, final IBlockState state, final boolean ifAir) {
        return setBlock(player, pos, state, ifAir, false);
    }
    
    public static boolean setBlock(final EntityPlayerMP player, final BlockPos pos, final IBlockState state, final boolean ifAir, final boolean ignoreChecks) {
        final WorldServer world = player.getServerWorld();
        if (ifAir && !world.isAirBlock(pos)) {
            return false;
        }
        if (!ignoreChecks && !canPlaceBlock(player, pos, state)) {
            return false;
        }
        if (ignoreChecks && ForgeEventFactory.onBlockPlace((Entity)player, new BlockSnapshot((World)player.getServerWorld(), pos, state), EnumFacing.UP).isCanceled()) {
            return false;
        }
        world.setBlockState(pos, state);
        return true;
    }
    
    public static boolean canPlaceBlock(final EntityPlayerMP player, final BlockPos pos, final IBlockState state) {
        return player.getServerWorld().mayPlace(state.getBlock(), pos, false, EnumFacing.UP, (Entity)player);
    }
    
    public static boolean canRemoveBlock(final EntityPlayerMP player, final BlockPos pos) {
        final WorldServer world = player.getServerWorld();
        final IBlockState state = world.getBlockState(pos);
        final Block block = state.getBlock();
        if (block.canHarvestBlock((IBlockAccess)world, pos, (EntityPlayer)player)) {
            final BlockEvent.BreakEvent event = new BlockEvent.BreakEvent((World)world, pos, state, (EntityPlayer)player);
            return !MinecraftForge.EVENT_BUS.post((Event)event);
        }
        return false;
    }
    
    public static boolean removeBlock(final EntityPlayerMP player, final BlockPos pos) {
        return removeBlock(player, pos, true);
    }
    
    public static boolean removeBlock(final EntityPlayerMP player, final BlockPos pos, final boolean attemptDrop) {
        final WorldServer world = player.getServerWorld();
        final IBlockState state = world.getBlockState(pos);
        final Block block = state.getBlock();
        final boolean flag = block.canHarvestBlock((IBlockAccess)world, pos, (EntityPlayer)player);
        if (flag && removeBlock(pos, (World)world, player, true)) {
            if (attemptDrop) {
                if (!player.getHeldItemMainhand().isEmpty()) {
                    player.getHeldItemMainhand().damageItem(1, (EntityLivingBase)player);
                }
                block.harvestBlock((World)world, (EntityPlayer)player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
            }
            return true;
        }
        return false;
    }
    
    public static boolean removeBlock(final BlockPos pos, final World world, final EntityPlayerMP player, final boolean canHarvest) {
        final IBlockState state = world.getBlockState(pos);
        boolean flag = false;
        final Block block = state.getBlock();
        final BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, (EntityPlayer)player);
        if (!MinecraftForge.EVENT_BUS.post((Event)event)) {
            block.onBlockHarvested(world, pos, state, (EntityPlayer)player);
            flag = block.removedByPlayer(state, world, pos, (EntityPlayer)player, canHarvest);
            if (flag) {
                animateBlockBreak(world, pos, state);
                block.onPlayerDestroy(world, pos, state);
            }
        }
        return flag;
    }
    
    public static void animateBlockBreak(final World world, final BlockPos pos, final IBlockState state) {
        world.playEvent(2001, pos, Block.getStateId(state));
    }
    
    public static void setBottomTitle(final ITextComponent content, final EntityPlayerMP player) {
        if (player != null && !player.hasDisconnected()) {
            player.connection.sendPacket((Packet)new SPacketTitle(SPacketTitle.Type.ACTIONBAR, content));
        }
    }
    
    public static void clearBottomTitle(final EntityPlayerMP player) {
        if (player != null && !player.hasDisconnected()) {
            player.connection.sendPacket((Packet)new SPacketTitle(SPacketTitle.Type.ACTIONBAR, (ITextComponent)new TextComponentString("")));
        }
    }
    
    public static void addEffect(final EntityLivingBase entity, final Potion potion, final int duration, final int amplifier) {
        addEffect(entity, potion, duration, amplifier, true, false);
    }
    
    public static void addEffect(final EntityLivingBase entity, final Potion potion, final int duration, final int amplifier, final boolean ambient, final boolean particles) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        server.addScheduledTask(() -> entity.addPotionEffect(new PotionEffect(potion, duration, amplifier, ambient, particles)));
    }
    
    public static AxisAlignedBB getAreaAround(final Entity entity, final double radius) {
        return new AxisAlignedBB(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
    }
    
    public static void teleport(final EntityPlayerMP entity, final int dimension, final double x, final double y, final double z) {
        final double motionX = 0.0;
        entity.motionZ = motionX;
        entity.motionY = motionX;
        entity.motionX = motionX;
        entity.fallDistance = 0.0f;
        entity.connection.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
        if (entity.world.provider.getDimension() != dimension && entity.getServer() != null) {
            final WorldServer destination = entity.getServer().getWorld(dimension);
            final Teleporter teleporter = (Teleporter)new GenericTeleporter(destination);
            entity.server.getPlayerList().transferPlayerToDimension(entity, dimension, teleporter);
            findSafeTeleportLocation(entity, entity.world.rand, entity.getServerWorld());
        }
    }
    
    public static void forceTeleport(final EntityPlayerMP entity, final int dimension, final double x, final double y, final double z, final float yaw, final float pitch) {
        final double motionX = 0.0;
        entity.motionZ = motionX;
        entity.motionY = motionX;
        entity.motionX = motionX;
        entity.fallDistance = 0.0f;
        entity.connection.setPlayerLocation(x, y, z, yaw, pitch);
        if (entity.world.provider.getDimension() != dimension && entity.getServer() != null) {
            final WorldServer destination = entity.getServer().getWorld(dimension);
            entity.server.getPlayerList().transferPlayerToDimension(entity, dimension, (Teleporter)new GenericTeleporter(destination));
        }
    }
    
    public static boolean findSafeTeleportLocation(final EntityPlayerMP entityIn, final Random random, final WorldServer world) {
        final int i = 16;
        double d0 = -1.0;
        final int j = MathHelper.floor(entityIn.posX);
        final int k = MathHelper.floor(entityIn.posY);
        final int l = MathHelper.floor(entityIn.posZ);
        int i2 = j;
        int j2 = k;
        int k2 = l;
        int l2 = 0;
        final int i3 = random.nextInt(4);
        final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int j3 = j - 16; j3 <= j + 16; ++j3) {
            final double d2 = j3 + 0.5 - entityIn.posX;
            for (int l3 = l - 16; l3 <= l + 16; ++l3) {
                final double d3 = l3 + 0.5 - entityIn.posZ;
            Label_0448:
                for (int j4 = world.getActualHeight() - 1; j4 >= 0; --j4) {
                    if (world.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(j3, j4, l3))) {
                        while (j4 > 0 && world.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(j3, j4 - 1, l3))) {
                            --j4;
                        }
                        for (int k3 = i3; k3 < i3 + 4; ++k3) {
                            int l4 = k3 % 2;
                            int i4 = 1 - l4;
                            if (k3 % 4 >= 2) {
                                l4 = -l4;
                                i4 = -i4;
                            }
                            for (int j5 = 0; j5 < 3; ++j5) {
                                for (int k4 = 0; k4 < 4; ++k4) {
                                    for (int l5 = -1; l5 < 4; ++l5) {
                                        final int i5 = j3 + (k4 - 1) * l4 + j5 * i4;
                                        final int j6 = j4 + l5;
                                        final int k5 = l3 + (k4 - 1) * i4 - j5 * l4;
                                        blockpos$mutableblockpos.setPos(i5, j6, k5);
                                        if (l5 < 0 && !world.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial().isSolid()) {
                                            continue Label_0448;
                                        }
                                        if (l5 >= 0 && !world.isAirBlock((BlockPos)blockpos$mutableblockpos)) {
                                            continue Label_0448;
                                        }
                                    }
                                }
                            }
                            final double d4 = j4 + 0.5 - entityIn.posY;
                            final double d5 = d2 * d2 + d4 * d4 + d3 * d3;
                            if (d0 < 0.0 || d5 < d0) {
                                d0 = d5;
                                i2 = j3;
                                j2 = j4;
                                k2 = l3;
                                l2 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }
        if (d0 < 0.0) {
            for (int l6 = j - 16; l6 <= j + 16; ++l6) {
                final double d6 = l6 + 0.5 - entityIn.posX;
                for (int j7 = l - 16; j7 <= l + 16; ++j7) {
                    final double d7 = j7 + 0.5 - entityIn.posZ;
                Label_0810:
                    for (int i6 = world.getActualHeight() - 1; i6 >= 0; --i6) {
                        if (world.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(l6, i6, j7))) {
                            while (i6 > 0 && world.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(l6, i6 - 1, j7))) {
                                --i6;
                            }
                            for (int k6 = i3; k6 < i3 + 2; ++k6) {
                                final int j8 = k6 % 2;
                                final int j9 = 1 - j8;
                                for (int j10 = 0; j10 < 4; ++j10) {
                                    for (int j11 = -1; j11 < 4; ++j11) {
                                        final int j12 = l6 + (j10 - 1) * j8;
                                        final int i7 = i6 + j11;
                                        final int j13 = j7 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i7, j13);
                                        if (j11 < 0 && !world.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial().isSolid()) {
                                            continue Label_0810;
                                        }
                                        if (j11 >= 0 && !world.isAirBlock((BlockPos)blockpos$mutableblockpos)) {
                                            continue Label_0810;
                                        }
                                    }
                                }
                                final double d8 = i6 + 0.5 - entityIn.posY;
                                final double d9 = d6 * d6 + d8 * d8 + d7 * d7;
                                if (d0 < 0.0 || d9 < d0) {
                                    d0 = d9;
                                    i2 = l6;
                                    j2 = i6;
                                    k2 = j7;
                                    l2 = k6 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        final int i8 = i2;
        final int k7 = j2;
        final int k8 = k2;
        entityIn.connection.setPlayerLocation((double)i8, (double)(k7 + 2), (double)k8, random.nextFloat() * 2.0f - 1.0f, random.nextFloat() * 2.0f - 1.0f);
        return true;
    }
    
    public static float lookAt(final Position target, final EntityLivingBase me) {
        double dx = me.posX - target.getX();
        double dy = me.posY - target.getY();
        double dz = me.posZ - target.getZ();
        final double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= len;
        dy /= len;
        dz /= len;
        double pitch = Math.asin(dy);
        double yaw = Math.atan2(dz, dx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        me.rotationYaw = NumberHelper.boundFloat((float)yaw);
        me.rotationPitch = NumberHelper.boundFloat((float)pitch);
        if (me instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP)me;
            final SPacketPlayerPosLook packet = new SPacketPlayerPosLook(me.posX, me.posY, me.posZ, me.rotationYaw, me.rotationPitch, (Set)new HashSet(), 0);
            player.connection.sendPacket((Packet)packet);
        }
        return (float)yaw;
    }
}
