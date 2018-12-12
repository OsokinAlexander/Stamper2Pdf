# Stamper2Pdf
Stamping to PDF documents

## Dependency

Download library from releases.
Install it into your maven, gradle or other builder.
Example dependency for maven:
```html
<dependency>
    <groupId>ru.osokin</groupId>
    <artifactId>stamper2pdf</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Using
```html
The best variant you can see in my tests.
Below it is the fastest for coding variant. 

// Fill stamp data
List<StampData> stampDataList = new ArrayList<>();
stampDataList.add(new StampData("Date of transaction", "11.12.2018 00:01:57"));
stampDataList.add(new StampData("Received", "11.12.2018 00:02:04"));
stampDataList.add(new StampData("Document hash", "0689db4a-1120-46a5-b6d6-d81c4e739366"));

// Initialize border size and image
Border border = new Border(portraitBorderStream, landscapeBorderStream)
        .setPortraitSize(570, 75)
        .setLandscapeSize(820, 75);

// Initialize stamp padding
Padding padding = new Padding(5, 20, 5, 5);

// Initialize font
StampFont font = new StampFont(fontInputStream, "times.ttf", 6).setColor(28, 73, 255);

// Initialize stamp
Stamp middleStamp = new Stamp(2, 100, 160)
        .setStampDataList(stampDataList)
        .setBorder(border)
        .setVisibleTableGrid(false)
        .setPadding(padding)
        .setFont(font);

// ...
PdfFactory pdfFactory = new PdfFactory()
        .addFirstStamp(firstStamp, 10, 80)
        .addMiddleStamp(middleStamp, 10, 80)
        .addLastStamp(lastStamp, 10, 80);

pdfFactory.drawAllStamps(inputPdfStream, outputPdfStream); 
```
If you add only middle stamps you add them to each pages (first and last too).
