package GTns_TestV.service.impl;

import GTns_TestV.service.ReportService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public ByteArrayInputStream generarReportePDF(Map<String, Map<String, Integer>> resultadoTest) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out))) {
            Document doc = new Document(pdfDoc);

            // Añadir título
            doc.add(new Paragraph("Resultados del Test").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(18));

            // Crear tabla con 3 columnas
            Table table = new Table(3);
            table.addHeaderCell("Tipo de Resultado");
            table.addHeaderCell("Categoría");
            table.addHeaderCell("Suma");

            // Definir el orden de las categorías CHASIDE
            List<String> chasideOrder = Arrays.asList("C", "H", "A", "S", "I", "D", "E");

            // Añadir datos de Intereses en el orden CHASIDE
            Map<String, Integer> interes = resultadoTest.get("Interes");
            for (String categoria : chasideOrder) {
                if (interes.containsKey(categoria)) {
                    table.addCell("Interes");
                    table.addCell(categoria);
                    table.addCell(String.valueOf(interes.get(categoria)));
                }
            }

            // Añadir datos de Aptitudes en el orden CHASIDE
            Map<String, Integer> aptitud = resultadoTest.get("Aptitud");
            for (String categoria : chasideOrder) {
                if (aptitud.containsKey(categoria)) {
                    table.addCell("Aptitud");
                    table.addCell(categoria);
                    table.addCell(String.valueOf(aptitud.get(categoria)));
                }
            }

            // Agregar la tabla al documento
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
