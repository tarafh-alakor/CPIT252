package tests;

import model.Report;

import strategy.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests the Strategy Pattern implementation.
 */
public class StrategyPatternTest {

    /*
     * SearchStrategy should be an interface.
     */
    @Test
    public void testStrategyIsInterface() {

        assertTrue(
                Modifier.isInterface(
                        SearchStrategy.class
                                .getModifiers()
                )
        );
    }

    /*
     * SearchContext should exist.
     */
    @Test
    public void testSearchContextExists() {

        SearchContext context
                = new SearchContext(
                        new SearchAll()
                );

        assertNotNull(context);
    }

    /*
     * SearchByAuthor should find reports correctly.
     */
    @Test
    public void testSearchByAuthor() {

        List<Report> reports
                = createSampleReports();

        SearchStrategy strategy
                = new SearchByAuthor();

        List<Report> results
                = strategy.search(
                        reports,
                        "Sarah"
                );

        assertEquals(
                1,
                results.size()
        );

        assertEquals(
                "Sarah",
                results.get(0).getAuthorName()
        );
    }

    /*
     * SearchByType should find reports correctly.
     */
    @Test
    public void testSearchByType() {

        List<Report> reports
                = createSampleReports();

        SearchStrategy strategy
                = new SearchByType();

        List<Report> results
                = strategy.search(
                        reports,
                        "Doctor"
                );

        assertEquals(
                1,
                results.size()
        );

        assertEquals(
                "Doctor",
                results.get(0).getRole()
        );
    }

    /*
     * SearchByChildName should find reports correctly.
     */
    @Test
    public void testSearchByChildName() {

        List<Report> reports
                = createSampleReports();

        SearchStrategy strategy
                = new SearchByChildName();

        List<Report> results
                = strategy.search(
                        reports,
                        "Jana"
                );

        assertEquals(
                1,
                results.size()
        );

        assertEquals(
                "Jana",
                results.get(0).getChildName()
        );
    }

    /*
     * SearchByContent should search inside report text.
     */
    @Test
    public void testSearchByContent() {

        List<Report> reports
                = createSampleReports();

        SearchStrategy strategy
                = new SearchByContent();

        List<Report> results
                = strategy.search(
                        reports,
                        "speech"
                );

        assertEquals(
                2,
                results.size()
        );

        assertTrue(
                results.get(0)
                        .getText()
                        .toLowerCase()
                        .contains("speech")
        );
    }

    /*
     * SearchAll should search all report fields.
     */
    @Test
    public void testSearchAll() {

        List<Report> reports
                = createSampleReports();

        SearchStrategy strategy
                = new SearchAll();

        List<Report> results
                = strategy.search(
                        reports,
                        "Reem"
                );

        assertEquals(
                1,
                results.size()
        );

        assertEquals(
                "Reem",
                results.get(0).getAuthorName()
        );
    }

    /*
     * SearchContext should switch strategies dynamically.
     */
    @Test
    public void testDynamicStrategySwitching() {

        List<Report> reports
                = createSampleReports();

        SearchContext context
                = new SearchContext(
                        new SearchByAuthor()
                );

        List<Report> authorResults
                = context.executeSearch(
                        reports,
                        "Sarah"
                );

        assertEquals(
                1,
                authorResults.size()
        );

        context.setStrategy(
                new SearchByType()
        );

        List<Report> typeResults
                = context.executeSearch(
                        reports,
                        "Teacher"
                );

        assertEquals(
                1,
                typeResults.size()
        );
    }

    /*
     * Helper method to generate sample reports.
     */
    private List<Report> createSampleReports() {

        List<Report> reports
                = new ArrayList<>();

        reports.add(
                new Report(
                        "Jana",
                        "Doctor",
                        "Sarah",
                        "Speech improvement observed."
                )
        );

        reports.add(
                new Report(
                        "Ali",
                        "Teacher",
                        "Reem",
                        "Participation improved in class."
                )
        );

        reports.add(
                new Report(
                        "Maha",
                        "Speech Therapist",
                        "Amani",
                        "Speech session completed successfully."
                )
        );

        return reports;
    }
}
