package com.esteban.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class ImgToPdf {

    private void handleText(PdfWriter writer, String content, String color,
            float x, float y, float z) {
        PdfContentByte canvas = writer.getDirectContent();
        Phrase phrase = new Phrase(content);
        if (color != null) {
            phrase = new Phrase(content, FontFactory.getFont(
                    FontFactory.COURIER, 12, Font.NORMAL, new BaseColor(255, 0, 0)));
        }

        ColumnText.showTextAligned(canvas, Element.ALIGN_UNDEFINED, phrase, x,
                y, z);
    }

    public boolean Pdf(String imagePath, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                    mOutputPdfFileName));
            doc.open();

            doc.newPage();
            Image png1 = Image.getInstance(imagePath);
            float heigth = png1.getHeight();
            float width = png1.getWidth();
            int percent = this.getPercent2(heigth, width);
            png1.setAlignment(Image.MIDDLE);
            png1.setAlignment(Image.TEXTWRAP);
            png1.scalePercent(percent + 3);
            doc.add(png1);
//            this.handleText(writer, "This is a test", "red", 400, 725, 0);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);
        try {
        	mOutputPdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getPercent1(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    private int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

}