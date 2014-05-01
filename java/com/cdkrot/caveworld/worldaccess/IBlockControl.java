package com.cdkrot.caveworld.worldaccess;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IBlockControl
{

    Block getBlock(int x, int y, int z);
    
    void setBlock(int x, int y, int z, Block b, int meta);
    
    void setBlock(int x, int y, int z, Block b);
    
    TileEntity getTileEntity(int x, int y, int z);

    int getMetadata(int x, int y, int z);

    byte getBiomeAt(int x, int z);
    
    int getLightAt(int x, int y, int z);
    
    /**
     * Sets a modified bit on a data provider, such as chunk.
     */
    void setModified();
    
    World getWorld();

	void updateLightAt(int x, int y, int z);
}
