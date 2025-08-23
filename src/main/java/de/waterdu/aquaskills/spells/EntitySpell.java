//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells;

import net.minecraft.entity.item.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.math.*;

public class EntitySpell
{
    private WorldServer world;
    private EntityArmorStand stand;
    private double speed;
    private double motionX;
    private double motionY;
    private double motionZ;
    private double range;
    private double startX;
    private double startY;
    private double startZ;
    private double posX;
    private double posY;
    private double posZ;
    private double size;
    private boolean expireOnImpact;
    private boolean noclip;
    private boolean collideMultipleTimes;
    private boolean isDead;
    private boolean canCollideWithcaster;
    private int age;
    private Spell spell;
    private Player caster;
    private Skill skill;
    private Ability ability;
    private HashSet<UUID> collided;
    
    public EntitySpell(final Player caster, final Skill skill, final Ability ability, final ISpell spell) {
        this.stand = null;
        this.isDead = false;
        this.canCollideWithcaster = false;
        this.collided = new HashSet<UUID>();
        final EntityPlayerMP player = caster.getPlayerEntity();
        if (player == null) {
            this.isDead = true;
            return;
        }
        this.world = player.getServerWorld();
        this.caster = caster;
        this.skill = skill;
        this.ability = ability;
        this.spell = new Spell(spell);
        this.size = this.spell.getSize();
        this.startX = player.posX + player.getLookVec().x * 0.5;
        this.startY = player.posY + 1.5;
        this.startZ = player.posZ + player.getLookVec().z * 0.5;
        this.speed = this.spell.getSpeed();
        this.range = this.spell.getRange() * caster.getRangeModifier();
        this.expireOnImpact = this.spell.doesExpireOnImpact();
        this.collideMultipleTimes = this.spell.canCollideMultipleTimes();
        this.noclip = this.spell.hasNoclip();
        if (this.spell.hasArmorStand()) {
            final EntityArmorStand stand2 = new EntityArmorStand((World)this.world);
            stand2.setPositionAndUpdate(this.startX, this.startY, this.startZ);
            stand2.setInvisible(true);
            this.world.spawnEntity((Entity)stand2);
            stand2.setInvisible(true);
            stand2.setNoGravity(true);
            stand2.setEntityInvulnerable(true);
            stand2.noClip = true;
            stand2.getEntityData().setBoolean(NbtKeys.TRANSIENT, true);
            stand2.getDataManager().set(EntityArmorStand.STATUS, (Object)this.setBit((byte)stand2.getDataManager().get(EntityArmorStand.STATUS), 1, true));
            stand2.getDataManager().set(EntityArmorStand.STATUS, (Object)this.setBit((byte)stand2.getDataManager().get(EntityArmorStand.STATUS), 16, true));
            this.stand = stand2;
        }
        this.motionX = player.getLookVec().x * this.speed;
        this.motionY = player.getLookVec().y * this.speed;
        this.motionZ = player.getLookVec().z * this.speed;
        this.posX = this.startX;
        this.posY = this.startY;
        this.posZ = this.startZ;
        this.ifArmorStandPresent(stand -> stand.setPositionAndUpdate(this.startX, this.startY, this.startZ));
        this.spell.onCreate(this, this.world);
        this.world.playSound((EntityPlayer)null, player.getPosition(), SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.MASTER, 0.5f, 1.0f);
    }
    
    public void ifArmorStandPresent(final Consumer<EntityArmorStand> consumer) {
        if (this.stand != null) {
            consumer.accept(this.stand);
        }
    }
    
    private byte setBit(byte p_184797_1_, final int p_184797_2_, final boolean p_184797_3_) {
        if (p_184797_3_) {
            p_184797_1_ |= (byte)p_184797_2_;
        }
        else {
            p_184797_1_ &= (byte)~p_184797_2_;
        }
        return p_184797_1_;
    }
    
    public Position getInterpolatedPosition(final double factor) {
        return new Position(this.posX - this.motionX * factor, this.posY - this.motionY * factor, this.posZ - this.motionZ * factor);
    }
    
