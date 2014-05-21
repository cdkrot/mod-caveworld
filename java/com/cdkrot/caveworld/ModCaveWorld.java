package com.cdkrot.caveworld;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

/**
 * @author cdkrot
 * 25.04.14
 */
@Mod(modid = ModCaveWorld.modid, version = ModCaveWorld.version, name="cave world generator")
public class ModCaveWorld
{
	public static final String modid = "cdkrot.caveworld";
	public static final String version = "0.5.2";

	@Mod.Instance(modid)
	public static ModCaveWorld instance;
	public Logger modLogger;
	public WorldType caveWorldType;
	public BiomeGenBase BiomeGenCaveClassic = new BiomeGenCaveClassic(53);//for example 53.
	public BiomeGenBase BiomeGenCaveMushroom = new BiomeGenCaveMycelium(54);
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception
	{
		modLogger = event.getModLog();
		/*Configuration c = new Configuration(event.getSuggestedConfigurationFile());
		c.load();
		c.save();*/
		modLogger.info("Initializing Cave world mod.");
		modLogger.info("Registering cave world provider.");
		
		modLogger.info("Fixing vanilla surface world provider.");
		Field providers_field = DimensionManager.class.getDeclaredField("providers");
		providers_field.setAccessible(true);
		Hashtable<Integer, Class<? extends WorldProvider>> providers =
			(Hashtable<Integer, Class<? extends WorldProvider>>)(providers_field.get(null));
		providers.put(DimensionManager.getProviderType(0), CaveWorldProvider.class);
		caveWorldType = new CaveWorldType();
		modLogger.info("Done!");
	}
}
