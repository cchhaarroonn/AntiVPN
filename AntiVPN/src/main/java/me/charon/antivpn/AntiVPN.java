package me.charon.antivpn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.List;

public final class AntiVPN extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        System.out.println("[AntiVPN] Starting AntiVPN v1.0");
        config.options().copyDefaults(true);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {

        //Getting variables
        Player player = e.getPlayer();
        String playerAddress = player.getAddress().getAddress().getHostAddress();
        String urlString = "http://ip-api.com/json/"+ playerAddress +"?fields=country";
        System.out.println("[AntiVPN] New connection incoming from " + playerAddress);

        //IP checking
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        if(code == 200){
            JSONObject retObj = null;
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            retObj = new JSONObject(result.toString());
            Object country = retObj.get("country");
            System.out.println("[AntiVPN] Connection is coming from " + country + " checking restrictions!");
            List<String> countries = config.getStringList("config.countries");
            boolean countryy = countries.contains(country);
            if (countryy == true){
                System.out.println("[AntiVPN] Address " + playerAddress + "(" + country + ") successfully approved!");
                player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------" + ChatColor.WHITE + "\nYour connection was successfully " + ChatColor.GREEN + "APPROVED" + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "\n------------------------------------");
            } else {
                System.out.println("[AntiVPN] Address " + playerAddress + "(" + country + ") kicked because server doesn't support connections from their country!");
                player.kickPlayer(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "AntiVPN" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Your country is not supported by server");
            }

        } else {
            System.out.println("[AntiVPN] An error occurred couldn't verify connection for " + playerAddress + " ...");
            System.out.println("[AntiVPN] Connection rejected");
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[AntiVPN] Stopping AntiVPN v1.0");
    }
}

