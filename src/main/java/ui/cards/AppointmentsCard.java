package ui.cards;

import facade.CarePlatformFacade;
import model.Appointment;
import ui.components.RoundedPanel;
import ui.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppointmentsCard extends RoundedPanel {

    private static final Color PURPLE
            = new Color(94, 75, 166);

    public AppointmentsCard(
            CarePlatformFacade facade
    ) {

        super(24, Color.WHITE, true);

        setLayout(new BorderLayout(10, 10));

        add(
                UIHelper.sectionTitle(
                        "Upcoming Appointments"
                ),
                BorderLayout.NORTH
        );

        JPanel list
                = new JPanel(
                        new GridLayout(3, 1, 0, 10)
                );

        list.setOpaque(false);

        List<Appointment> appointments
                = facade.getAppointments();

        for (int i = appointments.size() - 1;
                i >= Math.max(0, appointments.size() - 3);
                i--) {

            list.add(
                    UIHelper.rowCard(
                            appointments
                                    .get(i)
                                    .toString(),
                            PURPLE
                    )
            );
        }

        add(list, BorderLayout.CENTER);
    }
}
