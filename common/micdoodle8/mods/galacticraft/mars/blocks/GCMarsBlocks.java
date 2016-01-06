package micdoodle8.mods.galacticraft.mars.blocks;

import micdoodle8.mods.galacticraft.mars.GCMarsConfigManager;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import micdoodle8.mods.galacticraft.mars.items.GCMarsItemBlock;
import micdoodle8.mods.galacticraft.mars.items.GCMarsItemBlockMachine;
import micdoodle8.mods.galacticraft.mars.items.GCMarsItemBlockRock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 * All rights reserved.
 * 
 */
public class GCMarsBlocks
{
    public static Block marsBlock;
    public static Block blockSludge;
    public static Block vine;
    public static Block rock;
    public static Block tier2TreasureChest;
    public static Block machine;

    public static Material bacterialSludge = new MaterialLiquid(MapColor.foliageColor);

    public static void initBlocks()
    {
        GCMarsBlocks.marsBlock = new GCMarsBlock(GCMarsConfigManager.idBlockMars).setHardness(2.2F).setUnlocalizedName("mars");
        GCMarsBlocks.blockSludge = new GCMarsBlockSludge(GalacticraftMars.SLUDGE.getBlockID(), GCMarsBlocks.bacterialSludge).setUnlocalizedName("sludge");
        GCMarsBlocks.vine = new GCMarsBlockVine(GCMarsConfigManager.idBlockVine).setHardness(0.1F).setUnlocalizedName("vine");
        GCMarsBlocks.rock = new GCMarsBlockSlimelingEgg(GCMarsConfigManager.idBlockRock).setHardness(0.75F).setUnlocalizedName("slimelingEgg");
        GCMarsBlocks.tier2TreasureChest = new GCMarsBlockT2TreasureChest(GCMarsConfigManager.idBlockTreasureChestT2).setHardness(2.5F).setUnlocalizedName("treasureT2");
        GCMarsBlocks.machine = new GCMarsBlockMachine(GCMarsConfigManager.idBlockMachine).setHardness(1.8F).setUnlocalizedName("marsMachine");
    }

    public static void setHarvestLevels()
    {
        MinecraftForge.setBlockHarvestLevel(GCMarsBlocks.marsBlock, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(GCMarsBlocks.rock, "pickaxe", 3);
    }

    public static void registerBlocks()
    {
        GameRegistry.registerBlock(GCMarsBlocks.marsBlock, GCMarsItemBlock.class, GCMarsBlocks.marsBlock.getUnlocalizedName(), GalacticraftMars.MODID);
        GameRegistry.registerBlock(GCMarsBlocks.blockSludge, ItemBlock.class, GCMarsBlocks.blockSludge.getUnlocalizedName(), GalacticraftMars.MODID);
        GameRegistry.registerBlock(GCMarsBlocks.vine, ItemBlock.class, GCMarsBlocks.vine.getUnlocalizedName(), GalacticraftMars.MODID);
        GameRegistry.registerBlock(GCMarsBlocks.rock, GCMarsItemBlockRock.class, GCMarsBlocks.rock.getUnlocalizedName(), GalacticraftMars.MODID);
        GameRegistry.registerBlock(GCMarsBlocks.tier2TreasureChest, ItemBlock.class, GCMarsBlocks.tier2TreasureChest.getUnlocalizedName(), GalacticraftMars.MODID);
        GameRegistry.registerBlock(GCMarsBlocks.machine, GCMarsItemBlockMachine.class, GCMarsBlocks.machine.getUnlocalizedName(), GalacticraftMars.MODID);
    }
}
