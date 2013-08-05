package com.spacechase0.minecraft.usefulpets.item;

import java.util.List;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.pet.Level;
import com.spacechase0.minecraft.usefulpets.pet.PetType;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StatCollector;

public class PetWandItem extends Item
{
	public PetWandItem( int id )
	{
		super( id );
		setUnlocalizedName( "petWand" );
		setCreativeTab( CreativeTabs.tabTools );
		setMaxStackSize( 1 );
	}
	
	@Override
	public void registerIcons( IconRegister register )
	{
		itemIcon = register.registerIcon( "usefulpets:converter" );
	}
	
	@Override
    public boolean onLeftClickEntity( ItemStack stack, EntityPlayer player, Entity entity )
    {
		if ( player.worldObj.isRemote )
		{
			return true;
		}
		
		if ( entity instanceof EntityTameable )
		{
			convertPet( player, ( EntityTameable ) entity );
		}
		else if ( entity instanceof PetEntity )
		{
			levelUp( player, ( PetEntity ) entity );
		}
		
		return true;
    }
	
	private void convertPet( EntityPlayer player, EntityTameable entity )
	{
		if ( !entity.isTamed() )
		{
			player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.convert.notTamed" ) );
			return;
		}
		
		if ( !entity.getOwnerName().equals( player.username ) )
		{
			player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.convert.needOwnership" ) );
			return;
		}
		
		for ( PetType type : PetType.types.values() )
		{
			if ( !type.convertFrom.equals( entity.getClass() ) )
			{
				continue;
			}
			
			System.out.println( "Converting pet; setting type to " + type.name );
			
			PetEntity pet = new PetEntity( entity.worldObj );
			pet.setPosition( entity.posX, entity.posY, entity.posZ );
			pet.setOwnerName( entity.getOwnerName() );
			pet.setPetType( type );
			pet.setSitting( entity.isSitting() );
			
			entity.worldObj.removeEntity( entity );
			entity.worldObj.spawnEntityInWorld( pet );
			
			player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.convert.success" ) );
			return;
		}

		player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.convert.failure" ) );
	}
	
	private void levelUp( EntityPlayer player, PetEntity pet )
	{
		if ( pet.getLevel() >= Level.MAX_LEVEL )
		{
			player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.level.max" ) );
			return;
		}
		
		boolean someMissing = false;
		List< ItemStack > reqs = Level.getLevelItemRequirements( pet.getLevel() + 1 );
		for ( ItemStack stack : reqs )
		{
			if ( !hasAmount( player.inventory, stack.itemID, stack.stackSize ) )
			{
				someMissing = true;
				player.sendChatToPlayer( ChatMessageComponent.func_111082_b( "chat.pet.level.missing", stack.getDisplayName(), stack.stackSize ) );
			}
		}
		
		int reqLevel = Level.getLevelExperienceRequirements( pet.getLevel() + 1 );
		if ( player.experienceLevel < reqLevel )
		{
			someMissing = true;
			player.sendChatToPlayer( ChatMessageComponent.func_111082_b( "chat.pet.level.missing", StatCollector.translateToLocal( "misc.level.name" ), reqLevel ) );
		}
		
		if ( someMissing )
		{
			return;
		}
		
		player.addExperienceLevel( -reqLevel );
		for ( ItemStack stack : reqs )
		{
			takeAmount( player.inventory, stack.itemID, stack.stackSize );
		}
		
		pet.levelUp();
		player.sendChatToPlayer( ChatMessageComponent.func_111077_e( "chat.pet.level.success" ) );
	}
	
	private boolean hasAmount( IInventory inv, int id, int reqAmount )
	{
		int amount = 0;
		for ( int is = 0; is < inv.getSizeInventory(); ++is )
		{
			ItemStack stack = inv.getStackInSlot( is );
			if ( stack != null && stack.itemID == id )
			{
				amount += stack.stackSize;
			}
		}
		
		return ( amount >= reqAmount );
	}
	
	private void takeAmount( IInventory inv, int id, int reqAmount )
	{
		int left = reqAmount;
		for ( int is = 0; is < inv.getSizeInventory() && left > 0; ++is )
		{
			ItemStack stack = inv.getStackInSlot( is );
			if ( stack != null && stack.itemID == id )
			{
				left -= inv.decrStackSize( is, left ).stackSize;
			}
		}
	}
}
