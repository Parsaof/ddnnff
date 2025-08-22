//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.necromancy;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;

public class TollTheDead implements ISpell
{
    private final float damage;
    
    public TollTheDead(final float damage) {
        this.damage = damage;
    }
    
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        if (hit.isEntityUndead()) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.BLOCK_NOTE_BELL, SoundCategory.PLAYERS, 1.0f, 0.5f + world.rand.nextFloat());
            hit.attackEntityFrom(DamageHelper.causeSpellDamage(spell), this.damage);
        }
    }
    
    public double getRange() {
        return 10.0;
    }
    
    public double getSpeed() {
        return 2.0;
    }
    
    public double getSize() {
        return 1.0;
    }
    
    public boolean doesExpireOnImpact() {
        return false;
    }
}
