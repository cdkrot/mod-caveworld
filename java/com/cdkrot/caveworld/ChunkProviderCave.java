package com.cdkrot.caveworld;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cdkrot.caveworld.gen.IChunkGenerator;
import com.cdkrot.caveworld.gen.cave.GeneratorAnimalShelter;
import com.cdkrot.caveworld.gen.cave.GeneratorCavePostProduction;
import com.cdkrot.caveworld.gen.cave.GeneratorFoodChests;
import com.cdkrot.caveworld.gen.cave.GeneratorGlowStone;
import com.cdkrot.caveworld.worldaccess.ChunkBlockControl;
import com.cdkrot.caveworld.worldaccess.IBlockControl;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderCave implements IChunkProvider {
	private World worldObj;
	private Random random;
	private final List<MapGenStructure> structureGenerators = new ArrayList<MapGenStructure>();
	private final MapGenCaves mapGenCave;
	private final List<WorldGenerator> worldGenerators = new ArrayList<WorldGenerator>();
	private final MapGenRavine MapGenRavine;
	private final List<IChunkGenerator> CaveWorldGen= new ArrayList<IChunkGenerator>();
	private final WorldGenLakes GeneratorWaterLakes = new WorldGenLakes(Blocks.water);
	private final WorldGenLakes GeneratorLavaLakes = new WorldGenLakes(Blocks.lava);
	private final WorldGenDungeons GeneratorDungeons = new WorldGenDungeons();
	private static final Block[] blockPattern=new Block[65536];
	static
	{
		Arrays.fill(blockPattern, Blocks.stone);
		for (int i=0; i<65536; i+=256)
		{
			blockPattern[i]=blockPattern[i+254]=Blocks.bedrock;
			blockPattern[i+255]=Blocks.bedrock;
		}
	}
	
	protected void addCaveWorldGen(IChunkGenerator g)
	{
		g.initRG(this.random);
		g.initSEED(this.worldObj.getSeed());
		CaveWorldGen.add(g);
	}
	
	public ChunkProviderCave(World world, long seed)
	{
		this.worldObj = world;
		this.random = new Random(seed);
		
		addCaveWorldGen(new GeneratorCavePostProduction());
		addCaveWorldGen(new GeneratorFoodChests());
		addCaveWorldGen(new GeneratorGlowStone());
		addCaveWorldGen(new GeneratorAnimalShelter());
		
		structureGenerators.add(new MapGenStronghold());
		structureGenerators.add(new MapGenScatteredFeature());
		structureGenerators.add(new MapGenMineshaft());
		
		//vanilla cave generator, played with nums
		mapGenCave = new MapGenCaves()
		{
		    protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
		    {
		        int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(60) + 1) + 1);
		        int mode = Math.min(rand.nextInt(rand.nextInt(15)+1), 5); // specifieis how high should generate...
		        if (this.rand.nextInt(4) == 0)
		        {
		            i1 = 0;
		        }

		        for (int j1 = 0; j1 < i1; ++j1)
		        {
		            double d0 = (double)(p_151538_2_ * 16 + this.rand.nextInt(16));
		            double d1 = (double)(55+mode*50-this.rand.nextInt(this.rand.nextInt(50)+1));
		            double d2 = (double)(p_151538_3_ * 16 + this.rand.nextInt(16));
		            int k1 = 1;

		            if (this.rand.nextInt(2) == 0)
		            {
		                this.func_151542_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, d0, d1, d2);
		                k1 += this.rand.nextInt(4);
		            }

		            for (int l1 = 0; l1 < k1; ++l1)
		            {
		                float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
		                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
		                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

		                if (this.rand.nextInt(6) == 0)
		                {
		                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
		                }

		                this.func_151541_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
		            }
		        }
		    }
		};
		MapGenRavine = new MapGenRavine();
		
		worldGenerators.add(new WorldGenTrees(false));
		worldGenerators.add(new WorldGenClay(4+(int)(seed%4)));
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk(int par1, int par2) {
		return this.provideChunk(par1, par2);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
    public Chunk provideChunk(int x, int z)
    {
        this.random.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        Block[] ablock = blockPattern.clone();
        byte[] abyte = new byte[65536];
        for (int i=0; i<4; i++)
        {
        	this.mapGenCave.func_151539_a(this, this.worldObj, x, z, ablock);
        	this.MapGenRavine.func_151539_a(this, this.worldObj, x, z, ablock);
        }
        for (MapGenStructure structgen: structureGenerators)
        	structgen.func_151539_a(this, worldObj, x, z, ablock);

        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, x, z);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)BiomeGenBase.plains.biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

	@Override
	public boolean chunkExists(int par1, int par2) {
		return true;
	}

	@Override
	public void populate(IChunkProvider prov, int x, int z)
    {
		
        BlockFalling.fallInstantly = true;
        int k = x * 16;
        int l = z * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        random.setSeed((long)x * i1 + (long)z * j1 ^ this.worldObj.getSeed());

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(prov, worldObj, random, x, z, false));

        for (MapGenStructure struct: structureGenerators) 
        	struct.generateStructuresInChunk(this.worldObj, this.random, x, z);
        int k1;
        int l1;
        int i2;

        if (TerrainGen.populate(prov, worldObj, random, x, z, false, LAKE))
        {
        	for (int i=0; i<2+random.nextInt(8); i++)
        	{
        		k1 = k + random.nextInt(16) + 8;
        		l1 = random.nextInt(200) + 9 + 5*random.nextInt(10);
        		i2 = l + random.nextInt(16) + 8;
            
            GeneratorWaterLakes.generate(this.worldObj, this.random, k1, l1, i2);
        	}
        }

        if (TerrainGen.populate(prov, worldObj, random, x, z, false, LAVA))
        {
        	for (int i=0; i<1+random.nextInt(4); i++)
        	{
        		k1 = k + random.nextInt(16) + 8;
        		l1 = random.nextInt(this.random.nextInt(160) + 8)+5;
        		i2 = l + random.nextInt(16) + 8;
        		
        		if (l1 < 63 || this.random.nextInt(10) == 0)
        			GeneratorLavaLakes.generate(this.worldObj, this.random, k1, l1, i2);
        	}
        }

        for (k1 = 0; k1 < 8; ++k1)
        {
            l1 = k + this.random.nextInt(16) + 8;
            i2 = this.random.nextInt(256);
            int j2 = l + this.random.nextInt(16) + 8;
            GeneratorDungeons.generate(this.worldObj, this.random, l1, i2, j2);
        }
        
        
        ChunkBlockControl ChunkControl = new ChunkBlockControl();
        ChunkControl.initChunk(prov.provideChunk(x, z));
        for (IChunkGenerator cave_gen:CaveWorldGen)
        {
        	cave_gen.generate(ChunkControl, x, z);
        }
        
        biomegenbase.decorate(this.worldObj, this.random, k, l);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.random);
        k += 8;
        l += 8;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(prov, worldObj, random, x, z, false));

        BlockFalling.fallInstantly = false;
    }

	@Override
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
		return true;
	}
	
	@Override
	public void saveExtraData() {
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}
	
	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "Cave:FlatLevelSource";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
		return biomegenbase.getSpawnableList(par1EnumCreatureType);
	}
	
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
    {
        return "Stronghold".equals(p_147416_2_) && this.structureGenerators.get(1) != null ? this.structureGenerators.get(1).func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_) : null;
    }
	
	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(int x, int z)
	{
		for (MapGenStructure mapgenstructure: structureGenerators) 
		{
			mapgenstructure.func_151539_a(this, this.worldObj, x, z, (Block[]) null);
		}
	}
	
}