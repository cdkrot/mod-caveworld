package com.cdkrot.caveworld;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Cave Biome. Mycelium one.
 * @author cdkrot
 */
public class BiomeGenCaveMycelium extends BiomeGenCaveClassic
{
	public BiomeGenCaveMycelium(int par1)
	{
		super(par1);
		this.biomeName = "cdkrot-CaveMushroom";
		this.topBlock = Blocks.mycelium; //! Signal to GeneratorCavePostProduction -- the top floor block.
		this.theBiomeDecorator.bigMushroomsPerChunk =1; //try gen it
	}

}
