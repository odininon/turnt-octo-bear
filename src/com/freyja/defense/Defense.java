package com.freyja.defense;

import java.util.logging.Logger;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.freyja.defense.handler.Configurations;
import com.freyja.defense.items.ItemDefenseDoor;
import com.freyja.defense.lib.Strings;
import com.freyja.defense.network.CommonProxy;
import com.freyja.defense.network.GuiHandler;
import com.freyja.defense.registry.Registry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import com.freyja.defense.blocks.*;

@Mod(modid = Strings.MODID, name = Strings.MODNAME)
public class Defense
{
    @Instance(Strings.MODID)
    public static Defense instance;

    @SidedProxy(clientSide = "com.freyja.defense.network.ClientProxy", serverSide = "com.freyja.defense.network.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    public static CreativeTabs tab;

    @Init
    public void init(FMLInitializationEvent event)
    {
        Registry.init();

        Registry.register("ElectronicDoor", new BlockDefenseDoor(500, Material.rock), new ItemDefenseDoor(2000, "ElectronicDoor").setCreativeTab(tab));

        NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
        proxy.init();
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        Configurations.init(event.getSuggestedConfigurationFile());

        tab = new CreativeTabs(Strings.MODID);
    }
}
