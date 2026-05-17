package strategy;

import model.Report;
import java.util.List;

public interface SearchStrategy {

    List<Report> search(
            List<Report> reports,
            String keyword
    );
}