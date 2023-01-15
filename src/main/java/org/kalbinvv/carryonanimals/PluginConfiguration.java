package org.kalbinvv.carryonanimals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.kalbinvv.carryonanimals.protections.Protection;
import org.kalbinvv.carryonanimals.protections.ProtectionsList;
import org.kalbinvv.carryonanimals.sounds.SoundsUtils;
import org.kalbinvv.carryonanimals.utils.ChatUtils;
import org.kalbinvv.carryonanimals.utils.ConfigurationUtils;

/*
Modified configuration class with reload function
and save memory	via hash map.
*/
public class PluginConfiguration extends YamlConfiguration implements Reloadable {

	private final Map<String, Object> savedData = new HashMap<String, Object>();
	private final File configurationFile;

	public PluginConfiguration(String configurationFilePath) {
		this.configurationFile = new File(configurationFilePath);
		
		reload();
	}
	
	public PluginConfiguration(File file) {
		this.configurationFile = file;
		
		reload();
	}
	
	@Override
	public void reload() {
		clearSavedData();
		
		var configuration = YamlConfiguration.loadConfiguration(configurationFile);

		try {
			loadFromString(configuration.saveToString());
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		ProtectionsList.clear();
		ProtectionsList.init();
		
		ConfigurationUtils.loadWorldsFromConfiguration(configuration);
		ConfigurationUtils.loadAllowedEntitiesTypes(configuration);
		ConfigurationUtils.loadParticleFromConfiguration(configuration);
		
		SoundsUtils.loadSounds(configuration);

		ChatUtils.setMessagesEnabled(configuration.getBoolean("messages.enabled"));

		Protection.registerProtections();
		
	}
	
	public void clearSavedData() {
		savedData.clear();
	}

	@Override
	public String getString(String path) {
		if(savedData.containsKey(path)) {
			CarryOnAnimals.getPlugin().getLogger().info("From saved story!");
			return (String) savedData.get(path);
		}

		String value = super.getString(path);

		savedData.put(path, value);

		return value;
	}
	
	@Override
	public boolean getBoolean(String path) {
		if(savedData.containsKey(path)) {
			return (boolean) savedData.get(path);
		}
		
		boolean value = super.getBoolean(path);
		
		savedData.put(path, value);
		
		return value;
	}
	
	@Override
	public double getDouble(String path) {
		if(savedData.containsKey(path)) {
			return (double) savedData.get(path);
		}
		
		double value = super.getDouble(path);
		
		savedData.put(path, value);
		
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getStringList(String path) {
		if(savedData.containsKey(path)) {
			return (List<String>) savedData.get(path);
		}

		List<String> value = super.getStringList(path);

		savedData.put(path, value);

		return value;
	}

}
