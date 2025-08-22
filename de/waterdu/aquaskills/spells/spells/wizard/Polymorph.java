//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells.spells.wizard;

import de.waterdu.aquaskills.spells.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

public class Polymorph implements ISpell
{
    public void onTick(final EntitySpell spell, final WorldServer world) {
    }
    
    public void onCreate(final EntitySpell spell, final WorldServer world) {
        world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    
    public void onExpire(final EntitySpell spell, final WorldServer world, final BlockPos pos) {
    }
    
    public void onCollide(final EntitySpell spell, final EntityLivingBase hit, final WorldServer world) {
        if (hit instanceof EntityMob && hit.isNonBoss()) {
            world.playSound((EntityPlayer)null, spell.getPosX(), spell.getPosY(), spell.getPosZ(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.MASTER, 1.0f, 0.5f);
            ParticleHelper.drawParticleCloud(50, EnumParticleTypes.CLOUD, world, hit.posX, hit.posY, hit.posZ, 0.1);
            hit.setDead();
            EntityAnimal animal = null;
            switch (world.rand.nextInt(13)) {
                case 0: {
                    animal = (EntityAnimal)new EntityPig((World)world);
                    break;
                }
                case 1: {
                    animal = (EntityAnimal)new EntityChicken((World)world);
                    break;
                }
                case 2: {
                    animal = (EntityAnimal)new EntityHorse((World)world);
                    break;
                }
                case 3: {
                    animal = (EntityAnimal)new EntityLlama((World)world);
                    break;
                }
                case 4: {
                    animal = (EntityAnimal)new EntityCow((World)world);
                    break;
                }
                case 5: {
                    animal = (EntityAnimal)new EntityDonkey((World)world);
                    break;
                }
                case 6: {
                    animal = (EntityAnimal)new EntityMooshroom((World)world);
                    break;
                }
                case 7: {
                    animal = (EntityAnimal)new EntityOcelot((World)world);
                    break;
                }
                case 8: {
                    animal = (EntityAnimal)new EntityParrot((World)world);
                    break;
                }
                case 9: {
                    animal = (EntityAnimal)new EntityPolarBear((World)world);
                    break;
                }
                case 10: {
                    animal = (EntityAnimal)new EntityRabbit((World)world);
                    break;
                }
                case 11: {
                    animal = (EntityAnimal)new EntitySheep((World)world);
                    break;
                }
                default: {
                    animal = (EntityAnimal)new EntityWolf((World)world);
                    break;
                }
            }
            animal.setPositionAndRotation(hit.posX, hit.posY, hit.posZ, hit.rotationYaw, hit.rotationPitch);
            world.spawnEntity((Entity)animal);
            spell.setDead(world);
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
    
    public boolean hasNoclip() {
        return true;
    }
}
