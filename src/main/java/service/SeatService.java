package service;

import java.util.List;
import dao.SeatDao;
import model.Seat;

public class SeatService {
    private SeatDao seatDao;

    public SeatService() {
        seatDao = new SeatDao();
    }

    // Thêm ghế ngồi
    public boolean addSeat(Seat seat) {
        return seatDao.addSeat(seat);
    }

    // Sửa thông tin ghế ngồi
    public boolean updateSeat(Seat seat) {
        return seatDao.updateSeat(seat);
    }

    // Xóa ghế ngồi
    public boolean deleteSeat(int seatID) {
        return seatDao.deleteSeat(seatID);
    }

    // Lấy danh sách ghế ngồi
    public List<Seat> getAllSeats() {
        return seatDao.getAllSeats();
    }
    
 // Lấy danh sách ghế theo ID phòng
    public List<Seat> getSeatsByTheatreID(int theatreID) {
        return seatDao.getSeatsByTheatreID(theatreID);
    }
}
