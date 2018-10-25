package com.bookmerge.demo.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;

@Controller
public class MainController {

  /** A font that will be used in our PDF. */
  public static final Font BOLD_UNDERLINED =
      new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
  /** A font that will be used in our PDF. */
  public static final Font NORMAL =
      new Font(FontFamily.TIMES_ROMAN, 12);

  @GetMapping("/")
  public String main() {
    return "index";
  }

  @GetMapping(value = "pdf", produces = "application/pdf")
  public ResponseEntity<InputStreamResource> createPdf(String filename)
      throws DocumentException, IOException {
    // step 1
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // step 2
    PdfWriter.getInstance(document, out);
    // step 3
    document.open();
    // step 4

    Font font = new Font(FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);

    Phrase director = new Phrase();
    director.add(
        new Chunk("a ver a ver", BOLD_UNDERLINED));
    director.add(new Chunk(",", BOLD_UNDERLINED));
    director.add(new Chunk(" ", NORMAL));
    director.add(
        new Chunk(" pensando q colocar UTF-8", NORMAL));
    // step 5
    document.add(director);
    document.close();

    ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

    return ResponseEntity
        .ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }




}
