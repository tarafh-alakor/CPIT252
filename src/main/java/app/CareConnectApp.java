package app;

import facade.CarePlatformFacade;
import factory.UserFactory;
import model.*;
import observer.NotificationCenter;
import repository.CareRepository;

import ui.*;
import ui.components.*;
import ui.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.io.File;
import strategy.SearchAll;
import strategy.SearchByAuthor;
import strategy.SearchByChildName;
import strategy.SearchByContent;
import strategy.SearchByType;
import strategy.SearchContext;
import strategy.SearchStrategy;

/*
 * Main application GUI class for the Weam Inclusive Care Platform.
 
 * This class is responsible for:
 * - building and managing the main user interface
 * - handling page navigation and dashboard rendering
 * - connecting the GUI with backend system operations
 * - coordinating user interactions across the platform
 
 * DESIGN PATTERNS USED:
 *
 * 1. Factory Method Pattern
 *    Used during login to dynamically create the correct User object.
 
 * 2. Facade Pattern
 *    The GUI communicates with backend operations through
 *    CarePlatformFacade instead of accessing subsystems directly.
 
 * 3. Observer Pattern
 *    Notification panels automatically update whenever
 *    the NotificationCenter sends new system updates.
 
 * 4. Strategy Pattern
 *    Different report search algorithms are selected dynamically
 *    based on the user's selected filter option.*/
public class CareConnectApp extends JFrame {

    // =========================
    // Pattern objects and UI state
    // =========================
    private User currentUser;
    private CarePlatformFacade facade;
    private DashboardNotificationPanel notificationPanel;
    private JPanel contentPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;

    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private String activePage = "Home";

