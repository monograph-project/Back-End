package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.model.DerivedDocumentIndex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class DocumentExtractionService {

    private static final Pattern BLANK_BLOCK = Pattern.compile("(\\r?\\n){2,}");

    public boolean supports(String filePath) {
        String lower = normalizedExtension(filePath);
        return "docx".equals(lower) || "pdf".equals(lower);
    }

    public String detectFileType(String filePath) {
        String ext = normalizedExtension(filePath);
        if ("docx".equals(ext) || "pdf".equals(ext)) {
            return ext;
        }
        return "binary";
    }

    public boolean isBinary(String filePath, byte[] bytes) {
        if (supports(filePath)) {
            return true;
        }
        if (bytes == null || bytes.length == 0) {
            return false;
        }
        for (byte value : bytes) {
            int c = value & 0xff;
            if (c == 0) {
                return true;
            }
            if (c < 32 && c != '\n' && c != '\r' && c != '\t') {
                return true;
            }
        }
        return false;
    }

    public ExtractionResult extract(String fileName, byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new BadRequestException("Document bytes are empty");
        }

        String type = detectFileType(fileName);
        List<DerivedDocumentIndex.DocumentSegment> segments = switch (type) {
            case "docx" -> extractDocx(bytes);
            case "pdf" -> extractPdf(bytes);
            default -> throw new BadRequestException("Unsupported document type: " + fileName);
        };

        return ExtractionResult.builder()
                .fileType(type)
                .segments(segments)
                .build();
    }

    private List<DerivedDocumentIndex.DocumentSegment> extractDocx(byte[] bytes) {
        try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(bytes))) {
            List<DerivedDocumentIndex.DocumentSegment> segments = new ArrayList<>();
            int order = 0;

            for (IBodyElement element : document.getBodyElements()) {
                if (element instanceof XWPFParagraph paragraph) {
                    String text = normalizeText(paragraph.getText());
                    if (text.isBlank()) {
                        continue;
                    }
                    String style = paragraph.getStyle();
                    String kind = style != null && style.toLowerCase().contains("heading")
                            ? "heading"
                            : "paragraph";
                    segments.add(buildSegment(kind, text, order++, null));
                    continue;
                }

                if (element instanceof XWPFTable table) {
                    for (XWPFTableRow row : table.getRows()) {
                        List<String> cells = new ArrayList<>();
                        for (XWPFTableCell cell : row.getTableCells()) {
                            String cellText = normalizeText(cell.getText());
                            if (!cellText.isBlank()) {
                                cells.add(cellText);
                            }
                        }
                        String rowText = String.join(" | ", cells).trim();
                        if (!rowText.isBlank()) {
                            segments.add(buildSegment("table-row", rowText, order++, null));
                        }
                    }
                }
            }

            return segments;
        } catch (Exception e) {
            throw new BadRequestException("Failed to extract DOCX content: " + e.getMessage());
        }
    }

    private List<DerivedDocumentIndex.DocumentSegment> extractPdf(byte[] bytes) {
        try (PDDocument document = Loader.loadPDF(bytes)) {
            List<DerivedDocumentIndex.DocumentSegment> segments = new ArrayList<>();
            PDFTextStripper stripper = new PDFTextStripper();
            int order = 0;

            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                String pageText = normalizeText(stripper.getText(document));
                if (pageText.isBlank()) {
                    continue;
                }

                String[] blocks = BLANK_BLOCK.split(pageText);
                for (String block : blocks) {
                    String text = normalizeText(block);
                    if (text.isBlank()) {
                        continue;
                    }
                    segments.add(buildSegment("text-block", text, order++, page));
                }
            }

            return segments;
        } catch (Exception e) {
            throw new BadRequestException("Failed to extract PDF content: " + e.getMessage());
        }
    }

    private DerivedDocumentIndex.DocumentSegment buildSegment(
            String kind,
            String text,
            int order,
            Integer page
    ) {
        String normalized = normalizeText(text);
        return DerivedDocumentIndex.DocumentSegment.builder()
                .id(UUID.randomUUID().toString())
                .kind(kind)
                .text(normalized)
                .stableHash(stableHash(kind + "\n" + normalized))
                .page(page)
                .orderIndex(order)
                .build();
    }

    private String normalizeText(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace('\u00A0', ' ')
                .replaceAll("\\r\\n?", "\n")
                .replaceAll("[\\t ]+", " ")
                .replaceAll("\\n{3,}", "\n\n")
                .trim();
    }

    private String stableHash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to hash document segment", e);
        }
    }

    private String normalizedExtension(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return "";
        }
        String name = filePath.trim();
        int idx = name.lastIndexOf('.');
        if (idx < 0 || idx + 1 >= name.length()) {
            return "";
        }
        return name.substring(idx + 1).toLowerCase();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtractionResult {
        private String fileType;
        private List<DerivedDocumentIndex.DocumentSegment> segments;
    }
}
