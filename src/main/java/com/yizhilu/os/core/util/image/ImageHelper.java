package com.yizhilu.os.core.util.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @ClassName com.yizhilu.os.core.util.image.ImageHelper
 * @description
 * @author : qinggang.liu 305050016@qq.com
 * @Create Date : 2014-2-7 下午9:05:49
 */
public class ImageHelper {
    public static int defalutdropWidth = 150;
    public static int defalutdropHeight = 150;

    private static BufferedImage makeThumbnail(Image img, int width, int height) {
        BufferedImage tag = new BufferedImage(width, height, 1);
        Graphics g = tag.getGraphics();
        g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
        g.dispose();
        return tag;
    }

    private static void saveSubImage(BufferedImage image, Rectangle subImageBounds,
            File subImageFile, int left, int top, int dropWidth, int dropHeight)
            throws IOException {
        if (dropWidth != 0 && dropHeight != 0) {
            dropWidth = defalutdropWidth;
            dropHeight = defalutdropHeight;
        }
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf(46) + 1);
        BufferedImage subImage = new BufferedImage(dropWidth, dropWidth, 1);
        Graphics g = subImage.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, dropWidth, dropWidth);
        g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
                subImageBounds.width, subImageBounds.height), left, top, null);
        g.dispose();
        File file = new File(subImageFile.getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageIO.write(subImage, formatName, subImageFile);
    }

    public static void cut(String srcImageFile, String descDir, Rectangle rect,
            int[] intParms) throws IOException {
        Image image = ImageIO.read(new File(srcImageFile));
        BufferedImage bImage = makeThumbnail(image, intParms[0], intParms[1]);

        saveSubImage(bImage, rect, new File(descDir), intParms[2] < 0 ? -intParms[2] : 0,
                intParms[3] < 0 ? -intParms[3] : 0, intParms[4], intParms[5]);
    }

    public static void cut(File srcImageFile, File descDir, Rectangle rect, int[] intParms)
            throws IOException {
        Image image = ImageIO.read(srcImageFile);
        BufferedImage bImage = makeThumbnail(image, intParms[0], intParms[1]);

        saveSubImage(bImage, rect, descDir, intParms[2] < 0 ? -intParms[2] : 0,
                intParms[3] < 0 ? -intParms[3] : 0, intParms[4], intParms[5]);
    }
}