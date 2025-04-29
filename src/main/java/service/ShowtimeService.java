package service;

import java.util.List;
import dao.ShowtimeDao;
import model.Showtime;

public class ShowtimeService {
    private ShowtimeDao showtimeDao;

    public ShowtimeService() {
        this.showtimeDao = new ShowtimeDao();
    }

    // Thêm xuất chiếu mới
    public boolean addShowtime(Showtime showtime) {
        return showtimeDao.addShowtime(showtime);
    }

    // Sửa thông tin xuất chiếu
    public boolean updateShowtime(Showtime showtime) {
        return showtimeDao.updateShowtime(showtime);
    }

    // Xóa xuất chiếu
    public boolean deleteShowtime(int showtimeID) {
        return showtimeDao.deleteShowtime(showtimeID);
    }

    // Lấy danh sách xuất chiếu
    public List<Showtime> getAllShowtimes() {
        return showtimeDao.getAllShowtimes();
    }
    
    // Lấy danh sách xuất chiếu theo phim
    public List<Showtime> getShowtimesByMovie(int movieID) {
        return showtimeDao.getShowtimesByMovie(movieID);
    }
}
