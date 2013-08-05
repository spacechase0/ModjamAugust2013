package com.spacechase0.minecraft.usefulpets.client.gui;

import org.lwjgl.opengl.GL11;

import com.spacechase0.minecraft.usefulpets.UsefulPets;
import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.inventory.PetInventoryContainer;
import com.spacechase0.minecraft.usefulpets.pet.skill.Skill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PetInventoryGui extends GuiContainer
{
    private static final ResourceLocation field_110414_t = new ResourceLocation("usefulpets:textures/gui/pet.png");
    private IInventory field_110413_u;
    private IInventory field_110412_v;
    private PetEntity pet;
    private float mouseX;
    private float mouseY;

    public PetInventoryGui(IInventory par1IInventory, IInventory par2IInventory, PetEntity thePet)
    {
        super(new PetInventoryContainer(par1IInventory, par2IInventory, thePet));
        this.field_110413_u = par1IInventory;
        this.field_110412_v = par2IInventory;
        this.pet = thePet;
        //this.allowUserInput = false;
    }
    
    @Override
    public void initGui()
    {
    	super.initGui();
    	
    	buttonList.clear();
    	buttonList.add( new GuiButton( 0, guiLeft + 8, guiTop + 39, 48, 20, StatCollector.translateToLocal( "gui.pet.skills" ) ) );
    }
    
    @Override
    public void actionPerformed( GuiButton button )
    {
    	if ( button.id == 0 )
    	{
    		mc.thePlayer.closeScreen();
    		mc.thePlayer.openGui( UsefulPets.instance, UsefulPets.PET_SKILLS_GUI_ID, mc.thePlayer.worldObj, 0, 0, 0 );
    	}
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.field_110412_v.isInvNameLocalized() ? this.field_110412_v.getInvName() : I18n.func_135053_a(this.field_110412_v.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.field_110413_u.isInvNameLocalized() ? this.field_110413_u.getInvName() : I18n.func_135053_a(this.field_110413_u.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.func_110434_K().func_110577_a(field_110414_t);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if ( pet.hasSkill( Skill.INVENTORY_ARMOR.id ) )
        {
            this.drawTexturedModalRect(k + 7 + 18, l + 17, 0, 220, 18, 18);
        }
        if ( false )
        {
            this.drawTexturedModalRect(k + 7, l + 17, 18, 220, 18, 18);
        }
        if ( pet.hasSkill( Skill.INVENTORY_WEAPON.id ) )
        {
            this.drawTexturedModalRect(k + 7 + 36, l + 17, 36, 220, 18, 18);
        }
        
        if ( pet.hasSkill( Skill.INVENTORY.id ) )
        {
        	int rows = ( pet.getInventory().getSizeInventory() - 3 ) / 3;
        	drawTexturedModalRect( k + 115, l + 17, 0, 166, 54, 18 * rows );
        }

        GuiInventory.func_110423_a(k + 88, l + 60, 17, (float)(k + 51) - this.mouseX, (float)(l + 75 - 50) - this.mouseY, this.pet);
    }

    @Override
    public void drawScreen(int mx, int my, float par3)
    {
        this.mouseX = (float)mx;
        this.mouseY = (float)my;
        super.drawScreen(mx, my, par3);
    }
}
