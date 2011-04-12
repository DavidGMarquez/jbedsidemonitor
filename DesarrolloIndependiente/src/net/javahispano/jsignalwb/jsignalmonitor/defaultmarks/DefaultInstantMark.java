package net.javahispano.jsignalwb.jsignalmonitor.defaultmarks;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;

public class DefaultInstantMark  implements JSignalMonitorMark {
    private long markTime;
    private String title;
    private String comentary;
    private Color color;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean isImage;

    public DefaultInstantMark() {
        markTime = 0;
        title = "Write here the mark title...";
        comentary = "Write here your comentary....";
        color = Color.RED;
        image = getDefaultImage();
        setIsImage(false);
    }

    public String getName() {
        return "Default Instant Mark";
    }

    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    public long getMarkTime() {
        return markTime;
    }

    public Image getImage() {
        return bufferedImage;
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

    public boolean hasDataToSave() {
        return true;
    }

    public String getToolTipText() {
        return title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setImageToShow(Image image) {
        this.image = image;
    }

    public Image getImageToShow() {
        return image;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
        refreshBufferedImage();
    }

    private void refreshBufferedImage() {
        if (isImage) {
            bufferedImage = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, 15, 15, null);
        } else {
            bufferedImage = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
    }

    public Image getDefaultImage() {
        return new ImageIcon(DefaultInstantMark.class.getResource(
                "../images/defaultIconMark.png")).getImage();
    }

    public boolean isInterval() {
        return false;
    }

    public long getEndTime() {
        return 0L;
    }

    public boolean isOwnPainted() {
        return false;
    }

    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
