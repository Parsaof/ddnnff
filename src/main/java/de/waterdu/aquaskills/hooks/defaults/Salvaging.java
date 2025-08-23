//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.defaults;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;
import net.minecraft.enchantment.*;

public class Salvaging
{
    public static void create() {
        AquaConfig.put("aquaskills", (Class)Skill.class, (IData)new Skill("Salvaging", new String[] { "Gain experience by making the most out of your tools and armor." }, "Salvager", "gold_nugget", 13, 1000L, new String[] { "iron_ingot", "iron_nugget", "gold_nugget", "gold_ingot" }, new XPSource[] { new XPSource("ANVIL_XP", "iron_pickaxe,iron_shovel,iron_hoe,iron_sword,iron_axe,iron_helmet,iron_chestplate,iron_leggings,iron_boots,shears", 25.0), new XPSource("ANVIL_XP", "golden_pickaxe,golden_shovel,golden_hoe,golden_sword,golden_axe,golden_helmet,golden_chestplate,golden_leggings,golden_boots", 75.0), new XPSource("ANVIL_XP", "diamond_pickaxe,diamond_shovel,diamond_hoe,diamond_sword,diamond_axe,diamond_helmet,diamond_chestplate,diamond_leggings,diamond_boots", 175.0) }, new Ability[] { new Ability("FORAGER", "Chance=0.01 ChancePerLvl=0.00004 Items=gold_nugget,iron_nugget,ghast_tear,ender_pearl,glowstone_dust,blaze_rod,pumpkin_seeds,melon_seeds,carrot,potato,beetroot_seeds,blaze_powder,feather,charcoal,emerald", 5L, 500L, new DisplayInfo("Forager", "deadbush", 28, new String[] { "Can find useful things just about anywhere.", "You have a chance to find items in tall grass.", "Improves as your level of Salvaging increases.", "", "Passive ability." })), new Ability("RECOVERY", "Chance=0.05 ChancePerLvl=0.0007", 25L, 5000L, new DisplayInfo("Recovery", "iron_nugget", 30, new String[] { "Even when something it on its last legs, it can be recycled to be made anew.", "You can attempt to reclaim some material back from tools and armor.", "Destroys the item, irrespective of success.", "Improves as your level of Salvaging increases.", "", "Activate by left clicking a tool or armor on an anvil." })), new Ability("REPAIRMAN", "Mult=1.5 MultPerLvl=0.0015", 50L, 0L, new DisplayInfo("Repairman", "anvil", 32, new String[] { "Your penchant for repairing has made you quite effective at it.", "Anvil repairs are more effective.", "Improves as your level of Salvaging increases.", "", "Passive ability." })), new Ability("DISENCHANTMENT", "Chance=0.05 ChancePerLvl=0.0007", 100L, 30000L, new DisplayInfo("Disenchantment", "enchanted_book", 34, new String[] { "Your skill for extracting every last drop of value out of things extends to the magical.", "You can attempt to disenchant items, recovering an Enchanted Book if successful.", "Destroys the item, irrespective of success.", "Improves as your level of Salvaging increases.", "", "Activate by left clicking a tool or armor on an enchanting table." })) }, new Reward[] { new Reward(10L, "You have earned a diamond!", new String[] { "give @p diamond 1" }) }));
    }
    
