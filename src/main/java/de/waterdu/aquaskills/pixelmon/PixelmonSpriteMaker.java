//r

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
