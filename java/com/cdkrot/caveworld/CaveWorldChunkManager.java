package com.cdkrot.caveworld;

import java.lang.reflect.Field;

import com.cdkrot.caveworld.gen.cave.GenLayerBiomeCave;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class CaveWorldChunkManager extends WorldChunkManager
{
	public CaveWorldChunkManager(World world)
	{
		super(world);
		world.provider.setSpawnPoint(8, 243, 8);
		world.provider.hasNoSky=true;
		this.getBiomesToSpawnIn().clear();
		this.getBiomesToSpawnIn().add(BiomeGenBase.plains);
	}
		
	
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
        return new GenLayer[]{new GenLayerBiomeCave(seed, 1), new GenLayerBiomeCave(seed,1)};
    }

}
