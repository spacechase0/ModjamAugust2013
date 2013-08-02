package com.spacechase0.minecraft.usefulpets;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid = "SC0_UsefulPets", name = "Useful Pets", version = "0.1" )
public class UsefulPets
{
	@EventHandler
	void preInit( FMLPreInitializationEvent event )
	{
		config = new Configuration( event.getSuggestedConfigurationFile() );
		config.load();
	}
	
	@EventHandler
	void init( FMLInitializationEvent event )
	{
		// ...
	}
	
	@EventHandler
	void postInit( FMLPostInitializationEvent event )
	{
		config.save();
	}
	
	private Configuration config;
}
