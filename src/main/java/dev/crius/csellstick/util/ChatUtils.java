package dev.crius.csellstick.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    private final static Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F\\d]{6}");
    private final static MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(component -> component.decoration(TextDecoration.ITALIC, false)).build();

    @SuppressWarnings("deprecation") // I know, we need this method because holograms don't support adventure components
    public static String colorLegacy(String string, Placeholder... placeholders) {
        for (Placeholder placeholder : placeholders) {
            string = string.replace(placeholder.key(), placeholder.value());
        }

        Matcher matcher = HEX_PATTERN.matcher(string);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(builder, ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(builder).toString());
    }

    public static Component format(String string, TagResolver... placeholders) {
        return MINI_MESSAGE.deserialize(string, placeholders);
    }

    public static List<Component> format(List<String> list, TagResolver... placeholders) {
        return list.stream().map(s -> format(s, placeholders)).toList();
    }

}
