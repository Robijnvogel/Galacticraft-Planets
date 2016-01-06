package micdoodle8.mods.galacticraft.mars.client.gui;

import mekanism.api.EnumColor;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import micdoodle8.mods.galacticraft.mars.inventory.GCMarsContainerTerraformer;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityTerraformer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GCMarsGuiTerraformer extends GuiContainer
{
    private static final ResourceLocation terraformerGui = new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/gui/terraformer.png");

    private GCMarsTileEntityTerraformer terraformer;
    
    private GuiButton enableTreesButton;
    private GuiButton enableGrassButton;
    
    public GCMarsGuiTerraformer(InventoryPlayer par1InventoryPlayer, GCMarsTileEntityTerraformer terraformer)
    {
        super(new GCMarsContainerTerraformer(par1InventoryPlayer, terraformer));
        this.ySize = 228;
        this.terraformer = terraformer;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        if (this.terraformer.disableCooldown > 0)
        {
            this.enableTreesButton.enabled = false;
            this.enableGrassButton.enabled = false;
        }
        else
        {
            this.enableTreesButton.enabled = true;
            this.enableGrassButton.enabled = true;
        }
        
        this.enableTreesButton.displayString = (this.terraformer.treesDisabled ? "Enable" : "Disable") + " Trees";
        this.enableGrassButton.displayString = (this.terraformer.grassDisabled ? "Enable" : "Disable") + " Grass";
        
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.enableTreesButton = new GuiButton(0, var5 + 98, var6 + 85, 72, 20, "Enable Trees");
        this.enableGrassButton = new GuiButton(1, var5 + 98, var6 + 109, 72, 20, "Enable Grass");
        this.buttonList.add(this.enableTreesButton);
        this.buttonList.add(this.enableGrassButton);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            switch (par1GuiButton.id)
            {
            case 0:
                PacketDispatcher.sendPacketToServer(PacketUtil.createPacket(GalacticraftCore.CHANNEL, 17, new Object[] { this.terraformer.xCoord, this.terraformer.yCoord, this.terraformer.zCoord, 0 }));
                break;
            case 1:
                PacketDispatcher.sendPacketToServer(PacketUtil.createPacket(GalacticraftCore.CHANNEL, 17, new Object[] { this.terraformer.xCoord, this.terraformer.yCoord, this.terraformer.zCoord, 1 }));
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String displayString = "Terraformer";
        this.fontRenderer.drawString(displayString, this.xSize / 2 - this.fontRenderer.getStringWidth(displayString) / 2, 5, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 135, 4210752);
        this.fontRenderer.drawSplitString(this.getStatus(), 105, 24, this.xSize - 105, 4210752);
        this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.terraformer.ueWattsPerTick * 20, ElectricUnit.WATT), 105, 56, 4210752);
        this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.terraformer.getVoltage(), ElectricUnit.VOLTAGE), 105, 68, 4210752);
    }
    
    private String getStatus()
    {
        if (this.terraformer.getEnergyStored() <= 0.0F)
        {
            return EnumColor.RED + "Not Enough Energy";
        }
        
        if (this.terraformer.grassDisabled && this.terraformer.treesDisabled)
        {
            return EnumColor.ORANGE + "Disabled";
        }

        if (this.terraformer.terraformBubble.getSize() < this.terraformer.terraformBubble.MAX_SIZE)
        {
            return EnumColor.YELLOW + "Bubble Expanding";
        }

        if (this.terraformer.terraformableBlocksListSize <= 0)
        {
            return EnumColor.RED + "No Valid Blocks in Bubble";
        }
        
        return EnumColor.BRIGHT_GREEN + "Terraforming";
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.func_110577_a(GCMarsGuiTerraformer.terraformerGui);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }
}
