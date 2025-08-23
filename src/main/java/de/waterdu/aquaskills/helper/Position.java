//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.util.math.*;

public class Position
{
    private double x;
    private double y;
    private double z;
    
    public Position() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public Position(final BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    
    public Position(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public boolean equals(final Position pos) {
        return pos.getX() == this.x && pos.getY() == this.y && pos.getZ() == this.z;
    }
    
    public BlockPos getBlockPos() {
        return new BlockPos(this.x, this.y, this.z);
    }
    
    public Position add(final double x, final double y, final double z) {
        return new Position(this.x + x, this.y + y, this.z + z);
    }
}
