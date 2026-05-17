package careconnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Main GUI class for the CareConnect platform.
 *
 * Design goal:
 * - Make the interface fully English to avoid layout issues caused by mixed Arabic/English direction.
 * - Keep the same dashboard feeling as the reference image: purple header, left sidebar,
 *   white rounded cards, soft peach/pink accents, progress ring, team section, and child photo.
 * - Remove all text icons and symbolic icons from the interface.
 *
 * Patterns connected here:
 * - Factory Method: creates the correct User object during login.
 * - Facade: GUI talks to CarePlatformFacade instead of many subsystem classes.
 * - Observer: notification list updates automatically when the system changes.
 */
public class CareConnectApp extends JFrame {

    // =========================
    // Theme colors
    // =========================
    private static final Color PURPLE = new Color(94, 75, 166);
    private static final Color PURPLE_DARK = new Color(72, 56, 136);
    private static final Color PURPLE_LIGHT = new Color(126, 105, 195);
    private static final Color BACKGROUND = new Color(248, 246, 253);
    private static final Color CARD = Color.WHITE;
    private static final Color SOFT_PANEL = new Color(252, 250, 255);
    private static final Color PEACH = new Color(255, 232, 226);
    private static final Color PINK = new Color(236, 118, 153);
    private static final Color TEXT = new Color(43, 39, 72);
    private static final Color MUTED = new Color(130, 126, 154);
    private static final Color BLUE = new Color(72, 169, 215);
    private static final Color GREEN = new Color(83, 181, 122);
    private static final Color ORANGE = new Color(237, 164, 67);

