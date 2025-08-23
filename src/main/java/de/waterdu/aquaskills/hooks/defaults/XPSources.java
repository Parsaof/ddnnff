//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import net.minecraftforge.event.world.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import de.waterdu.aquaskills.*;
import net.minecraft.init.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.world.biome.*;
import net.minecraftforge.event.brewing.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.file.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class XPSources
{
    public static void init() {
        final XPSource source;
        String[] split;
        String tool;
        final String[] array;
        int length;
        int i = 0;
        String str;
        Block block;
        String reqTool;
        boolean any;
        String tool2;
        HookRegistry.get().registerMethodHook("BREAK_BLOCK_XP", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            source = (XPSource)data.hookable;
            if (!source.areArgsParsed()) {
                split = source.getArgs().split(" ");
                tool = split[0];
                source.getParsedArgs().add(tool);
                split[1].split(",");
                for (length = array.length; i < length; ++i) {
                    str = array[i];
                    block = Block.getBlockFromName(str);
                    if (block != null) {
                        source.getParsedArgs().add(block);
                    }
                }
            }
            if (!Config.log().contains(event.getPos())) {
                reqTool = source.getParsedArgs().get(0);
                any = reqTool.equalsIgnoreCase("any");
                tool2 = "any";
                if (!any) {
                    if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
                        tool2 = "pickaxes";
                    }
                    else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSpade) {
                        tool2 = "shovels";
                    }
                    else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemHoe) {
                        tool2 = "hoes";
                    }
                    else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemAxe) {
                        tool2 = "axes";
                    }
                    else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSword) {
                        tool2 = "swords";
                    }
                    else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemShears) {
                        tool2 = "shears";
                    }
                    else {
                        tool2 = "hands";
                    }
                }
                if (reqTool.equalsIgnoreCase(tool2) && source.getParsedArgs().contains(event.getState().getBlock())) {
                    data.player.gainExperience(data.skill, source.getAmount());
                }
            }
            return;
        }));
        final IBlockState state;
        BlockCrops block2;
        int age;
        final XPSource source2;
        final String[] array2;
        int length2;
        int j = 0;
        String str2;
        Block block3;
        HookRegistry.get().registerMethodHook("HARVEST_CROP_XP", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            state = event.getState();
            if (state.getBlock() instanceof BlockCrops) {
                block2 = (BlockCrops)state.getBlock();
                age = (int)state.getValue((IProperty)BlockCrops.AGE);
                if (age < block2.getMaxAge()) {
                    return;
                }
            }
            source2 = (XPSource)data.hookable;
            if (!source2.areArgsParsed()) {
                source2.getArgs().split(",");
                for (length2 = array2.length; j < length2; ++j) {
                    str2 = array2[j];
                    block3 = Block.getBlockFromName(str2);
                    if (block3 != null) {
                        source2.getParsedArgs().add(block3);
                    }
                }
            }
            if (source2.getParsedArgs().contains(event.getState().getBlock())) {
                data.player.gainExperience(data.skill, source2.getAmount());
                AquaSkills.blockLog.remove(event.getPos());
            }
            return;
        }));
        final XPSource source3;
        final String[] array3;
        int length3;
        int k = 0;
        String str3;
        Item item;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        Item item2;
        HookRegistry.get().registerMethodHook("FISHING_XP", new MethodHook<Event>((Class<? extends Event>)ItemFishedEvent.class, (event, data) -> {
            source3 = (XPSource)data.hookable;
            if (!source3.areArgsParsed()) {
                source3.getArgs().split(",");
                for (length3 = array3.length; k < length3; ++k) {
                    str3 = array3[k];
                    if (str3.equalsIgnoreCase("pokemon")) {
                        source3.getParsedArgs().add(true);
                    }
                    else {
                        item = Item.getByNameOrId(str3);
                        if (item != null) {
                            source3.getParsedArgs().add(item);
                        }
                    }
                }
            }
            event.getDrops().iterator();
            while (iterator.hasNext()) {
                stack = iterator.next();
                item2 = stack.getItem();
                if (source3.getParsedArgs().contains(item2)) {
                    data.player.gainExperience(data.skill, source3.getAmount());
                }
            }
            return;
        }));
        XPSource source4;
        String[] split2;
        final String[] array4;
        int length4;
        int l = 0;
        String str4;
        boolean gainXP;
        EntityPlayerMP attacker;
        boolean directDamage;
        double cap;
        HookRegistry.get().registerMethodHook("ATTACK_XP", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                if (event.getAmount() != Float.MAX_VALUE) {
                    source4 = (XPSource)data.hookable;
                    if (!source4.areArgsParsed()) {
                        split2 = source4.getArgs().split(" ");
                        source4.getParsedArgs().add(Double.parseDouble(split2[0]));
                        if (split2.length >= 3) {
                            source4.getParsedArgs().add(Double.parseDouble(split2[2]));
                        }
                        else {
                            source4.getParsedArgs().add(-1.0);
                        }
                        split2[1].split(",");
                        for (length4 = array4.length; l < length4; ++l) {
                            str4 = array4[l];
                            source4.getParsedArgs().add(str4);
                        }
                    }
                    gainXP = false;
                    attacker = data.player.getPlayerEntity();
                    directDamage = (event.getSource().getImmediateSource() == attacker);
                    if (directDamage && attacker.getHeldItemMainhand().isEmpty()) {
                        gainXP = source4.getParsedArgs().contains("unarmed");
                    }
                    if (!gainXP && directDamage && (attacker.getHeldItemMainhand().getItem() == Items.STICK || attacker.getHeldItemMainhand().getItem() == Items.WOODEN_SWORD)) {
                        gainXP = source4.getParsedArgs().contains("monk");
                    }
                    if (!gainXP && directDamage && attacker.getHeldItemMainhand().getItem() instanceof ItemSword) {
                        gainXP = source4.getParsedArgs().contains("sword");
                    }
                    if (!gainXP && directDamage && attacker.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                        gainXP = source4.getParsedArgs().contains("axe");
                    }
                    if (!gainXP && event.getSource().damageType.equalsIgnoreCase("arrow")) {
                        gainXP = (source4.getParsedArgs().contains("bow") || source4.getParsedArgs().contains("arrow"));
                    }
                    if (!gainXP && event.getSource().isMagicDamage()) {
                        gainXP = source4.getParsedArgs().contains("magic");
                    }
                    if (!gainXP && event.getSource().isProjectile()) {
                        gainXP = source4.getParsedArgs().contains("projectile");
                    }
                    if (gainXP) {
                        cap = source4.getParsedArgs().get(1);
                        if (cap < 0.0) {
                            cap = event.getAmount();
                        }
                        data.player.gainExperience(data.skill, (double)(int)(source4.getParsedArgs().get(0) * Math.min(cap, event.getAmount())));
                    }
                }
            }
            return;
        }));
        XPSource source5;
        HookRegistry.get().registerMethodHook("POISON_PARROT_XP", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (!event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                source5 = (XPSource)data.hookable;
                if (event.getAmount() == Float.MAX_VALUE && event.getEntityLiving() instanceof EntityParrot) {
                    data.player.gainExperience(data.skill, source5.getAmount());
                }
            }
            return;
        }));
        XPSource source6;
        String[] split3;
        final String[] array5;
        int length5;
        int n = 0;
        String str5;
        double cap2;
        HookRegistry.get().registerMethodHook("HURT_XP", new MethodHook<Event>((Class<? extends Event>)LivingHurtEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                source6 = (XPSource)data.hookable;
                if (!source6.areArgsParsed()) {
                    split3 = source6.getArgs().split(" ");
                    source6.getParsedArgs().add(Double.parseDouble(split3[0]));
                    if (split3.length >= 3) {
                        source6.getParsedArgs().add(Double.parseDouble(split3[2]));
                    }
                    else {
                        source6.getParsedArgs().add(-1.0);
                    }
                    split3[1].split(",");
                    for (length5 = array5.length; n < length5; ++n) {
                        str5 = array5[n];
                        source6.getParsedArgs().add(str5);
                    }
                }
                if (source6.getParsedArgs().contains(event.getSource().damageType)) {
                    cap2 = source6.getParsedArgs().get(1);
                    if (cap2 < 0.0) {
                        cap2 = event.getAmount();
                    }
                    data.player.gainExperience(data.skill, (double)(int)(source6.getParsedArgs().get(0) * Math.min(cap2, event.getAmount())));
                }
            }
            return;
        }));
        XPSource source7;
        double[] args;
        String[] split6;
        String[] split4;
        int length6;
        int n2 = 0;
        String arg;
        String[] kv;
        double[] args2;
        double cap3;
        double dist;
        double min;
        HookRegistry.get().registerMethodHook("FALL_XP", new MethodHook<Event>((Class<? extends Event>)LivingFallEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                source7 = (XPSource)data.hookable;
                if (!source7.areArgsParsed()) {
                    args = new double[] { -1.0, -1.0, -1.0 };
                    split4 = (split6 = source7.getArgs().split(" "));
                    for (length6 = split6.length; n2 < length6; ++n2) {
                        arg = split6[n2];
                        kv = arg.split("=");
                        if (kv[0].equalsIgnoreCase("cap")) {
                            args[2] = Double.parseDouble(kv[1]);
                        }
                        else if (kv[0].equalsIgnoreCase("mult")) {
                            args[1] = Double.parseDouble(kv[1]);
                        }
                        else if (kv[0].equalsIgnoreCase("min")) {
                            args[0] = Double.parseDouble(kv[1]);
                        }
                    }
                    source7.getParsedArgs().add(args);
                }
                args2 = source7.getParsedArgs().get(0);
                cap3 = args2[2];
                if (cap3 <= 0.0) {
                    cap3 = event.getDistance();
                }
                dist = Math.min(cap3, event.getDistance());
                min = args2[0];
                if (min < 0.0 || dist >= min) {
                    data.player.gainExperience(data.skill, (double)(int)(args2[1] * dist));
                }
            }
            return;
        }));
        XPSource source8;
        double[] args3;
        String[] split7;
        String[] split5;
        int length7;
        int n3 = 0;
        String arg2;
        String[] kv2;
        double[] args4;
        double cap4;
        double dist2;
        double min2;
        HookRegistry.get().registerMethodHook("FLY_FALL_XP", new MethodHook<Event>((Class<? extends Event>)PlayerFlyableFallEvent.class, (event, data) -> {
            if (event.getEntityLiving().getUniqueID().equals(data.player.getUUID())) {
                source8 = (XPSource)data.hookable;
                if (!source8.areArgsParsed()) {
                    args3 = new double[] { -1.0, -1.0, -1.0 };
                    split5 = (split7 = source8.getArgs().split(" "));
                    for (length7 = split7.length; n3 < length7; ++n3) {
                        arg2 = split7[n3];
                        kv2 = arg2.split("=");
                        if (kv2[0].equalsIgnoreCase("cap")) {
                            args3[2] = Double.parseDouble(kv2[1]);
                        }
                        else if (kv2[0].equalsIgnoreCase("mult")) {
                            args3[1] = Double.parseDouble(kv2[1]);
                        }
                        else if (kv2[0].equalsIgnoreCase("min")) {
                            args3[0] = Double.parseDouble(kv2[1]);
                        }
                    }
                    source8.getParsedArgs().add(args3);
                }
                args4 = source8.getParsedArgs().get(0);
                cap4 = args4[2];
                if (cap4 <= 0.0) {
                    cap4 = event.getDistance();
                }
                dist2 = Math.min(cap4, event.getDistance());
                min2 = args4[0];
                if (min2 < 0.0 || dist2 >= min2) {
                    data.player.gainExperience(data.skill, (double)(int)(args4[1] * dist2));
                }
            }
            return;
        }));
        final XPSource source9;
        final String[] array6;
        int length8;
        int n4 = 0;
        String str6;
        Item item3;
        HookRegistry.get().registerMethodHook("ANVIL_XP", new MethodHook<Event>((Class<? extends Event>)AnvilRepairEvent.class, (event, data) -> {
            source9 = (XPSource)data.hookable;
            if (!source9.areArgsParsed()) {
                source9.getArgs().split(",");
                for (length8 = array6.length; n4 < length8; ++n4) {
                    str6 = array6[n4];
                    item3 = Item.getByNameOrId(str6);
                    if (item3 != null) {
                        source9.getParsedArgs().add(item3);
                    }
                    else {
                        source9.getParsedArgs().add(str6);
                    }
                }
            }
            if (source9.getParsedArgs().contains(event.getItemResult().getItem())) {
                data.player.gainExperience(data.skill, source9.getAmount());
            }
            return;
        }));
        final XPSource source10;
        final String[] array7;
        int length9;
        int n5 = 0;
        String str7;
        Item item4;
        HookRegistry.get().registerMethodHook("CRAFT_XP", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemCraftedEvent.class, (event, data) -> {
            source10 = (XPSource)data.hookable;
            if (!source10.areArgsParsed()) {
                source10.getArgs().split(",");
                for (length9 = array7.length; n5 < length9; ++n5) {
                    str7 = array7[n5];
                    item4 = Item.getByNameOrId(str7);
                    if (item4 != null) {
                        source10.getParsedArgs().add(item4);
                    }
                    else {
                        source10.getParsedArgs().add(str7);
                    }
                }
            }
            if (source10.getParsedArgs().contains(event.crafting.getItem())) {
                data.player.gainExperience(data.skill, source10.getAmount());
            }
            return;
        }));
        final XPSource source11;
        final String[] array8;
        int length10;
        int n6 = 0;
        String str8;
        Item item5;
        HookRegistry.get().registerMethodHook("SMELT_XP", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemSmeltedEvent.class, (event, data) -> {
            source11 = (XPSource)data.hookable;
            if (!source11.areArgsParsed()) {
                source11.getArgs().split(",");
                for (length10 = array8.length; n6 < length10; ++n6) {
                    str8 = array8[n6];
                    item5 = Item.getByNameOrId(str8);
                    if (item5 != null) {
                        source11.getParsedArgs().add(item5);
                    }
                    else {
                        source11.getParsedArgs().add(str8);
                    }
                }
            }
            if (source11.getParsedArgs().contains(event.smelting.getItem())) {
                data.player.gainExperience(data.skill, source11.getAmount() * event.smelting.getCount());
            }
            else if (source11.getParsedArgs().contains("any_apricorn") && event.smelting.getItem().getRegistryName().toString().contains("apricorn")) {
                data.player.gainExperience(data.skill, source11.getAmount() * event.smelting.getCount());
            }
            return;
        }));
        final XPSource source12;
        final String[] array9;
        int length11;
        int n7 = 0;
        String str9;
        Item item6;
        HookRegistry.get().registerMethodHook("PLACE_BLOCK_XP", new MethodHook<Event>((Class<? extends Event>)BlockEvent.PlaceEvent.class, (event, data) -> {
            source12 = (XPSource)data.hookable;
            if (!source12.areArgsParsed()) {
                source12.getArgs().split(",");
                for (length11 = array9.length; n7 < length11; ++n7) {
                    str9 = array9[n7];
                    if (!str9.isEmpty()) {
                        item6 = Item.getByNameOrId(str9);
                        if (item6 != null) {
                            source12.getParsedArgs().add(item6);
                        }
                    }
                }
            }
            if (source12.getParsedArgs().isEmpty() || source12.getParsedArgs().contains(event.getState().getBlock())) {
                data.player.gainExperience(data.skill, source12.getAmount());
            }
            return;
        }));
        final XPSource source13;
        final EntityPlayerMP player;
        AbilityInfo info;
        Biome biome;
        int id;
        ArrayList biomes;
        HookRegistry.get().registerMethodHook("VISIT_BIOME_XP", new MethodHook<Event>((Class<? extends Event>)TickEvent.PlayerTickEvent.class, (event, data) -> {
            source13 = (XPSource)data.hookable;
            player = data.player.getPlayerEntity();
            if (player != null) {
                info = data.player.getPersistentInfo("VISIT_BIOME_XP");
                biome = player.getServerWorld().getBiome(player.getPosition());
                id = Biome.getIdForBiome(biome);
                biomes = info.getIntegers();
                if (!biomes.contains(id)) {
                    data.player.gainExperience(data.skill, source13.getAmount());
                    biomes.add(id);
                }
            }
            return;
        }));
        final XPSource source14;
        HookRegistry.get().registerMethodHook("BREW_XP", new MethodHook<Event>((Class<? extends Event>)PlayerBrewedPotionEvent.class, (event, data) -> {
            source14 = (XPSource)data.hookable;
            data.player.gainExperience(data.skill, source14.getAmount());
            return;
        }));
        final XPSource source15;
        HookRegistry.get().registerMethodHook("SHOOT_XP", new MethodHook<Event>((Class<? extends Event>)ArrowLooseEvent.class, (event, data) -> {
            source15 = (XPSource)data.hookable;
            if (event.hasAmmo() && ItemBow.getArrowVelocity(event.getCharge()) >= 0.1) {
                data.player.gainExperience(data.skill, source15.getAmount());
            }
            return;
        }));
        final XPSource source16;
        HookRegistry.get().registerMethodHook("TELEPORT_XP", new MethodHook<Event>((Class<? extends Event>)EnderTeleportEvent.class, (event, data) -> {
            source16 = (XPSource)data.hookable;
            data.player.gainExperience(data.skill, source16.getAmount());
            return;
        }));
        final XPSource source17;
        HookRegistry.get().registerMethodHook("CHAT_XP", new MethodHook<Event>((Class<? extends Event>)ServerChatEvent.class, (event, data) -> {
            source17 = (XPSource)data.hookable;
            data.player.gainExperience(data.skill, source17.getAmount());
            return;
        }));
        final XPSource source18;
        final String[] array10;
        int length12;
        int n8 = 0;
        String str10;
        Item item7;
        HookRegistry.get().registerMethodHook("PICKUP_XP", new MethodHook<Event>((Class<? extends Event>)PlayerEvent.ItemPickupEvent.class, (event, data) -> {
            source18 = (XPSource)data.hookable;
            if (!source18.areArgsParsed()) {
                source18.getArgs().split(",");
                for (length12 = array10.length; n8 < length12; ++n8) {
                    str10 = array10[n8];
                    item7 = Item.getByNameOrId(str10);
                    if (item7 != null) {
                        source18.getParsedArgs().add(item7);
                    }
                }
            }
            if ((source18.getParsedArgs().isEmpty() || source18.getParsedArgs().contains(event.getStack().getItem())) && (event.getOriginalEntity().getThrower() == null || !event.getOriginalEntity().getThrower().equalsIgnoreCase(data.player.getName()))) {
                data.player.gainExperience(data.skill, source18.getAmount());
            }
            return;
        }));
        final XPSource source19;
        final String[] array11;
        int length13;
        int n9 = 0;
        String str11;
        Block block4;
        HookRegistry.get().registerMethodHook("BONEMEAL_XP", new MethodHook<Event>((Class<? extends Event>)BonemealEvent.class, (event, data) -> {
            source19 = (XPSource)data.hookable;
            if (!source19.areArgsParsed()) {
                source19.getArgs().split(",");
                for (length13 = array11.length; n9 < length13; ++n9) {
                    str11 = array11[n9];
                    block4 = Block.getBlockFromName(str11);
                    if (block4 != null) {
                        source19.getParsedArgs().add(block4);
                    }
                }
            }
            if (source19.getParsedArgs().isEmpty() || source19.getParsedArgs().contains(event.getBlock().getBlock())) {
                data.player.gainExperience(data.skill, source19.getAmount());
            }
            return;
        }));
        final XPSource source20;
        HookRegistry.get().registerMethodHook("CAST_XP", new MethodHook<Event>((Class<? extends Event>)AbilityUseEvent.class, (event, data) -> {
            source20 = (XPSource)data.hookable;
            if (!source20.areArgsParsed()) {
                if (source20.getArgs().isEmpty()) {
                    source20.getParsedArgs().add(null);
                }
                else {
                    source20.getParsedArgs().add(source20.getArgs());
                }
            }
            if (event.ability.isBindable() && (source20.getParsedArgs().contains(null) || source20.getParsedArgs().contains(event.skill.getName()))) {
                data.player.gainExperience(data.skill, source20.getAmount());
            }
            return;
        }));
        final XPSource source21;
        HookRegistry.get().registerMethodHook("SPELL_XP", new MethodHook<Event>((Class<? extends Event>)SpellImpactEvent.class, (event, data) -> {
            source21 = (XPSource)data.hookable;
            if (!source21.areArgsParsed()) {
                if (source21.getArgs().isEmpty()) {
                    source21.getParsedArgs().add(null);
                }
                else {
                    source21.getParsedArgs().add(source21.getArgs());
                }
            }
            if (event.ability.isBindable() && (source21.getParsedArgs().contains(null) || source21.getParsedArgs().contains(event.skill.getName()))) {
                data.player.gainExperience(data.skill, source21.getAmount());
            }
            return;
        }));
        final XPSource source22;
        final String[] array12;
        int length14;
        int n10 = 0;
        String arg3;
        HookRegistry.get().registerMethodHook("TAME_XP", new MethodHook<Event>((Class<? extends Event>)AnimalTameEvent.class, (event, data) -> {
            source22 = (XPSource)data.hookable;
            if (!source22.areArgsParsed()) {
                if (source22.getArgs().isEmpty()) {
                    source22.getParsedArgs().add(null);
                }
                else {
                    source22.getArgs().split(" ");
                    for (length14 = array12.length; n10 < length14; ++n10) {
                        arg3 = array12[n10];
                        source22.getParsedArgs().add(arg3);
                    }
                }
            }
            if (source22.getParsedArgs().contains(null) || source22.getParsedArgs().contains(event.getAnimal().getClass().getSimpleName())) {
                data.player.gainExperience(data.skill, source22.getAmount());
            }
            return;
        }));
        final XPSource source23;
        final String[] array13;
        int length15;
        int n11 = 0;
        String arg4;
        HookRegistry.get().registerMethodHook("BABY_XP", new MethodHook<Event>((Class<? extends Event>)BabyEntitySpawnEvent.class, (event, data) -> {
            source23 = (XPSource)data.hookable;
            if (event.getChild() != null) {
                if (!source23.areArgsParsed()) {
                    if (source23.getArgs().isEmpty()) {
                        source23.getParsedArgs().add(null);
                    }
                    else {
                        source23.getArgs().split(" ");
                        for (length15 = array13.length; n11 < length15; ++n11) {
                            arg4 = array13[n11];
                            source23.getParsedArgs().add(arg4);
                        }
                    }
                }
                if (source23.getParsedArgs().contains(null) || source23.getParsedArgs().contains(event.getChild().getClass().getSimpleName())) {
                    data.player.gainExperience(data.skill, source23.getAmount());
                }
            }
        }));
    }
}
