package strategy;

import model.Report;
import java.util.List;

// BEHAVIORAL PATTERN: Strategy Context.
// It holds the selected search strategy and executes it without exposing
// the concrete search class to the GUI.
public class SearchContext {
    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Report> executeSearch(List<Report> reports, String keyword) {
        return strategy.search(reports, keyword);
    }
}