    // =========================
    // Fonts
    // =========================
    private static final Font FONT = new Font("Arial", Font.PLAIN, 13);
    private static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 13);
    private static final Font TITLE = new Font("Arial", Font.BOLD, 23);
    private static final Font SMALL = new Font("Arial", Font.PLAIN, 11);

    // =========================
    // Pattern objects and UI state
    // =========================
    private User currentUser;
    private CarePlatformFacade facade;
    private DashboardNotificationPanel notificationPanel;
    private JPanel contentPanel;

    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private String activePage = "Home";

    public CareConnectApp() {
        setupPatterns();
        showLoginDialog();
        buildWindow();
    }

    /*
     * Creates the main objects used by the three design patterns.
     * The GUI stays clean because these objects handle user creation,
     * subsystem operations, and notifications.
     */
    private void setupPatterns() {
        CareRepository repository = new CareRepository();
        NotificationCenter notificationCenter = new NotificationCenter();

        // Observer Pattern:
        // The notification panel subscribes to the notification center.
        // Any new report, appointment, or child profile will update it automatically.
        notificationPanel = new DashboardNotificationPanel();
        notificationCenter.attach(notificationPanel);

        // Facade Pattern:
        // The GUI uses one simplified interface instead of calling repository
        // and notification classes directly.
        facade = new CarePlatformFacade(repository, notificationCenter);
    }

    /*
     * Login dialog.
     * Factory Method Pattern is used here because the selected role determines
     * which concrete User class should be created.
     */
    private void showLoginDialog() {
        JTextField nameField = new JTextField("Tarafh");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{
                "Parent", "Doctor", "School Teacher", "Special Teacher", "Speech Therapist"
        });

        JPanel panel = new JPanel(new GridLayout(4, 1, 8, 8));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.add(new JLabel("Enter your name:"));
        panel.add(nameField);
        panel.add(new JLabel("Select your role:"));
        panel.add(roleBox);

        JOptionPane.showMessageDialog(null, panel, "CareConnect Login", JOptionPane.PLAIN_MESSAGE);

        UserFactory factory = new UserFactory();
        currentUser = factory.createUser(roleBox.getSelectedItem().toString(), nameField.getText().trim());
    }

    /*
     * Builds the main application frame after login.
     */
    private void buildWindow() {
        setTitle("CareConnect - Inclusive Care Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 760));
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        add(contentPanel, BorderLayout.CENTER);

        showHome();

        setVisible(true);
    }

    /*
     * Creates the purple top header.
     * The layout is fully English and left-to-right.
     */
    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(PURPLE, PURPLE_DARK);
        header.setPreferredSize(new Dimension(0, 78));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 22, 12, 22));

        JLabel logo = new JLabel("<html><b>CareConnect</b><br><span style='font-size:10px'>Inclusive Care Coordination Platform</span></html>");
        logo.setForeground(Color.WHITE);
        logo.setFont(FONT_BOLD);

        JLabel userLabel = new JLabel("Welcome, " + currentUser.getName() + "  |  " + currentUser.getRole());
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(FONT_BOLD);

        header.add(logo, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);
        return header;
    }

    /*
     * Creates the left navigation menu.
     * Icons were removed as requested; only clean English text remains.
     */
    private JPanel createSidebar() {
        RoundedPanel sidebar = new RoundedPanel(0, Color.WHITE, true);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(new EmptyBorder(20, 16, 20, 16));

        JPanel menu = new JPanel(new GridLayout(9, 1, 0, 8));
        menu.setOpaque(false);

        addNav(menu, "Home", this::showHome);
        addNav(menu, "Child Profile", this::showChildProfile);
        addNav(menu, "Reports", this::showReports);
        addNav(menu, "Appointments", this::showAppointments);
        addNav(menu, "Communication", this::showCommunication);
        addNav(menu, "Notifications", this::showNotifications);
        addNav(menu, "Care Team", this::showCareTeam);
        addNav(menu, "Search", this::showSearch);
        addNav(menu, "Settings", this::showSettings);

        JPanel account = new JPanel(new BorderLayout(10, 0));
        account.setOpaque(false);
        account.setBorder(new EmptyBorder(16, 6, 6, 6));
        account.add(new Avatar(currentUser.getName().substring(0, 1).toUpperCase(), 42, new Color(235, 231, 255), PURPLE), BorderLayout.WEST);
        account.add(new JLabel("<html><b>" + safe(currentUser.getName()) + "</b><br><span style='color:#888888'>" + safe(currentUser.getRole()) + "</span></html>"), BorderLayout.CENTER);

        sidebar.add(menu, BorderLayout.NORTH);
        sidebar.add(account, BorderLayout.SOUTH);
        return sidebar;
    }

    /*
     * Adds one navigation button to the sidebar.
     */
    private void addNav(JPanel menu, String label, Runnable action) {
        JButton button = new JButton(label);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 14, 12, 14));
        button.setFont(FONT_BOLD);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> {
            activePage = label;
            updateNavStyle();
            action.run();
        });
        navButtons.put(label, button);
        menu.add(button);
    }

    /*
     * Applies the selected style to the active navigation page.
     */
    private void updateNavStyle() {
        for (Map.Entry<String, JButton> entry : navButtons.entrySet()) {
            JButton button = entry.getValue();
            if (entry.getKey().equals(activePage)) {
                button.setBackground(PURPLE);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(MUTED);
            }
        }
    }

    /*
     * Clears the content area before switching pages.
     */
    private void resetContent() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /*
     * Reusable section title.
     */
    private JLabel sectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(TEXT);
        return label;
    }

    /*
     * Reusable rounded card container.
     */
    private RoundedPanel card(String title) {
        RoundedPanel card = new RoundedPanel(24, CARD, true);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(18, 18, 20, 18));
        card.add(sectionTitle(title), BorderLayout.NORTH);
        return card;
    }

    /*
     * Home dashboard page.
     */
    private void showHome() {
        resetContent();
        activePage = "Home";
        updateNavStyle();

        JPanel page = new JPanel(new BorderLayout(16, 16));
        page.setBackground(BACKGROUND);

        page.add(createHeroCard(), BorderLayout.NORTH);

        JPanel middle = new JPanel(new GridLayout(1, 3, 16, 16));
        middle.setBackground(BACKGROUND);
        middle.add(createAppointmentsCard());
        middle.add(createReportsCard());
        middle.add(createProgressCard());

        page.add(middle, BorderLayout.CENTER);
        page.add(createTimelineCard(), BorderLayout.SOUTH);

        contentPanel.add(page, BorderLayout.CENTER);
    }

    /*
     * Top hero card with child profile and care team members.
     * The child image is custom drawn in Java, so there is no external file needed.
     */
    private JPanel createHeroCard() {
        RoundedPanel hero = new RoundedPanel(28, Color.WHITE, true);
        hero.setPreferredSize(new Dimension(0, 150));
        hero.setLayout(new BorderLayout(20, 0));
        hero.setBorder(new EmptyBorder(18, 22, 18, 22));

        JPanel child = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        child.setOpaque(false);
        child.add(new ChildPhoto());
        child.add(new JLabel("<html>"
                + "<h2 style='margin:0'>Jana Ahmad</h2>"
                + "<p style='margin:4px 0'>6 years old</p>"
                + "<p style='margin:4px 0'>Hearing impairment</p>"
                + "<p style='color:#6b55a8;margin:4px 0'>Profile details</p>"
                + "</html>"));

        JPanel team = new JPanel(new GridLayout(1, 4, 14, 0));
        team.setOpaque(false);

        String[][] members = {
                {"S", "Dr. Sarah", "Doctor"},
                {"A", "Amani", "Speech Therapist"},
                {"R", "Reem", "School Teacher"},
                {"N", "Noura", "Special Teacher"}
        };

        for (String[] member : members) {
            team.add(memberBox(member[0], member[1], member[2]));
        }

        JPanel teamWrapper = new JPanel(new BorderLayout(0, 8));
        teamWrapper.setOpaque(false);
        teamWrapper.add(sectionTitle("Care Team"), BorderLayout.NORTH);
        teamWrapper.add(team, BorderLayout.CENTER);

        hero.add(child, BorderLayout.WEST);
        hero.add(teamWrapper, BorderLayout.EAST);
        return hero;
    }

    /*
     * Small care team card used inside the hero card.
     */
    private JPanel memberBox(String letter, String name, String role) {
        JPanel box = new JPanel(new BorderLayout(0, 6));
        box.setOpaque(false);

        JLabel text = new JLabel("<html><center><b>" + safe(name) + "</b><br>"
                + "<span style='color:#888888'>" + safe(role) + "</span></center></html>", SwingConstants.CENTER);
        text.setFont(FONT);

        box.add(new Avatar(letter, 46, new Color(238, 233, 255), PURPLE), BorderLayout.NORTH);
        box.add(text, BorderLayout.CENTER);
        return box;
    }

    /*
     * Upcoming appointments card.
     */
    private JPanel createAppointmentsCard() {
        RoundedPanel c = card("Upcoming Appointments");

        JPanel list = new JPanel(new GridLayout(3, 1, 0, 10));
        list.setOpaque(false);

        List<Appointment> appointments = facade.getAppointments();
        for (int i = 0; i < Math.min(3, appointments.size()); i++) {
            list.add(rowCard(appointments.get(i).toString(), PURPLE));
        }

        JButton add = actionButton("Add Appointment");
        add.addActionListener(e -> addAppointment());

        c.add(list, BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        return c;
    }

    /*
     * Recent reports card.
     */
    private JPanel createReportsCard() {
        RoundedPanel c = card("Latest Reports");

        JPanel list = new JPanel(new GridLayout(3, 1, 0, 10));
        list.setOpaque(false);

        List<Report> reports = facade.getReports();
        for (int i = 0; i < Math.min(3, reports.size()); i++) {
            Report report = reports.get(i);
            list.add(rowCard(report.getRole() + " - " + report.getText(), BLUE));
        }

        JButton add = actionButton("Add Report");
        add.addActionListener(e -> addReport());

        c.add(list, BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        return c;
    }

    /*
     * Progress summary card.
     */
    private JPanel createProgressCard() {
        RoundedPanel c = card("Personal Summary");

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(new ProgressRing(75), BorderLayout.CENTER);

        JPanel goals = new JPanel(new GridLayout(3, 1, 0, 7));
        goals.setOpaque(false);
        goals.add(goal("Improve word pronunciation", true));
        goals.add(goal("Focus for 10 minutes", true));
        goals.add(goal("Participate in class", false));

        center.add(goals, BorderLayout.SOUTH);
        c.add(center, BorderLayout.CENTER);
        return c;
    }

    /*
     * One goal row.
     * No icons are used; the status is written in English.
     */
    private JLabel goal(String text, boolean done) {
        JLabel label = new JLabel((done ? "Completed: " : "Pending: ") + text);
        label.setOpaque(true);
        label.setBackground(SOFT_PANEL);
        label.setBorder(new EmptyBorder(8, 10, 8, 10));
        label.setForeground(done ? GREEN : MUTED);
        label.setFont(FONT_BOLD);
        return label;
    }

    /*
     * Timeline card at the bottom of the dashboard.
     */
    private JPanel createTimelineCard() {
        RoundedPanel c = card("Timeline");
        c.setPreferredSize(new Dimension(0, 160));

        JPanel line = new JPanel(new GridLayout(1, 5, 12, 0));
        line.setOpaque(false);

        line.add(timelineItem("Speech session report", "2 hours ago", BLUE));
        line.add(timelineItem("School note added", "1 day ago", GREEN));
        line.add(timelineItem("New appointment created", "3 days ago", ORANGE));
        line.add(timelineItem("Medical follow-up report", "1 week ago", PINK));
        line.add(timelineItem("Child profile updated", "1 week ago", PURPLE));

        c.add(line, BorderLayout.CENTER);
        return c;
    }

    /*
     * Reusable row card.
     * The colored vertical bar replaces icons.
     */
    private JPanel rowCard(String text, Color color) {
        RoundedPanel row = new RoundedPanel(18, SOFT_PANEL, false);
        row.setLayout(new BorderLayout(12, 0));
        row.setBorder(new EmptyBorder(10, 12, 10, 12));

        JPanel bar = new JPanel();
        bar.setBackground(color);
        bar.setPreferredSize(new Dimension(5, 1));

        JLabel label = new JLabel("<html><div style='width:245px'>" + safe(text) + "</div></html>");
        label.setFont(FONT);
        label.setForeground(TEXT);

        row.add(bar, BorderLayout.WEST);
        row.add(label, BorderLayout.CENTER);
        return row;
    }

    /*
     * One timeline item.
     * A small colored circle is drawn by a custom component instead of using icon text.
     */
    private JPanel timelineItem(String title, String date, Color color) {
        RoundedPanel item = new RoundedPanel(18, Color.WHITE, false);
        item.setLayout(new BorderLayout(0, 8));
        item.setBorder(new EmptyBorder(10, 12, 10, 12));

        item.add(new StatusDot(color), BorderLayout.NORTH);
        item.add(new JLabel("<html><center><b>" + safe(title) + "</b><br>"
                + "<span style='color:#888888'>" + safe(date) + "</span></center></html>", SwingConstants.CENTER), BorderLayout.CENTER);
        return item;
    }

    private void showChildProfile() {
        resetContent();
        activePage = "Child Profile";
        updateNavStyle();

        RoundedPanel c = card("Child Profile");

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 10));
        list.setOpaque(false);

        for (ChildProfile child : facade.getChildren()) {
            list.add(rowCard(child.toString(), PURPLE));
        }

        JButton add = actionButton("Add Child");
        add.addActionListener(e -> addChild());

        c.add(new JScrollPane(list), BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showReports() {
        resetContent();
        activePage = "Reports";
        updateNavStyle();

        RoundedPanel c = card("Reports");

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 10));
        list.setOpaque(false);

        for (Report report : facade.getReports()) {
            list.add(rowCard(report.toString(), BLUE));
        }

        JButton add = actionButton("Add Report");
        add.addActionListener(e -> addReport());

        c.add(new JScrollPane(list), BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showAppointments() {
        resetContent();
        activePage = "Appointments";
        updateNavStyle();

        RoundedPanel c = card("Appointments");

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 10));
        list.setOpaque(false);

        for (Appointment appointment : facade.getAppointments()) {
            list.add(rowCard(appointment.toString(), ORANGE));
        }

        JButton add = actionButton("Add Appointment");
        add.addActionListener(e -> addAppointment());

        c.add(new JScrollPane(list), BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showCommunication() {
        resetContent();
        activePage = "Communication";
        updateNavStyle();

        RoundedPanel c = card("Communication");

        JPanel chat = new JPanel(new GridLayout(5, 1, 0, 10));
        chat.setOpaque(false);

        chat.add(rowCard("Dr. Sarah: Please review the latest speech session report.", PURPLE));
        chat.add(rowCard("Amani: There is clear improvement in pronunciation.", BLUE));
        chat.add(rowCard("Parent: The home exercises were completed today.", PINK));
        chat.add(rowCard("Reem: Classroom participation improved this week.", GREEN));
        chat.add(rowCard(currentUser.getRole() + ": A new message can be added here.", ORANGE));

        c.add(chat, BorderLayout.CENTER);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showNotifications() {
        resetContent();
        activePage = "Notifications";
        updateNavStyle();

        RoundedPanel c = card("Notifications");
        JList<String> list = notificationPanel.getNotificationList();

        list.setFont(FONT);
        list.setBorder(new EmptyBorder(12, 12, 12, 12));
        c.add(new JScrollPane(list), BorderLayout.CENTER);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showCareTeam() {
        resetContent();
        activePage = "Care Team";
        updateNavStyle();

        RoundedPanel c = card("Care Team");

        JPanel team = new JPanel(new GridLayout(1, 5, 14, 14));
        team.setOpaque(false);
        team.add(bigMember("S", "Dr. Sarah", "ENT Doctor", PURPLE));
        team.add(bigMember("A", "Amani", "Speech Therapist", BLUE));
        team.add(bigMember("R", "Reem", "School Teacher", GREEN));
        team.add(bigMember("N", "Noura", "Special Teacher", PINK));
        team.add(bigMember("P", "Parent", "Guardian", ORANGE));

        c.add(team, BorderLayout.NORTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private JPanel bigMember(String letter, String name, String role, Color color) {
        RoundedPanel box = new RoundedPanel(22, SOFT_PANEL, false);
        box.setLayout(new BorderLayout(0, 12));
        box.setBorder(new EmptyBorder(22, 12, 22, 12));
        box.add(new Avatar(letter, 62, new Color(240, 235, 255), color), BorderLayout.NORTH);
        box.add(new JLabel("<html><center><h3>" + safe(name) + "</h3><p>" + safe(role) + "</p></center></html>", SwingConstants.CENTER), BorderLayout.CENTER);
        return box;
    }

    private void showSearch() {
        resetContent();
        activePage = "Search";
        updateNavStyle();

        RoundedPanel c = card("Search Reports");

        JTextField field = new JTextField();
        field.setBorder(new EmptyBorder(10, 12, 10, 12));

        JButton search = actionButton("Search");

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        top.add(field, BorderLayout.CENTER);
        top.add(search, BorderLayout.EAST);

        JPanel results = new JPanel(new GridLayout(0, 1, 0, 10));
        results.setOpaque(false);

        search.addActionListener(e -> {
            results.removeAll();

            List<Report> found = facade.searchReports(field.getText());
            if (found.isEmpty()) {
                results.add(rowCard("No matching results were found.", PINK));
            } else {
                for (Report report : found) {
                    results.add(rowCard(report.toString(), BLUE));
                }
            }

            results.revalidate();
            results.repaint();
        });

        c.add(top, BorderLayout.NORTH);
        c.add(new JScrollPane(results), BorderLayout.CENTER);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showSettings() {
        resetContent();
        activePage = "Settings";
        updateNavStyle();

        RoundedPanel c = card("Settings");
        c.add(rowCard("Current user: " + currentUser.getName()
                + "\nRole: " + currentUser.getRole()
                + "\nTheme: Purple + Peach Pink"
                + "\nPlatform: Java Swing Desktop Application"
                + "\nPatterns: Factory Method, Facade, Observer", PURPLE), BorderLayout.NORTH);

        contentPanel.add(c, BorderLayout.CENTER);
    }

    /*
     * Adds a child profile through the Facade.
     */
    private void addChild() {
        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JTextField condition = new JTextField();
        JTextField guardian = new JTextField();
        JTextField progress = new JTextField("75");

        JPanel panel = formPanel(
                new String[]{"Child name", "Age", "Condition", "Guardian", "Progress percentage"},
                new JComponent[]{name, age, condition, guardian, progress}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Child", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            facade.addChild(
                    name.getText(),
                    Integer.parseInt(age.getText()),
                    condition.getText(),
                    guardian.getText(),
                    Integer.parseInt(progress.getText())
            );
            showChildProfile();
        }
    }

    /*
     * Adds a report. The current user creates the report object,
     * so the report automatically stores the correct role and author name.
     */
    private void addReport() {
        JTextField childName = new JTextField("Jana Ahmad");
        JTextArea text = new JTextArea(6, 24);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Child name", "Report text"},
                new JComponent[]{childName, new JScrollPane(text)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Report", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            facade.addReport(currentUser, childName.getText(), text.getText());
            showReports();
        }
    }

    /*
     * Adds a future appointment.
     */
    private void addAppointment() {
        JTextField title = new JTextField();
        JTextField specialist = new JTextField();
        JTextField days = new JTextField("1");

        JPanel panel = formPanel(
                new String[]{"Appointment title", "Specialist", "After how many days?"},
                new JComponent[]{title, specialist, days}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            facade.addAppointment(
                    title.getText(),
                    specialist.getText(),
                    LocalDateTime.now().plusDays(Integer.parseInt(days.getText()))
            );
            showAppointments();
        }
    }

    /*
     * Creates a simple vertical form.
     */
    private JPanel formPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length * 2, 1, 6, 6));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i] + ":"));
            panel.add(fields[i]);
        }

        return panel;
    }

    /*
     * Creates a consistent rounded action button.
     */
    private JButton actionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(PURPLE);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BOLD);
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /*
     * Protects text that will be placed inside HTML labels.
     */
    private String safe(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CareConnectApp::new);
    }

    /*
     * Purple gradient panel used for the top header.
     */
    private static class GradientPanel extends JPanel {
        private final Color start;
        private final Color end;

        GradientPanel(Color start, Color end) {
            this.start = start;
            this.end = end;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setPaint(new GradientPaint(0, 0, start, getWidth(), getHeight(), end));
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.dispose();
            super.paintComponent(g);
        }
    }

    /*
     * Circular initials avatar.
     * It is used as a clean visual element, not as an icon.
     */
    private static class Avatar extends JComponent {
        private final String text;
        private final int size;
        private final Color background;
        private final Color foreground;

        Avatar(String text, int size, Color background, Color foreground) {
            this.text = text;
            this.size = size;
            this.background = background;
            this.foreground = foreground;
            setPreferredSize(new Dimension(size, size));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(background);
            graphics.fillOval(0, 0, size - 1, size - 1);

            graphics.setColor(foreground);
            graphics.setFont(new Font("Arial", Font.BOLD, size / 2));

            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(text,
                    (size - metrics.stringWidth(text)) / 2,
                    (size + metrics.getAscent()) / 2 - 3);

            graphics.dispose();
        }
    }

    /*
     * Small colored dot used in the timeline.
     */
    private static class StatusDot extends JComponent {
        private final Color color;

        StatusDot(Color color) {
            this.color = color;
            setPreferredSize(new Dimension(18, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(color);
            graphics.fillOval(5, 5, 8, 8);
            graphics.dispose();
        }
    }

    /*
     * Illustrated child photo.
     * This keeps the child picture from the previous version while avoiding external image files.
     */
    private static class ChildPhoto extends JComponent {
        ChildPhoto() {
            setPreferredSize(new Dimension(92, 92));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Rounded image background.
            graphics.setColor(new Color(255, 230, 226));
            graphics.fillRoundRect(0, 0, 90, 90, 24, 24);

            // Hair.
            graphics.setColor(new Color(115, 78, 52));
            graphics.fillOval(25, 12, 40, 44);

            // Face.
            graphics.setColor(new Color(245, 196, 158));
            graphics.fillOval(29, 22, 32, 32);

            // Shirt.
            graphics.setColor(new Color(255, 156, 178));
            graphics.fillRoundRect(22, 55, 48, 28, 18, 18);

            // Eyes and smile.
            graphics.setColor(new Color(55, 45, 65));
            graphics.fillOval(38, 35, 3, 3);
            graphics.fillOval(51, 35, 3, 3);
            graphics.drawArc(40, 39, 14, 8, 190, 160);

            graphics.dispose();
        }
    }

    /*
     * Circular progress component shown in the personal summary card.
     */
    private static class ProgressRing extends JComponent {
        private final int value;

        ProgressRing(int value) {
            this.value = value;
            setPreferredSize(new Dimension(145, 145));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 30;
            int x = (getWidth() - size) / 2;
            int y = 8;

            graphics.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Background circle.
            graphics.setColor(new Color(231, 232, 245));
            graphics.drawOval(x, y, size, size);

            // Progress arc.
            graphics.setColor(new Color(76, 176, 224));
            graphics.drawArc(x, y, size, size, 90, -(int) (360 * (value / 100.0)));

            // Percentage number.
            graphics.setFont(new Font("Arial", Font.BOLD, 24));
            graphics.setColor(new Color(45, 45, 65));
            String label = value + "%";
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(label, (getWidth() - metrics.stringWidth(label)) / 2, y + size / 2 + 8);

            // Subtitle.
            graphics.setFont(SMALL);
            String subtitle = "Goal Progress";
            metrics = graphics.getFontMetrics();
            graphics.setColor(new Color(125, 125, 145));
            graphics.drawString(subtitle, (getWidth() - metrics.stringWidth(subtitle)) / 2, y + size + 25);

            graphics.dispose();
        }
    }
}
