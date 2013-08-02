package com.spacechase0.minecraft.usefulpets.item;

import com.spacechase0.minecraft.usefulpets.pet.PetType;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

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
	
	@Override
    public boolean onLeftClickEntity( ItemStack stack, EntityPlayer player, Entity entity )
    {
		if ( player.worldObj.isRemote || !( entity instanceof EntityTameable ) )
		{
			return true;
		}
		EntityTameable tameable = ( EntityTameable ) entity;
		
		for ( PetType type : PetType.types.values() )
		{
			if ( !type.convertFrom.equals( entity.getClass() ) )
			{
				continue;
			}
			
			if ( !tameable.isTamed() )
			{
				// TODO: Localize properly
				player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "This animal isn't tamed!" ) );
				return true;
			}
			
			if ( !tameable.getOwnerName().equals( player.username ) )
			{
				player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "You don't own this pet." ) );
				return true;
			}
			
			entity.worldObj.removeEntity( entity );
			// TODO: Spawn entity
			
			player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "Converted!" ) );
			return true;
		}

		player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "This pet can't be converted." ) );
		return true;
    }
}
