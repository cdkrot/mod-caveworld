package com.cdkrot.caveworld.gen.cave;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import com.cdkrot.caveworld.ModCaveWorld;
import com.cdkrot.caveworld.gen.AbstractChunkGenerator;
import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.worldaccess.IBlockControl;
import com.cdkrot.caveworld.worldaccess.IChunkBlockControl;

/**
 * Cave-world generation -- post primary world generation -- fills world with grass and dirt.
 * 
 * @author cdkrot
 *
 */
public class GeneratorCavePostProduction extends AbstractChunkGenerator
{
	
	public GeneratorCavePostProduction()
	{
	}
	
	@Override
	public void generate(IChunkBlockControl w, int chunk_x, int chunk_z)
	{
		for (int x=0; x<16; x++)
			for (int z=0; z<16; z++)
			{
				BiomeGenBase biome = BiomeGenBase.getBiome(w.getBiomeAt(x, z));
				Random r = AbstractChunkGenerator.makeCoordRandom(16*chunk_x+x, 16*chunk_z+z, seed);
				for (int y=220+r.nextInt(4); y<255; y++)
				{
					if (!w.getBlock(x, y, z).isOpaqueCube()) //air and etc.
						continue;
					if (w.getBlock(x, y+1, z)==Blocks.air)
					{
						w.setBlock(x, y, z, biome.topBlock);
						y++;//skip one block.
					}
					else
						w.setBlock(x, y, z, biome.fillerBlock);
				}
				
				for (int y=1; y<2+r.nextInt(8); y++)
					w.setBlock(x, y, z, Blocks.bedrock);
				for (int y=255; y>255-r.nextInt(8); y--)
					w.setBlock(x, y, z, Blocks.bedrock);

			}
	}
	
}
