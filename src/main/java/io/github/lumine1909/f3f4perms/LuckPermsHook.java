/*
This code is come from F3NPerm project under MIT Licence
Original file:
https://github.com/SlimeNexus/F3NPerm/blob/master/src/main/java/nexus/slime/f3nperm/hooks/LuckPermsHook.java

MIT License

Copyright (c) 2022 RodneyMKay

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package io.github.lumine1909.f3f4perms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsHook {

    private EventSubscription<?> subscription;

    public void register(F3F4PermsPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return;
        }
        LuckPerms luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            return;
        }
        subscription = luckPerms.getEventBus().subscribe(UserDataRecalculateEvent.class, e -> {
            Player player = Bukkit.getPlayer(e.getUser().getUniqueId());
            if (player != null) {
                plugin.updateOpLevel(player);
            }
        });
        plugin.getLogger().info("Successfully hooked into LuckPerms!");
    }

    public void unregister() {
        if (subscription != null) {
            subscription.close();
        }
    }
}