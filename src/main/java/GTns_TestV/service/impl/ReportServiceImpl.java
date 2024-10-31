package GTns_TestV.service.impl;

import GTns_TestV.service.ReportService;
import GTns_TestV.infra.repository.HistorialTestRepository;
import GTns_TestV.infra.repository.CarreraRepository;
import GTns_TestV.model.entity.HistorialTest;
import GTns_TestV.model.entity.Carrera;
import GTns_TestV.model.enums.ChasideCategory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final HistorialTestRepository historialTestRepository;
    private final CarreraRepository carreraRepository;

    @Override
    public ByteArrayInputStream generarReportePDF(Map<String, Object> resultadoTest) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out))) {
            Document doc = new Document(pdfDoc);

            // Añadir título
            doc.add(new Paragraph("Resultados del Test")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(18));

            // Agregar mensajes de intereses y carreras
            String mensajeIntereses = (String) resultadoTest.get("mensajeIntereses");
            String mensajeCarreras = (String) resultadoTest.get("mensajeCarreras");

            doc.add(new Paragraph(mensajeIntereses)
                    .setFontSize(12)
                    .setMultipliedLeading(1.5f)); // Ajustar interlineado si es necesario

            doc.add(new Paragraph(mensajeCarreras)
                    .setFontSize(12)
                    .setMultipliedLeading(1.5f));

            // Crear tabla con 3 columnas
            Table table = new Table(3);
            table.addHeaderCell("Tipo de Resultado");
            table.addHeaderCell("Categoría");
            table.addHeaderCell("Suma");

            Map<String, String> nombresCategorias = Map.of(
                    "C", "Área Administrativa",
                    "H", "Área de Humanidades",
                    "A", "Área Artística",
                    "S", "Área de Ciencias de la Salud",
                    "I", "Área de Enseñanzas Técnicas",
                    "D", "Área de Defensa y Seguridad",
                    "E", "Área de Ciencias Experimentales"
            );

            Map<String, Integer> interes = (Map<String, Integer>) resultadoTest.get("Interes");
            for (String categoria : Arrays.asList("C", "H", "A", "S", "I", "D", "E")) {
                if (interes.containsKey(categoria)) {
                    table.addCell("Interés");
                    table.addCell(nombresCategorias.get(categoria));
                    table.addCell(String.valueOf(interes.get(categoria)));
                }
            }

            Map<String, Integer> aptitud = (Map<String, Integer>) resultadoTest.get("Aptitud");
            for (String categoria : Arrays.asList("C", "H", "A", "S", "I", "D", "E")) {
                if (aptitud.containsKey(categoria)) {
                    table.addCell("Aptitud");
                    table.addCell(nombresCategorias.get(categoria));
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
