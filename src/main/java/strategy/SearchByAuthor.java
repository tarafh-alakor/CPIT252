package strategy;

import model.Report;

import java.util.ArrayList;
import java.util.List;

public class SearchByAuthor
        implements SearchStrategy {

    @Override
    public List<Report> search(
            List<Report> reports,
            String keyword
    ) {

        List<Report> result =
                new ArrayList<>();

        for (Report report : reports) {

            if (report.getAuthorName()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                result.add(report);
            }
        }

        return result;
    }
}