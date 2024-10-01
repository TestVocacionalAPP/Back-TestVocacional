package GTns_TestV.service;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface ReportService {
    ByteArrayInputStream generarReportePDF(Map<String, Map<String, Integer>> resultadoTest);
}
