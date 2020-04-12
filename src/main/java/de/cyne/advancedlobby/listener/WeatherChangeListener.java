package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (!AdvancedLobby.singleWorld_mode | e.getWorld() == AdvancedLobby.lobbyWorld) {
            if(AdvancedLobby.cfg.getBoolean("weather.lock_weather")) {
                e.setCancelled(true);
            }
        }
    }

}
