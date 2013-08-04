package com.spacechase0.minecraft.usefulpets.inventory;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.pet.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class PetInventory implements IInventory
{
	public PetInventory( PetEntity thePet )
	{
		pet = thePet;
	}
	
	@Override
	public int getSizeInventory()
	{
		int base = 3;
		base += 3 * ( pet.hasSkill( Skill.INVENTORY.id ) ? 1 : 0 );
		base += 3 * ( pet.hasSkill( Skill.INVENTORY_UPGRADE1.id ) ? 1 : 0 );
		base += 3 * ( pet.hasSkill( Skill.INVENTORY_UPGRADE2.id ) ? 1 : 0 );
		
		return base;
	}

	@Override
	public ItemStack getStackInSlot( int slot )
	{
		return stacks[ slot ];
	}

	@Override
	public ItemStack decrStackSize( int slot, int amount )
	{
		// From InventoryBasic
		ItemStack[] inventoryContents = stacks;
		int par1 = slot;
		int par2 = amount;
		
        if (inventoryContents[par1] != null)
        {
            ItemStack itemstack;

            if (inventoryContents[par1].stackSize <= par2)
            {
                itemstack = inventoryContents[par1];
                inventoryContents[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = inventoryContents[par1].splitStack(par2);

                if (inventoryContents[par1].stackSize == 0)
                {
                    inventoryContents[par1] = null;
                }

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int slot )
	{
		return getStackInSlot( slot );
	}

	@Override
	public void setInventorySlotContents( int slot, ItemStack stack )
	{
		stacks[ slot ] = stack;
	}

	@Override
	public String getInvName()
	{
		return "gui.pet.inventory";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player )
	{
		return ( player.username.equals( pet.getOwnerName() ) );
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isItemValidForSlot( int i, ItemStack stack )
	{
		// TODO
		return true;
	}
	
	private PetEntity pet;
	private ItemStack[] stacks = new ItemStack[ 12 ];
}