    public static void init() {
        final Ability ability;
        Cooldown cooldown;
        ItemStack stack;
        EntityPlayerMP player;
        IBlockState state;
        HashMap map;
        long level;
        double chance;
        ItemStack book;
        Map enchantments;
        EntityItem item;
        HookRegistry.get().registerMethodHook("DISENCHANTMENT", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                cooldown = data.getCooldownSafely();
                if (!cooldown.isOnCooldown()) {
                    stack = event.getItemStack();
                    if (!stack.isEmpty() && (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor)) {
                        player = data.player.getPlayerEntity();
                        state = player.getServerWorld().getBlockState(event.getPos());
                        if (state.getBlock() == Blocks.ENCHANTING_TABLE && !EnchantmentHelper.getEnchantments(stack).isEmpty()) {
                            map = AbilityHelper.getMap(ability);
                            level = data.getTrueFunctionalLevel();
                            chance = map.get("Chance") + level * map.get("ChancePerLvl");
                            if (RandomHelper.nextDouble() < chance) {
                                book = new ItemStack(Items.ENCHANTED_BOOK);
                                enchantments = EnchantmentHelper.getEnchantments(stack);
                                while (enchantments.size() > 1) {
                                    enchantments.remove(RandomHelper.getRandomElementFromCollection((Collection)enchantments.keySet()));
                                }
                                EnchantmentHelper.setEnchantments(enchantments, book);
                                item = new EntityItem((World)player.getServerWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 1.2, event.getPos().getZ() + 0.5, book.copy());
                                player.getServerWorld().spawnEntity((Entity)item);
                                cooldown.use(ability.getCooldownMilliseconds());
                                data.player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0f, 0.2f + RandomHelper.nextFloat() * 0.2f);
                            }
                            else {
                                data.player.playSound(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + RandomHelper.nextFloat() * 0.4f);
                                player.sendMessage((ITextComponent)new TextComponentString(Config.negative("destroyed", new Object[0])));
                            }
                            stack.damageItem(9999999, (EntityLivingBase)player);
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability2;
        HashMap map2;
        long level2;
        double multiplier;
        int damage;
        int bonus;
        HookRegistry.get().registerMethodHook("REPAIRMAN", new MethodHook<Event>((Class<? extends Event>)AnvilRepairEvent.class, (event, data) -> {
            ability2 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability2.getLevelRequirement()) {
                map2 = AbilityHelper.getMap(ability2);
                level2 = data.getTrueFunctionalLevel();
                multiplier = map2.get("Mult") + level2 * map2.get("MultPerLvl") - 1.0;
                damage = event.getItemInput().getItemDamage() - event.getItemResult().getItemDamage();
                bonus = (int)(damage * multiplier);
                event.getItemResult().damageItem(-bonus, (EntityLivingBase)event.getEntityPlayer());
            }
            return;
        }));
        final Ability ability3;
        Cooldown cooldown2;
        IBlockState state2;
        String[] items;
        ArrayList<Item> parsedItems;
        final String[] array;
        int length;
        int j = 0;
        String item2;
        Item i;
        HashMap map3;
        double chance2;
        ArrayList<Item> parsedItems2;
        Item item3;
        final Entity entity;
        Entity ei;
        HookRegistry.get().registerMethodHook("FORAGER", new MethodHook<Event>((Class<? extends Event>)BlockEvent.BreakEvent.class, (event, data) -> {
            ability3 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability3.getLevelRequirement()) {
                cooldown2 = data.getCooldownSafely();
                if (!cooldown2.isOnCooldown()) {
                    state2 = event.getState();
                    if (state2.getBlock() instanceof BlockTallGrass || state2.getBlock() instanceof BlockBush || state2.getBlock() instanceof BlockDeadBush) {
                        map3 = AbilityHelper.getMap(ability3, arg -> {
                            items = arg.split(",");
                            parsedItems = new ArrayList<Item>();
                            for (length = array.length; j < length; ++j) {
                                item2 = array[j];
                                i = Item.getByNameOrId(item2);
                                if (i != null) {
                                    parsedItems.add(i);
                                }
                            }
                            return parsedItems;
                        });
                        chance2 = map3.get("Chance") + map3.get("ChancePerLvl") * data.getTrueFunctionalLevel();
                        if (RandomHelper.nextDouble() < chance2) {
                            parsedItems2 = ability3.getParsedArgs().get(1);
                            item3 = (Item)RandomHelper.getRandomElementFromCollection((Collection)parsedItems2);
                            new EntityItem(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, new ItemStack(item3));
                            ei = entity;
                            event.getWorld().spawnEntity(ei);
                            cooldown2.use();
                        }
                    }
                }
            }
            return;
        }));
        final Ability ability4;
        Cooldown cooldown3;
        ItemStack stack2;
        EntityPlayerMP player2;
        IBlockState state3;
        HashMap map4;
        long level3;
        double chance3;
        ItemTool tool;
        Item.ToolMaterial material;
        ItemStack repairItem;
        ItemArmor armor;
        ItemArmor.ArmorMaterial material2;
        EntityItem item4;
        HookRegistry.get().registerMethodHook("RECOVERY", new MethodHook<Event>((Class<? extends Event>)PlayerInteractEvent.LeftClickBlock.class, (event, data) -> {
            ability4 = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability4.getLevelRequirement()) {
                cooldown3 = data.getCooldownSafely();
                if (!cooldown3.isOnCooldown()) {
                    stack2 = event.getItemStack();
                    if (!stack2.isEmpty() && (stack2.getItem() instanceof ItemTool || stack2.getItem() instanceof ItemArmor)) {
                        player2 = data.player.getPlayerEntity();
                        state3 = player2.getServerWorld().getBlockState(event.getPos());
                        if (state3.getBlock() == Blocks.ANVIL) {
                            map4 = AbilityHelper.getMap(ability4);
                            level3 = data.getTrueFunctionalLevel();
                            chance3 = map4.get("Chance") + level3 * map4.get("ChancePerLvl");
                            if (RandomHelper.nextDouble() < chance3) {
                                if (stack2.getItem() instanceof ItemTool) {
                                    tool = (ItemTool)stack2.getItem();
                                    material = Item.ToolMaterial.valueOf(tool.getToolMaterialName());
                                    repairItem = material.getRepairItemStack();
                                }
                                else {
                                    armor = (ItemArmor)stack2.getItem();
                                    material2 = armor.getArmorMaterial();
                                    repairItem = material2.getRepairItemStack();
                                }
                                repairItem.setItemDamage(0);
                                item4 = new EntityItem((World)player2.getServerWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 1.2, event.getPos().getZ() + 0.5, repairItem.copy());
                                player2.getServerWorld().spawnEntity((Entity)item4);
                                cooldown3.use(ability4.getCooldownMilliseconds());
                                data.player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 0.8f, 0.9f + RandomHelper.nextFloat() * 0.1f);
                            }
                            else {
                                data.player.playSound(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + RandomHelper.nextFloat() * 0.4f);
                                player2.sendMessage((ITextComponent)new TextComponentString(Config.negative("destroyed", new Object[0])));
                            }
                            stack2.damageItem(9999999, (EntityLivingBase)player2);
                        }
                    }
                }
            }
        }));
    }
}
