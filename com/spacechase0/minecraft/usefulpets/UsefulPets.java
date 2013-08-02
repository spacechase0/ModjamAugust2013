package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.entity.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraftforge.common.Configuration;

@Mod( modid = "SC0_UsefulPets", name = "Useful Pets", version = "0.1" )
@NetworkMod( clientSideRequired = true, serverSideRequired = false )
public class UsefulPets
{
	@Instance( "SC0_UsefulPets" )
	public static UsefulPets instance;
	
	@SidedProxy( serverSide = "com.spacechase0.minecraft.usefulpets.CommonProxy",
			     clientSide = "com.spacechase0.minecraft.usefulpets.client.ClientProxy" )
	public static CommonProxy proxy;
	
	@EventHandler
	void preInit( FMLPreInitializationEvent event )
	{
		config = new Configuration( event.getSuggestedConfigurationFile() );
		config.load();
	}
	
	@EventHandler
	void init( FMLInitializationEvent event )
	{
		EntityRegistry.registerGlobalEntityID( PetEntity.class, "Pet", EntityRegistry.findGlobalUniqueEntityId() );
		
		proxy.registerRenderers();
	}
	
	@EventHandler
	void postInit( FMLPostInitializationEvent event )
	{
		config.save();
	}
	
	private Configuration config;
}
