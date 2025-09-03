package at.dietze.quadru.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;


public interface ILocalization {
    Component freshMarijuana = Component.text("Frisches Marihuana").color(TextColor.color(0x00FF00));
    Component driedMarijuana = Component.text("Getrocknetes Marihuana").color(TextColor.color(0xFFA500));
    Component joint = Component.text("Joint").color(TextColor.color(0x00FFAF)).decorate(TextDecoration.BOLD);
}
