package com.spacechase0.minecraft.usefulpets.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ConverterItem extends Item
{
	public ConverterItem( int id )
	{
		super( id );
		setUnlocalizedName( "petConverter" );
		setCreativeTab( CreativeTabs.tabTools );
	}
	
	@Override
	public void registerIcons( IconRegister register )
	{
		itemIcon = register.registerIcon( "usefulpets:converter" );
	}
}
