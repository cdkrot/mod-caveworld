package com.cdkrot.caveworld;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Controls enemies and animals spawned in the biome.
 * @author cdkrot
 */
public class BiomeGenCaveClassic extends BiomeGenBase
{
	public BiomeGenCaveClassic(int par1)
	{
		super(par1);
		this.biomeName = "cdkrot-CaveClassic";
		this.fillerBlock = Blocks.stone;
		this.topBlock = Blocks.bedrock;
		this.rootHeight = 10;
		this.heightVariation = 0;
		
		this.theBiomeDecorator.generateLakes = false;
	}

}
