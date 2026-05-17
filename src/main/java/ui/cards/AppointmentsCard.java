package ui.cards;

import facade.CarePlatformFacade;
import model.Appointment;
import ui.components.RoundedPanel;
import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
/*
 * Dashboard card that displays
 * the latest scheduled appointments.
 */
public class AppointmentsCard extends RoundedPanel {

    public AppointmentsCard(CarePlatformFacade facade) {
        super(22, Color.WHITE, true);
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(12, 14, 12, 14));
        add(UIHelper.sectionTitle("Upcoming Appointments"), BorderLayout.NORTH);

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 8));
        list.setOpaque(false);

        List<Appointment> appointments = facade.getAppointments();
        int shown = 0;
        for (int i = appointments.size() - 1; i >= 0 && shown < 2; i--, shown++) {
            list.add(UIHelper.rowCard(appointments.get(i).toString(), UIConstants.PURPLE));
        }
        if (appointments.isEmpty()) {
            list.add(UIHelper.rowCard("No upcoming appointments.", UIConstants.PURPLE));
        }

        add(list, BorderLayout.CENTER);
    }
}
