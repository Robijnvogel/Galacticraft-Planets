package micdoodle8.mods.galacticraft.mars.blocks;

import java.util.List;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.mars.GCMarsConfigManager;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityCryogenicChamber;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityTerraformer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.block.BlockTile;

public class GCMarsBlockMachine extends BlockTile
{
    public static final int TERRAFORMER_METADATA = 0;
    public static final int CRYOGENIC_CHAMBER_METADATA = 4;
    public static final int UNUSED_MACHINE_1 = 8;

    private Icon iconMachineSide;
    private Icon iconInput;

    private Icon iconTerraformer;
    private Icon unusedIcon0;
    private Icon unusedIcon1;
    private Icon unusedIcon2;

    public GCMarsBlockMachine(int id)
    {
        super(id, UniversalElectricity.machine);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_blank");
        this.iconInput = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_power_input");

        this.iconMachineSide = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_blank");
        this.iconTerraformer = par1IconRegister.registerIcon(GalacticraftMars.TEXTURE_PREFIX + "terraformer_0");
    }

    @Override
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        final TileEntity var9 = var1.getBlockTileEntity(var2, var3, var4);

        if (var9 instanceof IMultiBlock)
        {
            ((IMultiBlock) var9).onDestroy(var9);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GalacticraftMars.galacticraftMarsTab;
    }

    @Override
    public Icon getIcon(int side, int metadata)
    {
        if (side == 0 || side == 1)
        {
            return this.blockIcon;
        }

        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            metadata -= GCMarsBlockMachine.UNUSED_MACHINE_1;

            // If it is the front side
            if (side == metadata + 2)
            {
                return this.iconInput;
            }
            // If it is the back side
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.unusedIcon1;
            }
        }
        else
        {
            if (side == ForgeDirection.UP.ordinal() || side == ForgeDirection.DOWN.ordinal())
            {
                return this.iconMachineSide;
            }
            else if (side == ForgeDirection.getOrientation(metadata + 2).ordinal())
            {
                return this.iconInput;
            }
            else
            {
                return this.iconTerraformer;
            }
        }

        return this.iconMachineSide;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
        case 0:
            change = 1;
            break;
        case 1:
            change = 2;
            break;
        case 2:
            change = 0;
            break;
        case 3:
            change = 3;
            break;
        }

        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            world.setBlockMetadataWithNotify(x, y, z, GCMarsBlockMachine.UNUSED_MACHINE_1 + change, 3);
        }
        else if (metadata >= GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
            switch (angle)
            {
            case 0:
                change = 3;
                break;
            case 1:
                change = 1;
                break;
            case 2:
                change = 2;
                break;
            case 3:
                change = 0;
                break;
            }

            world.setBlockMetadataWithNotify(x, y, z, GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA + change, 3);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y, z, GCMarsBlockMachine.TERRAFORMER_METADATA + change, 3);
        }

        TileEntity var8 = world.getBlockTileEntity(x, y, z);

        if (var8 instanceof IMultiBlock)
        {
            ((IMultiBlock) var8).onCreate(new Vector3(x, y, z));
        }
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata;

        int change = 0;

        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            original -= GCMarsBlockMachine.UNUSED_MACHINE_1;
        }
        else if (metadata >= GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
            return false;
        }

        // Re-orient the block
        switch (original)
        {
        case 0:
            change = 3;
            break;
        case 3:
            change = 1;
            break;
        case 1:
            change = 2;
            break;
        case 2:
            change = 0;
            break;
        }

        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            change += GCMarsBlockMachine.UNUSED_MACHINE_1;
        }

        par1World.setBlockMetadataWithNotify(x, y, z, change, 3);
        return true;
    }

    /**
     * Called when the block is right clicked by the player
     */
    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);

        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            par5EntityPlayer.openGui(GalacticraftMars.instance, -1, par1World, x, y, z);
            return true;
        }
        else if (metadata >= GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
            par5EntityPlayer.openGui(GalacticraftMars.instance, -1, par1World, x, y, z);
            return true;
        }
        else
        {
            par5EntityPlayer.openGui(GalacticraftMars.instance, GCMarsConfigManager.idGuiMachine, par1World, x, y, z);
            return true;
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            return null;
        }
        else if (metadata >= GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
            return new GCMarsTileEntityCryogenicChamber();
        }
        else
        {
            return new GCMarsTileEntityTerraformer();
        }
    }

    public ItemStack getTerraformer()
    {
        return new ItemStack(this.blockID, 1, GCMarsBlockMachine.TERRAFORMER_METADATA);
    }

    public ItemStack getChamber()
    {
        return new ItemStack(this.blockID, 1, GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA);
    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(this.getTerraformer());
        par3List.add(this.getChamber());
    }

    @Override
    public int damageDropped(int metadata)
    {
        if (metadata >= GCMarsBlockMachine.UNUSED_MACHINE_1)
        {
            return GCMarsBlockMachine.UNUSED_MACHINE_1;
        }
        else if (metadata >= GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA)
        {
            return GCMarsBlockMachine.CRYOGENIC_CHAMBER_METADATA;
        }
        else
        {
            return GCMarsBlockMachine.TERRAFORMER_METADATA;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        int id = this.idPicked(world, x, y, z);

        if (id == 0)
        {
            return null;
        }

        Item item = Item.itemsList[id];

        if (item == null)
        {
            return null;
        }

        int metadata = this.getDamageValue(world, x, y, z);

        return new ItemStack(id, 1, metadata);
    }

    @Override
    public int getRenderType()
    {
        return GalacticraftMars.proxy.getMachineRenderID();
    }

    public boolean isBed(World world, int x, int y, int z, EntityLivingBase player)
    {
        return world.getBlockMetadata(x, y, z) >= CRYOGENIC_CHAMBER_METADATA;
    }

    public ChunkCoordinates getBedSpawnPosition(World world, int x, int y, int z, EntityPlayer player)
    {
        return getNearestEmptyChunkCoordinates(world, x, y, z, 0);
    }

    public void setBedOccupied(World world, int x, int y, int z, EntityPlayer player, boolean occupied)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        
        if (tile instanceof GCMarsTileEntityCryogenicChamber)
        {
            ((GCMarsTileEntityCryogenicChamber) tile).isOccupied = true;
        }
    }

    public static ChunkCoordinates getNearestEmptyChunkCoordinates(World par0World, int par1, int par2, int par3, int par4)
    {
        for (int k1 = 0; k1 <= 1; ++k1)
        {
            int l1 = par1 - 1;
            int i2 = par3 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;

            for (int l2 = l1; l2 <= j2; ++l2)
            {
                for (int i3 = i2; i3 <= k2; ++i3)
                {
                    if (par0World.doesBlockHaveSolidTopSurface(l2, par2 - 1, i3) && !par0World.getBlockMaterial(l2, par2, i3).isOpaque() && !par0World.getBlockMaterial(l2, par2 + 1, i3).isOpaque())
                    {
                        if (par4 <= 0)
                        {
                            return new ChunkCoordinates(l2, par2, i3);
                        }

                        --par4;
                    }
                }
            }
        }

        return null;
    }
}
