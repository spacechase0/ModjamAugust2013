package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.entity.*;
import com.spacechase0.minecraft.usefulpets.item.*;
import com.spacechase0.minecraft.usefulpets.network.PacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

@Mod( modid = "SC0_UsefulPets", name = "Useful Pets", version = "0.1" )
@NetworkMod( clientSideRequired = true, serverSideRequired = false )
public class UsefulPets
{
	@Instance( "SC0_UsefulPets" )
	public static UsefulPets instance;
	
	@SidedProxy( serverSide = "com.spacechase0.minecraft.usefulpets.server.ServerProxy",
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
		registerItems();
		registerRecipes();
		registerEntities();
		registerLanguage();
		registerBonusChest();
		proxy.registerRenderers();
		
		NetworkRegistry.instance().registerChannel( new PacketHandler(), "SC0_UP|CS" );
		NetworkRegistry.instance().registerGuiHandler( this, new GuiHandler() );

		MinecraftForge.EVENT_BUS.register( avoidanceHandler = new AvoidanceEventHandler() );
		MinecraftForge.EVENT_BUS.register( starterItemHandler = new StarterItemEventHandler() );
	}
	
	@EventHandler
	void postInit( FMLPostInitializationEvent event )
	{
		config.save();
	}
	
	private void registerItems()
	{
		wand = new PetWandItem( getItemId( "wand", 0 ) );
		GameRegistry.registerItem( wand, "wand" );

		goldClaws = new ClawItem( getItemId( "goldClaws", 1 ), "gold", 1 );
		GameRegistry.registerItem( goldClaws, "goldClaws" );
		
		ironClaws = new ClawItem( getItemId( "ironClaws", 1 ), "iron", 3 );
		GameRegistry.registerItem( ironClaws, "ironClaws" );
		
		diamondClaws = new ClawItem( getItemId( "diamondClaws", 1 ), "diamond", 5 );
		GameRegistry.registerItem( diamondClaws, "diamondClaws" );
	}
	
	private void registerRecipes()
	{
		GameRegistry.addRecipe( new ItemStack( wand ),
				                "*",
				                "|",
				                '*', Item.goldNugget,
				                '|', Item.bone );

		GameRegistry.addRecipe( new ItemStack( wand ),
				                " *",
				                "| ",
				                '*', Item.goldNugget,
				                '|', Item.bone );
	}
	
	private void registerEntities()
	{
		petEntityId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID( PetEntity.class, "Pet", petEntityId );
	}
	
	private void registerLanguage()
	{
		registerLanguage( "en_US" );
	}
	
	private void registerBonusChest()
	{
		if ( config.get( "general", "bonusChestAdditions", true ).getBoolean( true ) )
		{
			ChestGenHooks bonusChest = ChestGenHooks.getInfo( ChestGenHooks.BONUS_CHEST );
			bonusChest.addItem( new WeightedRandomChestContent( new ItemStack( Item.bone ), 3, 6, 4 ) );
			bonusChest.addItem( new WeightedRandomChestContent( new ItemStack( Item.fishRaw ), 3, 6, 4 ) );
			bonusChest.addItem( new WeightedRandomChestContent( new ItemStack( Item.monsterPlacer, 1, 95 ), 1, 1, 3 ) ); // Wolf
			bonusChest.addItem( new WeightedRandomChestContent( new ItemStack( Item.monsterPlacer, 1, 98 ), 1, 1, 3 ) ); // Ocelot
		}
	}
	
	private int getItemId( String name, int itemNum )
	{
		return config.getItem( name, ITEM_ID_BASE + itemNum ).getInt();
	}
	
	private void registerLanguage( String lang )
	{
		LanguageRegistry.instance().loadLocalization( "/assets/usefulpets/lang/" + lang + ".lang", lang, false );
	}
	
	public PetWandItem wand;
	public ClawItem goldClaws;
	public ClawItem ironClaws;
	public ClawItem diamondClaws;
	
	public int petEntityId;
	
	public AvoidanceEventHandler avoidanceHandler;
	public StarterItemEventHandler starterItemHandler;
	
	private Configuration config;
	private static final int ITEM_ID_BASE = 15764;
	
	public static final int PET_INVENTORY_GUI_ID = 0;
	public static final int PET_SKILLS_GUI_ID = 1;
}
