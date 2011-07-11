package net.javahispano.jsignalwb.jsignalmonitor.defaultmarks;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import net.javahispano.jsignalwb.jsignalmonitor.marks.*;

public class DefaultInstantAnnotation implements JSignalMonitorAnnotation {
    private long annotationTime;
    private String title;
    private String comentary;
    private String category;
    private Color color;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean isImage;
    public DefaultInstantAnnotation() {
        annotationTime = 0;
        title = "Write here the annotation title...";
        comentary = "Write here your comentary....";
        color = Color.RED;
        image = getDefaultImage();
        category = "Instant";
        setIsImage(false);
    }

    public String getName() {
        return "Default Instant Annotation";
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Image getDefaultImage() {
        return new ImageIcon(DefaultInstantAnnotation.class.getResource(
                "../images/defaultIconMark.png")).getImage();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        refreshBufferedImage();
    }

    public void setImageToShow(Image image) {
        this.image = image;
        this.setIsImage(true);
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
            bufferedImage = new BufferedImage(image.getHeight(null), image.getWidth(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, image.getHeight(null), image.getWidth(null), null);
        } else {
            bufferedImage = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
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

    public void paint(Graphics2D g2d, Point p, int height, int widht) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
