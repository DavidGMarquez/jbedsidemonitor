package net.javahispano.jsignalwb.jsignalmonitor.defaultmarks;


import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import net.javahispano.jsignalwb.jsignalmonitor.marks.*;

public class DefaultIntervalAnnotation  implements JSignalMonitorAnnotation {

    private long annotationTime;
    private long endTime;
    private String title;
    private String comentary;
    private String category;
    private Color color;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean isImage;
    private int height = 1;
    private int width = 1;
    private Graphics2D g2d;
    private int barHeight = 4;
    private Color fontColor = Color.blue;


    public DefaultIntervalAnnotation() {
        annotationTime = 0;
        title = "Write here the annotation title...";
        comentary = "Write here your comentary....";
        color = Color.RED;
        image = getDefaultImage();
        isImage = false;
        category = "Interval";
        this.refreshBufferedImage();
    }

    public String getName() {
        return "Default Interval Annotation";
    }

    public long getAnnotationTime() {
        return annotationTime;
    }

    public Image getImage() {
        return bufferedImage;
    }

    public void setAnnotationTime(long annotationTime) {
        this.annotationTime = annotationTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isInterval() {
        return true;
    }

    public void showMarkInfo(Window owner) {
        throw new UnsupportedOperationException("Unsupported operation");
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
        return color;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setColor(Color color) {
        this.color = color;
        this.refreshBufferedImage();
    }

    public Image getImageToShow() {
        return image;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        refreshBufferedImage();
    }

    public void setWidth(int width) {
        this.width = width;
        refreshBufferedImage();
    }

    public void setHeight(int height) {
        this.height = height;
        refreshBufferedImage();
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        refreshBufferedImage();
    }

    public String getTextToShow() {
        return title;
    }

    private void refreshBufferedImage() {
        if (!isImage) {
            isImage = false;
            bufferedImage = new BufferedImage(width, height,
                                              BufferedImage.TYPE_INT_RGB);
            g2d = bufferedImage.createGraphics();
        }
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(color);
        if (this.width > 15) {
            g2d.fillRect(0, 0, 4, height);
            g2d.fillRect(width - 4, 0, 4, height);
            int barYPosition = Math.min(2 * this.height / 3 - barHeight / 2,
                                        this.height - this.barHeight);
            g2d.fillRect(4, barYPosition, this.width - 8,
                         barHeight);
            if (height > 15) {
                g2d.setColor(fontColor);
                g2d.drawString(getTextToShow(), 6, barYPosition - 2);
            }
        } else {
            g2d.fillRect(0, 0, this.width, height);
        }
    }

    public boolean isOwnPainted() {
        return true;
    }

    public void paint(Graphics2D g2d, Point p, int height, int width) {
        if (height > 0 && width > 0 && (this.height != height || this.width != width)) {
            this.height = height;
            this.width = width;
            refreshBufferedImage();
        }
        g2d.drawImage(bufferedImage, p.x, p.y, null);
    }

    public Image getDefaultImage() {
        return new ImageIcon(DefaultIntervalAnnotation.class.getResource(
                "../images/defaultIconMark.png")).getImage();
    }

}
