//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.skill.*;

public class ItemHelper
{
    public static void setLore(final ItemStack stack, final String... lines) {
        final NBTTagList lore = new NBTTagList();
        for (final String loreLine : lines) {
            lore.appendTag((NBTBase)new NBTTagString(TextHelper.format(loreLine)));
        }
        stack.getOrCreateSubCompound("display").setTag("Lore", (NBTBase)lore);
    }
    
    public static void prefixLore(final boolean reset, final ItemStack stack, final String... lines) {
        final NBTTagCompound tag = stack.getOrCreateSubCompound("display");
        final NBTTagList lore = new NBTTagList();
        for (final String loreLine : lines) {
            lore.appendTag((NBTBase)new NBTTagString(TextHelper.format(loreLine)));
        }
        if (tag.hasKey("Lore") && !reset) {
            boolean first = true;
            final NBTTagList oldLore = tag.getTagList("Lore", 8);
            if (!oldLore.isEmpty()) {
                for (final NBTBase obj : oldLore) {
                    if (obj instanceof NBTTagString) {
                        if (first) {
                            lore.appendTag((NBTBase)new NBTTagString());
                            first = false;
                        }
                        lore.appendTag(obj);
                    }
                }
            }
        }
        tag.setTag("Lore", (NBTBase)lore);
    }
    
    public static boolean isItemBound(final ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NbtKeys.BOUND_ITEM);
    }
    
    public static Optional<Tuple<Skill, Ability>> getBoundAbility(final ItemStack stack) {
        if (isItemBound(stack)) {
            final String skillID = stack.getTagCompound().getString(NbtKeys.BOUND_SKILL);
            final String abilityID = stack.getTagCompound().getString(NbtKeys.BOUND_ABILITY);
            final Optional<Skill> skill = SkillMap.get(skillID);
            if (skill.isPresent()) {
                final Optional<Ability> ability = (Optional<Ability>)skill.get().getAbility(abilityID);
                if (ability.isPresent()) {
                    return Optional.of(new Tuple((Object)skill.get(), (Object)ability.get()));
                }
            }
        }
        return Optional.empty();
    }
}
