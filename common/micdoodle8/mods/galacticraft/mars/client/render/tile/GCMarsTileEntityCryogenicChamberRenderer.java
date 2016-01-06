package micdoodle8.mods.galacticraft.mars.client.render.tile;

import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import micdoodle8.mods.galacticraft.mars.blocks.GCMarsBlockMachine;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityCryogenicChamber;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 * All rights reserved.
 * 
 */
@SideOnly(Side.CLIENT)
public class GCMarsTileEntityCryogenicChamberRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation chamberTexture0 = new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/model/chamber.png");
    private static final ResourceLocation chamberTexture1 = new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/model/chamber2.png");

    private final IModelCustom model;
    
    public GCMarsTileEntityCryogenicChamberRenderer(IModelCustom model)
    {
        this.model = model;
    }

    public void renderCryogenicChamber(GCMarsTileEntityCryogenicChamber chamber, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2 + 0.5F, (float) par4, (float) par6 + 0.5F);
        
        float rotation = 0.0F;
        
        switch (chamber.getBlockMetadata() - GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
        case 0:
            rotation = 180.0F;
            break;
        case 1:
            rotation = 0.0F;
            break;
        case 2:
            rotation = 270.0F;
            break;
        case 3:
            rotation = 90.0F;
            break;
        }
        
        GL11.glRotatef(rotation, 0, 1, 0);
        GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        
        this.func_110628_a(chamberTexture0);
        this.model.renderPart("Main_Cylinder");
        this.func_110628_a(chamberTexture1);
        this.model.renderPart("Shield_Torus");

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderCryogenicChamber((GCMarsTileEntityCryogenicChamber) par1TileEntity, par2, par4, par6, par8);
    }
}
