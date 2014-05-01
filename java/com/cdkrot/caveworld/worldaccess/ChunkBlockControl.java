package com.cdkrot.caveworld.worldaccess;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/**
 * Chunk as an IBlockControl impl
 * @author cdkrot
 *
 */
public class ChunkBlockControl implements IChunkBlockControl
{

	public Chunk chunk;
	public ChunkBlockControl(){}
	
	/**
	 * Reinit object with new chunk.
	 * @param c
	 */
	public void initChunk(Chunk c)
	{
		this.chunk=c;
	}
	@Override
	public Block getBlock(int x, int y, int z)
	{
		return chunk.getBlock(x, y, z);
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z)
	{
		return chunk.getTileEntityUnsafe(x, y, z);
	}

	@Override
	public int getMetadata(int x, int y, int z)
	{
		return chunk.getBlockMetadata(x, y, z);
	}

	@Override
	public byte getBiomeAt(int x, int z)
	{
		return chunk.getBiomeArray()[z << 4 | x];
	}

	@Override
	public int getLightAt(int x, int y, int z)
	{
		ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[y >> 4];
		if (ebs == null)
			return 0;
		return ebs.getExtBlocklightValue(x, y&15, z);
	}

	@Override
	public void setBlock(int x, int y, int z, Block b, int meta)
	{
		chunk.func_150807_a(x, y, z, b, meta);
	}

	@Override
	public void setModified()
	{
		chunk.setChunkModified();
	}
	
	@Override
	public World getWorld()
	{
		return chunk.worldObj;
	}

	@Override
	public void updateLightAt(int x, int y, int z)
	{
		chunk.enqueueRelightChecks();
		this.getWorld().updateLightByType(EnumSkyBlock.Block, chunk.xPosition<<4 + x , y, chunk.zPosition<<4 + z);
	}

	//int array will contain the first height with air, having an air above and a ground below.
	public int[] perform_height_mapping()
	{
		int buf[] = new int[256];
		Arrays.fill(buf, 0);
		for (int x=0; x<16; x++)
			for (int z=0; z<16; z++)
			{
				int Y;
				for (Y=255; Y>=0; Y--)
					if (this.getBlock(x, Y, z)==Blocks.air)
						break;
				if (Y==0) break;
				
				for (Y--; Y>=0; Y--)
				{
					if (this.getBlock(x, Y, z)!=Blocks.air)
					{
						buf[(x<<4) + z]=Y;
						break;
					}
				}
			}
		return buf;
	}

	@Override
	public void setBlock(int x, int y, int z, Block b) {
		this.setBlock(x, y, z, b, 0);
	}
}
