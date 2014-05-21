package com.cdkrot.caveworld;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Cave Biome.
 * @author cdkrot
 */
public class BiomeGenCaveClassic extends BiomeGenBase
{
	public BiomeGenCaveClassic(int par1)
	{
		super(par1);
		this.biomeName = "cdkrot-CaveClassic";
		this.fillerBlock = Blocks.dirt; //! Signal to GeneratorCavePostProduction -- the block type in upper layer (~210--255)
		this.topBlock = Blocks.grass; //! Signal to GeneratorCavePostProduction -- the top floor block.
		// this.rootHeight = 10; N/A -- we generate this ourselves.
		// this.heightVariation = 0; N/A -- we generate this ourselves.
		
		//Important, biome decorator config.
		//TODO: Chop out biome decoration. Use only our gens.
		this.theBiomeDecorator.generateLakes=false; //we gen lakes ourselves
		this.theBiomeDecorator.grassPerChunk=120; // many grass. //TODO: gen most of this ourselves.
		this.theBiomeDecorator.treesPerChunk=0;
		this.theBiomeDecorator.bigMushroomsPerChunk =0;
		this.theBiomeDecorator.cactiPerChunk=0;
		this.theBiomeDecorator.sandPerChunk=0;
		this.theBiomeDecorator.sandPerChunk2=0;
		this.theBiomeDecorator.clayPerChunk=0;
	}

}
