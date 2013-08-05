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
        if (stacks[slot] != null)
        {
            ItemStack itemstack;

            if (stacks[slot].stackSize <= amount)
            {
                itemstack = stacks[slot];
                stacks[slot] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = stacks[slot].splitStack(amount);

                if (stacks[slot].stackSize == 0)
                {
                	stacks[slot] = null;
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
		ItemStack tmp = getStackInSlot( slot );
		stacks[ slot ] = null;
		return tmp;
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
