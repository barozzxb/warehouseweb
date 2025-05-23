package vn.warehouse.util;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;
import vn.warehouse.dto.response.TransactionStatisticsResponse;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CsvExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<TransactionStatisticsResponse> stats, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        String[] headers = {"Loại giao dịch", "Trạng thái", "Tổng số giao dịch", "Tổng số lượng"};
        csvWriter.writeNext(headers);

        for (TransactionStatisticsResponse stat : stats) {
            csvWriter.writeNext(new String[]{
                    stat.getType().toString(),
                    stat.getStatus(),
                    String.valueOf(stat.getTotalTransactions()),
                    String.valueOf(stat.getTotalQuantity())
            });
        }

        csvWriter.close();
        return stringWriter.toString().getBytes();
    }

    @Override
    public String getFileName() {
        return "transaction-statistics.csv";
    }

    @Override
    public String getContentType() {
        return "text/csv";
    }
}