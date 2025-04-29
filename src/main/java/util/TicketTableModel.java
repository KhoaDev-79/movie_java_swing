package util;

import javax.swing.table.AbstractTableModel;
import model.Ticket;
import java.util.List;

public class TicketTableModel extends AbstractTableModel {
    private List<Ticket> ticketList;
    private final String[] columnNames = {"ID", "Showtime ID", "Seat ID", "Price", "Booking Time"};

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public int getRowCount() {
        return ticketList != null ? ticketList.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (ticketList == null || ticketList.isEmpty()) {
            return null;
        }

        Ticket ticket = ticketList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return ticket.getTicketID();
            case 1:
                return ticket.getShowtimeID();
            case 2:
                return ticket.getSeatID();
            case 3:
                return ticket.getPrice();
            case 4:
                return ticket.getBookingTime();
            default:
                return null;
        }
    }
}

