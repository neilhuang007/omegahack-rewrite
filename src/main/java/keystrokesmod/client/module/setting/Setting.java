package keystrokesmod.client.module.setting;

import com.google.gson.JsonObject;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;

public abstract class Setting {
   public String settingName;
   public float optionAnim = 0;//present
   public float optionAnimNow = 0;//present

   public Setting(String name) {
      this.settingName = name;
   }

   public String getName() {
      return this.settingName;
   }

   public abstract void resetToDefaults();
   public abstract JsonObject getConfigAsJson();

   public abstract String getSettingType();

   public abstract void applyConfigFromJson(JsonObject data);

   public abstract Component createComponent(ModuleComponent moduleComponent);
}
