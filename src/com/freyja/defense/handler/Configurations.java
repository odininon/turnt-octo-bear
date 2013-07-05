package com.freyja.defense.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;

import com.freyja.defense.Defense;

public class Configurations
{

    public static Configuration config;

    public static void init(File suggestedConfigurationFile)
    {
        try
        {
            config = new Configuration(suggestedConfigurationFile);

        } catch (final Exception ex)
        {
            Defense.logger.severe("Can not load Configuration file.");
            Defense.logger.severe(ex.getLocalizedMessage());
        } finally
        {
            if (config.hasChanged())
            {
                config.save();
            }
        }
    }
}
