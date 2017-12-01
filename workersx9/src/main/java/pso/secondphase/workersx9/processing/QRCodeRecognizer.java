/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.workersx9.processing;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pso.secondphase.iox9.business.processing.EntityRecognizer;

/**
 *
 * @author vitorgreati
 */
public class QRCodeRecognizer implements EntityRecognizer<Image>{

    private Map<EncodeHintType, ErrorCorrectionLevel> HINT_MAP = new EnumMap<EncodeHintType, ErrorCorrectionLevel>(EncodeHintType.class);
    private final String CHAR_SET = "UTF-8";
    
    @Override
    public String recognize(Image idt) {
        
        if (idt != null) {
        
            BufferedImage bimage = new BufferedImage(idt.getWidth(null), idt.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(idt, 0, 0, null);
            bGr.dispose();

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource((bimage))));
            Result qrCodeResult = null;
            try {
                qrCodeResult = new MultiFormatReader().decode(binaryBitmap,(Map)HINT_MAP);
            } catch (NotFoundException ex) {
                Logger.getLogger(QRCodeRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            }

            return qrCodeResult.getText();
        }
        return null;
    }
    
}
