package GTns_TestV.service.impl;

import GTns_TestV.service.ReportService;
import GTns_TestV.infra.repository.HistorialTestRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final HistorialTestRepository historialTestRepository;

    @Override
    public ByteArrayInputStream generarReportePDF(Map<String, Object> resultadoTest) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out))) {
            Document doc = new Document(pdfDoc);

            // Añadir título principal
            doc.add(new Paragraph("Reporte de Resultados de Test")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(20)
                    .setMarginBottom(15)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));

            // Añadir subtítulo
            doc.add(new Paragraph("Tus Resultados:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(16)
                    .setMarginBottom(10)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT));

            // Verificar y añadir mensajes de intereses y carreras
            String mensajeIntereses = (String) resultadoTest.getOrDefault("mensajeIntereses", "No se encontraron datos de intereses.");
            String mensajeCarreras = (String) resultadoTest.getOrDefault("mensajeCarreras", "No se encontraron datos de carreras.");

            // Añadir sección de intereses
            doc.add(new Paragraph("Intereses:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(14)
                    .setMarginTop(5));
            doc.add(new Paragraph(mensajeIntereses)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12)
                    .setMultipliedLeading(1.5f));

            // Añadir sección de carreras
            doc.add(new Paragraph("Carreras Relacionadas:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(14)
                    .setMarginTop(10));
            doc.add(new Paragraph(mensajeCarreras)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12)
                    .setMultipliedLeading(1.5f));

            // Añadir un pie de página o nota final
            doc.add(new Paragraph("Este informe proporciona un resumen de tus intereses y las carreras relacionadas basadas en tus respuestas.")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(10)
                    .setMarginTop(20)
                    .setItalic());

            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
