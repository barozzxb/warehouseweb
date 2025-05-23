package vn.warehouse.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;
import vn.warehouse.dto.response.TransactionStatisticsResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PdfExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<TransactionStatisticsResponse> stats, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Thống kê giao dịch từ " + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                " đến " + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        document.add(new Paragraph("\n"));

        float[] columnWidths = {2, 2, 2, 2};
        Table table = new Table(columnWidths);
        table.addHeaderCell("Loại giao dịch");
        table.addHeaderCell("Trạng thái");
        table.addHeaderCell("Tổng số giao dịch");
        table.addHeaderCell("Tổng số lượng");

        for (TransactionStatisticsResponse stat : stats) {
            table.addCell(stat.getType().toString());
            table.addCell(stat.getStatus());
            table.addCell(String.valueOf(stat.getTotalTransactions()));
            table.addCell(String.valueOf(stat.getTotalQuantity()));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

    @Override
    public String getFileName() {
        return "transaction-statistics.pdf";
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }
}