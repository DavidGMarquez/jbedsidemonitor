package net.javahispano.jsignalwb.jsignalmonitor.defaultmarks;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;

public class DefaultIntervalMark implements JSignalMonitorMark, Comparable {
    private long markTime;
    private long endTime;
    private String title;
    private String comentary;
    protected Color color;
    private BufferedImage im;
    private int extraheightPixels = 10;
    private int innerTransparencyLevel = 50;
    private int borderTransparencyLevel = 150;
    public DefaultIntervalMark() {
        markTime = 0;
        endTime = 0;
        title = "Write here the mark title...";
        comentary = "Write here your comentary....";
        color = Color.GREEN;
        refreshBufferedImage();
    }

    public String getName() {
        return "Default Interval Mark";
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public long getMarkTime() {
        return markTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isInterval() {
        return true;
    }

    public long getEndTime() {
        return endTime;
    }


    public void showMarkInfo(Window owner) {
       throw new UnsupportedOperationException("Unsupported operation");
    }

    public Image getImage() {
        return im;
    }

    public boolean isOwnPainted() {
        return true;
    }

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        Stroke oldStroke = g2d.getStroke();
        Color color2 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), innerTransparencyLevel);
        Color color3 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), borderTransparencyLevel);
        int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
                                  markPaintInfo.getMaxValueY());
        int minY = (int) Math.min(markPaintInfo.getPoint().getY() +
                                  markPaintInfo.getHeight(),
                                  markPaintInfo.getMinValueY());
        g2d.setColor(color3);

        g2d.setStroke(new BasicStroke(3));

        int x = markPaintInfo.getPoint().x;
        int y = maxY - extraheightPixels - 2;
        int width = markPaintInfo.getWidth();
        int height = minY - maxY + 2 * extraheightPixels + 3;
        g2d.draw(new java.awt.geom.RoundRectangle2D.Float(x - 2, y, width + 3,
                height, 15, 15));
        g2d.setColor(color2);
        g2d.fillRect(x, y, width, height);
        g2d.setStroke(oldStroke);
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public String getComentary() {
        return comentary;
    }

    public String getTitle() {
        return title;
    }
    public String getToolTipText() {
        return title;
    }

    public Color getColor() {
        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue());
        return c;
    }

    public int getExtraheightPixels() {
        return extraheightPixels;
    }

    public int getInnerTransparencyLevel() {
        return innerTransparencyLevel;
    }

    public int getBorderTransparencyLevel() {
        return borderTransparencyLevel;
    }

    public void setColor(Color color) {
        this.color = color;
        refreshBufferedImage();
    }

    public void setExtraheightPixels(int extraheightPixels) {
        this.extraheightPixels = extraheightPixels;
    }

    public void setInnerTransparencyLevel(int innerTransparencyLevel) {
        this.innerTransparencyLevel = innerTransparencyLevel;
    }

    public void setBorderTransparencyLevel(int borderTransparencyLevel) {
        this.borderTransparencyLevel = borderTransparencyLevel;
    }

    private void refreshBufferedImage() {
        im = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = im.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 5, 15);
    }

    public int compareTo(Object o) {
        JSignalMonitorMark i = (JSignalMonitorMark) o;
        if (i.getMarkTime() < this.getMarkTime()) {
            return 1;
        } else if (i.getMarkTime() > this.getMarkTime()) {
            return -1;
        }
        return 0;
    }

    public int hashCode() {
        return (int) (this.getMarkTime() | this.getEndTime());
    }
}