    //Initializes the application window,loads the visual theme, prepares system patterns,and launches the login workflow.
    public CareConnectApp() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        setupPatterns();
        showLoginDialog();
        buildWindow();
    }

    /*
     * Initializes the main backend pattern objects used by the system, This method connects:
    * - the repository layer
    * - notification system
    * - facade layer
    * - observer components
    * The GUI remains loosely coupled because all subsystem, communication is centralized through the facade.*/
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
    * Displays the login and registration workflow for the mother account.Factory Method Pattern is used to create
    * the appropriate User object after authentication. The role is currently fixed as Parent, to match the system's mother dashboard design.*/
    private void showLoginDialog() {
        // Mother-only access. The user can login or create a simple new account.
        String[] options = {"Login", "Register"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome to Weam. Please login or create a mother account.",
                "Weam Mother Access",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        MotherProfile mother = facade.getMotherProfile();

        if (choice == 1) {
            JTextField name = new JTextField(mother.getName());
            JTextField email = new JTextField(mother.getEmail());
            JPasswordField password = new JPasswordField();
            JTextField phone = new JTextField(mother.getPhone());

            JPanel panel = formPanel(
                    new String[]{"Full name", "Email", "Password", "Phone number"},
                    new JComponent[]{name, email, password, phone}
            );

            int result = JOptionPane.showConfirmDialog(null, panel, "Register Mother Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    if (new String(password.getPassword()).trim().isEmpty()) {
                        throw new IllegalArgumentException("Password is required.");
                    }
                    facade.updateMotherProfile(name.getText(), phone.getText(), email.getText(), mother.getInformation());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else {
            JTextField email = new JTextField(mother.getEmail());
            JPasswordField password = new JPasswordField();
            JPanel panel = formPanel(new String[]{"Email", "Password"}, new JComponent[]{email, password});
            int result = JOptionPane.showConfirmDialog(null, panel, "Mother Login", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    if (email.getText().trim().isEmpty() || new String(password.getPassword()).trim().isEmpty()) {
                        throw new IllegalArgumentException("Email and password are required.");
                    }
                    facade.updateMotherProfile(mother.getName(), mother.getPhone(), email.getText(), mother.getInformation());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }

        UserFactory factory = UserFactory.forRole("Parent");
        currentUser = factory.createUser(facade.getMotherProfile().getName());
    }

    /*
    * Builds the main application window after login. This method creates:
    * - the top header
    * - sidebar navigation
    * - dynamic content area
    * The dashboard layout is initialized here.*/
    private void buildWindow() {
        setTitle("Weam - Inclusive Care Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 820));
        setSize(1440, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.BACKGROUND);

        headerPanel = createHeader();
        sidebarPanel = createSidebar();
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIConstants.BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(
                new HomePanel(facade),
                BorderLayout.CENTER
        );

        setVisible(true);
    }

    /*
     * Creates the purple top header.
     */
    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(UIConstants.PURPLE, UIConstants.PURPLE_DARK);
        header.setPreferredSize(new Dimension(0, 78));
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 22, 12, 22));

        JLabel logo = new JLabel("<html><b>Weam</b><br><span style='font-size:10px'>Inclusive Care Coordination Platform</span></html>");
        logo.setForeground(Color.WHITE);
        logo.setFont(UIConstants.FONT_BOLD);

        JLabel userLabel = new JLabel("Welcome, " + facade.getMotherProfile().getName() + "  |  Mother Dashboard");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(UIConstants.FONT_BOLD);

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

        JPanel menu = new JPanel(new GridLayout(7, 1, 0, 12));
        menu.setOpaque(false);

        addNav(menu, "Home", () -> {
            resetContent();
            activePage = "Home";
            updateNavStyle();

            contentPanel.add(
                    new HomePanel(facade),
                    BorderLayout.CENTER
            );

            contentPanel.revalidate();
            contentPanel.repaint();
        });
        addNav(menu, "Profile", this::showProfile);
        addNav(menu, "Care Team", this::showCareTeam);
        addNav(menu, "Reports", this::showReports);
        addNav(menu, "Notifications", this::showNotifications);
        addNav(menu, "Communication", this::showCommunication);

        JPanel account = new JPanel(new BorderLayout(10, 0));
        account.setOpaque(false);
        account.setBorder(new EmptyBorder(16, 6, 6, 6));
        account.add(new Avatar(facade.getMotherProfile().getName().substring(0, 1).toUpperCase(), 42, new Color(235, 231, 255), UIConstants.PURPLE), BorderLayout.WEST);
        account.add(new JLabel("<html><b>" + UIHelper.safe(facade.getMotherProfile().getName()) + "</b><br><span style='color:#888888'>Mother</span></html>"), BorderLayout.CENTER);

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
        button.setFont(UIConstants.FONT_BOLD);
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
                button.setBackground(UIConstants.PURPLE);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(UIConstants.MUTED);
            }
        }
    }

    /*
     * Rebuilds the header and sidebar after mother profile changes.
     * This keeps the dashboard name and sidebar account name updated.
     */
    private void refreshHeaderAndSidebar() {
        if (headerPanel != null) {
            remove(headerPanel);
        }
        if (sidebarPanel != null) {
            remove(sidebarPanel);
        }

        navButtons.clear();
        headerPanel = createHeader();
        sidebarPanel = createSidebar();
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        updateNavStyle();
        revalidate();
        repaint();
    }

    /*
     * Clears the content area before switching pages.
     */
    private void resetContent() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showReports() {

        resetContent();
        activePage = "Reports";
        updateNavStyle();

        RoundedPanel c = UIHelper.createCard("Reports");

        JTextField field = new JTextField();
        field.setFont(UIConstants.FONT);
        field.setBorder(new EmptyBorder(12, 14, 12, 14));
        field.setToolTipText("Search reports");

        JComboBox<String> searchType = new JComboBox<>(new String[]{
            "All", "Content", "Author", "Role", "Child Name"
        });

        searchType.setFont(UIConstants.FONT);
        searchType.setPreferredSize(new Dimension(170, 44));

        JButton search = actionButton("Search");
        search.setPreferredSize(new Dimension(120, 44));

        JPanel filterPanel = new JPanel(new BorderLayout(8, 4));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Filter");
        filterLabel.setFont(UIConstants.SMALL);
        filterLabel.setForeground(UIConstants.MUTED);

        filterPanel.add(filterLabel, BorderLayout.NORTH);
        filterPanel.add(searchType, BorderLayout.CENTER);

        JPanel searchBar = new JPanel(new BorderLayout(12, 0));
        searchBar.setOpaque(false);
        searchBar.setBorder(new EmptyBorder(4, 4, 14, 4));

        searchBar.add(field, BorderLayout.CENTER);
        searchBar.add(filterPanel, BorderLayout.EAST);
        searchBar.add(search, BorderLayout.WEST);

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 10));
        list.setOpaque(false);

        /*
     * Loads all reports into the dashboard.
         */
        Runnable loadAllReports = () -> {

            list.removeAll();

            for (Report report : facade.getReports()) {

                list.add(
                        UIHelper.rowCard(
                                report.toString(),
                                UIConstants.BLUE
                        )
                );
            }

            list.revalidate();
            list.repaint();
        };

        loadAllReports.run();

        /*
     * Strategy Pattern:
     * The selected search strategy changes dynamically
     * depending on the selected filter type.
         */
        search.addActionListener(e -> {

            list.removeAll();

            String keyword = field.getText().trim();

            if (keyword.isEmpty()) {

                loadAllReports.run();
                return;
            }

            SearchStrategy strategy;

            switch (searchType.getSelectedItem().toString()) {

                case "Content":
                    strategy = new SearchByContent();
                    break;

                case "Author":
                    strategy = new SearchByAuthor();
                    break;

                case "Role":
                    strategy = new SearchByType();
                    break;

                case "Child Name":
                    strategy = new SearchByChildName();
                    break;

                default:
                    strategy = new SearchAll();
                    break;
            }

            SearchContext context = new SearchContext(strategy);

            java.util.List<Report> matches
                    = facade.searchReports(context, keyword);

            if (matches.isEmpty()) {

                list.add(
                        UIHelper.rowCard(
                                "No matching reports were found.",
                                UIConstants.PINK
                        )
                );

            } else {

                for (Report report : matches) {

                    list.add(
                            UIHelper.rowCard(
                                    report.toString(),
                                    UIConstants.BLUE
                            )
                    );
                }
            }

            list.revalidate();
            list.repaint();
        });

        /*
     * Allows pressing Enter inside the search field
     * to trigger the search button automatically.
         */
        field.addActionListener(e -> search.doClick());

        JButton add = actionButton("Add Report");

        add.addActionListener(e -> addReport());

        c.add(searchBar, BorderLayout.NORTH);
        c.add(new JScrollPane(list), BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);

        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showCommunication() {
        resetContent();
        activePage = "Communication";
        updateNavStyle();

        RoundedPanel c = UIHelper.createCard("Communication");

        JPanel chat = new JPanel(new GridLayout(5, 1, 0, 10));
        chat.setOpaque(false);

        chat.add(UIHelper.rowCard("Dr. Sarah: Please review the latest speech session report.", UIConstants.PURPLE));
        chat.add(UIHelper.rowCard("Amani: There is clear improvement in pronunciation.", UIConstants.BLUE));
        chat.add(UIHelper.rowCard("Parent: The home exercises were completed today.", UIConstants.PINK));
        chat.add(UIHelper.rowCard("Reem: Classroom participation improved this week.", UIConstants.GREEN));
        chat.add(UIHelper.rowCard("Mother: A new message can be added here.", UIConstants.ORANGE));

        c.add(chat, BorderLayout.CENTER);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showNotifications() {
        resetContent();
        activePage = "Notifications";
        updateNavStyle();

        RoundedPanel c = UIHelper.createCard("Notifications");
        JList<String> list = notificationPanel.getNotificationList();

        list.setFont(UIConstants.FONT);
        list.setBorder(new EmptyBorder(12, 12, 12, 12));
        c.add(new JScrollPane(list), BorderLayout.CENTER);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private void showCareTeam() {
        resetContent();
        activePage = "Care Team";
        updateNavStyle();

        RoundedPanel c = UIHelper.createCard("Care Team");

        JPanel team = new JPanel(new GridLayout(0, 2, 14, 14));
        team.setOpaque(false);

        for (CareTeamMember member : facade.getCareTeam()) {
            String letter = member.getName().isEmpty() ? "?" : member.getName().substring(0, 1).toUpperCase();
            team.add(bigMember(letter, member, UIConstants.PURPLE));
        }

        JButton add = actionButton("Add Care Team Member");
        add.addActionListener(e -> addCareTeamMember());

        c.add(new JScrollPane(team), BorderLayout.CENTER);
        c.add(add, BorderLayout.SOUTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    private JPanel bigMember(String letter, CareTeamMember member, Color color) {
        RoundedPanel box = new RoundedPanel(22, UIConstants.SOFT_PANEL, false);
        box.setLayout(new BorderLayout(0, 12));
        box.setBorder(new EmptyBorder(18, 14, 18, 14));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(new Avatar(letter, 54, new Color(240, 235, 255), color), BorderLayout.WEST);
        top.add(new JLabel("<html><b>" + UIHelper.safe(member.getName()) + "</b><br>" + UIHelper.safe(member.getRole()) + "<br><span style='color:#777777'>" + UIHelper.safe(member.getPhone()) + "</span></html>"), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 0));
        buttons.setOpaque(false);
        JButton request = actionButton("Request Appointment");
        request.addActionListener(e -> requestAppointmentFor(member));
        JButton chat = actionButton("Chat");
        chat.addActionListener(e -> startChatWith(member));
        buttons.add(request);
        buttons.add(chat);

        box.add(top, BorderLayout.CENTER);
        box.add(buttons, BorderLayout.SOUTH);
        return box;
    }

    private void showProfile() {
        resetContent();
        activePage = "Profile";
        updateNavStyle();

        RoundedPanel c = UIHelper.createCard("Profile");

        JPanel body = new JPanel(new GridLayout(0, 1, 0, 12));
        body.setOpaque(false);

        MotherProfile mother = facade.getMotherProfile();
        body.add(UIHelper.rowCard("Mother Profile\n" + mother.toString(), UIConstants.PURPLE));

        body.add(UIHelper.rowCard("Children Profiles", UIConstants.BLUE));
        for (ChildProfile child : facade.getChildren()) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);
            row.add(UIHelper.rowCard(child.toString(), UIConstants.PINK), BorderLayout.CENTER);
            JButton edit = actionButton("Edit Child");
            edit.addActionListener(e -> editChild(child));
            row.add(edit, BorderLayout.EAST);
            body.add(row);
        }

        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setOpaque(false);
        JButton editMother = actionButton("Edit Mother Profile");
        editMother.addActionListener(e -> editMotherProfile());
        JButton addChild = actionButton("Add Another Child");
        addChild.addActionListener(e -> addChild());
        buttons.add(editMother);
        buttons.add(addChild);

        c.add(new JScrollPane(body), BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);
        contentPanel.add(c, BorderLayout.CENTER);
    }

    /*
     * Adds a child profile through the Facade.
     */
    private void addChild() {
        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JTextField condition = new JTextField();
        JTextField guardian = new JTextField(facade.getMotherProfile().getName());
        JTextField progress = new JTextField("75");
        JTextArea information = new JTextArea(4, 24);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Child name", "Age", "Condition", "Guardian", "Progress percentage", "Child information"},
                new JComponent[]{name, age, condition, guardian, progress, new JScrollPane(information)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Child", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                facade.addChild(
                        name.getText(),
                        Integer.parseInt(age.getText().trim()),
                        condition.getText(),
                        guardian.getText(),
                        Integer.parseInt(progress.getText().trim()),
                        information.getText()
                );
                showProfile();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age and progress must be valid numbers.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void editChild(ChildProfile child) {
        JTextField name = new JTextField(child.getName());
        JTextField age = new JTextField(String.valueOf(child.getAge()));
        JTextField condition = new JTextField(child.getCondition());
        JTextField guardian = new JTextField(child.getGuardianName());
        JTextField progress = new JTextField(String.valueOf(child.getProgressPercentage()));
        JTextArea information = new JTextArea(child.getInformation(), 4, 24);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Child name", "Age", "Condition", "Guardian", "Progress percentage", "Child information"},
                new JComponent[]{name, age, condition, guardian, progress, new JScrollPane(information)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Child", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                facade.updateChild(child, name.getText(), Integer.parseInt(age.getText().trim()), condition.getText(), guardian.getText(), Integer.parseInt(progress.getText().trim()), information.getText());
                showProfile();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age and progress must be valid numbers.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
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

        JTextField filePath = new JTextField();
        filePath.setEditable(false);
        JButton chooseFile = new JButton("Choose File");
        chooseFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                filePath.setText(selectedFile.getAbsolutePath());
            }
        });

        JPanel filePanel = new JPanel(new BorderLayout(8, 0));
        filePanel.add(filePath, BorderLayout.CENTER);
        filePanel.add(chooseFile, BorderLayout.EAST);

        JPanel panel = formPanel(
                new String[]{"Child name", "Report text", "External file"},
                new JComponent[]{childName, new JScrollPane(text), filePanel}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Report", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String finalText = text.getText();
                if (!filePath.getText().trim().isEmpty()) {
                    finalText += "\nAttached file: " + new File(filePath.getText()).getName();
                }
                facade.addReport(currentUser, childName.getText(), finalText);
                showReports();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    /*
     * Adds a future appointment.
     */
 /*
     * Creates an appointment request directly from the selected care team member card.
     * Appointments are now part of Care Team instead of a separate sidebar page.
     */
    private void requestAppointmentFor(CareTeamMember member) {
        JComboBox<String> child = new JComboBox<>();
        for (ChildProfile childProfile : facade.getChildren()) {
            child.addItem(childProfile.getName());
        }
        if (child.getItemCount() == 0) {
            child.addItem("No child added yet");
        }

        JComboBox<String> meetingType = new JComboBox<>(new String[]{"Online", "In-person"});
        JTextField days = new JTextField("1");
        JTextArea reason = new JTextArea(4, 24);
        reason.setLineWrap(true);
        reason.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Child", "Care team member", "Meeting type", "After how many days?", "Reason / comment"},
                new JComponent[]{child, new JLabel(member.getName() + " - " + member.getRole()), meetingType, days, new JScrollPane(reason)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Request Appointment", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                if (child.getSelectedItem() == null || child.getSelectedItem().toString().startsWith("No child")) {
                    throw new IllegalArgumentException("Please add a child profile first.");
                }
                String comment = reason.getText().trim();
                if (comment.isEmpty()) {
                    throw new IllegalArgumentException("Please write the reason for the meeting.");
                }

                String title = "Appointment Request - Pending | Child: " + child.getSelectedItem()
                        + " | Type: " + meetingType.getSelectedItem()
                        + " | Comment: " + comment;

                facade.addAppointment(
                        title,
                        member.getName() + " - " + member.getRole(),
                        LocalDateTime.now().plusDays(Integer.parseInt(days.getText().trim()))
                );

                JOptionPane.showMessageDialog(this, "Appointment request sent to " + member.getName() + ".");
                showCareTeam();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Days must be a valid number.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void startChatWith(CareTeamMember member) {
        JTextArea message = new JTextArea(5, 24);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"To", "Message"},
                new JComponent[]{new JLabel(member.getName() + " - " + member.getRole()), new JScrollPane(message)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Chat with " + member.getName(), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (message.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please write a message first.");
            } else {
                JOptionPane.showMessageDialog(this, "Message sent to " + member.getName() + ".");
            }
        }
    }

    private void editMotherProfile() {
        MotherProfile mother = facade.getMotherProfile();
        JTextField name = new JTextField(mother.getName());
        JTextField phone = new JTextField(mother.getPhone());
        JTextField email = new JTextField(mother.getEmail());
        JTextArea information = new JTextArea(mother.getInformation(), 4, 24);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Mother name", "Phone number", "Email", "Mother information"},
                new JComponent[]{name, phone, email, new JScrollPane(information)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Mother Profile", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                facade.updateMotherProfile(name.getText(), phone.getText(), email.getText(), information.getText());
                currentUser = UserFactory.forRole("Parent").createUser(name.getText().trim());
                refreshHeaderAndSidebar();
                showProfile();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void addCareTeamMember() {
        JTextField name = new JTextField();
        JComboBox<String> role = new JComboBox<>(new String[]{
            "Doctor", "Speech Therapist", "Father", "Teacher", "Shadow Teacher", "Other"
        });
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JTextArea notes = new JTextArea(4, 24);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);

        JPanel panel = formPanel(
                new String[]{"Name", "Role", "Phone", "Email", "Notes"},
                new JComponent[]{name, role, phone, email, new JScrollPane(notes)}
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Care Team Member", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                facade.addCareTeamMember(name.getText(), role.getSelectedItem().toString(), phone.getText(), email.getText(), notes.getText());
                showCareTeam();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    /*
     * Creates a simple vertical form.
     */
    private JPanel formPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            JLabel label = new JLabel(labels[i] + ":");
            label.setPreferredSize(new Dimension(150, 26));
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            JComponent field = fields[i];
            field.setPreferredSize(new Dimension(260, field instanceof JScrollPane ? 70 : 30));
            panel.add(field, gbc);
        }

        return panel;
    }

    /*
     * Creates a consistent rounded action button.
     */
    private JButton actionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(UIConstants.PURPLE);
        button.setForeground(Color.WHITE);
        button.setFont(UIConstants.FONT_BOLD);
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Main method used to start and test the program
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

}