    public boolean onTick() {
        final EntityPlayerMP source = this.caster.getPlayerEntity();
        if (source == null || source.hasDisconnected()) {
            this.setDead(this.world);
            return true;
        }
        if (this.isDead) {
            return true;
        }
        final double prevPosX = this.posX;
        final double prevPosY = this.posY;
        final double prevPosZ = this.posZ;
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        this.ifArmorStandPresent(stand -> {
            stand.posX += this.motionX;
            stand.posY += this.motionY;
            stand.posZ += this.motionZ;
            return;
        });
        this.spell.onTick(this, this.world);
        final double radius = this.size / 2.0;
        int interpolationFactor = (int)(this.speed * 4.0);
        if (interpolationFactor <= 0) {
            interpolationFactor = 1;
        }
        for (int i = 1; i <= interpolationFactor; ++i) {
            final double x = prevPosX + this.motionX / interpolationFactor * i;
            final double y = prevPosY + this.motionY / interpolationFactor * i;
            final double z = prevPosZ + this.motionZ / interpolationFactor * i;
            if (!this.noclip) {
                final BlockPos pos = new BlockPos(x, y, z);
                final IBlockState block = this.world.getBlockState(pos);
                if (block.isNormalCube()) {
                    this.setDead(this.world, pos);
                    return true;
                }
            }
            final AxisAlignedBB aabb = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
            final List<Entity> collisions = (List<Entity>)this.world.getEntitiesWithinAABBExcludingEntity((Entity)source, aabb);
            for (final Entity e : collisions) {
                if (e instanceof EntityLivingBase) {
                    final EntityLivingBase living = (EntityLivingBase)e;
                    if (((living == this.stand || living == source) && !this.canCollideWithcaster) || (!this.collideMultipleTimes && this.collided.contains(living.getUniqueID()))) {
                        continue;
                    }
                    if (!this.collideMultipleTimes) {
                        this.collided.add(living.getUniqueID());
                    }
                    if (AquaSkills.EVENT_BUS.post((Event)new SpellImpactEvent(this.caster, living, this.skill, this.ability, (ISpell)this.spell))) {
                        continue;
                    }
                    this.spell.onCollide(this, living, this.world);
                    if (this.expireOnImpact) {
                        this.setDead(this.world);
                        return true;
                    }
                    continue;
                }
            }
        }
        if (this.getDistance(this.startX, this.startY, this.startZ) >= this.range) {
            this.setDead(this.world);
            return true;
        }
        return false;
    }
    
    public double getDistance(final double x, final double y, final double z) {
        final double d0 = this.posX - x;
        final double d2 = this.posY - y;
        final double d3 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public void setDead(final WorldServer world) {
        this.setDead(world, null);
    }
    
    public void setDead(final WorldServer world, final BlockPos pos) {
        if (!this.isDead) {
            this.spell.onExpire(this, world, pos);
            this.ifArmorStandPresent(Entity::setDead);
        }
        this.isDead = true;
    }
    
    public Position getPosition() {
        return new Position(this.posX, this.posY, this.posZ);
    }
    
    public boolean age() {
        --this.age;
        return this.age <= 0;
    }
    
    public WorldServer getWorld() {
        return this.world;
    }
    
    public EntityArmorStand getStand() {
        return this.stand;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public double getRange() {
        return this.range;
    }
    
    public double getStartX() {
        return this.startX;
    }
    
    public double getStartY() {
        return this.startY;
    }
    
    public double getStartZ() {
        return this.startZ;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public double getSize() {
        return this.size;
    }
    
    public boolean isExpireOnImpact() {
        return this.expireOnImpact;
    }
    
    public boolean isNoclip() {
        return this.noclip;
    }
    
    public boolean isCollideMultipleTimes() {
        return this.collideMultipleTimes;
    }
    
    public boolean isDead() {
        return this.isDead;
    }
    
    public boolean isCanCollideWithcaster() {
        return this.canCollideWithcaster;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public Spell getSpell() {
        return this.spell;
    }
    
    public Player getCaster() {
        return this.caster;
    }
    
    public Skill getSkill() {
        return this.skill;
    }
    
    public Ability getAbility() {
        return this.ability;
    }
    
    public HashSet<UUID> getCollided() {
        return this.collided;
    }
    
    public void setWorld(final WorldServer world) {
        this.world = world;
    }
    
    public void setStand(final EntityArmorStand stand) {
        this.stand = stand;
    }
    
    public void setSpeed(final double speed) {
        this.speed = speed;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
    
    public void setRange(final double range) {
        this.range = range;
    }
    
    public void setStartX(final double startX) {
        this.startX = startX;
    }
    
    public void setStartY(final double startY) {
        this.startY = startY;
    }
    
    public void setStartZ(final double startZ) {
        this.startZ = startZ;
    }
    
    public void setPosX(final double posX) {
        this.posX = posX;
    }
    
    public void setPosY(final double posY) {
        this.posY = posY;
    }
    
    public void setPosZ(final double posZ) {
        this.posZ = posZ;
    }
    
    public void setSize(final double size) {
        this.size = size;
    }
    
    public void setExpireOnImpact(final boolean expireOnImpact) {
        this.expireOnImpact = expireOnImpact;
    }
    
    public void setNoclip(final boolean noclip) {
        this.noclip = noclip;
    }
    
    public void setCollideMultipleTimes(final boolean collideMultipleTimes) {
        this.collideMultipleTimes = collideMultipleTimes;
    }
    
    public void setCanCollideWithcaster(final boolean canCollideWithcaster) {
        this.canCollideWithcaster = canCollideWithcaster;
    }
    
    public void setAge(final int age) {
        this.age = age;
    }
    
    public void setSpell(final Spell spell) {
        this.spell = spell;
    }
    
    public void setCaster(final Player caster) {
        this.caster = caster;
    }
    
    public void setSkill(final Skill skill) {
        this.skill = skill;
    }
    
    public void setAbility(final Ability ability) {
        this.ability = ability;
    }
    
    public void setCollided(final HashSet<UUID> collided) {
        this.collided = collided;
    }
    
    public EntitySpell() {
        this.stand = null;
        this.isDead = false;
        this.canCollideWithcaster = false;
        this.collided = new HashSet<UUID>();
    }
}
