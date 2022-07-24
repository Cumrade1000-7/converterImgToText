package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;

public class Converter implements TextGraphicsConverter{

    private int maxWidth;
    private int maxHeight;
    private double maxRatio;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        SelectSymbol schema = new SelectSymbol();
        StringBuilder sb = new StringBuilder();
        BufferedImage img = ImageIO.read(new URL(url));
        int[] tempArray = new int[3];

        double ratio = (double) img.getHeight() / img.getWidth();
        int height = img.getHeight();
        int width = img.getWidth();

        if (ratio > maxRatio){
            throw new BadImageSizeException(ratio, maxRatio);
        }

        double ratioCoef = 1.0;
        if (height > maxHeight || width > maxWidth){
            if (width > height){
                ratioCoef = (double) maxWidth / width;
            } else {
                ratioCoef = (double) maxHeight / height;
            }
        }

        int newWidth = (int) (ratioCoef * width);
        int newHeight = (int) (ratioCoef * height);

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        /*Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();*/

        for (int i = 0; i < newHeight; i++){
            for (int j = 0; j < newWidth; j++){
                int color = bwRaster.getPixel(j, i, tempArray)[0];
                /*int [] colorArray = bwRaster.getPixel(j, i, tempArray);
                int color = (int) Math.round(0.2126 * colorArray[0] + 0.7152 * colorArray[1] + 0.0722 * colorArray[2]);*/
                char c = schema.convert(color);
                sb.append(c).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
