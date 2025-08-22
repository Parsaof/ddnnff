//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon;

import net.minecraft.item.*;
import com.pixelmonmod.pixelmon.*;
import com.pixelmonmod.pixelmon.api.pokemon.*;
import com.pixelmonmod.pixelmon.items.*;

public class PixelmonSpriteMaker
{
    public static ItemStack makeSprite(final String spec) {
        return ItemPixelmonSprite.getPhoto(Pixelmon.pokemonFactory.create(PokemonSpec.from(spec.split(" "))));
    }
}
