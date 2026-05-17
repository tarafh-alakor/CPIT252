package strategy;

import model.Report;
import java.util.ArrayList;
import java.util.List;

// BEHAVIORAL PATTERN: Strategy.
// This strategy searches in all report fields at the same time.
public class SearchAll implements SearchStrategy {

    @Override
    public List<Report> search(List<Report> reports, String keyword) {
        List<Report> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();

        for (Report report : reports) {
            if (report.getText().toLowerCase().contains(lowerKeyword)
                    || report.getAuthorName().toLowerCase().contains(lowerKeyword)
                    || report.getRole().toLowerCase().contains(lowerKeyword)
                    || report.getChildName().toLowerCase().contains(lowerKeyword)) {
                result.add(report);
            }
        }

        return result;
    }
}
