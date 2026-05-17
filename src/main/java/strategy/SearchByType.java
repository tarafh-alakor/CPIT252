package strategy;

import model.Report;

import java.util.ArrayList;
import java.util.List;

public class SearchByType
        implements SearchStrategy {

    @Override
    public List<Report> search(List<Report> reports, String keyword) {
        List<Report> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();
        for (Report report : reports) {

            if (report.getRole()
                    .toLowerCase()
                    .contains(lowerKeyword)) {

                result.add(report);
            }
        }
        return result;
    }
}
