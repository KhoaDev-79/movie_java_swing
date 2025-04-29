package service;

import java.util.List;
import dao.TicketDao;
import model.Ticket;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    // Lấy danh sách vé theo ID suất chiếu
    public List<Ticket> getTicketsByShowtimeID(int showtimeID) {
        return ticketDao.getTicketsByShowtimeID(showtimeID);
    }
}
