package ru.osokin.stamp;

import com.itextpdf.text.DocumentException;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stamper2PdfTest {

    private static final String TIMES_TTF = "times.ttf";
    private static final String BORDER_IMAGE_SMALL_PNG = "/border/borderImageSmall.png";
    private static final String BORDER_IMAGE_PORTRAIT_PNG = "/border/borderImagePortrait.png";
    private static final String BORDER_IMAGE_LANDSCAPE_PNG = "/border/borderImageLandscape.png";
    private static final String LANDSCAPE_PDF = "/pdf/landscape.pdf";
    private static final String PORTRAIT_PDF = "/pdf/portrait.pdf";
    private static final String MULTI_PAGES_PDF = "/pdf/multiPages.pdf";
    private static final int FONT_SIZE = 6;
    private static final int RED = 28;
    private static final int GREEN = 73;
    private static final int BLUE = 255;

    @Test
    public void drawStamp2LandscapePdf() throws IOException, DocumentException {
        File result = File.createTempFile("stamp", ".pdf");
        System.out.println(result.getAbsolutePath());
        PdfFactory pdfFactory = new PdfFactory()
                .addMiddleStamp(getBigStamp(), 10, 10);
        pdfFactory.drawAllStamps(Stamper2PdfTest.class.getResourceAsStream(LANDSCAPE_PDF),
                new FileOutputStream(result));
    }

    @Test
    public void drawStamp2PortraitPdf() throws IOException, DocumentException {
        File result = File.createTempFile("stamp", ".pdf");
        System.out.println(result.getAbsolutePath());
        PdfFactory pdfFactory = new PdfFactory()
                .addMiddleStamp(getBigStamp(), 10, 10);
        pdfFactory.drawAllStamps(Stamper2PdfTest.class.getResourceAsStream(PORTRAIT_PDF),
                new FileOutputStream(result));
    }

    @Test
    public void drawStamp2MultiPagePdf() throws IOException, DocumentException {
        File result = File.createTempFile("stamp", ".pdf");
        System.out.println(result.getAbsolutePath());
        PdfFactory pdfFactory = new PdfFactory()
                .addMiddleStamp(getBigStamp(), 10, 10);
        pdfFactory.drawAllStamps(Stamper2PdfTest.class.getResourceAsStream(MULTI_PAGES_PDF),
                new FileOutputStream(result));
    }

    @Test
    public void drawStamp2MultiPagePdf_DiffStamps() throws IOException, DocumentException {
        File result = File.createTempFile("stamp", ".pdf");
        System.out.println(result.getAbsolutePath());
        PdfFactory pdfFactory = new PdfFactory()
                .addMiddleStamp(getSmallStamp(), 10, 80)
                .addMiddleStamp(getSmallStamp(), 10, 10)
                .addLastStamp(getBigStamp(), 10, 10)
                .addLastStamp(getBigStamp(), 10, 90);
        pdfFactory.drawAllStamps(Stamper2PdfTest.class.getResourceAsStream(MULTI_PAGES_PDF),
                new FileOutputStream(result));
    }

    private Stamp getSmallStamp() throws IOException, DocumentException {
        return new Stamp(2, 100, 160)
                .setStampDataList(getSmallStampDataList())
                .setBorder(new Border(Stamper2PdfTest.class.getResourceAsStream(BORDER_IMAGE_SMALL_PNG),
                        Stamper2PdfTest.class.getResourceAsStream(BORDER_IMAGE_SMALL_PNG))
                        .setPortraitSize(260, 50)
                        .setLandscapeSize(260, 50))
                .setVisibleTableGrid(false)
                .setPadding(new Padding(5, 20, 5, 5))
                .setFont(new StampFont(Stamper2PdfTest.class.getResourceAsStream("/" + TIMES_TTF), TIMES_TTF,
                        FONT_SIZE, "Cp1251").setColor(RED, GREEN, BLUE));
    }

    private Stamp getBigStamp() throws IOException, DocumentException {
        return new Stamp(4, 120, 200)
                .setStampDataList(getBigStampDataList())
                .setBorder(new Border(Stamper2PdfTest.class.getResourceAsStream(BORDER_IMAGE_PORTRAIT_PNG),
                        Stamper2PdfTest.class.getResourceAsStream(BORDER_IMAGE_LANDSCAPE_PNG))
                        .setPortraitSize(570, 75)
                        .setLandscapeSize(820, 75))
                .setVisibleTableGrid(false)
                .setPadding(new Padding(20, 10, 5, 5))
                .setFont(new StampFont(Stamper2PdfTest.class.getResourceAsStream("/" + TIMES_TTF), TIMES_TTF,
                        FONT_SIZE).setColor(RED, GREEN, BLUE));
    }

    private List<StampData> getBigStampDataList() {
        List<StampData> stampDataList = new ArrayList<>();
        stampDataList.add(new StampData("Certificate", "TEST0000CERTIFICATE0000STRING"));
        stampDataList.add(new StampData("", ""));
        stampDataList.add(new StampData("Owner", "Osokin Alexander (Java developer)"));
        stampDataList.add(new StampData("Date of transaction", "11.12.2018 00:01:57"));
        stampDataList.add(new StampData("", "My home company"));
        stampDataList.add(new StampData("Document hash", "0689db4a-1120-46a5-b6d6-d81c4e739366"));
        stampDataList.add(new StampData("Valid", "from 01.01.2017 17:30+0300 to 31.12.2019 17:40+0300"));
        stampDataList.add(new StampData("", ""));
        return stampDataList;
    }

    private List<StampData> getSmallStampDataList() {
        List<StampData> stampDataList = new ArrayList<>();
        stampDataList.add(new StampData("Date of transaction", "11.12.2018 00:01:57"));
        stampDataList.add(new StampData("Received", "11.12.2018 00:02:04"));
        stampDataList.add(new StampData("Document hash", "0689db4a-1120-46a5-b6d6-d81c4e739366"));
        return stampDataList;
    }
}
