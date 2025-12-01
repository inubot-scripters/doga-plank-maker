package com.inubot.script.plankmaker.commons;

import org.rspeer.commons.swing.SwingCommons;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.script.meta.paint.PaintScheme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class RSPeerPaintScheme extends PaintScheme {

  private static final Color BACKGROUND = new Color(0x1E1E1E);   // deep dark gray
  private static final Color BORDER = new Color(0x2A2A2A); // subtle edge
  private static final Color FONT = new Color(0xDDDDDD); // light gray text
  private static final Color TITLE = new Color(0x4FC3F7);   // light blue accent (matches RSPeer highlights)
  private static final Color HIDE_BTN = new Color(0x3A3A3A);   // muted control
  private static final Color SHOW_BTN = new Color(0xFF9800);   // orange accent (like RSPeer hover)

  private static final Map<Skill, Color> SKILL_COLORS = new EnumMap<>(Skill.class);
  private static final Color DEFAULT_SKILL_COLOR = new Color(0xFBC02D); // warm yellow accent

  private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 14);
  private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 12);

  private static final Image ICON;

  static {
    Image icon;
    try {
      icon = ImageIO.read(new URL("https://i.ibb.co/21spsdmx/logo.png"));
    } catch (IOException e) {
      icon = SwingCommons.getIcon();
    }

    ICON = icon;

    for (Skill skill : Skill.values()) {
      SKILL_COLORS.put(skill, DEFAULT_SKILL_COLOR);
    }
  }

  @Override
  public Color getBackgroundColor() {
    return BACKGROUND;
  }

  @Override
  public Color getBorderColor() {
    return BORDER;
  }

  @Override
  public Color getFontColor() {
    return FONT;
  }

  @Override
  public Color getTitleColor() {
    return TITLE;
  }

  @Override
  public Color getHideColor() {
    return HIDE_BTN;
  }

  @Override
  public Color getShowColor() {
    return SHOW_BTN;
  }

  @Override
  public int getArcLength() {
    return 8;
  }

  @Override
  public int getPadding() {
    return 6;
  }

  @Override
  public int getX() {
    return 30;
  }

  @Override
  public int getY() {
    return 30;
  }

  @Override
  public Font getTitleFont() {
    return TITLE_FONT;
  }

  @Override
  public Font getBodyFont() {
    return BODY_FONT;
  }

  @Override
  public Color getSkillColor(Skill skill) {
    return SKILL_COLORS.getOrDefault(skill, DEFAULT_SKILL_COLOR);
  }

  @Override
  public int getBackgroundAlpha() {
    return 200;
  }

  public Image getIcon() {
    return ICON;
  }

  public String getFooter() {
    return "RSPeer Scripts | Inubot";
  }
}
